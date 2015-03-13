package no.moller.cmpmigrator;

import static org.joox.JOOX.$;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
import org.xml.sax.SAXException;

public class XMLFieldFetcher {
	/**
	 *  Parse xmi-file to match class/method/params and return the where-clause.
	 *
	 * @param docAsString
	 * @param className
	 * @param name
	 * @param params
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
    public final static String retrieveWhereStatement(final String docAsString,
            final String className,
            final String name,
            final String params) throws SAXException, IOException {

        return $(docAsString).find("finderDescriptors")
                    		 .filter(ctx -> $(ctx).child().attr("name").equalsIgnoreCase(name))
                    		 .filter(ctx -> $(ctx).child().attr("parms").trim().equalsIgnoreCase(params))
                    		 .filter(ctx -> $(ctx).child().child().attr("href").split("#")[1].equalsIgnoreCase(className))
                    		 .attr("whereClause");
	}

    public static Collection<String> retrieveFields(String ejbjarDocAsString, String className) {
        return $(ejbjarDocAsString).find("entity")
                .filter(ctx -> $(ctx).attr("id").equals(className))
                .find("cmp-field")
                .find("field-name")
                .contents();
    }
}

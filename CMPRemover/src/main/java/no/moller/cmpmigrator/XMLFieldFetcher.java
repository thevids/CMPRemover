package no.moller.cmpmigrator;

import static org.joox.JOOX.$;

import java.io.IOException;
import java.util.Collection;

import org.xml.sax.SAXException;

public class XMLFieldFetcher {
    /**
     *  Parse xmi-file to match class/method/params and return the where-clause.
     *
     * @param xmiDocAsString
     * @param className
     * @param name
     * @param params
     * @return
     * @throws SAXException
     * @throws IOException
     */
    public final static String retrieveWhereStatement(final String xmiDocAsString,
            final String className,
            final String name,
            final String params) throws SAXException, IOException {

        return $(xmiDocAsString).find("finderDescriptors")
                                .filter(ctx -> $(ctx).child().attr("name").equalsIgnoreCase(name))
                                .filter(ctx -> $(ctx).child().attr("parms").trim().equalsIgnoreCase(params))
                                .filter(ctx -> $(ctx).child().child().attr("href").split("#")[1].equalsIgnoreCase(className))
                                .attr("whereClause");
    }

    public static Collection<String> retrieveFields(String ejbJarDocAsString, String className) {
        return $(ejbJarDocAsString).find("entity")
                                   .filter(ctx -> $(ctx).attr("id").equals(className))
                                   .find("cmp-field")
                                   .find("field-name")
                                   .contents();
    }

    /**
     * For EJB2, parse the ejb-jar.xml for where-statements.
     *
     * @param ejbJarDocAsString ejb-jar.xml in a string
     * @param className The entity classname
     * @param methName Findermethod
     * @return where-statement
     * @throws SAXException
     * @throws IOException
     */
    public static String retrieveWhereForEJB2(String ejbJarDocAsString,
            final String className,
            final String methName) throws SAXException, IOException {
        return $(ejbJarDocAsString).find("entity")
                                   .filter(ctx -> $(ctx).attr("id").equals(className))
                                   .find("query")
                                   .filter(ctx -> $(ctx).child("query-method").child("method-name").content().equalsIgnoreCase(methName) )
                                   .child("ejb-ql")
                                   .content();
    }

    public static boolean isCMP2(String ejbJarDocAsString, String className) {
        String ver = $(ejbJarDocAsString).find("entity")
                .filter(ctx -> $(ctx).attr("id").equals(className))
                .child("cmp-version")
                .content();

        if (ver == null) { return false; }

        return ver.startsWith("2.");
    }
}

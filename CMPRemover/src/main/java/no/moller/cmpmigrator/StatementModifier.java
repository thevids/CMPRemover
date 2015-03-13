package no.moller.cmpmigrator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

public class StatementModifier {
    /**
     * Takes a list of methodparams and a wherestatement with ? and makes a named query string.
     *
     * @param where Where-statement with ?
     * @param methodParams the methods parameters
     * @return query with named params in stead of anonymous ?
     */
    final public static String makeNamedParamWhereStatement(String where, List<ParameterSource<JavaInterfaceSource>> methodParams) {

        // Putting method params in a list of strings, easier to unit-test and work with
        final List<String> paramStrs = methodParams.stream().map(p -> p.getName()).collect(Collectors.toList());

        return recursiveReplaceNamedParams(where, paramStrs.iterator());
    }

    public static String recursiveReplaceNamedParams(String where, Iterator<String> iterator) {
        if(iterator.hasNext()) {
            String mParam = iterator.next();
            return recursiveReplaceNamedParams(where.replaceFirst("\\?", ":" + mParam.toLowerCase()), iterator);
        } else {
            return where;
        }
    }

    /**
     * Puts together the start of a select statement based on fields in ejb-jar.xml.
     *
     * @param className The class to find fields for in ejb-jar.xml
     * @param fieldNames fieldnames from the ejb-jar.xml
     * @return select-statement
     */
    public static String makeSelectStatement(final String className, Collection<String> fieldNames) {

        String fieldNamesCommaSeperated = fieldNames
                               .stream().collect(Collectors.joining(", ")); // All fields joined with comma in between

        return "public final static String SELECT = \"select "
                    + fieldNamesCommaSeperated
                    + " from " + className + " \"";
    }
}

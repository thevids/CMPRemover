package no.moller.cmpmigrator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

public class StatementModifier {
    final public static String makeNamedParamWhereStatement(String where, List<ParameterSource<JavaInterfaceSource>> methodParams) {

        final ArrayList<String> paramStrs = new ArrayList<String>(methodParams.size());

        // Putting method params in a list of strings, easier to unit-test and work with
        methodParams.forEach(p -> paramStrs.add(p.getName()));

        Iterator<String> iterator = paramStrs.iterator();
        return recursiveReplaceNamedParams(where, iterator);
    }

    public static String recursiveReplaceNamedParams(String where, Iterator<String> iterator) {
        if(iterator.hasNext()) {
            String mParam = iterator.next();
            return recursiveReplaceNamedParams(where.replaceFirst("\\?", ":" + mParam.toLowerCase()), iterator);
        } else {
            return where;
        }
    }
}

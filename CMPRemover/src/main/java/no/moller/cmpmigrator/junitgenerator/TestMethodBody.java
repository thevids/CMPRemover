package no.moller.cmpmigrator.junitgenerator;

import java.util.Collection;
import java.util.stream.Collectors;

import no.moller.cmpmigrator.XMLFieldFetcher;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class TestMethodBody {
    private static String enlargeFirstLetter(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /** Makes a getter-call out of a fieldname. */
    static String gettify(String field, String className) {
        String get = "get" + enlargeFirstLetter(field);
        return className.toLowerCase() + "." + get + "()";
    }

    /** Makes a getter-call out of a fieldname. */
    static String settify(String field, String className) {
        String set = "set" + enlargeFirstLetter(field);
        return className + "." + set;
    }


    public static String makeMapRow(String className, String ejbjarDocAsString, JavaClassSource bean,
                                    JavaClassSource key) {

        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);

        final String dom = className + "Dom";
        final String data = className + "Data";
        final String pk = className + "Key";

        StringBuilder str = new StringBuilder();
        str.append(dom + " dom = new " + dom + "();\n");

        // Primary-key-fields will be retrieved into a PrimaryKey object
        str.append("final " + pk + " pk = new " + pk)
           .append("(")
           .append(key.getFields().stream()
                                   .filter(f -> !f.getName().equalsIgnoreCase("serialVersionUid"))
                                   .map(f -> fieldRetriverPK(f, key) )
                                   .collect(Collectors.joining(", ")) )
           .append(");\n");

        String objToSet = "data";

        str.append(data + " data = new " + data + "();\n");
        //str.append("data.setPrimaryKey(pk);\n");

        fields.stream()
            .filter(f -> key.getField(f) == null) // Don't fetch keys here
            .forEach(p -> str.append(settify(p,  objToSet) + "(" + fieldRetriver(p, bean) + ");\n"));

        str.append("data.copyTo(dom);");

        key.getFields().stream()
                   .filter( f -> !f.getName().equalsIgnoreCase("serialVersionUid") )
                   .forEach( f ->
                       str.append(
                               "dom." + "set" + enlargeFirstLetter(f.getName()) + "(" + "pk." + f.getName() + ");"
                       ) );

        for (String p : fields) {
            Type<JavaClassSource> type = bean.getField(p).getType();
            if (type.getName().equalsIgnoreCase("boolean")) {
                str.append("assertTrue(" + p.toUpperCase() + " == " + gettify(p, "dom") + ");\n");
            } else if (type.isPrimitive()){
                if (type.getName().equalsIgnoreCase("double") || type.getName().equalsIgnoreCase("float")) {
                    str.append("assertEquals(" + p.toUpperCase() + ", " + gettify(p, "dom") + ",0);\n");
                } else {
                    str.append("assertEquals(" + p.toUpperCase() + ", " + gettify(p, "dom") + ");\n");
                }
            } else {
                str.append("assertSame(" + p.toUpperCase() + ", " + gettify(p, "dom") + ");\n");
            }
        }

        return str.toString();
    }


    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriver(String field, JavaClassSource bean) {
        String get = "get" + enlargeFirstLetter(field);
        String is = "is" + enlargeFirstLetter(field);

        String getMethod;

        if(bean.hasMethodSignature(get)) {
            getMethod = get;
        } else if(bean.hasMethodSignature(is)) {
            getMethod = is;
        } else {
            return null;
        }

        return makeFieldRetrieverString(field, bean, getMethod);
    }

    private static String makeFieldRetrieverString(String field, JavaClassSource bean,
            String getMethod) {
        return field.toUpperCase();
    }

    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriverPK(FieldSource<JavaClassSource> field, JavaClassSource key) {
        return field.getName().toUpperCase();
    }
}

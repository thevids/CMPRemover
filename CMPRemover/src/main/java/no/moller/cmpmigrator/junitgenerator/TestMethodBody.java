package no.moller.cmpmigrator.junitgenerator;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
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


    public static String makeDataToDom(String className, String ejbjarDocAsString, JavaClassSource bean,
                                    JavaClassSource key) {

        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);

        final String dom = className + "Dom";
        final String data = className + "Data";
        final String pk = className + "Key";

        StringBuilder str = new StringBuilder();
        str.append(dom + " dom = new " + dom + "();\n");

        // Primary-key-fields will be set into a PrimaryKey object
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

        // Call setters
        int countFieldsExceptKeys = callSetters(bean, key, fields, str, objToSet);

        str.append("data.copyTo(dom);");

        key.getFields().stream()
                   .filter( f -> !f.getName().equalsIgnoreCase("serialVersionUid") )
                   .forEach( f ->
                       str.append(
                               "dom." + "set" + enlargeFirstLetter(f.getName()) + "(" + "pk." + f.getName() + ");"
                       ) );

        makeAsserts(bean, fields, str, "dom");

        str.append("Map<String, Object> changedFieldsMap = data.getChangedFieldsMap();");
        str.append("assertEquals(" + countFieldsExceptKeys + ", changedFieldsMap.size());");

        key.getFields().stream()
        .filter(f -> !f.getName().equalsIgnoreCase("serialVersionUid"))
        .forEach(f -> str.append("assertTrue(dom.toString().contains(pk." + f.getName() +"));"));

        return str.toString();
    }

    public static String makeDomToData(String className,
            String ejbjarDocAsString, JavaClassSource bean, JavaClassSource key) {

        Collection<String> fields = XMLFieldFetcher.retrieveFields(
                ejbjarDocAsString, className);

        final String dom = className + "Dom";
        final String data = className + "Data";
        final String pk = className + "Key";

        StringBuilder str = new StringBuilder();
        str.append(dom + " dom = new " + dom + "();\n");

        // Primary-key-fields will be set into a PrimaryKey object
        str.append("final " + pk + " pk = new " + pk)
                .append("(")
                .append(key
                        .getFields()
                        .stream()
                        .filter(f -> !f.getName().equalsIgnoreCase(
                                "serialVersionUid"))
                        .map(f -> fieldRetriverPK(f, key))
                        .collect(Collectors.joining(", "))).append(");\n");

        String objToSet = "dom";

        // str.append("data.setPrimaryKey(pk);\n");

        // Call setters
        callSetters(bean, key, fields, str, objToSet);


        str.append(data + " data = new " + data + "(dom);\n");
        makeAsserts(bean, fields, str, "data");

        str.append("Map<String, Object> changedFieldsMap = data.getChangedFieldsMap();");
        str.append("assertTrue(changedFieldsMap.isEmpty());");

        return str.toString();
    }

    private static int callSetters(JavaClassSource bean, JavaClassSource key,
            Collection<String> fields, StringBuilder str, String objToSet) {
        AtomicInteger count = new AtomicInteger(0);
        fields.stream()
            .filter(f -> key.getField(f) == null) // Don't fetch keys here
            .forEach(p -> {
                str.append(settify(p,  objToSet) + "(" + fieldRetriver(p, bean) + ");\n");
                count.incrementAndGet();
            });
        return count.get();
    }

    private static void makeAsserts(JavaClassSource bean,
            Collection<String> fields, StringBuilder str, String objToGetFrom) {
        for (String p : fields) {
            Type<JavaClassSource> type = bean.getField(p).getType();
            if (type.getName().equalsIgnoreCase("boolean")) {
                str.append("assertTrue(" + p.toUpperCase() + " == " + gettify(p, objToGetFrom) + ");\n");
            } else if (type.isPrimitive()){
                if (type.getName().equalsIgnoreCase("double") || type.getName().equalsIgnoreCase("float")) {
                    str.append("assertEquals(" + p.toUpperCase() + ", " + gettify(p, objToGetFrom) + ",0);\n");
                } else {
                    str.append("assertEquals(" + p.toUpperCase() + ", " + gettify(p, objToGetFrom) + ");\n");
                }
            } else {
                str.append("assertSame(" + p.toUpperCase() + ", " + gettify(p, objToGetFrom) + ");\n");
            }
        }
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

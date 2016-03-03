package no.moller.cmpmigrator;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class RowMapperMethodBody {

    private static String enlargeFirsLetter(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /** Makes a getter-call out of a fieldname. */
    static String gettify(String field, String className) {
        String get = "get" + enlargeFirsLetter(field);
        return className.toLowerCase() + "." + get + "()";
    }

    /** Makes a getter-call out of a fieldname. */
    static String settify(String field, String className) {
        String set = "set" + enlargeFirsLetter(field);
        return className + "." + set;
    }


    public static String makeMapRow(String className, String ejbjarDocAsString, JavaClassSource bean,
                                    JavaClassSource key, boolean isCMP2) {

        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);

        final String dom = className + "Dom";
        final String data = className + "Data";
        final String pk = className + "Key";

        StringBuilder str = new StringBuilder();
        str.append(dom + " domObject = new " + dom + "();\n");

        // Primary-key-fields will be retrieved into a PrimaryKey object
        str.append("final " + pk + " pk = new " + pk)
           .append("(")
           .append(key.getFields().stream()
                                   .filter(f -> !f.getName().equalsIgnoreCase("serialVersionUid"))
                                   .map(f -> fieldRetriverPK(f, key) )
                                   .collect(Collectors.joining(", ")) )
           .append(");\n");

        String objToSet = isCMP2 ? "domObject" : "data";

        if(!isCMP2) {
            str.append(data + " data = new " + data + "();\n");
            str.append("data.setPrimaryKey(pk);\n");
        }

        fields.stream()
            .filter(f -> key.getField(f) == null) // Don't fetch keys here
            .forEach(p -> str.append(settify(p,  objToSet) + "(" + fieldRetriver(p, bean) + ");\n"));

        if(!isCMP2) { str.append("data.copyTo(domObject);"); }

        key.getFields().stream()
                   .filter( f -> !f.getName().equalsIgnoreCase("serialVersionUid") )
                   .forEach( f ->
                       str.append(
                               "domObject." + "set" + enlargeFirsLetter(f.getName()) + "(" + "pk." + f.getName() + ");"
                       ) );

        str.append("\nreturn domObject;");

        return str.toString();
    }


    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriver(String field, JavaClassSource bean) {
        String get = "get" + enlargeFirsLetter(field);
        String is = "is" + enlargeFirsLetter(field);

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
        boolean doTrim = false;
        Type<JavaClassSource> returnType = bean.getMethod(getMethod).getReturnType();
        if(returnType.isType(String.class)) {
            doTrim = true; // Strings need trimming
        }
        String rsGetType = returnTypesMightBeArrayOrInt(returnType);

        return trimStart(doTrim) +
                "rs.get" +
                removePath(rsGetType) + "(" +
                "\"" +
                              field.toUpperCase() +"\")" +
                trimEnd(doTrim);
    }

    private static String returnTypesMightBeArrayOrInt(Type<JavaClassSource> returnType) {
        if (returnType.getName().contains("[]")) {
            if (returnType.getName().startsWith("byte")) {
                return returnType.getQualifiedName() + "s";
            }
            return "array";
        }
        if (returnType.isType("java.lang.Integer")) {
            return "int"; // Resultset has method for "getInt", but not "getInteger"
        }
        return returnType.getName();
    }

    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriverPK(FieldSource<JavaClassSource> field, JavaClassSource key) {
//        String get = "get" + enlargeFirsLetter(field);
        boolean doTrim = false;
        Type<JavaClassSource> returnType = field.getType();
        if(returnType.isType(String.class)) {
            doTrim = true; // Strings need trimming
        }

        String retType = returnTypesMightBeArrayOrInt(returnType);

        return trimStart(doTrim) +
                "rs.get" +
                removePath(retType) + "(" +
                "\"" +
                field.getName().toUpperCase() +"\")" +
                trimEnd(doTrim);
    }

    private static String trimStart(boolean doTrim) {
        return doTrim ? "trim(": "";
    }

    private static String trimEnd(boolean doTrim) {
        return doTrim ? ")" : "";
    }


    /** Remove all package-path before type-name. */
    private static String removePath(String retType) {
        if(retType.lastIndexOf('.') < 0 ) {
            return enlargeFirsLetter(retType);
        }
        return enlargeFirsLetter(retType.substring(1 + retType.lastIndexOf('.')));
    }
}

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
        return className.toLowerCase() + "." + set;
    }


    public static String makeMapRow(String className, String ejbjarDocAsString, JavaClassSource bean, JavaClassSource key) {
        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);

        final String dom = className + "Dom";
        final String data = className + "Data";
        final String pk = className + "Key";

        StringBuilder str = new StringBuilder();
        str.append(data + " data = new " + data + "();\n");

        // Primary-key-fields will be retrieved into a PrimaryKey object
        str.append("final " + pk + " pk = new " + pk)
           .append("(")
           .append(key.getFields().stream()
                                   .map((f -> fieldRetriverPK(f, key)) )
                                   .collect(Collectors.joining(", ")) )
           .append(");\n");

        str.append("data.setPrimaryKey(pk);\n");

        fields.stream()
            .forEach(p -> str.append(settify(p,  "data") + "(" + fieldRetriver(p, bean) + ");\n"));

        str.append(dom + " domOjbect = new " + dom + "(pk);\n")
           .append("data.copyTo(domOjbect);\nreturn domOjbect;");

        return str.toString();
    }


    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriver(String field, JavaClassSource bean) {
        String get = "get" + enlargeFirsLetter(field);
        if(bean.hasMethodSignature(get)) {
            boolean doTrim = false;
            Type<JavaClassSource> returnType = bean.getMethod(get).getReturnType();
            if(returnType.isType(String.class)) {
                doTrim = true; // Strings need trimming
            }
            String retType = returnType.getName();
            return "rs.get" +
                    removePath(retType) + "(" +
                    (doTrim ? "trim(": "") + "\"" +
                    field.toUpperCase() +"\")" +
                    (doTrim ? ")": "");
        }
        return null;
    }

    /** Making a resultset retrieve-command for the correct type of field. */
    private static String fieldRetriverPK(FieldSource<JavaClassSource> field, JavaClassSource key) {
//        String get = "get" + enlargeFirsLetter(field);
        boolean doTrim = false;
        Type<JavaClassSource> returnType = field.getType();
        if(returnType.isType(String.class)) {
            doTrim = true; // Strings need trimming
        }
        String retType = returnType.getName();
        return "rs.get" +
                removePath(retType) + "(" +
                (doTrim ? "trim(": "") + "\"" +
                field.getName().toUpperCase() +"\")" +
                (doTrim ? ")": "");
    }

    /** Remove all package-path before type-name. */
    private static String removePath(String retType) {
        if(retType.lastIndexOf('.') < 0 ) {
            return enlargeFirsLetter(retType);
        }
        return enlargeFirsLetter(retType.substring(1 + retType.lastIndexOf('.')));
    }
}

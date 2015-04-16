package no.moller.cmpmigrator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.xml.sax.SAXException;

public class DaoMethodBody {
    /** For methods we do not support, generate body with this throw. */
    private static final String THROW_UNSUPPORTED =
            "throw new java.lang.UnsupportedOperationException(\"Not yet implemented\");\n"
            + "/* TODO: Empty method, needs to be written by hand.*/ \n";

    public static String makeMethodBodyFinder(final String className,
            String docAsString, MethodSource<JavaInterfaceSource> met)
            throws SAXException, IOException {

        // Put parameters in a string equal to that in the xmi-file
        String paramsAsString = met.getParameters().stream()
                                   .map(p -> p.getType().toString()) // java-type of param
                                   .collect(Collectors.joining(" ")); // joined seperated by a space

        // Find where-statement in xmi-file
        String whereStatement = XMLFieldFetcher.retrieveWhereStatement(docAsString,
                                    className, met.getName(), paramsAsString.trim());

        if(whereStatement == null || whereStatement.trim().isEmpty()) {
            return THROW_UNSUPPORTED;
        }

        // We like to use named parameters (not the anonym '?' that is default
        String namedParamWhereStatement =
                StatementModifier.makeNamedParamWhereStatement(whereStatement, met.getParameters());

        final StringBuilder str = new StringBuilder();

        str.append("String whereSQL = \" WHERE " + namedParamWhereStatement + "\";\n\n")
           .append("final MapSqlParameterSource parameters = new MapSqlParameterSource();\n");

        // Method parameters are to be mapped into sql
        met.getParameters().stream().map(p -> p.getName())
                            .forEach(p -> str.append("parameters.addValue(\"" + p.toLowerCase() + "\", " + p + ");\n"));

        str.append("\nreturn SafeReturn.ret("
                + "mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper)"
                + ", " + met.getReturnType() +".class)"
                + ";\n");

        return str.toString();
    }


    public static String makeMethodBodyCreate(String className, String docAsString,
            MethodSource<JavaInterfaceSource> met) {

        StringBuilder str = new StringBuilder();
        str.append("Map<String,Object> parameters = new HashMap<String,Object>();\n");

        // Method parameters are to be mapped into db, simplejdbcinsert takes a hash-map
        met.getParameters().stream().map(p -> p.getName())
            .forEach(p -> str.append("parameters.put(\"" + fieldnameify(p) + "\", " + p + ");\n"));

        String insertException = "throw new SQLException(\"Failure to insert \" + "
                + met.getParameters().stream().map( p -> p.getName() ).collect(Collectors.joining(" + ")) + ");";

        System.err.println(insertException);
        str.append("\nint nr = simpleInsert.execute(parameters);\n")
           .append("if (nr==0) { ").append(insertException).append("}")
           .append("return null;\n"); // We will just smoke out who uses this reference, its detached data now
        return str.toString();
    }

    /* Takes and argument name and extracts the most likely db-field-name. */
    private static String fieldnameify(String p) {
        if(p.startsWith("arg")) {
            return p.substring(3).toUpperCase();
        }
        return p.toUpperCase();
    }


    /**
     * Makes a create method that takes a domain-object and inserts every field.
     *
     * @param className Name of the domain class
     * @param ejbjarDocAsString ejb-jar.xml in a string
     * @param key Primary Key
     * @return body of create method
     */
    public static String makeCreateAll(String className, String ejbjarDocAsString, JavaClassSource key) {
        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);
        List<Object> keyFields = Arrays.asList(key.getFields().stream().map(p -> p.getName()).toArray());

        StringBuilder str = new StringBuilder();
        str.append("Map<String,Object> parameters = new HashMap<String,Object>();\n");

        // Method parameters are to be mapped into db, simplejdbcinsert takes a hash-map
        fields.stream()
            .filter(p -> !keyFields.contains(p))
            .forEach(p -> str.append("parameters.put(\"" + fieldnameify(p) + "\", " + FieldNameTool.gettify(p, className) + ");\n"));

        String insertException = "throw new SQLException(\"Failure to insert \" + "
                + className.toLowerCase().toString() + ");";

        System.err.println(insertException);
        str.append("\nint nr = simpleInsert.execute(parameters);\n")
           .append("if (nr==0) { ").append(insertException).append("}")
           .append("\nreturn true;");

        System.err.println(str.toString());
        return str.toString();
    }


    public static String makeRemoveMethod(String className, JavaClassSource key) {
        // Method parameters are to be mapped into db, simplejdbcinsert takes a hash-map
        String parametersAdding = key.getFields().stream()
            .filter(p -> !p.getName().equalsIgnoreCase("serialVersionUID"))
            .map(p -> "parameters.addValue(\"" + p.getName().toLowerCase() + "\", pk." + p.getName() + ");\n")
            .collect(Collectors.joining());

        return "String sql = \"DELETE FROM MWIN." + fieldnameify(className)
                + " T1 WHERE "
                + namedKeyQuery(key) + "\"; "
                + "final MapSqlParameterSource parameters = new MapSqlParameterSource();"
                + parametersAdding
                + "return mwinNamedTemplate.update(sql, parameters);";
    }


    private static String namedKeyQuery(JavaClassSource key) {
        return key.getFields().stream()
           .filter(p -> !p.getName().equalsIgnoreCase("serialVersionUID"))
           .map(p -> " T1." + p.getName().toUpperCase() + " = :" + p.getName().toLowerCase() + " ")
           .collect(Collectors.joining(" AND "));
    }

    public static String makeFindByPK(String className, JavaClassSource key) {
        // Method parameters are to be mapped into db, simplejdbcinsert takes a hash-map
        String parametersAdding = key.getFields().stream()
            .filter(p -> !p.getName().equalsIgnoreCase("serialVersionUID"))
            .map(p -> "parameters.addValue(\"" + p.getName().toLowerCase() + "\", key." + p.getName() + ");\n")
            .collect(Collectors.joining());

        // TODO: Parametiser navnene på nøkkel, felt og klasse!
        return "String whereSQL = \" "
                + " T1 WHERE "
                + namedKeyQuery(key) + "\";\n"
                + "final MapSqlParameterSource parameters = new MapSqlParameterSource();"
                + parametersAdding
                + "return SafeReturn.ret("
                + "mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper), "
                + className + "Dom.class);";
    }
}

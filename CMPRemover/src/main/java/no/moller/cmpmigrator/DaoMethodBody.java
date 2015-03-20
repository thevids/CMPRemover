package no.moller.cmpmigrator;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

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

        str.append("String whereSQL = \"" + namedParamWhereStatement + "\";\n\n")
           .append("final MapSqlParameterSource parameters = new MapSqlParameterSource();\n");

        // Method parameters are to be mapped into sql
        met.getParameters().stream().map(p -> p.getName())
                            .forEach(p -> str.append("parameters.addValue(\"" + p.toLowerCase() + "\", " + p + ");\n"));

        str.append("\nreturn SafeReturn.ret("
                + "mwinNamedTemplate.query(whereSQL, parameters, mapper)"
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
            .forEach(p -> str.append("parameters.put(\"" + p.toUpperCase() + "\", " + p + ");\n"));

        String insertException = "throw new SQLException(\"Failure to insert \" + "
                + met.getParameters().stream().map( p -> p.getName() ).collect(Collectors.joining(" + ")) + ");";

        System.err.println(insertException);
        str.append("\nint nr = simpleInsert.execute(parameters);\n")
           .append("if (nr==0) { ").append(insertException).append("}")
           .append("return null;\n"); // We will just smoke out who uses this reference, its detached data now
        return str.toString();
    }


    /**
     * Makes a create method that takes a domain-object and inserts every field.
     *
     * @param className Name of the domain class
     * @param ejbjarDocAsString ejb-jar.xml in a string
     * @return body of create method
     */
    public static String makeCreateAll(String className, String ejbjarDocAsString) {
        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);

        StringBuilder str = new StringBuilder();
        str.append("Map<String,Object> parameters = new HashMap<String,Object>();\n");

        // Method parameters are to be mapped into db, simplejdbcinsert takes a hash-map
        fields.stream()
            .forEach(p -> str.append("parameters.put(\"" + p.toUpperCase() + "\", " + gettify(p, className) + ");\n"));

        String insertException = "throw new SQLException(\"Failure to insert \" + "
                + className.toLowerCase().toString() + ");";

        System.err.println(insertException);
        str.append("\nint nr = simpleInsert.execute(parameters);\n")
           .append("if (nr==0) { ").append(insertException).append("}")
           .append("\nreturn true;");

        System.err.println(str.toString());
        return str.toString();
    }

    protected static String enlargeFirsLetter(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /** Makes a getter-call out of a fieldname. */
    protected static String gettify(String field, String className) {
        String get = "get" + enlargeFirsLetter(field);
        return className.toLowerCase() + "." + get + "()";
    }
}

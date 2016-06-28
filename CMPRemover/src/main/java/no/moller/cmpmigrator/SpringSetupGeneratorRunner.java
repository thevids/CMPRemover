package no.moller.cmpmigrator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jboss.forge.roaster.model.Named;
import org.xml.sax.SAXException;

/**
 * Runs the code manipulator using Roaster library.
 * @author k001vfo
 *
 */
public final class SpringSetupGeneratorRunner {
    private static final String FILE_PATH_TO_OLD_CODE =
            "C:/dev/work/workspaceGammel/MollerEVPEJB/src/main/java/no/moller/evp/model/ejb/";
    private static final String FILE_PATH_TO_OLD_XMI =
            "C:/dev/work/workspaceGammel/MollerEVPEJB/src/main/resources/META-INF/";
    private static final String NEW_PACKAGE = "no.moller.evp.model.ejb";
            // In future, use this: "no.moller.evp.model.dao";

    private SpringSetupGeneratorRunner() {}

    /**
     * Main, can be modified to use arguments supplied from command-line,
     * for now though, we use the constants and start this via the IDE.
     *
     * @param args command line arguments
     * @throws SAXException We throw these raw, so you know things have gone wrong
     * @throws IOException We throw these raw, so you know things have gone wrong
     */
    public static void main(final String[] args) throws SAXException, IOException {
        File dir = new File(FILE_PATH_TO_OLD_CODE);

        File[] listFiles = dir.listFiles(f -> f.getName().endsWith("Home.java")
                                              && !f.getName().endsWith("LocalHome.java")
                                              /* && f.getName().startsWith("Etr")*/ );
        int x = 0;
        StringBuffer springxml = new StringBuffer();

        for (File file : listFiles) {
            System.out.println(++x + ": " + file.getName());
            String classToGenerateFor = file.getName().substring(0, file.getName().indexOf("Home.java"));
            generate(classToGenerateFor, springxml);
        }
        writeToFiles(springxml.toString(), "spring-dao.xml");
    }

    private static void generate(String classToGenerateFor, StringBuffer springxml)
            throws IOException, SAXException {
        System.out.println("Start: " + classToGenerateFor);

        makeSimpleInsert(classToGenerateFor, springxml);

        springxml.append("    <bean class=\"")
                 .append(NEW_PACKAGE)
                 .append(".")
                 .append(classToGenerateFor)
                 .append("DaoImpl\">\n")
                 .append("        <property name=\"simpleInsert\" ref=\"simpleJdbcInsert")
                 .append(classToGenerateFor)
                 .append("\" />\n")
                 .append("    </bean>\n\n");

        System.out.println("Done: " + classToGenerateFor);
    }

    private static void makeSimpleInsert(String classToGenerateFor,
            StringBuffer springxml) {
        springxml.append("    <bean id=\"simpleJdbcInsert")
                 .append(classToGenerateFor)
                 .append("\"\n")
                 .append("        class=\"org.springframework.jdbc.core.simple.SimpleJdbcInsert\">\n")
                 .append("        <constructor-arg ref=\"mwinDatasource\" />\n")
                 .append("        <property name=\"schemaName\" value=\"MWIN\" />\n")
                 .append("        <property name=\"tableName\" value=\"")
                 .append(classToGenerateFor.toUpperCase())
                 .append("\"/>\n")
                 .append("    </bean>\n\n");
    }

    private static void writeToFiles(final String source, String name)
            throws IOException {
        System.out.println(source);
        try (PrintWriter prw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(new File(name)))))
        {
            prw.print(source);
        }
    }
}

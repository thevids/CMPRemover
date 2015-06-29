package no.moller.cmpmigrator.junitgenerator;

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
public final class TestGeneratorRunner {
    private static final String FILE_PATH_TO_OLD_CODE =
            "C:/dev/work/workspaceGammel/MollerEVPDomain/src/main/java/no/moller/evp/model/domain/";
    private static final String FILE_PATH_TO_OLD_XMI =
            "./src/test/resources/";
    private static final String NEW_PACKAGE = "no.moller.evp.model.domain";
            // In future, use this: "no.moller.evp.model.dao";

    private TestGeneratorRunner() {}

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

        File[] listFiles = dir.listFiles(f -> f.getName().endsWith("Dom.java")
                                              /* && f.getName().startsWith("Etr")*/ );
        int x = 0;
        for (File file : listFiles) {
            System.out.println(++x + ": " + file.getName());
            String classToGenerateFor = file.getName().substring(0, file.getName().indexOf("Dom.java"));
            generate(classToGenerateFor);
        }

//        String classToGenerateFor = "Appointment";
//        String classToGenerateFor = "Resource";
//        String classToGenerateFor = "EtrReportAdditional";
//        String classToGenerateFor = "Appointmentchanged";
//        generate(classToGenerateFor);
    }

    private static void generate(String classToGenerateFor)
            throws IOException, SAXException {
        System.out.println("Start: " + classToGenerateFor);

        final DomainTestGenerator rmGen = new DomainTestGenerator(FILE_PATH_TO_OLD_CODE,
                NEW_PACKAGE,
                FILE_PATH_TO_OLD_XMI,
                classToGenerateFor);

        writeToFiles(rmGen.getTest());
        System.out.println("Done: " + classToGenerateFor);
    }

    private static void writeToFiles(final Named source)
            throws IOException {
        try (PrintWriter prw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(new File(source.getName() + ".java")))))
        {
            prw.print(source.toString());
        }
    }
}

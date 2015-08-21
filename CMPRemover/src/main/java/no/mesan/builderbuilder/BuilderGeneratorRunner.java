package no.mesan.builderbuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Assert;
import no.mesan.builder.ReallyLargeDomainThing;
import no.mesan.builder.ReallyLargeDomainThingBuilder;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Named;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.xml.sax.SAXException;

/**
 * Runs the code manipulator using Roaster library.
 * @author k001vfo
 *
 */
public final class BuilderGeneratorRunner {
    private static final String FILE_PATH_TO_OLD_CODE =
                    "./src/test/resources/";
    private static final String NEW_PACKAGE =
            "no.mesan.builder";

    private BuilderGeneratorRunner() {}

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

//        File[] listFiles = dir.listFiles(f -> f.getName().endsWith("Home.java")
//                                              && !f.getName().endsWith("LocalHome.java")
//                                              /* && f.getName().startsWith("Etr")*/ );
//        int x = 0;
//        for (File file : listFiles) {
//            System.out.println(++x + ": " + file.getName());
//            String classToGenerateFor = file.getName().substring(0, file.getName().indexOf("Home.java"));
//            generate(classToGenerateFor);
//        }

        String classToGenerateFor = "ReallyLargeDomainThing";
        generate(classToGenerateFor);
    }

    private static void generate(String classToGenerateFor)
            throws IOException, SAXException {
        System.out.println("Start: " + classToGenerateFor);

        final BuilderGenerator rmGen = new BuilderGenerator(FILE_PATH_TO_OLD_CODE,
                NEW_PACKAGE,
                classToGenerateFor);

        int count = 0;
        for(JavaClassSource blr: rmGen.getBuilders()) {
            writeToFiles(blr, count++);
            System.out.println("Done: " + classToGenerateFor);
        }
    }

    private static void writeToFiles(final Named source, int count)
            throws IOException {
        String name = source.getName()
                + ((count == 0) ? "" : "" + count)
                + ".java";

        try (PrintWriter prw = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(new File(name))))) {
            prw.print(Roaster.format(source.toString()));
        }
    }
}

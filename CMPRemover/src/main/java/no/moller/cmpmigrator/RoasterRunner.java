package no.moller.cmpmigrator;

import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * Runs the code manipulator using Roaster library.
 * @author k001vfo
 *
 */
public final class RoasterRunner {
    private static final String FILE_PATH_TO_OLD_CODE =
            "C:/dev/work/workspaceGammel/MollerEVPEJB/src/main/java/no/moller/evp/model/ejb/";
    private static final String FILE_PATH_TO_OLD_XMI =
            "C:/dev/work/workspaceGammel/MollerEVPEJB/src/main/resources/META-INF/";
    private static final String NEW_PACKAGE =
            "no.moller.evp.dao";

    private RoasterRunner() {}

    /**
     * Main, can be modified to use arguments supplied from command-line,
     * for now though, we use the constants and start this via the IDE.
     *
     * @param args command line arguments
     * @throws SAXException We throw these raw, so you know things have gone wrong
     * @throws IOException We throw these raw, so you know things have gone wrong
     */
    public static void main(final String[] args) throws SAXException, IOException {
        final DaoGenerator dgen = new DaoGenerator(FILE_PATH_TO_OLD_CODE,
                                             NEW_PACKAGE,
                                             FILE_PATH_TO_OLD_XMI);

        System.out.println(dgen.getNewDaoInterface());
        System.out.println(dgen.getNewDaoImpl());
    }

}

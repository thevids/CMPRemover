package no.moller.cmpmigrator;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.*;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DaoGeneratorTest {

    private static final String FILE_PATH_TO_OLD_CODE =
            "./src/test/resources/";
    private static final String FILE_PATH_TO_OLD_XMI =
            "./src/test/resources/";
    private static final String NEW_PACKAGE =
            "no.moller.evp.dao";

    @Test
	public void makeDaoTest() throws IOException, SAXException {
        final DaoGenerator dgen = new DaoGenerator(FILE_PATH_TO_OLD_CODE,
                NEW_PACKAGE,
                FILE_PATH_TO_OLD_XMI);

//        System.out.println(dgen.getNewDaoInterface());
        System.out.println(dgen.getNewDaoImpl());

        assertFalse(dgen.getNewDaoInterface().hasInterface("javax.ejb.EJBHome"));

        JavaClassSource impl = dgen.getNewDaoImpl();
        List<MethodSource<JavaClassSource>> methods = impl.getMethods();
        for (MethodSource<JavaClassSource> met : methods) {
			if(met.getName().startsWith("find") && !met.getName().equals("findByPrimaryKey")) {

				assertNotNull(met.getBody());
				System.out.println(met.getBody());
				assertTrue("Body should contain a where-statement", met.getBody().indexOf("whereSQL") > -1);
			} else {
				assertTrue("Shold have UnsupportedOperationException if no where-statement",
						met.getBody().contains("UnsupportedOperationException"));
			}
		}

        assertFalse(dgen.getNewDaoInterface().hasInterface("javax.ejb.EJBHome"));

	}
}


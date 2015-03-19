package no.moller.cmpmigrator;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.*;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.junit.Test;
import org.xml.sax.SAXException;

public class RowMapperGeneratorTest {

    private static final String FILE_PATH_TO_OLD_CODE =
            "./src/test/resources/";
    private static final String FILE_PATH_TO_OLD_XMI =
            "./src/test/resources/";
    private static final String NEW_PACKAGE =
            "no.moller.evp.model.ejb";

    @Test
	public void makeDaoTest() throws IOException, SAXException {
        final RowMapperGenerator rmGen = new RowMapperGenerator(FILE_PATH_TO_OLD_CODE,
                NEW_PACKAGE,
                FILE_PATH_TO_OLD_XMI,
                "Appointment");

        System.out.println(rmGen.getRowMapper());
        System.out.println(rmGen.getDomainObj());

        JavaClassSource impl = rmGen.getRowMapper();
        List<MethodSource<JavaClassSource>> methods = impl.getMethods();
        for (MethodSource<JavaClassSource> met : methods) {
			if(met.getName().startsWith("mapRow")) {
			    assertTrue("Body shold contain set-calls on data", met.getBody().indexOf("data.set") > -1);
                assertTrue("Body shold contain get-calls on rs", met.getBody().indexOf("rs.get") > -1);
			}
		}

        assertFalse(rmGen.getDomainObj().hasInterface("javax.ejb.EJBHome"));

	}
}


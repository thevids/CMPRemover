package no.moller.cmpmigrator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XMLFieldFetcherTest {

	private static final String FILE_PATH_TO_OLD_XMI =
            "./src/test/resources/";
	private String docAsString;
	private String ret;

	@Before
	public void setup() throws IOException {
		docAsString = IOUtils.toString(new File(FILE_PATH_TO_OLD_XMI + "ibm-ejb-jar-ext.xmi").toURI(),
                Charset.forName("ISO-8859-1"));
	}

	@Test
	public void testWhere() throws SAXException, IOException {
		ret = XMLFieldFetcher.retrieveWhereStatement(docAsString, "Appointmentchanged", "findChangedAppointment", "java.lang.String int");
		Assert.assertEquals("T1.FNR = ? AND T1.APPOINTMENTID = ?", ret);
	}
}

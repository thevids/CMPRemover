package no.moller.cmpmigrator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XMLFieldFetcherTest {

	private static final String FILE_PATH_TO_OLD_XMI =
            "./src/test/resources/";
	private String docAsString;
    private String ejbjarDocAsString;

	@Before
	public void setup() throws IOException {
		docAsString = IOUtils.toString(new File(FILE_PATH_TO_OLD_XMI + "ibm-ejb-jar-ext.xmi").toURI(),
                Charset.forName("ISO-8859-1"));
		ejbjarDocAsString = IOUtils.toString(new File(FILE_PATH_TO_OLD_XMI + "ejb-jar.xml").toURI(),
                Charset.forName("ISO-8859-1"));
	}

	@Test
	public void testWhere() throws SAXException, IOException {
		String ret = XMLFieldFetcher.retrieveWhereStatement(docAsString, "Appointmentchanged", "findChangedAppointment", "java.lang.String int");
		assertEquals("T1.FNR = ? AND T1.APPOINTMENTID = ?", ret);
	}

    @Test
    public void testFields() throws SAXException, IOException {
        Collection<String> ret = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, "Appointmentchanged");

        String collect = ret.stream().collect(Collectors.joining(", "));
        System.out.println(collect);

        assertEquals("DateNew, AppointmentID, fnr, DateOld, Action, timeStampCreated, gjgNr, gjgAar, "
                + "OrdrenummerMverk", collect);
    }
}

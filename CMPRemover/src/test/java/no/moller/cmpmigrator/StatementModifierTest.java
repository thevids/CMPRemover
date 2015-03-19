package no.moller.cmpmigrator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

public class StatementModifierTest {

	//@Test
	public void makeNamedParamWhereStatementTest() {
		System.out.println(
				StatementModifier.makeNamedParamWhereStatement("T1.FNR = ? AND T1.EVENTTYPE > 0 AND "
						+ "T1.MAINRESOURCE = ? AND T1.STARTTIME >= ? AND T1.STARTTIME <= ? FOR READ ONLY", null));
	}

	@Test
	public void recursiveNamedParamWhereStatementTest() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("par1");
		arrayList.add("par2");
		arrayList.add("par3");
		arrayList.add("par4");


		String res = StatementModifier.recursiveReplaceNamedParams("T1.FNR = ? AND T1.EVENTTYPE > 0 AND "
				+ "T1.MAINRESOURCE = ? AND T1.STARTTIME >= ? AND T1.STARTTIME <= ? FOR READ ONLY",
				arrayList.iterator());

		assert(res.length()>0);
		Assert.assertEquals("T1.FNR = :par1 AND T1.EVENTTYPE > 0 AND T1.MAINRESOURCE = :par2 AND "
				+ "T1.STARTTIME >= :par3 AND T1.STARTTIME <= :par4 FOR READ ONLY", res);
	}

    @Test
    public void testSelect() throws SAXException, IOException {
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, "DateNew", "AppointmentID", "fnr", "DateOld");

        String ret = StatementModifier.makeSelectStatement("TestClass",list);

        assertEquals("public final static String SELECT = \"select DateNew, AppointmentID, fnr, DateOld from TestClass \"", ret);
    }
}


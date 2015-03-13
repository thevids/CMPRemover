package no.moller.cmpmigrator;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

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
}


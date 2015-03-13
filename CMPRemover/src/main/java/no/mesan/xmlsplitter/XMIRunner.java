package no.mesan.xmlsplitter;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

public class XMIRunner {
	public static void main(String[] args) throws SAXException, IOException {
		$(new File("./src/main/resources/ibm-ejb-jar-ext.xmi")).find("finderDescriptors")
        .each(ctx -> {
			System.out.println(
					"public Object " + $(ctx).child().attr("name") + "(" + 
							nameParams($(ctx).child().attr("parms")) 
							+ ") {\n" +
					"    String whereSQL = \"" +
					$(ctx).attr("whereClause") +
					"\";\n}"

				);
		});
	}
	
	private final static String nameParams(String par) {
		if(par == null || par.trim().isEmpty()) {
			return "";
		}

		StringBuilder ret = new StringBuilder();
		int i = 1;
		
		for (String p : par.split(" ")) {
			ret.append(p).append(" param").append(i++).append(" ");
		};
		return ret.toString();
	}
}

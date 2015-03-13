package no.mesan.xmlsplitter;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import static org.joox.JOOX.*;

public class POMTestRunner {
	public static void main(String[] args) throws SAXException, IOException {
		$(new File("./pom.xml")).find("groupId")
        .each(ctx -> {
			System.out.println(
				$(ctx).text() + ":" +
				$(ctx).siblings("artifactId").text() + ":" +
				$(ctx).siblings("version").text()
				);
			
		});
	}
}

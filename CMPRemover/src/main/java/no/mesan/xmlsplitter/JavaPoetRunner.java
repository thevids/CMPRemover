package no.mesan.xmlsplitter;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import org.xml.sax.SAXException;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;


public class JavaPoetRunner {
	public static void main(String[] args) throws SAXException, IOException {

		final Builder theClass = TypeSpec.classBuilder("MinDao");
		theClass.addMethod(
				MethodSpec.methodBuilder("name").addParameter(String.class, "param1", Modifier.FINAL)
				.addStatement("String str = \"where ...\"").build());

		JavaFile javaFile = JavaFile.builder("com.example.helloworld", theClass.build()) .build();
		javaFile.writeTo(System.out);
	}
}

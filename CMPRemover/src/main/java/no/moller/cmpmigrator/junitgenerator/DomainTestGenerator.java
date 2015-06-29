package no.moller.cmpmigrator.junitgenerator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import no.moller.cmpmigrator.XMLFieldFetcher;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.xml.sax.SAXException;

public class DomainTestGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;
    final private String filePathToOldXmi;

    // Produces these source-files
    private JavaClassSource dom;
    private JavaClassSource test;
    private JavaClassSource key;

    /** Constructor that also does the job of generating the code. Fetch the generated code
     *  by calling the getters. Throws any exception, we want to fail fast as the end-user
     *  is a developer that needs to know if something is going wrong.
     *
     * @param filePathToOldCode
     * @param newPackage
     * @param filePathToOldXmi
     * @throws IOException
     * @throws SAXException
     */
    public DomainTestGenerator(String filePathToOldCode,
                        String newPackage,
                        String filePathToOldXmi,
                        String className) throws IOException, SAXException {
        this.filePathToOldCode = filePathToOldCode;
        this.newPackage = newPackage;
        this.filePathToOldXmi = filePathToOldXmi;

        generate(className);
    }

    /**
     * Does the real work of generating domain and mapper classes.
     *
     * @param className Existing base class for entity
     *
     * @throws IOException
     * @throws SAXException
     */
    private void generate(String className) throws IOException, SAXException {
        final String classAsString = IOUtils.toString(new File(filePathToOldCode + className + "Dom.java").toURI(),
                Charset.forName("ISO-8859-1"));
        final String keyAsString = IOUtils.toString(new File(filePathToOldCode + className + "Key.java").toURI(),
                Charset.forName("ISO-8859-1"));
        key = Roaster.parse(JavaClassSource.class, keyAsString);

        final String domObjName = className + "Dom";

        // Read existing bean, and make domain-object out of it
        dom = Roaster.parse(JavaClassSource.class, classAsString);
        //makeTestForDomainObject(className);

        generateTest(className, domObjName);
    }

    private void generateTest(final String className, final String domObjName)
            throws SAXException, IOException {

        // Read file, once, into memory for speed
        String ejbjarDocAsString = IOUtils.toString(new File(filePathToOldXmi + "ejb-jar.xml").toURI(),
                Charset.forName("ISO-8859-1"));

        test = Roaster.create(JavaClassSource.class);

        addImports();

        test.setName(domObjName + "Test")
              .setPackage(newPackage)
              .getJavaDoc().setText("Test the mapping between domain and data-objects.");

        makeConstants(className, ejbjarDocAsString, domObjName);

        implementTestMethods(className, ejbjarDocAsString, domObjName);
    }

    private void makeConstants(String className, String ejbjarDocAsString,
            String domObjName) {
        Collection<String> fields = XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className);
        short count = 0;

        for (String field : fields) {
            FieldSource<JavaClassSource> source = this.dom.getField(field);
            FieldSource<JavaClassSource> addField = test.addField(source.toString());
            Type<JavaClassSource> type = source.getType();
            addField.setFinal(true)
                    .setPrivate()
                    .setType(type.getName())
                    .setName(source.getName().toUpperCase())
                    .removeJavaDoc();

            if (type.isPrimitive()) {
                if ( type.getName().equals("boolean")) {
                    addField.setLiteralInitializer((count % 2 == 0) ? "true" : "false");
                } else {
                    addField.setLiteralInitializer("(" + type.getName() + ") " + count);
                }
            } else if (type.getName().equals("String") || type.getName().equals("java.lang.String")) {
                addField.setStringInitializer("streng" + count);
            } else  {
                addField.setLiteralInitializer("Mockito.mock(" + type.getName() + ".class)");
            }
            count++;
        }
    }

    private void implementTestMethods(final String className, String ejbjarDocAsString, String domObjName)
            throws SAXException, IOException {


        test.addMethod("public void mapToDomTest() {}")
                .setBody(TestMethodBody.makeDataToDom(className, ejbjarDocAsString, dom, key))
                .addAnnotation("Test");

        test.addMethod("public void mapToDataTest() {}")
                .setBody(TestMethodBody.makeDomToData(className, ejbjarDocAsString, dom, key))
                .addAnnotation("Test");
    }

    private void addImports() {
        test.addImport("org.mockito.Mockito");
        test.addImport("org.junit.Test");
        test.addImport("org.junit.Before");
        test.addImport("java.sql.Timestamp");
        test.addImport("org.junit.Assert.*").setStatic(true);
        test.addImport("java.util.Map");
        test.addImport("java.util.Date");
    }

    public JavaClassSource getDomainObj() {
        return dom;
    }

    public JavaClassSource getTest() {
        return test;
    }
}


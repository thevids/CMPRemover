package no.moller.cmpmigrator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.xml.sax.SAXException;

public class RowMapperGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;
    final private String filePathToOldXmi;

    // Produces these source-files
    private JavaClassSource bean;
    private JavaClassSource mapper;

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
    public RowMapperGenerator(String filePathToOldCode,
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
        final String classAsString = IOUtils.toString(new File(filePathToOldCode + className + "Bean.java").toURI(),
                Charset.forName("ISO-8859-1"));
        final String domObjName = className + "Dom";

        // Read existing bean, and make domain-object out of it
        bean = Roaster.parse(JavaClassSource.class, classAsString);
        purifyRemoveEjbLegacy(domObjName);
        bean.setName(domObjName);

        generateRowMapper(className, domObjName);
    }

    private void purifyRemoveEjbLegacy(final String domName) {
        // Remove all things ejb from the class declarations
        bean.removeInterface("javax.ejb.EntityBean")
                     .setPackage(newPackage)
                     .setName(domName)
                     .getJavaDoc().setFullText("Rowmapper for JdbcDao.");

        bean.getMethods().stream()
            .filter(m -> m.getName().startsWith("ejb"))
            .forEach(m -> bean.removeMethod(m));

        // Remove all things ejb from the method declarations
        bean.getMethods().forEach(
            met -> met.removeThrows("java.rmi.RemoteException")
                      .removeThrows("javax.ejb.CreateException")
                      .removeThrows("javax.ejb.RemoveException")
                      .removeJavaDoc());
    }

    private void generateRowMapper(final String className, final String domObjName)
            throws SAXException, IOException {

        // Read file, once, into memory for speed
        String ejbjarDocAsString = IOUtils.toString(new File(filePathToOldXmi + "ejb-jar.xml").toURI(),
                Charset.forName("ISO-8859-1"));

        mapper = Roaster.create(JavaClassSource.class);

        addImports();

        mapper.setName(domObjName + "RowMapper")
            .setPackage(newPackage)
            .addInterface("UtilRowMapper<" + domObjName + ">")
            .getJavaDoc().setText("Implementation of JdbcDao");


        implementMethods(className, ejbjarDocAsString, domObjName);
    }

    private void implementMethods(final String className, String ejbjarDocAsString, String domObjName) throws SAXException, IOException {

        mapper.addMethod("public " + domObjName + " mapRow(final ResultSet rs, final int rowNum) {}")
                .setBody(RowMapperMethodBodyGenerator.makeMapRow(className, ejbjarDocAsString, bean))
                .addThrows(java.sql.SQLException.class);
    }

    private void addImports() {
        mapper.addImport(ResultSet.class);
        mapper.addImport(SQLException.class);
        mapper.addImport("no.moller.util.jdbc.UtilRowMapper");
    }

    public JavaClassSource getDomainObj() {
        return bean;
    }

    public JavaClassSource getRowMapper() {
        return mapper;
    }
}


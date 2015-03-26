package no.moller.cmpmigrator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodHolderSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.xml.sax.SAXException;

public class DaoGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;
    final private String filePathToOldXmi;

    // Produces these source-files
    private JavaInterfaceSource homeInterface;
    private JavaClassSource impl;

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
    public DaoGenerator(String filePathToOldCode,
                        String newPackage,
                        String filePathToOldXmi,
                        String className) throws IOException, SAXException {
        this.filePathToOldCode = filePathToOldCode;
        this.newPackage = newPackage;
        this.filePathToOldXmi = filePathToOldXmi;

        generate(className);
    }

    /**
     * Does the real work of generating interface and dao impl class.
     * @param className
     *
     * @throws IOException
     * @throws SAXException
     */
    private void generate(String className) throws IOException, SAXException {
        final String classAsString = IOUtils.toString(new File(filePathToOldCode + className + "Home.java").toURI(),
                Charset.forName("ISO-8859-1"));

        // Read existing ebj-HomeInterface
        homeInterface = Roaster.parse(JavaInterfaceSource.class, classAsString);

        final String interfaceName = className + "Dao";

        purifyInterfaceRemoveEjbLegacy(interfaceName);

        modifyInterface(className);

        generateImplementationClass(className, interfaceName);

        prettyReturnTypes(this.homeInterface, className);
        prettyReturnTypes(this.impl, className);
    }

    /** Removes the package-path from the return-type, the type must be imported in stead. */
    private void prettyReturnTypes(MethodHolderSource<?> source, String className) {
        homeInterface.getMethods().stream().filter(p -> p.getName().startsWith("find"))
                    .filter(p -> p.getReturnType().isType(Enumeration.class))
                    .forEach(p -> p.setReturnType("Enumeration<" + className + "Dom" + ">"));
    }

    private void modifyInterface(final String className) {
        homeInterface.getMethods().stream().filter(p -> p.getName().startsWith("create"))
                                  .forEach(p -> p.addThrows(java.sql.SQLException.class));

        homeInterface.getMethods().stream().filter(p -> p.getReturnType().isType(className))
                                  .forEach(p -> p.setReturnType(className + "Dom"));

        homeInterface.addImport(Enumeration.class);
    }

    private void purifyInterfaceRemoveEjbLegacy(final String interfaceName) {
        // Remove all things ejb from the class declarations
        homeInterface.removeInterface("javax.ejb.EJBHome")
                     .setPackage(newPackage)
                     .setName(interfaceName)
                     .getJavaDoc().setFullText("Public interface for JdbcDao.");

        // Remove all things ejb from the method declarations
        homeInterface.getMethods().forEach(
            met -> met.removeThrows("java.rmi.RemoteException")
                      .removeThrows("javax.ejb.FinderException")
                      .removeThrows("javax.ejb.CreateException")
                      .removeJavaDoc());
    }

    private void generateImplementationClass(final String className, final String interfaceName)
            throws SAXException, IOException {

        // Read file, once, into memory for speed
        String docAsString = IOUtils.toString(new File(filePathToOldXmi + "ibm-ejb-jar-ext.xmi").toURI(),
                Charset.forName("ISO-8859-1"));
        String ejbjarDocAsString = IOUtils.toString(new File(filePathToOldXmi + "ejb-jar.xml").toURI(),
                Charset.forName("ISO-8859-1"));

        impl = Roaster.create(JavaClassSource.class);

        impl.setName(interfaceName + "Impl")
            .setPackage(newPackage)
            .addInterface(homeInterface.getQualifiedName())
            .getJavaDoc().setText("Implementation of JdbcDao");

        addImports();

        addFields(className, ejbjarDocAsString);

        implementMethods(className, docAsString, ejbjarDocAsString);
    }

    private void implementMethods(final String className, String docAsString,
            String ejbjarDocAsString) throws SAXException, IOException {
        // Implement methods
        for(MethodSource<JavaInterfaceSource> met: homeInterface.getMethods()) {
            impl.addMethod(met.toString())
                .setPublic() // Methods from interface are public
                .setBody (makeMethodBody(className, docAsString, met));
                //.addAnnotation("@SuppressWarnings(\"unchecked\")");
        }

        // Make an easier insert-method for all fields via domain-obj
        impl.addMethod("public boolean create(" + className + "Bean " + className.toLowerCase() + ") {}")
                .setBody(DaoMethodBody.makeCreateAll(className, ejbjarDocAsString))
                .addThrows(java.sql.SQLException.class);
    }

    private void addFields(final String className, String ejbjarDocAsString) {
        impl.addField("private NamedParameterJdbcTemplate mwinNamedTemplate")
              .addAnnotation(org.springframework.beans.factory.annotation.Autowired.class);

        impl.addField("private SimpleJdbcInsert simpleInsert")
            .addAnnotation(org.springframework.beans.factory.annotation.Autowired.class);

        impl.addField("private RowMapper<" + className + "Dom> mapper = new " + className + "DomRowMapper();");

        impl.addField(StatementModifier.makeSelectStatement(className,
                                            XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className)))
            .getJavaDoc().setFullText("Select-statement with ALL fields.");
    }

    private void addImports() {
        homeInterface.getImports().forEach(p -> impl.addImport(p));
        impl.addImport(HashMap.class);
        impl.addImport(Map.class);
        impl.addImport(SQLException.class);
        impl.addImport(List.class);
        impl.addImport("org.springframework.jdbc.core.namedparam.MapSqlParameterSource");
        impl.addImport("org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate");
        impl.addImport("org.springframework.jdbc.core.simple.SimpleJdbcInsert");
        impl.addImport("org.springframework.jdbc.core.RowMapper");
    }

    private String makeMethodBody(final String className, String docAsString,
            MethodSource<JavaInterfaceSource> met) throws SAXException, IOException {

        if(met.getName().startsWith("create")) {
            return DaoMethodBody.makeMethodBodyCreate(className, docAsString, met);
        }

        return DaoMethodBody.makeMethodBodyFinder(className, docAsString, met);
    }


    public JavaInterfaceSource getDaoInterface() {
        return homeInterface;
    }

    public JavaClassSource getDaoImpl() {
        return impl;
    }
}


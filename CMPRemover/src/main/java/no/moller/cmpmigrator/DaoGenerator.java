package no.moller.cmpmigrator;

import static org.joox.JOOX.$;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
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
                        String filePathToOldXmi) throws IOException, SAXException {
        this.filePathToOldCode = filePathToOldCode;
        this.newPackage = newPackage;
        this.filePathToOldXmi = filePathToOldXmi;

        generate();
    }

    /**
     * Does the real work of generating interface and dao impl class.
     *
     * @throws IOException
     * @throws SAXException
     */
    private void generate() throws IOException, SAXException {
        final String classAsString = IOUtils.toString(new File(filePathToOldCode + "AppointmentHome.java").toURI(),
                Charset.forName("ISO-8859-1"));

        // Read existing ebj-HomeInterface
        homeInterface = Roaster.parse(JavaInterfaceSource.class, classAsString);

        final String className = homeInterface.getName().replaceAll("Home", "");
        final String interfaceName = className + "Dao";

        purifyInterfaceRemoveEjbLegacy(interfaceName);

        generateImplementationClass(className, interfaceName);
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

        impl.addField("private NamedParameterJdbcTemplate mwinNamedTemplate")
              .addAnnotation(org.springframework.beans.factory.annotation.Autowired.class);

        impl.addField(StatementModifier.makeSelectStatement(className,
                                            XMLFieldFetcher.retrieveFields(ejbjarDocAsString, className)))
            .getJavaDoc().setFullText("Select-statement with ALL fields.");

        // Implement methods
        for(MethodSource<JavaInterfaceSource> met: homeInterface.getMethods()) {
            impl.addMethod(met.toString()).setBody(
                    makeMethodBody(className, docAsString, met));
        }
    }

    private String makeMethodBody(final String className, String docAsString,
            MethodSource<JavaInterfaceSource> met) throws SAXException, IOException {

        // Put parameters in a string equal to that in the xmi-file
        String paramsAsString = met.getParameters().stream()
                                   .map(p -> p.getType().toString()) // java-type of param
                                   .collect(Collectors.joining(" ")); // joined seperated by a space

        // Find where-statement in xmi-file
        String whereStatement = XMLFieldFetcher.retrieveWhereStatement(docAsString,
                                    className, met.getName(), paramsAsString.trim());

        if(whereStatement == null || whereStatement.trim().isEmpty()) {
            return "    throw java.lang.UnsupportedOperationException(\"Not yet implemented\");\n"
                    + "/* TODO: Empty method, needs to be written by hand.*/ \n";
        }

        // We like to use named parameters (not the anonym '?' that is default
        String namedParamWhereStatement =
                StatementModifier.makeNamedParamWhereStatement(whereStatement, met.getParameters());

        return "    String whereSQL = \"" + namedParamWhereStatement + "\";\n" + "";
    }


    public JavaInterfaceSource getNewDaoInterface() {
        return homeInterface;
    }

    public JavaClassSource getNewDaoImpl() {
        return impl;
    }
}


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

public class DataGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;
    final private String filePathToOldXmi;
    final private String filePathToOldDataCode;

    // Produces these source-files
    private JavaClassSource impl;
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
    public DataGenerator(String filePathToOldCode,
                        String filePathToOldDataCode,
                        String newPackage,
                        String filePathToOldXmi,
                        String className) throws IOException, SAXException {
        this.filePathToOldCode = filePathToOldCode;
        this.filePathToOldDataCode = filePathToOldDataCode;
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
        final String classAsString = IOUtils.toString(new File(filePathToOldDataCode + className + ".java").toURI(),
                Charset.forName("ISO-8859-1"));
        String keyAsString;
        try {
            String pathname = filePathToOldCode + className.replace("ETR", "Etr") + "Key.java";
            System.out.println(pathname);
            keyAsString = IOUtils.toString(new File(pathname).toURI(),
                    Charset.forName("ISO-8859-1"));
        } catch (java.io.FileNotFoundException e) {
            try {
                String pathname = filePathToOldCode + className.replace("ETR", "Etr") + "DataKey.java";
                System.out.println(pathname);
                keyAsString = IOUtils.toString(new File(pathname).toURI(),
                        Charset.forName("ISO-8859-1"));
            } catch (java.io.FileNotFoundException ex) {
                try {
                    String pathname = filePathToOldCode + className.replace("ETR", "Etr") + "sKey.java";
                    System.out.println(pathname);
                    keyAsString = IOUtils.toString(new File(pathname).toURI(),
                            Charset.forName("ISO-8859-1"));
                } catch (java.io.FileNotFoundException exx) {
                        String pathname = filePathToOldCode + className.replace("ETR", "Etr").replace("Data", "") + "Key.java";
                        System.out.println(pathname);
                        keyAsString = IOUtils.toString(new File(pathname).toURI(),
                                Charset.forName("ISO-8859-1"));
                }
            }
        }
        key = Roaster.parse(JavaClassSource.class, keyAsString);

        generateUpdateInfoMethod(className, classAsString);
    }

    private void generateUpdateInfoMethod(final String className, String classAsString)
            throws SAXException, IOException {

        impl = Roaster.parse(JavaClassSource.class, classAsString);

        addImports();

        implementMethods(className);
    }

    private void implementMethods(final String className) throws SAXException, IOException {
        String ejbjarDocAsString = IOUtils.toString(new File(filePathToOldXmi + "ejb-jar.xml").toURI(),
                Charset.forName("ISO-8859-1"));

        // Make an easier insert-method for all fields via domain-obj
        impl.addMethod("public Map<String, Object> getChangedFieldsMap()" + " {}")
                .setBody(DaoMethodBody.makeChangedFieldMap(className, key, ejbjarDocAsString));
    }

    private void addImports() {
        impl.addImport(HashMap.class);
        impl.addImport(Map.class);
    }

    public JavaClassSource getImpl() {
        return impl;
    }
}


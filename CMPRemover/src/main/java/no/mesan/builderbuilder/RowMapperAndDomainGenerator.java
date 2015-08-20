package no.mesan.builderbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
import org.xml.sax.SAXException;

public class RowMapperAndDomainGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;

    // Produces these source-files
    private JavaClassSource bean;
    private List<JavaClassSource> builders;

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
    public RowMapperAndDomainGenerator(String filePathToOldCode,
                        String newPackage,
                        String filePathToOldXmi,
                        String className) throws IOException, SAXException {
        this.filePathToOldCode = filePathToOldCode;
        this.newPackage = newPackage;

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
    private void generate(String className) throws IOException {
        final String classAsString = IOUtils.toString(new File(filePathToOldCode + className + "Bean.java").toURI(),
                Charset.forName("ISO-8859-1"));

        // Read existing bean, and make domain-object out of it
        bean = Roaster.parse(JavaClassSource.class, classAsString);

        generateBuilder(className);
    }

    private void generateBuilder(final String className)
            throws IOException {


        bean.getMethods().stream().filter(m -> m.isConstructor())
            .forEach(m -> makeBuilder(m, className));
    }


    private void makeBuilder(MethodSource<JavaClassSource> constructor, String className) {
        int count = 0;
        for (ParameterSource<JavaClassSource> parm: constructor.getParameters()) {

            count++;

            JavaClassSource builder = Roaster.create(JavaClassSource.class);

            addImports();

            builder.setName(className + "Builder" + count)
            .setPackage(newPackage)
            .getJavaDoc().setText("Builder for " + className);

            builder.addProperty(parm.getType().toString(), parm.getName());

            builder.addMethod(parm.getName())
                .setParameters(parm.getType().toString() + " " + parm.getName())
                .setReturnType(builder)
                .setBody("this." + parm.getName() +" = " + parm.getName() + "; return this;");

            builders.add(builder);
        }
    }

    private void addImports() {
//        builder.addImport(ResultSet.class);
    }

    public List<JavaClassSource> getBuilders() {
        return builders;
    }
}


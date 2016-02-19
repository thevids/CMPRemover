package no.moller.cmpmigrator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.xml.sax.SAXException;

public class RowMapperAndDomainGenerator {

    // Init with filepaths etc
    final private String filePathToOldCode;
    final private String newPackage;
    final private String filePathToOldXmi;

    // Produces these source-files
    private JavaClassSource bean;
    private JavaClassSource mapper;
    private JavaClassSource key;
    private boolean isCMP2;

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
        final String keyAsString = IOUtils.toString(new File(filePathToOldCode + className + "Key.java").toURI(),
                Charset.forName("ISO-8859-1"));
        key = Roaster.parse(JavaClassSource.class, keyAsString);

        final String domObjName = className + "Dom";

        // Read existing bean, and make domain-object out of it
        bean = Roaster.parse(JavaClassSource.class, classAsString);
        isCMP2 = bean.isAbstract();
        purifyDomainObjRemoveEjbLegacy(domObjName);
        improveDomainObject(className);
        bean.setName(domObjName);

        generateRowMapper(className, domObjName);
    }

    private void improveDomainObject(final String className) {
        // if abstract, then cmp 2.x, which do not have fields in class
        if(isCMP2) {
            bean.getMethods().stream()
                             .filter(method -> method.getName().startsWith("get"))
                             .filter(method -> !method.getName().startsWith("getPrimaryKey"))
                             .filter(method -> !method.getName().startsWith("getMyEntityCtx"))
                             .filter(method -> !method.getName().startsWith("get" + className + "Data"))
                             .forEach(m -> bean.addField()
                                               .setType( m.getReturnType().getName() )
                                               .setName(extractFieldname(m))
                                               .setPrivate()
                                               );
            bean.setAbstract(false);
            bean.getMethods().stream()
                             .filter(m -> m.isAbstract())
                             .forEach(m -> bean.removeMethod(m));
            DomainObjMethodBody.makeMissingGettersAndSetters(bean);
            DomainObjMethodBody.makePimaryKeyObjectCreatorAndGetter(key, bean);
        } else {
            DomainObjMethodBody.makePrimaryKeyFieldGettersAndSetters(key, bean);
        }
    }

    private String extractFieldname(MethodSource<JavaClassSource> m) {
        final String restWord = m.getName().substring(4);
        final String firstLetter = m.getName().substring(3, 4);
        return firstLetter.toLowerCase() + restWord;
    }

    private void purifyDomainObjRemoveEjbLegacy(final String domName) {
        // Remove all things ejb from the class declarations
        bean.removeInterface("javax.ejb.EntityBean")
                     .setPackage(newPackage)
                     .setName(domName)
                     .getJavaDoc().setFullText("Domain object.");

        bean.getMethods().stream()
                         .filter(m -> m.getName().startsWith("ejb")
                                 || m.getName().startsWith("_")
                                 || m.getName().contains("EntityContext"))
                         .forEach(m -> bean.removeMethod(m));

        bean.getFields().stream()
                        .filter(f -> f.getName().equals("myEntityCtx"))
                        .forEach(f -> bean.removeField(f));

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
              .setSuperType("UtilRowMapper<" + domObjName + ">")
              .getJavaDoc().setText("Rowmapper for JdbcDao");

        implementMapperMethod(className, ejbjarDocAsString, domObjName);
    }

    private void implementMapperMethod(final String className, String ejbjarDocAsString, String domObjName)
            throws SAXException, IOException {

        final String classAsString = IOUtils.toString(new File(filePathToOldCode + className + "Bean.java").toURI(),
                Charset.forName("ISO-8859-1"));
        JavaClassSource orginalBean = Roaster.parse(JavaClassSource.class, classAsString);

        mapper.addMethod("public " + domObjName + " mapRow(final ResultSet rs, final int rowNum) {}")
                .setBody(RowMapperMethodBody.makeMapRow(className, ejbjarDocAsString, orginalBean, key, isCMP2))
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


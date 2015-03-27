package no.moller.cmpmigrator;

import org.jboss.forge.roaster.model.source.JavaClassSource;

public class DomainObjMethodBody {

    /**
     * Make getter methods on domain obj for the fields contained inside the primary key object.
     * @param key
     * @param bean
     */
    static void makePrimaryKeyFieldGetters(JavaClassSource key, JavaClassSource bean) {

        bean.getMethod("getPrimaryKey").setBody("return this.pk;").setReturnType(key.getName());

        key.getFields().stream().filter(f -> !f.getName().equalsIgnoreCase("serialVersionUID"))
                                .forEach(f -> bean.addMethod("public " + f.getType() + " get"
                                    + FieldNameTool.enlargeFirsLetter(f.getName())
                                    + "() { return pk." + f.getName() + "; }"));
    }

    /**
     * We want to make both the primary key object, and the fields inside it available
     * directly from the domain object.
     *
     * @param className Name of the base class (remote interface in this case)
     * @param bean
     * @param key
     */
    static void makeConstructorAndFieldWithPrimaryKey(final String className, JavaClassSource bean, JavaClassSource key) {
        bean.addField(key.getName() + " pk;").setPrivate().setFinal(true);
        bean.addMethod("public " + className + "Dom(" + className + "Key pk) { this.pk = pk; }")
            .setConstructor(true);
    }
}

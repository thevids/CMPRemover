package no.moller.cmpmigrator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class DomainObjMethodBody {

    /**
     * Make getter methods on domain obj for the fields contained inside the primary key object.
     * @param key
     * @param bean
     */
    static void makePrimaryKeyFieldGettersAndSetters(JavaClassSource key, JavaClassSource bean) {

        makePimaryKeyObjectCreatorAndGetter(key, bean);

        getFieldStream(key) .forEach(f -> {
            addSetterAndGetter(bean, f);
            }
        );
    }

    static void makePimaryKeyObjectCreatorAndGetter(JavaClassSource key, JavaClassSource bean) {
        primaryKeyGetterMethod(key, bean).setBody("return new " + key.getName() + "("
                + getFieldStream(key).map(k -> k.getName())
                           .collect(Collectors.joining(","))
                +");").setReturnType(key.getName());
    }

    /**
     * Make getter methods on domain obj for the fields that dont have it, typically
     * in CMP2-beans.
     * @param key
     * @param bean
     */
    static void makeMissingGettersAndSetters(JavaClassSource bean) {
        getFieldStream(bean)
            .forEach(f -> {
            addSetterAndGetter(bean, f);
            }
        );
    }

    private static MethodSource<JavaClassSource> primaryKeyGetterMethod(JavaClassSource key, JavaClassSource bean) {
        MethodSource<JavaClassSource> method = bean.getMethod("getPrimaryKey");
        if(method ==null) {
            method = bean.addMethod().setName("getPrimaryKey").setPublic().setReturnType(key.getClass());
        }

        return method;
    }

    private static void addSetterAndGetter(JavaClassSource bean, FieldSource<JavaClassSource> f) {
        bean.addMethod("public " + f.getType().getName() + " get"
             + FieldNameTool.enlargeFirsLetter(f.getName())
             + "() { return " + f.getName() + "; }");

        bean.addMethod("public void set"
             + FieldNameTool.enlargeFirsLetter(f.getName())
             + "(" + f.getType().getQualifiedName() + " " + f.getName() + ")"
             + " { this." + f.getName() + " = " + f.getName() + "; }");
    }

    private static Stream<FieldSource<JavaClassSource>> getFieldStream(
            JavaClassSource key) {
        return key.getFields().stream().filter(f -> !f.getName().equalsIgnoreCase("serialVersionUID"));
    }
}

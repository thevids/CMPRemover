package no.moller.cmpmigrator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class DomainObjMethodBody {

    /**
     * Make getter methods on domain obj for the fields contained inside the primary key object.
     * @param key
     * @param bean
     */
    static void makePrimaryKeyFieldGettersAndSetters(JavaClassSource key, JavaClassSource bean) {

        bean.getMethod("getPrimaryKey").setBody("return new " + key.getName() + "("
                + getKeyFieldStream(key).map(k -> k.getName())
                           .collect(Collectors.joining(","))
                +");").setReturnType(key.getName());

        getKeyFieldStream(key) .forEach(f -> {
            addSetterAndGetter(bean, f);
            }
        );
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

    private static Stream<FieldSource<JavaClassSource>> getKeyFieldStream(
            JavaClassSource key) {
        return key.getFields().stream().filter(f -> !f.getName().equalsIgnoreCase("serialVersionUID"));
    }
}

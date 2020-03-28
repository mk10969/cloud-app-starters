package org.uma.cloud.batch;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

class JvLinkProcessorsTest {

    @Test
    void test() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .filter(i -> !"BaseModel".equals(i))
                .sorted()
                .forEach(i -> System.out.println(create(i)));
    }
    
    @Test
    void test_odds() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model.odds");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .sorted()
                .forEach(i -> System.out.println(create(i)));
    }


    private String create(String className) {
        return String.format(
                "    @Bean\n" +
                        "    public ItemProcessor<String, %1$s> %2$sItemProcessor() {\n" +
                        "        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()\n" +
                        "                .andThen(jvLinkFunction::%2$sFunction));\n" +
                        "    }\n"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

    public static Set<Class<?>> getClassesFrom(String packagee) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return ClassPath.from(loader)
                    .getTopLevelClasses(packagee)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @Test
    void test111() {
        String className = "AccRooo";
        String aaa = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        System.out.println(aaa);
    }

}
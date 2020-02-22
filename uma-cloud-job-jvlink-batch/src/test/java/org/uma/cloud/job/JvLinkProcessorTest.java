package org.uma.cloud.job;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

class JvLinkProcessorTest {

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
    void test111() {
        String className = "AccRooo";
        String aaa = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        System.out.println(aaa);
    }

    private String create(String className) {
        return String.format("    @Bean\n" +
                        "    public ItemProcessor<String, %1$s> %2$sItemProcessor() {\n" +
                        "        return data -> jvLinkFunction.decode()\n" +
                        "                .andThen(jvLinkFunction::%2$sFunction)\n" +
                        "                .apply(data);\n" +
                        "    }"

                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }


    public static Set<Class<?>> getClassesFrom(String packagee) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return ClassPath.from(loader)
                    .getTopLevelClasses(packagee)
                    .stream()
                    .map(info -> info.load())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

}
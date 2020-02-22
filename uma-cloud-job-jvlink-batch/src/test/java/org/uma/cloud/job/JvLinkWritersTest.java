package org.uma.cloud.job;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.uma.cloud.job.JvLinkProcessorTest.getClassesFrom;

class JvLinkWritersTest {


    @Test
    void test() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .filter(i -> !"BaseModel".equals(i))
                .sorted()
                .forEach(i -> System.out.println(create(i)));
    }

    private String create(String className) {
        return String.format(
                "    @Bean\n" +
                        "    public ItemWriter<%1$s> %2$sItemWriter(MongoOperations template) {\n" +
                        "        return new MongoItemWriterBuilder<%1$s>()\n" +
                        "                .template(template)\n" +
                        "                .collection(%1$s.class.getSimpleName())\n" +
                        "                .build();\n" +
                        "    }"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));

    }

}
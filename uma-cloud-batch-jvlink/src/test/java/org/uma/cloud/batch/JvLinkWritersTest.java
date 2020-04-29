package org.uma.cloud.batch;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.uma.cloud.batch.JvLinkProcessorsTest.getClassesFrom;

class JvLinkWritersTest {


    @Test
    void test() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model.odds");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .filter(i -> !"BaseModel".equals(i))
                .sorted()
                .forEach(i -> System.out.println(createItemWriter(i)));
    }

    @Test
    void tes_odds() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model.odds");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .sorted()
                .forEach(i -> System.out.println(createJpa(i)));
    }


    private String createJpa(String className) {
        return String.format(
                "    @Bean\n" +
                        "    public ItemWriter<%1$s> %2$sItemWriter() {\n" +
                        "        return new JpaItemWriterBuilder<%1$s>()\n" +
                        "                .entityManagerFactory(entityManagerFactory).build();\n" +
                        "    }"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

    private String createlocalVersion(String className) {
        return String.format(
                "    @Bean\n" +
                        "    public ItemWriter<%1$s> %2$sItemWriter() {\n" +
                        "        return items -> items.forEach(item -> log.info(\"{}\", item));\n" +
                        "    }"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

    private String createItemWriter(String className) {
        return String.format(
                "    @Bean\n" +
                        "    public ItemWriter<%1$s> %2$sItemWriter(%1$sService service) {\n" +
                        "        return items -> items.forEach(item -> {\n" +
                        "            if (service.exists(item.getRaceId())) {\n" +
                        "                log.warn(\"exists: {}\", item);\n" +
                        "            } else {\n" +
                        "                service.save(item);\n" +
                        "            }\n" +
                        "        });\n" +
                        "    }"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

}
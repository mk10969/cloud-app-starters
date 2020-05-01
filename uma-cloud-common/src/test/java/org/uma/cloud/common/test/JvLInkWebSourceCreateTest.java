package org.uma.cloud.common.test;

import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;

public class JvLInkWebSourceCreateTest {

    @Test
    void test_create() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model.odds")
                .stream()
                .map(Class::getSimpleName)
                .filter(i -> !"BaseModel".equals(i))
                .filter(i -> !"BaseModelTest".equals(i))
                .filter(i -> !"TimeSeries".equals(i))
                .filter(i -> !"CreateModelToSql".equals(i))
                .filter(i -> !"ModelTypeCheckTest".equals(i))
                .sorted()
                .forEach(i -> System.out.println(format(i)));

    }

    private String format(String className) {
        return String.format(
                "    public Flux<%1$s> store%1$s(long baseDate) {\n" +
                        "        return findAllByBaseDate(StorePath.%2$s, baseDate)\n" +
                        "                .map(jvLinkDeserializer::to%1$s)\n" +
                        "                .doOnNext(ModelUtil::fieldNotNull);\n" +
                        "    }", className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

}

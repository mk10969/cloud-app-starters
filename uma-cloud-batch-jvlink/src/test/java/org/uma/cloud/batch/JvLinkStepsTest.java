package org.uma.cloud.batch;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.uma.cloud.batch.JvLinkProcessorsTest.getClassesFrom;

class JvLinkStepsTest {

    @Test
    void methodCreater() {
        Set<Class<?>> modelClazz = getClassesFrom("org.uma.cloud.common.model.odds");
        modelClazz.stream()
                .map(Class::getSimpleName)
                .sorted()
                .forEach(i -> System.out.println(create(i)));
    }


    private String create(String className) {
        return String.format(

                "    @Bean(name = \"%2$s\")\n" +
                        "    public Step %2$sStep(ItemProcessor<String, %1$s> processor, ItemWriter<%1$s> writer) throws Exception {\n" +
                        "        return stepBuilderFactory.get(%1$s.class.getSimpleName())\n" +
                        "                .<String, %1$s>chunk(100)\n" +
                        "                .reader(reader)\n" +
                        "                .processor(processor)\n" +
                        "                .writer(writer)\n" +
                        "                .listener(new JvLinkStepExecutionListener())\n" +
                        "                .listener(new JvLinkProcessorListener())\n" +
                        "                .listener(new JvLinkWriterListener())\n" +
                        "                .faultTolerant()\n" +
                        "                .skipPolicy(new JvLinkSkipPolicy())\n" +
                        "                .build();\n" +
                        "    }"
                , className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

}
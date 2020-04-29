package org.uma.cloud.common.service;

import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RacingDetailServiceTest {

    @Test
    void test() {
        assertEquals(1, 1);
    }


    @Test
    void test_run() {
        repositories().stream().map(this::createClass).forEach(System.out::println);
    }


    private List<String> repositories() {
        return ReflectionUtils.getClassesFrom("org.uma.cloud.common.repository.odds")
                .stream()
                .map(Class::getSimpleName)
                .filter(i -> !"CreateJvLinkRepository".equals(i))
                .map(i -> i.replace("Repository", ""))
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }


    public String createClass(String className) {
        return String.format("@Service\n" +
                "public class %1$sService {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private %1$sRepository repository;\n" +
                "\n" +
                "\n" +
                "    public %1$s findOne() {\n" +
                "        return this.repository.findById(raceId).orElseThrow();\n" +
                "    }\n" +
                "\n" +
                "    public boolean exists() {\n" +
                "        return this.repository.existsById(raceId);\n" +
                "    }\n" +
                "\n" +
                "    @Transactional\n" +
                "    public %1$s save(%1$s %2$s) {\n" +
                "        return this.repository.save(%2$s);\n" +
                "    }\n" +
                "\n" +
                "    @Transactional\n" +
                "    public List<%1$s> saveAll(List<%1$s> %2$ss) {\n" +
                "        return this.repository.saveAll(%2$ss);\n" +
                "    }\n" +
                "\n" +
                "    @Transactional\n" +
                "    public void delete(%1$s %2$s) {\n" +
                "        this.repository.delete(%2$s);\n" +
                "    }\n" +
                "}\n", className, Character.toLowerCase(className.charAt(0)) + className.substring(1));
    }

}
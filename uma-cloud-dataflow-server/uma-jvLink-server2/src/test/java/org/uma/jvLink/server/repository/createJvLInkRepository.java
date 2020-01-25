package org.uma.jvLink.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.jvLink.core.config.spec.RecordSpec;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.jvLink.server.component.ReflectionUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class createJvLInkRepository {

    @Autowired
    Map<String, StoredOpenCondition> stringStoredOpenConditionMap;

    @Autowired
    EnumMap<RecordSpec, Class<?>> recordSpecClass;


    @Test
    void create_impl() {
        Map<Class<?>, RecordSpec> reverseMap = recordSpecClass.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getValue(),
                        e -> e.getKey()
                ));

        ReflectionUtils.getClassesFrom("org.uma.platform.common.model").stream()
                .filter(i -> i != RacingDetails.class)
                .filter(i -> i != RaceRefund.class)
                .filter(i -> i != HorseRacingDetails.class)
                .flatMap(i -> stringStoredOpenConditionMap.keySet()
                        .stream()
                        .filter(j -> j.split("_")[1].equals(reverseMap.get(i).getCode()))
                        .map(j -> Arrays.asList(i, j))
                )
                .forEach(System.out::println);
    }


    private String template(Class<?> className, String qualifier) {
        String tmp =
                "@Repository\n" +
                        "public class JvStored%1$sRepository implements JvLinkStoredRepository<%1$s> {\n" +
                        "\n" +
                        "    private final JvLinkModelMapper jvLinkModelMapper;\n" +
                        "\n" +
                        "    private final StoredOpenCondition storedOpenCondition;\n" +
                        "\n" +
                        "    public JvStored%1$sRepository(JvLinkModelMapper jvLinkModelMapper,\n" +
                        "                                        @Qualifier(\"%2$s\") StoredOpenCondition storedOpenCondition) {\n" +
                        "        this.jvLinkModelMapper = jvLinkModelMapper;\n" +
                        "        this.storedOpenCondition = storedOpenCondition;\n" +
                        "    }\n" +
                        "\n" +
                        "    @Override\n" +
                        "    public List<%1$s> findAll(ZonedDateTime dateTime, Option option) {\n" +
                        "\n" +
                        "        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {\n" +
                        "            return lines\n" +
                        "                    .map(jvContent -> jvLinkModelMapper\n" +
                        "                            .deserialize(jvContent.getLine(), %1$s.class))\n" +
                        "                    .collect(ImmutableList.toImmutableList());\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "}\n";
        return String.format(tmp, className.getSimpleName(), qualifier);

    }


}
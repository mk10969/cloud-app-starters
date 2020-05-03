package org.uma.cloud.stream.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.ReflectionUtils;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.service.RacingDetailService;
import org.uma.cloud.common.service.RacingHorseDetailService;
import org.uma.cloud.common.utils.javatuples.Triplet;
import org.uma.cloud.common.utils.lang.JacksonUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class JvBatchConsumerTest {

    private static final ObjectMapper objectMapper = JacksonUtil.getDefaultObjectMapper();

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("classpath:racing_detail.json")
    private Resource resourceFile;

    @Value("classpath:once.txt")
    private Resource onceTxt;


    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private RacingDetailService racingDetailService;

    @Autowired
    private RacingHorseDetailService racingHorseDetailService;


    @Test
    void patch_racing_horse_detail() {
        //  [引数のコード :000] class org.uma.cloud.common.code.MarginCode に引数のコードが、存在しません。
        // 2003/12/27 中山10R は、レース中止になったため、000がセットされている。
        Arrays.asList(
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwMTAxMTk5NTEwNzc4OYOBg1eDjYNBg2eDiYNYgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDAzMzEwNzA4MTAwMTQ2iZyVvZBejqE0OTg4MDCDgYNXg42WcY/qgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAlJKBQ5fOiOqWe5fWgUORs5fOj2OOyIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDY5NTAwMDAwlWyW7JJKjJuBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwMzAzMTk5NjEwNzA0M4OBg1eDjYOJg0ODZoOTgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDAzMzEwMzA3MjAwMTM3keWLdpXbkLM0OTg4MDCDgYNXg42WcY/qgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAlJKBQ5fOiOqWe5fWgUORs5fOj2OOyIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDU2MTAwMDAwicOTsJBNl1mBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwMjAyMTk5OTEwNTI1MoOBg4uDVoFbg16DSoNJgVuBQIFAgUCBQIFAgUCBQIFAgUCBQDA4MzEwMTA0MjAwMjIxlZCNR5W9gUAwMDQwMDWJaYjkgUCNTphZgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAl86BQ5DUkbOXzojqlnuX1oFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDYyNzAwMDAwj2+Sw41GiOqBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNDA0MTk5NzEwMzcxOIN1g4mDk4Nmg0KDWIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDAzMzEwMzA2MTAwNDM1k6GMtJJDl1kyMjY4MDCDVIOTg2aBW4OMgVuDVoOTg0+BQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAjZWBQ5DUj1yOmuZGgUORs4mpj2OOyIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDU2MDAwMDAwkeWNXYy0l7KBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNTA1MTk5OTEwMTI4NYOBg0ODVoOHg0WDfoNjg06DaoFAgUCBQIFAgUCBQIFAgUCBQDAwMTEwMzA0MjAwMjEykKOMy4z7ldc1MjMwMDWPvJZ7gUCNRJdZgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAkMKBQ5ON5kaBQ5ONkbOBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMTAwNTAwMDAwkLySSpC9gUCBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNTA2MTk5ODEwMTc0N4Nyg2KDT4NlgVuDWINngUCBQIFAgUCBQIFAgUCBQIFAgUCBQDA0MTEwMTA1MjAwMjUzkoaU9pCzgUA1Njg4MDCDcoNig0+BQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAk42BQ5SSj2OOyIFDkbOUko5PlnuX1oFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMTAxMzAwMDAwj+2Qzo+fi2CBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNjA3MTk5ODEwNDcxM4NngVuDWoOTg4GDQ4NVg5OBQIFAgUCBQIFAgUCBQIFAgUCBQDA0MTEwMzA1MTAwMTg0jbKBWJbYiJ8yNzAwMDaTh5DsgUCXso3GgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAl86BQ5DClUiOUoxggUOQwpGzgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDYxODAwMDAwjk+JWYyYjqGBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNjA4MTk5OTEwMjk4N4Ngg0GDWYNWg4ODQ4Nqg5ODT4FAgUCBQIFAgUCBQIFAgUCBQDAwMTEwMzA0MjAxMDQxk6GR8pGll1kyNjAwMDmWa5G6gUCDTIOIjnGBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAjoeBQ4mpi8roxYFDk42Rs4FAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDY5MTAwMDAwlmuR8pBMlueBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNzA5MTk5NzEwOTM2OIOGg0WDdIOIg0WDeoNFgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDAzMTEwMzA2MjAwNDE5j7yMs5bOjvc5NTg4MDCDQYNDg2WDY4FAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAlJKBQ5ONiOSMheNSgUOTjZGzgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDU5MTAwMDAwk2OSho2EgUCBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwNzEwMTk5NzEwNjI4M4NFg0ODk4N9gVuDeIOJg1iBQIFAgUCBQIFAgUCBQIFAgUCBQDAwMTEwNDA2MjAwNDI3kFiPR41zgUA0OTQ4MDCDRYNDg5OBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAkNSBQ42Vj2OOyIFDlJKRs5DUiOqWe5fWgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMTAzNTAwMDAwlJKVbJdZkaKBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwODExMTk5OTEwMTcxOYNlg0ODR4OAg26DhINKg1uBQIFAgUCBQIFAgUCBQIFAgUCBQDAwMTEwNDA0MjAwMjg2lZ+Th4+fgUAzNTYwMDKSfImAgUCQs+OLgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAk42BQ5fOiOqWe5fWgUORs4mpj2OOyIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMDcxMzAwMDAwjYKLtI1OlFaBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K",
                "U0U5MDAwMDAwMDAyMDAzMTIyNzA2MDYwNzEwODEyMTk5NDEwOTAwMYNJg2qDXoNDg1eBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDA0MzEwNDA5MTAwMzUzmGGTY5Czk7k4MzYwMDWQX45SgUCNS5JqgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAkMKBQ4mpkbOUkpPxlnuX1oFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFANjMwMDAwMDAwMTA0MDAwMDAwleSKoY71lUaBQIFAgUCBQDAwICAgICAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAwMDAwMDAwMDAwICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMA0K"
        ).stream()
                .map(i -> {
                    try {
                        RacingHorseDetail model = jvLinkDeserializer.toRacingHorseDetail(i);
                        ReflectionUtils.setField(BaseModel.class.getDeclaredField("dataCreateDate"), model, LocalDate.of(2003, 12, 27));
                        return model;
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(ModelUtil::fieldNotNull)
                .forEach(i -> racingHorseDetailService.save(i));
    }


    private List<Triplet<String, String, String>> localTriplet() {
        return Arrays.asList(
                Triplet.with("2014082646110311", "2017/12/11", null),
                Triplet.with("2018110130150311", "2019/03/05", null),
                Triplet.with("2012070445040310", "2019/07/04", null),
                Triplet.with("2018123047160310", "2019/05/10", null),
                Triplet.with("2018123047160310", "2019/05/10", null),
                Triplet.with("2018062130050512", "2019/06/21", "HIDAKASOU CUP"),
                Triplet.with("2019011147170403", "2020/01/03", null)
        );
    }

    private List<Triplet<String, String, RaceGradeCode>> listedPatch() throws IOException {
        try (InputStream inputStream = onceTxt.getInputStream();
             Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
            return lines.map(i -> {
                String[] tmp = i.split(",");
                return Triplet.with(tmp[1], tmp[0], RaceGradeCode.LISTED);
            }).collect(Collectors.toList());
        }
    }


    @Test
    void patch2() throws IOException {
        listedPatch().stream().forEach(i -> {
            RacingDetail racingDetail = racingDetailService.findOne(i.getValue1());
            String tmp = i.getValue2();
            int year = Integer.parseInt(tmp.split("/")[0]);
            int month = Integer.parseInt(tmp.split("/")[1]);
            int day = Integer.parseInt(tmp.split("/")[2]);
            try {
                ReflectionUtils.setField(BaseModel.class.getDeclaredField("dataCreateDate"), racingDetail, LocalDate.of(year, month, day));
                ReflectionUtils.setField(RacingDetail.class.getDeclaredField("gradeCd"), racingDetail, i.getValue3());

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            // 大丈夫そう。
            racingDetailService.save(racingDetail);
        });
    }


    @Test
    void patch() {
        localTriplet().stream().forEach(i -> {
            RacingDetail racingDetail = racingDetailService.findOne(i.getValue1());
            String tmp = i.getValue2();
            int year = Integer.parseInt(tmp.split("/")[0]);
            int month = Integer.parseInt(tmp.split("/")[1]);
            int day = Integer.parseInt(tmp.split("/")[2]);
            try {
                ReflectionUtils.setField(BaseModel.class.getDeclaredField("dataCreateDate"), racingDetail, LocalDate.of(year, month, day));
                if (i.getValue3() != null) {
                    ReflectionUtils.setField(RacingDetail.class.getDeclaredField("raceNameFullEng"), racingDetail, i.getValue3());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            // 大丈夫そう。
            racingDetailService.save(racingDetail);
        });
    }

    @Test
    void testest() {
        // Already exist data...
        List<String> raceIds = Arrays.asList(
                "2014082646110311",
                "2018110130150311",
                "2019010606010210",
                "2019011306010411",
                "2019011408010510",
                "2019011408010511",
                "2019011908010610",
                "2019011908010611",
                "2019012605010110",
                "2019012605010111",
                "2019020208020310",
                "2019020908020511",
                "2019021705010809",
                "2019022309010110",
                "2019022309010111",
                "2019022409010210",
                "2019030309010411",
                "2019031006020610",
                "2019031006020611",
                "2019031609010711",
                "2012070445040310",
                "2018123047160310",
                "2018062130050512",
                "2019011147170403");

        raceIds.stream()
                .map(raceId -> racingDetailService.findOne(raceId))
                .map(BaseModel::toString)
                .forEach(System.out::println);
    }
    

    private JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    List<String> readJson() throws IOException {
        try (InputStream inputStream = resourceFile.getInputStream();
             Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
            return lines.collect(Collectors.toList());
        }
    }


    @Test
    JsonNode readJson2() throws IOException {
        List<Map<String, Object>> tmp = objectMapper.readValue(resourceFile.getFile(), new TypeReference<List<Map<String, Object>>>() {
        });

        return objectMapper.convertValue(tmp.stream()
                .sorted(Comparator.comparing(a -> (String) a.get("raceId")))
                .collect(Collectors.toList()), JsonNode.class);
    }

}
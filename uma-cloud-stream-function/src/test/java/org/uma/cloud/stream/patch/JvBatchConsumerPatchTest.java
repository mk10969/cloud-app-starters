package org.uma.cloud.stream.patch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.repository.RacingHorseDetailRepository;
import org.uma.cloud.common.service.RacingDetailService;
import org.uma.cloud.common.service.RacingHorseDetailService;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;
import org.uma.cloud.common.utils.lang.JacksonUtil;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class JvBatchConsumerPatchTest {

    @Value("classpath:racing_horse_detail.json")
    private Resource resourceFile;

    @Value("classpath:racing_horse_detail2.json")
    private Resource resourceFile2;

    @Value("classpath:racing_horse_detail.csv")
    private Resource racingHorseDetailCsvFile;


    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private RacingDetailService racingDetailService;

    @Autowired
    private RacingHorseDetailService racingHorseDetailService;

    @Autowired
    private RacingHorseDetailRepository racingHorseDetailRepository;


    private static final ObjectMapper objectMapper = JacksonUtil.getDefaultObjectMapper();


    @Test
    void test_racing_horse_detailのパッチ() throws IOException, InterruptedException {
        // https://www.keibalab.jp/db/race/200312270610/
        // 走っていないので、nullでOK
    }
    
    @Test
    void test_racing_horse_detail2_dataCreateDateのパッチ() throws IOException, InterruptedException {
        List<Map<String, Object>> inputJsonFile = objectMapper.readValue(
                resourceFile2.getFile(), new TypeReference<>() {
                });
        // Fileにあるもの
        List<JsonNode> inputJsonNode = inputJsonFile.stream()
                .map(i -> objectMapper.convertValue(i, JsonNode.class))
                .collect(Collectors.toList());
        // DBにあるものを検索して、書き換える。
        inputJsonNode.forEach(i -> {
            RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
            compositeId.setRaceId(i.get("raceId").asText());
            compositeId.setHorseNo(i.get("horseNo").asText());
            compositeId.setBloodlineNo(Long.parseLong(i.get("bloodlineNo").asText()));
            RacingHorseDetail old = racingHorseDetailRepository.findById(compositeId).orElseThrow();
            // update
//            LocalDate newDate = toLocalDate(i.get("dataCreateDate").asText());
//            old.setDataCreateDate(newDate);
//            racingHorseDetailRepository.save(old);
        });
    }

    private static LocalDate toLocalDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(date, dateTimeFormatter);
    }


    @Test
    void test_racing_horse_detail2_ownerNameWithoutCorpのパッチ() throws IOException, InterruptedException {

        List<Map<String, Object>> inputJsonFile = objectMapper.readValue(
                resourceFile2.getFile(), new TypeReference<>() {
                });

        // Fileにあるもの
        List<JsonNode> inputJsonNode = inputJsonFile.stream()
                .map(i -> objectMapper.convertValue(i, JsonNode.class))
                .collect(Collectors.toList());

        // DBにあるものを検索して、書き換える。
        inputJsonNode.forEach(i -> {
            RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
            compositeId.setRaceId(i.get("raceId").asText());
            compositeId.setHorseNo(i.get("horseNo").asText());
            compositeId.setBloodlineNo(Long.parseLong(i.get("bloodlineNo").asText()));
            RacingHorseDetail old = racingHorseDetailRepository.findById(compositeId).orElseThrow();

//            // update
//            old.setOwnerNameWithoutCorp(i.get("ownerNameWithoutCorp").asText());
//            racingHorseDetailRepository.save(old);
        });
    }

    @Test
    void test_racing_horse_detail2_2003122706060710_data_create_date_insert() {
        long now = System.currentTimeMillis();
        List<RacingHorseDetail> old = racingHorseDetailRepository.findByRaceId("2003122706060710");
        System.out.println(System.currentTimeMillis() - now);
//        old.stream()
//                .peek(i -> i.setDataCreateDate(toLocalDate("20031227")))
//                .peek(i -> racingHorseDetailRepository.save(i))
//                .forEach(i -> System.out.println(i));
    }


    @Test
    void test_JSONAssertを利用() throws IOException, InterruptedException {

        List<Map<String, Object>> inputJsonFile = objectMapper.readValue(
                resourceFile2.getFile(), new TypeReference<>() {
                });
        // Fileにあるもの
        List<JsonNode> inputJsonNode = inputJsonFile.stream()
                .map(i -> objectMapper.convertValue(i, JsonNode.class))
                .collect(Collectors.toList());
        // DBにあるもの
        List<JsonNode> selectedJsonNode = inputJsonNode.stream()
                .map(i -> {
                    RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
                    compositeId.setRaceId(i.get("raceId").asText());
                    compositeId.setHorseNo(i.get("horseNo").asText());
                    compositeId.setBloodlineNo(Long.parseLong(i.get("bloodlineNo").asText()));
                    return racingHorseDetailRepository.findById(compositeId).orElseThrow();
                })
                .map(i -> objectMapper.convertValue(i, JsonNode.class))
                .collect(Collectors.toList());

//        // thread sleep
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux.zip(Flux.fromIterable(inputJsonNode), Flux.fromIterable(selectedJsonNode), Pair::with)
                .subscribe(i -> {
                    String beforeJson = i.getValue1().toString();
                    String afterJson = i.getValue2().toString();

                    try {
                        JSONAssert.assertEquals(beforeJson, afterJson, JSONCompareMode.STRICT);
                    } catch (AssertionError | JSONException e) {

                        System.out.println("----------------------------------");
                        System.out.println("raceId: " + i.getValue1().get("raceId").asText()
                                + "\n"
                                + "horseName: " + i.getValue1().get("horseName").asText()
                                + "\n"
                                + "diff: " + e.getMessage());

                    }
                }, System.out::println, countDownLatch::countDown);

        countDownLatch.await();
    }


    @Test
    void test_racing_detail_findOne() {
        // racingDetailは、リステッドに変わったことだけが、重複している。
        Stream.of(
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
                "2019011147170403")
                .map(i -> racingDetailService.findOne(i))
                .forEach(i -> System.out.println(i.toJson() + ","));
    }

    @Test
    void test_racing_horse_detail_findOne() throws IOException {
        // 地方の結果しか差分がない。
        // 中央のオーナー名の変更は、属性から除外したので出なくなった。
        Files.lines(Paths.get(racingHorseDetailCsvFile.getURI()), StandardCharsets.UTF_8)
                .map(i -> {
                    String[] tmp = i.split(",");
                    return Triplet.with(tmp[0], tmp[1], tmp[2]);
                })
                .map(triplet -> {
                    RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
                    compositeId.setRaceId(triplet.getValue1());
                    compositeId.setHorseNo(triplet.getValue2());
                    compositeId.setBloodlineNo(Long.valueOf(triplet.getValue3()));
                    return compositeId;
                })
                .map(compositeId -> racingHorseDetailService.findOne(compositeId))
                .forEach(i -> System.out.println(i.toJson() + ","));

    }

}

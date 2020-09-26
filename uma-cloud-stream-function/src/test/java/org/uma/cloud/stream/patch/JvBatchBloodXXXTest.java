package org.uma.cloud.stream.patch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.service.BloodBreedingService;
import org.uma.cloud.common.service.BloodLineService;
import org.uma.cloud.common.service.DiffRaceHorseService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
public class JvBatchBloodXXXTest {

    @Value("classpath:blood_breeding.csv")
    private Resource bloodBreedingCsv;

    @Value("classpath:blood_line.csv")
    private Resource bloodLineCsv;

    @Value("classpath:diff_race_horse.csv")
    private Resource diffRaceHorseCsv;

//    @Value("classpath:racing_refund.csv")
//    private Resource racingRefundCsv;


    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private BloodBreedingService bloodBreedingService;

    @Autowired
    private BloodLineService bloodLineService;

    @Autowired
    private DiffRaceHorseService diffRaceHorseService;

//    @Autowired
//    private RacingRefundService racingRefundService;


//    @Test
//    void test_racing_refund() throws IOException {
//        // ぴったり一致でしたね
//        Files.lines(Paths.get(racingRefundCsv.getURI()), StandardCharsets.UTF_8)
//                .flatMap(i -> Stream.of(1, 2, 4, 5, 6, 7, 8)
//                        .map(j -> {
//                            RacingRefund.CompositeId compositeId = new RacingRefund.CompositeId();
//                            compositeId.setRaceId(i);
//                            compositeId.setBetting(j);
//                            return compositeId;
//                        }))
//                .sorted(Comparator.comparing(RacingRefund.CompositeId::getRaceId)
//                        .thenComparing(RacingRefund.CompositeId::getBetting))
//                .map(compositeId -> racingRefundService.findOne(compositeId))
//                .forEach(i -> System.out.println(i.toJson() + ","));
//    }


    @Test
    void test_Blood_Breeding() throws IOException {
        Files.lines(Paths.get(bloodBreedingCsv.getURI()), StandardCharsets.UTF_8)
                .map(i -> bloodBreedingService.findOne(Integer.valueOf(i)))
                .forEach(i -> System.out.println(i.toJson() + ","));
    }

    @Test
    void test_Blood_Line() throws IOException {
        Files.lines(Paths.get(bloodLineCsv.getURI()), StandardCharsets.UTF_8)
                .map(i -> bloodLineService.findOne(Long.valueOf(i)))
                .forEach(i -> System.out.println(i.toJson() + ","));
    }

}

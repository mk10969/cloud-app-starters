package org.uma.cloud.stream.patch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.service.BloodBreedingService;
import org.uma.cloud.common.service.BloodLineService;
import org.uma.cloud.common.service.RaceHorseService;

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

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    @Autowired
    private BloodBreedingService bloodBreedingService;

    @Autowired
    private BloodLineService bloodLineService;

    @Autowired
    private RaceHorseService raceHorseService;


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

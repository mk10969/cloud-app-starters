package org.uma.cloud.batch.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.repository.RacingDetailsRepository;

import java.time.LocalDate;

@SpringBootTest
class RacingDetailsRepositoryTest {

    @Autowired
    private RacingDetailsRepository repository;

    @Test
    void test_find() {
        repository.findByCourseCd(RaceCourseCode.TOKYO).stream()
                .map(RacingDetails::getRaceId)
                .forEach(System.out::println);
    }

    @Test
    void test_find_date() {

        LocalDate a = LocalDate.now().minusYears(3);
        LocalDate b = LocalDate.now().minusYears(1);

        long count = repository.findByHoldingDateBetween(a, b).stream()
                .map(RacingDetails::getHoldingDate)
                .sorted()
                .peek(System.out::println)
                .count();
        System.out.println(count);
    }

}
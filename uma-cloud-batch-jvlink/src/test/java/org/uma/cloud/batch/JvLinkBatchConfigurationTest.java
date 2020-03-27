package org.uma.cloud.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.repository.RacingDetailsRepository;


@SpringBootTest
class JvLinkBatchConfigurationTest {

    @Autowired
    private RacingDetailsRepository repository;

    @Test
    void test() {
        repository.findAll().forEach(System.out::println);

    }

}
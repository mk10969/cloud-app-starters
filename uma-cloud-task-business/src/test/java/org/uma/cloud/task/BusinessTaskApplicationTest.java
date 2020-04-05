package org.uma.cloud.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.model.business.BusinessBaseDate;
import org.uma.cloud.common.service.business.BusinessBaseDateService;

import java.time.ZonedDateTime;

@SpringBootTest
class BusinessTaskApplicationTest {

    @Autowired
    private BusinessBaseDateService service;

    @Test
    void test_BusinessModelRepository() throws InterruptedException {
        BusinessBaseDate businessBaseDate1 = new BusinessBaseDate();
        long time1 = ZonedDateTime.now().minusWeeks(1).toInstant().toEpochMilli();
        businessBaseDate1.setBaseDate(time1);

        BusinessBaseDate businessBaseDate2 = new BusinessBaseDate();
        long time2 = ZonedDateTime.now().minusWeeks(2).toInstant().toEpochMilli();
        businessBaseDate2.setBaseDate(time2);

        BusinessBaseDate businessBaseDate3 = new BusinessBaseDate();
        long time3 = ZonedDateTime.now().minusWeeks(3).toInstant().toEpochMilli();
        businessBaseDate3.setBaseDate(time3);

        service.save(businessBaseDate3);
        service.save(businessBaseDate2);
        service.save(businessBaseDate1);
    }

}
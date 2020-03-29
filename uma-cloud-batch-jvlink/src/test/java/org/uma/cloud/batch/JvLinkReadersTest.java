package org.uma.cloud.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.model.BusinessModel;
import org.uma.cloud.common.repository.BusinessModelRepository;

import java.time.ZonedDateTime;
import java.util.Arrays;

@SpringBootTest
class JvLinkReadersTest {

    @Autowired
    private JvLinkReaders jvLinkReaders;

    @Autowired
    private BusinessModelRepository businessModelRepository;


    @Test
    void test() {
        System.out.println(jvLinkReaders.getResource());
        System.out.println(jvLinkReaders.getResource().isFile());
        System.out.println(jvLinkReaders.getReaderName());
    }

    @Test
    void testtest() {
        String path = "file:///c://config/app.properties";
        String[] level = path.split("/");
        System.out.println(Arrays.toString(level));
        System.out.println(level.length);

        System.out.println(level[level.length - 1]);
        String aa = level[level.length - 1];
    }

    @Test
    void test_BusinessModelRepository() throws InterruptedException {
        BusinessModel businessModel1 = new BusinessModel();
        long time1 = ZonedDateTime.now().minusDays(1).toInstant().getEpochSecond();
//        businessModel1.setId(1);
        businessModel1.setBaseDate(time1);

        BusinessModel businessModel2 = new BusinessModel();
        long time2 = ZonedDateTime.now().minusDays(2).toInstant().getEpochSecond();
//        businessModel1.setId(2);
        businessModel2.setBaseDate(time2);

        BusinessModel businessModel3 = new BusinessModel();
        long time3 = ZonedDateTime.now().minusDays(3).toInstant().getEpochSecond();
//        businessModel1.setId(3);
        businessModel3.setBaseDate(time3);

        businessModelRepository.save(businessModel1);
        businessModelRepository.save(businessModel2);
        businessModelRepository.save(businessModel3);

    }

}
package org.uma.cloud.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.service.business.BusinessBaseDateService;

import java.util.Arrays;

@SpringBootTest
class JvLinkReadersTest {

    @Autowired
    private JvLinkReaders jvLinkReaders;

    @Autowired
    private BusinessBaseDateService businessBaseDateService;


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
    void test_optional() {
        long l = businessBaseDateService.getLatestBaseDate();
        System.out.println(l);
    }

}
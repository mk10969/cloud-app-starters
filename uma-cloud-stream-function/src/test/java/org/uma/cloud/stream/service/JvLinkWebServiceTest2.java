package org.uma.cloud.stream.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.service.RacingDetailsService;

@SpringBootTest
public class JvLinkWebServiceTest2 {


    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private RacingDetailsService racingDetailsService;


    /**
     * enumを、AttributeConverterを使ってマッピングしたので、試す。
     */
    @Test
    void test_RacingDetailのモデルをDBに登録する() throws InterruptedException {

        jvLinkWebService.getRaceIds()
                .flatMap(jvLinkWebService::racingDetail)
                .doOnNext(i -> racingDetailsService.save(i))
                .subscribe();
        Thread.sleep(10000L);
    }

}

package org.uma.cloud.stream.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.uma.cloud.common.service.RacingDetailService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JvLinkWebSourceTest2 {


    @Autowired
    private JvLinkWebSource jvLinkWebSource;

    @Autowired
    private RacingDetailService racingDetailService;


    /**
     * enumを、AttributeConverterを使ってマッピングしたので、試す。
     */
    @Test
    void test_RacingDetailのモデルをDBに登録する() throws InterruptedException {
        this.getRaceId()
                .flatMap(jvLinkWebSource::realtimeRacingDetail)
                .flatMap(i -> Mono.fromCallable(() -> racingDetailService.save(i)))
                .subscribe();
        Thread.sleep(10000L);
    }

    private Flux<String> getRaceId() {
        List<String> tmp = Arrays.asList("2020042503010510", "2020042503010511", "2020042503010512", "2020042505020101",
                "2020042505020102", "2020042505020103", "2020042505020104", "2020042505020105", "2020042505020106",
                "2020042505020107", "2020042505020108", "2020042505020109", "2020042505020110", "2020042505020111",
                "2020042505020112", "2020042508030101", "2020042508030102", "2020042508030103", "2020042508030104",
                "2020042508030105", "2020042508030106", "2020042508030107", "2020042508030108", "2020042508030109",
                "2020042508030110", "2020042508030111", "2020042508030112");
        return Flux.fromIterable(tmp);
    }

}

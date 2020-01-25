package org.uma.jvLink.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.core.config.option.RealTimeKey;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.utils.lang.ThreadUtil;
import reactor.core.publisher.Flux;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JvLinkClientRealTimeTest {

    @Autowired
    private Map<String, RealTimeOpenCondition> rtOpenCondition;

    /**
     * 0B15の方が、データ提供の期間範囲が広い。
     */
    @Autowired
    @Qualifier("0B12_HR") // 成績確定後 OK
    private RealTimeOpenCondition HRcondition;

    @Autowired
    @Qualifier("0B12_RA") // 成績確定後 OK
    private RealTimeOpenCondition RAcondition;

    @Autowired
    @Qualifier("0B11_WH") // 馬体重 OK
    private RealTimeOpenCondition WHcondition;

//    下記 全てNG
//    @Autowired
//    @Qualifier("0B16_WE")
//    private RealTimeOpenCondition WEcondition;
//
//    @Autowired
//    @Qualifier("0B16_AV")
//    private RealTimeOpenCondition AVcondition;
//
//    @Autowired
//    @Qualifier("0B16_JC")
//    private RealTimeOpenCondition JCcondition;
//
//    @Autowired
//    @Qualifier("0B16_TC")
//    private RealTimeOpenCondition TCcondition;
//
//    @Autowired
//    @Qualifier("0B16_CC")
//    private RealTimeOpenCondition CCcondition;

    // keyとして、下記が適切かな
    private RealTimeKey realTimeKey = () -> "2019112408050812";
    private RealTimeKey realTimeKeyShort = () -> "201911240812";
    private RealTimeKey realTimeKeyVeryShort = () -> "20191124";


    @Test
    void test_0B12_HRデータ取得() {
        // 払い戻しは、取れるね！
        JvLinkClient.readFlux(HRcondition, realTimeKey)
                .subscribe(
                        i -> System.out.println(i.getLine()),
                        e -> e.printStackTrace(),
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(1000L);
    }

    @Test
    void test_0B12_RAデータ取得() {
        JvLinkClient.readFlux(RAcondition, realTimeKey)
                .subscribe(
                        i -> System.out.println(i.getLine()),
                        e -> e.printStackTrace(),
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(1000L);
    }

    @Test
    void test_0B11_WHデータ取得() {
        // 馬体重は、取れるね！
        Flux.just(realTimeKey, realTimeKeyVeryShort)
                .flatMap(key -> JvLinkClient.readFlux(WHcondition, key))
                .subscribe(
                        i -> System.out.println(i.getLine()),
                        e -> e.printStackTrace(),
                        () -> System.out.println("完了")
                );
        ThreadUtil.sleep(1000L);
    }


    /**
     * 0B16は、イベント通知系のデータなので、
     * イベント通知を利用しないと取得できない。
     */
//    @Test
//    void test_0B16のデータ取得() {
//        Flux.fromStream(findConditions("0B16"))
////                .flatMap(rtCondition -> JvLink
////                        .readFlux(rtCondition, realTimeKey))
//                .subscribe(i -> System.out.println(i.getRecordType().getCode()));
//        ThreadUtil.sleep(6000L);
//    }
//
//    private Stream<RealTimeOpenCondition> findConditions(String key) {
//        return rtOpenCondition.entrySet().stream()
//                .filter(i -> i.getKey().contains(key))
//                .map(Map.Entry::getValue);
//    }

}

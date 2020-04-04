//package org.uma.cloud.common.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.uma.jvLink.core.JvLinkClient;
//import org.uma.jvLink.core.config.option.Option;
//import org.uma.jvLink.core.config.condition.StoredOpenCondition;
//import org.uma.jvLink.core.config.spec.RecordSpec;
//import org.uma.platform.common.utils.lang.ThreadUtil;
//import org.uma.jvLink.core.response.JvStringContent;
//import reactor.core.publisher.Flux;
//import reactor.core.scheduler.Schedulers;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//import java.util.stream.Stream;
//
//
//@SpringBootTest
//class JvLinkClientTest {
//
//    @Autowired
//    @Qualifier("RACE_RA")
//    private StoredOpenCondition conditionRA;
//
//    @Autowired
//    @Qualifier("RACE_SE")
//    private StoredOpenCondition conditionSE;
//
//    @Autowired
//    @Qualifier("RACE_HR")
//    private StoredOpenCondition conditionHR;
//
//    @Autowired
//    @Qualifier("YSCH_YS") //これ情報としていらん。
//    private StoredOpenCondition conditionYS;
//
//    @Autowired
//    @Qualifier("BLOD_SK")
//    private StoredOpenCondition conditionSK;
//
//    @Autowired
//    @Qualifier("BLOD_BT")
//    private StoredOpenCondition conditionBT;
//
//    @Autowired
//    @Qualifier("BLOD_HN")
//    private StoredOpenCondition conditionHN;
//
//    @Autowired
//    @Qualifier("RACE_H1")
//    private StoredOpenCondition conditionH1;
//
//    @Autowired
//    @Qualifier("RACE_JG")
//    private StoredOpenCondition conditionJG;
//
//    @Autowired
//    @Qualifier("RACE_O1")
//    private StoredOpenCondition conditionO1;
//
//    @Autowired
//    private List<StoredOpenCondition> conditions;
//
//
//    private final LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
//
//
//    private Predicate<StoredOpenCondition> grep(RecordSpec record) {
//        return condition -> condition.getRecordType() == record;
//    }
//
//    @SafeVarargs
//    private final Predicate<StoredOpenCondition> filters(Predicate<StoredOpenCondition>... predicate) {
//        return Stream.of(predicate).reduce(Predicate::and).orElseThrow(IllegalStateException::new);
//    }
//
//    private Stream<StoredOpenCondition> conditionGrep(RecordSpec... recordSpec) {
//        return Stream.of(recordSpec)
//                .flatMap(record -> conditions.stream()
//                        .filter(grep(record)));
//    }
//
//    private Stream<StoredOpenCondition> conditionGrepV(RecordSpec... recordSpec) {
//        return Stream.of(recordSpec)
//                .flatMap(record -> conditions.stream()
//                        .filter(grep(record).negate()));
//    }
//
//
//    @Test
//    void test() {
//        run(
//                () -> runWith(RA, SE),
//                () -> runWithout(O1, O2, O3, O4, O5, O6, CS)
//        );
//    }
//
//
//    private Flux<String> runWith(RecordSpec... recordSpec) {
//        return Flux.fromStream(conditionGrep(recordSpec))
//                .flatMap(i -> JvLinkClient
//                        .readFlux(i, dateTime, Option.STANDARD)
//                        .map(JvStringContent::getLine)
//                        .take(5));
//    }
//
//    private Flux<String> runWithout(RecordSpec... recordSpec) {
//        return Flux.fromStream(conditionGrepV(recordSpec))
//                .flatMap(i -> JvLinkClient
//                        .readFlux(i, dateTime, Option.STANDARD)
//                        .map(JvStringContent::getLine)
//                        .take(5));
//    }
//
//
//    private Flux<String> test_BLOD() {
//        return Flux.just(conditionSK, conditionHN)
//                .subscribeOn(Schedulers.elastic())
//                .log()
//                .flatMap(i -> JvLinkClient
//                        .readFlux(i, dateTime, Option.SETUP_WITHOUT_DIALOG)
//                        .map(JvStringContent::getLine)
//                );
//    }
//
//
//    @SafeVarargs
//    private final void run(Supplier<Flux<String>>... runner) {
//        Flux.fromStream(Stream.of(runner))
//                .flatMap(Supplier::get)
//                .subscribe(
//                        System.out::println,
//                        Throwable::printStackTrace,
//                        () -> System.out.println("完了"));
//        ThreadUtil.sleep(10000L);
//    }
//
//
//    /**
//     * 基本操作確認
//     */
//
//    @Test
//    void test_シングルスレッドでJvLink呼び出し() {
//        JvLinkClient.readFlux(conditionO1, dateTime, Option.STANDARD)
//                .subscribe(
//                        i -> System.out.println(i.getLine()),
//                        Throwable::printStackTrace,
//                        () -> System.out.println("完了")
//                );
//        ThreadUtil.sleep(3000L);
//    }
//
//    @Test
//    void test_マルチスレッドでJvLink呼び出し() {
//        // 想定通りの動きをしてくれている。よしよし。（速い処理はできないね！仕方ない！）
//        // ただし、クラスロック「synchronized (jvlink)」と、「.subscribeOn(Schedulers.single())」
//        // のどちらかを取り除くと、203エラーが返ってくる。（JvLink側、マルチスレッド非対応）
//        Flux.fromIterable(conditions)
//                .subscribeOn(Schedulers.elastic())
//                .log()
//                .flatMap(i -> JvLinkClient.readFlux(i, dateTime, Option.STANDARD)
//                        .log()
//                        .take(10)
//                        .map(JvStringContent::getLine))
//                .subscribe(
//                        System.out::println,
//                        Throwable::printStackTrace,
//                        () -> System.out.println("完了")
//                );
//        ThreadUtil.sleep(30000L);
//    }
//
//
//}
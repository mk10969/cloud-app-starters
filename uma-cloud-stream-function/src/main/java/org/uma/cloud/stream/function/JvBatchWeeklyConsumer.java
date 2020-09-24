package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.entity.BaseModel;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.service.RacingDetailService;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.stream.type.FileSource;
import org.uma.cloud.stream.type.JpaEntitySink;
import org.uma.cloud.stream.type.JvLinkWebSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.function.Consumer;

@Slf4j
@Profile("batchWeekly")
@Configuration
public class JvBatchWeeklyConsumer {

    @Autowired
    private JvLinkWebSource jvLinkWebSource;

    @Autowired
    private FileSource fileSource;

    @Autowired
    private JpaEntitySink jpaEntitySink;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private RacingDetailService racingDetailService;


    @Bean
    public CommandLineRunner init() {
        return args -> batch();
    }

    public void onceBatch() {
//        long baseDate = LocalDateTime.of(
//                LocalDate.of(2020, 2, 2), LocalTime.of(0, 0, 0))
//                .toInstant(ZoneOffset.ofHours(9))
//                .toEpochMilli();
//        // out of memoryだったのでファイル読み込みに代替(file path注意)
//        merge("RaceHorse").accept(fileSource.getRaceHorse());
//        // out of memoryだったのでファイル読み込みに代替(file path注意)
//        merge("TrioOdds").accept(fileSource.getTrio());
    }

    public void batch() {
        // 最新レースの日付を取得
        RacingDetail lastRacingDetail = this.racingDetailService.findLastOne();
        // 13桁 epochMillSecondに変換
        long baseDate = LocalDateTime.of(
                lastRacingDetail.getHoldingDate(), LocalTime.of(0, 0, 0))
                .toInstant(ZoneOffset.ofHours(9))
                .toEpochMilli();

        // 更新Batch Weekly
        merge("RacingDetail").accept(jvLinkWebSource.storeRacingDetail(baseDate));
        merge("RacingHorseDetail").accept(jvLinkWebSource.storeRacingHorseDetail(baseDate));
        merge("RacingRefund").accept(jvLinkWebSource.storeRacingRefund(baseDate));
        merge("RacingHorseExclusion").accept(jvLinkWebSource.storeRacingHorseExclusion(baseDate));

//        枠連と三連単は、データ入れていない。
//        理由：枠連 => 見ないから
//             三連単 => データデカすぎるから
        merge("WinOdds").accept(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue1));
        merge("ShowOdds").accept(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue2));
        merge("QuinellaOdds").accept(jvLinkWebSource.storeQuinella(baseDate));
        merge("QuinellaPlaceOdds").accept(jvLinkWebSource.storeQuinellaPlace(baseDate));
        merge("ExactaOdds").accept(jvLinkWebSource.storeExacta(baseDate));
        merge("TrioOdds").accept(jvLinkWebSource.storeTrio(baseDate));

        // 血統
        merge("BloodAncestry").accept(jvLinkWebSource.storeBloodAncestry(baseDate));
        merge("BloodBreeding").accept(jvLinkWebSource.storeBloodBreeding(baseDate));
        merge("BloodLine").accept(jvLinkWebSource.storeBloodLine(baseDate));

        // 馬
        merge("RaceHorse").accept(jvLinkWebSource.storeRaceHorse(baseDate));
        merge("Jockey").accept(jvLinkWebSource.storeJockey(baseDate));
        merge("Trainer").accept(jvLinkWebSource.storeTrainer(baseDate));
        merge("Breeder").accept(jvLinkWebSource.storeBreeder(baseDate));
        merge("Owner").accept(jvLinkWebSource.storeOwner(baseDate));
        merge("Course").accept(jvLinkWebSource.storeCourse(baseDate));
    }


    private Consumer<Flux<? extends BaseModel>> merge(String batchName) {
        return entityFlux -> entityFlux
                .filter(entity -> !entity.getDataDiv().equals("0"))
                // PKが重複した場合、データを上書きで更新かける（仕様）が、
                // どこのカラムに差分があるかチェックする。
                .doOnNext(entity -> jpaEntitySink.logAlreadyExists(entity, entity.getPrimaryKey()))
                .buffer(100)
                .flatMap(entities -> Mono.fromCallable(() -> jpaEntitySink.mergeAll(entities))
                        .flatMapMany(Flux::fromIterable))
                .onErrorContinue((throwable, object) -> {
                    log.error("JPA ERROR:", throwable);
                    log.error("Missing Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {
                        },
                        e -> log.error("Batch Error [" + batchName + "]: ", e),
                        () -> log.info(batchName + ": 完了"));
    }
}

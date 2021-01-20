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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.function.Function;

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
        long baseDate = LocalDateTime.of(
                LocalDate.of(2020, 2, 2), LocalTime.of(0, 0, 0))
                .toInstant(ZoneOffset.ofHours(9))
                .toEpochMilli();
//        // out of memoryだったのでファイル読み込みに代替(file path注意)
//        merge("RaceHorse").apply(fileSource.getRaceHorse());
//        // out of memoryだったのでファイル読み込みに代替(file path注意)
//        merge("TrioOdds").apply(fileSource.getTrio());

        Mono.just("batch weekly 開始")
                .flatMap(i -> merge("RacingHorseDetail")
                        .apply(jvLinkWebSource.storeRacingHorseDetail(baseDate)))
                .onErrorContinue((throwable, object) -> {
                    log.error("JPA ERROR:", throwable);
                    log.error("Missing Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {
                        },
                        e -> {
                            log.error("Batch Error: ", e);
                            System.exit(8);
                        },
                        () -> {
                            log.info("batch weekly 完了");
                            System.exit(0);
                        });
    }

    public void batch() {
        // 最新レースの日付を取得
        final RacingDetail lastRacingDetail = this.racingDetailService.findLastOne();
        // 13桁 epochMillSecondに変換
        final long baseDate = LocalDateTime.of(
                lastRacingDetail.getHoldingDate(), LocalTime.of(0, 0, 0))
                .toInstant(ZoneOffset.ofHours(9))
                .toEpochMilli();

        Mono.just("batch weekly 開始")
                .flatMap(i -> merge("RacingDetail")
                        .apply(jvLinkWebSource.storeRacingDetail(baseDate)
                                .filter(entity -> "7".equals(entity.getDataDiv())
                                        | "A".equals(entity.getDataDiv())
                                        | "B".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("RacingHorseDetail")
                        .apply(jvLinkWebSource.storeRacingHorseDetail(baseDate)
                                .filter(entity -> "7".equals(entity.getDataDiv())
                                        | "A".equals(entity.getDataDiv())
                                        | "B".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("RacingRefund")
                        .apply(jvLinkWebSource.storeRacingRefund(baseDate)
                                .filter(entity -> "2".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("RacingHorseExclusion")
                        .apply(jvLinkWebSource.storeRacingHorseExclusion(baseDate)
                                .filter(entity -> "1".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("WinOdds")
                        .apply(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue1)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("ShowOdds")
                        .apply(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue2)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("QuinellaOdds")
                        .apply(jvLinkWebSource.storeQuinella(baseDate)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("QuinellaPlaceOdds")
                        .apply(jvLinkWebSource.storeQuinellaPlace(baseDate)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("ExactaOdds")
                        .apply(jvLinkWebSource.storeExacta(baseDate)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("TrioOdds")
                        .apply(jvLinkWebSource.storeTrio(baseDate)
                                .filter(entity -> "5".equals(entity.getDataDiv())
                                        | "9".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("BloodAncestry")
                        .apply(jvLinkWebSource.storeBloodAncestry(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("BloodBreeding")
                        .apply(jvLinkWebSource.storeBloodBreeding(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("BloodLine")
                        .apply(jvLinkWebSource.storeBloodLine(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("RaceHorse")
                        .apply(jvLinkWebSource.storeRaceHorse(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("Jockey")
                        .apply(jvLinkWebSource.storeJockey(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("Trainer")
                        .apply(jvLinkWebSource.storeTrainer(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("Breeder")
                        .apply(jvLinkWebSource.storeBreeder(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("Owner")
                        .apply(jvLinkWebSource.storeOwner(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .flatMap(i -> merge("Course")
                        .apply(jvLinkWebSource.storeCourse(baseDate)
                                .filter(entity -> !"0".equals(entity.getDataDiv()))
                        ))
                .onErrorContinue((throwable, object) -> {
                    log.error("JPA ERROR:", throwable);
                    log.error("Missing Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {
                        },
                        e -> {
                            log.error("Batch Error: ", e);
                            System.exit(8);
                        },
                        () -> {
                            log.info("batch weekly 完了");
                            System.exit(0);
                        });
    }


    private Function<Flux<? extends BaseModel>, Mono<String>> merge(String batchName) {
        return entityFlux -> entityFlux
                // PKが重複した場合、データを上書きで更新かける（仕様）が、
                // どこのカラムに差分があるかチェックする。
                .doOnNext(entity -> jpaEntitySink.logAlreadyExists(entity, entity.getPrimaryKey()))
                .buffer(100)
                .flatMap(entities -> Mono.fromCallable(() -> jpaEntitySink.mergeAll(entities))
                        .flatMapMany(Flux::fromIterable))
//                .doOnNext(i -> System.out.println(i.toJson()))
                .then(Mono.just(batchName + "完了"));
    }
}

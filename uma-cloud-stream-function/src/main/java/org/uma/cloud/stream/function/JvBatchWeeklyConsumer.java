package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.entity.BaseModel;
import org.uma.cloud.common.service.RacingDetailService;
import org.uma.cloud.stream.type.JpaEntitySink;
import org.uma.cloud.stream.type.JvLinkWebSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.LocalDate;
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
    private JpaEntitySink jpaEntitySink;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private RacingDetailService racingDetailService;


    @Bean
    public CommandLineRunner init() {
        return args -> batch();
    }


    public void batch() {
//        // 最新レースの日付を取得
//        RacingDetail lastRacingDetail = this.racingDetailService.findLastOne();
//        // 13桁 epochMillSecondに変換
//        long baseDate = LocalDateTime.of(
//                lastRacingDetail.getHoldingDate(), LocalTime.of(0, 0, 0))
//                .toInstant(ZoneOffset.ofHours(9))
//                .toEpochMilli();
        long baseDate = LocalDateTime.of(
                LocalDate.of(2019, 2, 2), LocalTime.of(0, 0, 0))
                .toInstant(ZoneOffset.ofHours(9))
                .toEpochMilli();
//        // 更新Batch Weekly
//        merge().accept(jvLinkWebSource.storeRacingDetail(baseDate));
//        merge().accept(jvLinkWebSource.storeRacingHorseDetail(baseDate));
//        merge().accept(jvLinkWebSource.storeRacingRefund(baseDate));
//        merge().accept(jvLinkWebSource.storeRacingHorseExclusion(baseDate));


//        merge().accept(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue1));
//        merge().accept(jvLinkWebSource.storeWinsShowBracketQ(baseDate).map(Pair::getValue2));
        merge().accept(jvLinkWebSource.storeQuinella(baseDate));
//        merge().accept(jvLinkWebSource.storeQuinellaPlace(baseDate));
//        merge().accept(jvLinkWebSource.storeExacta(baseDate));
//        merge().accept(jvLinkWebSource.storeTrio(baseDate));
//        merge().accept(jvLinkWebSource.storeTrifecta(baseDate));
//
//
//        persist().accept(jvLinkWebSource.storeBloodAncestry(baseDate));
//        persist().accept(jvLinkWebSource.storeBloodBreeding(baseDate));
//        persist().accept(jvLinkWebSource.storeBloodLine(baseDate));
//
//        persist().accept(jvLinkWebSource.storeRaceHorse(baseDate));
//        persist().accept(jvLinkWebSource.storeJockey(baseDate));
//        persist().accept(jvLinkWebSource.storeTrainer(baseDate));
//        persist().accept(jvLinkWebSource.storeBreeder(baseDate));
//        persist().accept(jvLinkWebSource.storeOwner(baseDate));
//        persist().accept(jvLinkWebSource.storeCourse(baseDate));
    }

    private Consumer<Flux<? extends BaseModel>> persist() {
        return entityFlux -> entityFlux
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .doOnNext(entity -> jpaEntitySink.logAlreadyExists(entity, entity.getPrimaryKey()))
                .buffer(100)
                .flatMap(entities -> Mono.fromCallable(() -> jpaEntitySink.persistAll(entities))
                        .flatMapMany(Flux::fromIterable))
                .onErrorContinue((throwable, object) -> {
                    log.error("JPA ERROR:", throwable);
                    log.error("Missing Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {
                }, e -> log.error("Batch ERROR: ", e), () -> log.info("完了"));
    }


    private Consumer<Flux<? extends BaseModel>> merge() {
        return entityFlux -> entityFlux
                .filter(entity -> !entity.getDataDiv().equals("0"))
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
                }, e -> log.error("Batch ERROR: ", e), () -> log.info("完了"));
    }
}

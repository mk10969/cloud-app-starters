package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.model.BloodAncestry;
import org.uma.cloud.common.model.BloodBreeding;
import org.uma.cloud.common.model.BloodLine;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingHorseExclusion;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.RacingVote;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.type.FileSource;
import org.uma.cloud.stream.type.JpaEntitySink;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.function.Consumer;

@Slf4j
@Profile("batch")
@Configuration
public class JvBatchConsumer {

    @Autowired
    private FileSource fileSource;

    @Autowired
    private JpaEntitySink jpaEntitySink;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private StreamFunctionProperties properties;


    @Bean
    public CommandLineRunner init() {
        return args -> batch();
    }


    public void batch() {
        batchTrifecta();
    }

    // レース

    private void batchRacingDetail() {
        Flux<RacingDetail> flux = fileSource.getRacingDetail()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchRacingHorseDetail() {
        Flux<RacingHorseDetail> flux = fileSource.getRacingHorseDetail()
                .filter(entity -> {
                    RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setHorseNo(entity.getHorseNo());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                })
                // 木曜データの場合（dataDiv = 1）はないと思うのでfilterかけない。
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchRacingRefund() {
        Flux<RacingRefund> flux = fileSource.getRacingRefund()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchRacingVote() {
        Flux<RacingVote> flux = fileSource.getRacingVote()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchRacingHorseExclusion() {
        Flux<RacingHorseExclusion> flux = fileSource.getRacingHorseExclusion()
                .filter(entity -> {
                    RacingHorseExclusion.CompositeId compositeId = new RacingHorseExclusion.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    compositeId.setEntryOrderNo(entity.getEntryOrderNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                })
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    // オッズ

    private void batchWinsShowBracketQ() {
        Flux<WinsShowBracketQ> flux = fileSource.getWinsShowBracketQ()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchQuinella() {
        Flux<Quinella> flux = fileSource.getQuinella()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchQuinellaPlace() {
        Flux<QuinellaPlace> flux = fileSource.getQuinellaPlace()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchExacta() {
        Flux<Exacta> flux = fileSource.getExacta()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchTrio() {
        Flux<Trio> flux = fileSource.getTrio()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchTrifecta() {
        Flux<Trifecta> flux = fileSource.getTrifecta()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    // 馬

    private void batchRaceHorse() {
        Flux<RaceHorse> flux = fileSource.getRaceHorse()
                .filter(entity -> {
                    RaceHorse.CompositeId compositeId = new RaceHorse.CompositeId();
                    compositeId.setDataDiv(entity.getDataDiv());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                })
                .filter(entity -> !entity.getDataDiv().equals("0"));
        persist().accept(flux);
    }

    private void batchJockey() {
        Flux<Jockey> flux = fileSource.getJockey()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getJockeyCd()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchTrainer() {
        Flux<Trainer> flux = fileSource.getTrainer()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getTrainerCd()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchBreeder() {
        Flux<Breeder> flux = fileSource.getBreeder()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreederCd()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchOwner() {
        Flux<Owner> flux = fileSource.getOwner()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getOwnerCd()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchCourse() {
        Flux<Course> flux = fileSource.getCourse()
                .filter(entity -> {
                    Course.CompositeId compositeId = new Course.CompositeId();
                    compositeId.setCourseCd(entity.getCourseCd());
                    compositeId.setDistance(entity.getDistance());
                    compositeId.setTrackCd(entity.getTrackCd());
                    compositeId.setCourseRepairDate(entity.getCourseRepairDate());
                    return jpaEntitySink.notExists(entity, compositeId);
                })
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergerでいい。
        merge().accept(flux);
    }

    // 血統

    private void batchBloodAncestry() {
        Flux<BloodAncestry> flux = fileSource.getBloodAncestry()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい
        merge().accept(flux);
    }

    private void batchBloodBreeding() {
        Flux<BloodBreeding> flux = fileSource.getBloodBreeding()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい
        merge().accept(flux);
    }

    private void batchBloodLine() {
        Flux<BloodLine> flux = fileSource.getBloodLine()
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBloodlineNo()))
                .filter(entity -> !entity.getDataDiv().equals("0"));
        // mergeでいい
        merge().accept(flux);
    }


    private Consumer<Flux<?>> persist() {
        return entityFlux -> entityFlux
                .buffer(100)
                .flatMap(entities -> Mono.fromCallable(() -> jpaEntitySink.persistAll(entities))
                        .flatMapMany(Flux::fromIterable))
                .onErrorContinue((throwable, object) -> {
                    log.error("Batch Error: ", throwable);
                    log.warn("Error Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {}, e -> {}, () -> log.info("完了"));
    }


    private Consumer<Flux<?>> merge() {
        return entityFlux -> entityFlux
                .buffer(100)
                .flatMap(entities -> Mono.fromCallable(() -> jpaEntitySink.mergeAll(entities))
                        .flatMapMany(Flux::fromIterable))
                .onErrorContinue((throwable, object) -> {
                    log.error("Batch Error: ", throwable);
                    log.warn("Error Object: {}", object);
                })
                .publishOn(scheduler)
                .subscribe(i -> {}, e -> {}, () -> log.info("完了"));
    }
}

package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.entity.OddsQuinella;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.entity.OddsShow;
import org.uma.cloud.common.entity.OddsTrifecta;
import org.uma.cloud.common.entity.OddsTrio;
import org.uma.cloud.common.entity.OddsWin;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.utils.javatuples.Pair;
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
        /**
         * TODO:BloodBreeding -> 重複調査
         * TODO:BloodLine -> 重複調査
         */
        batchWinsShowBracketQ();
    }

    // レース

    private void batchRacingDetail() {
        Flux<RacingDetail> flux = fileSource.getRacingDetail()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchRacingHorseDetail() {
        Flux<RacingHorseDetail> flux = fileSource.getRacingHorseDetail()
                // 木曜データの場合（dataDiv = 1）はないと思うのでfilterかけない。
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    RacingHorseDetail.CompositeId compositeId = new RacingHorseDetail.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setHorseNo(entity.getHorseNo());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                });
        persist().accept(flux);
    }

    private void mergeBatchRacingHorseDetail() {
        Flux<RacingHorseDetail> flux = fileSource.getRacingHorseDetail()
                // 木曜データの場合（dataDiv = 1）はないと思うのでfilterかけない。
                .filter(entity -> !entity.getDataDiv().equals("0"));
        merge().accept(flux);
    }

    private void batchRacingRefund() {
        Flux<RacingRefund> flux = fileSource.getRacingRefund()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchRacingVote() {
        Flux<RacingVote> flux = fileSource.getRacingVote()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchRacingHorseExclusion() {
        Flux<RacingHorseExclusion> flux = fileSource.getRacingHorseExclusion()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    RacingHorseExclusion.CompositeId compositeId = new RacingHorseExclusion.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    compositeId.setEntryOrderNo(entity.getEntryOrderNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                });
        persist().accept(flux);
    }

    // オッズ

    private void batchWinsShowBracketQ() {
        Flux<Pair<OddsWin, OddsShow>> flux = fileSource.getWinsShowBracketQ();
        Flux<OddsWin> oddsWinFlux = flux.map(Pair::getValue1)
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        Flux<OddsShow> oddsShowFlux = flux.map(Pair::getValue2)
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(oddsWinFlux);
        persist().accept(oddsShowFlux);
    }

    private void batchQuinella() {
        Flux<OddsQuinella> flux = fileSource.getQuinella()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchQuinellaPlace() {
        Flux<OddsQuinellaPlace> flux = fileSource.getQuinellaPlace()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchExacta() {
        Flux<OddsExacta> flux = fileSource.getExacta()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchTrio() {
        Flux<OddsTrio> flux = fileSource.getTrio()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    private void batchTrifecta() {
        Flux<OddsTrifecta> flux = fileSource.getTrifecta()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }

    // 血統

    private void batchBloodAncestry() {
        Flux<BloodAncestry> flux = fileSource.getBloodAncestry()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()));
        persist().accept(flux);
    }

    private void batchBloodBreeding() {
        Flux<BloodBreeding> flux = fileSource.getBloodBreeding()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()));
        persist().accept(flux);
    }

    private void batchBloodLine() {
        Flux<BloodLine> flux = fileSource.getBloodLine()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBloodlineNo()));
        persist().accept(flux);
    }


    // 馬 (以下いらんかもね。。)

    private void batchRaceHorse() {
        Flux<DiffRaceHorse> flux = fileSource.getRaceHorse()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    DiffRaceHorse.CompositeId compositeId = new DiffRaceHorse.CompositeId();
                    compositeId.setDataDiv(entity.getDataDiv());
                    compositeId.setBloodlineNo(entity.getBloodlineNo());
                    return jpaEntitySink.notExists(entity, compositeId);
                });
        persist().accept(flux);
    }

    private void batchJockey() {
        Flux<DiffJockey> flux = fileSource.getJockey()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getJockeyCd()));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchTrainer() {
        Flux<DiffTrainer> flux = fileSource.getTrainer()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getTrainerCd()));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchBreeder() {
        Flux<DiffBreeder> flux = fileSource.getBreeder()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreederCd()));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchOwner() {
        Flux<DiffOwner> flux = fileSource.getOwner()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getOwnerCd()));
        // mergeでいい。
        merge().accept(flux);
    }

    private void batchCourse() {
        Flux<Course> flux = fileSource.getCourse()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    Course.CompositeId compositeId = new Course.CompositeId();
                    compositeId.setCourseCd(entity.getCourseCd());
                    compositeId.setDistance(entity.getDistance());
                    compositeId.setTrackCd(entity.getTrackCd());
                    compositeId.setCourseRepairDate(entity.getCourseRepairDate());
                    return jpaEntitySink.notExists(entity, compositeId);
                });
        // mergerでいい。
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
                .subscribe(i -> {
                }, e -> {
                }, () -> log.info("完了"));
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
                .subscribe(i -> {
                }, e -> {
                }, () -> log.info("完了"));
    }
}

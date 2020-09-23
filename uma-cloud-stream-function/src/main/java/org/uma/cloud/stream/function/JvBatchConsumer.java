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
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingOdds;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.utils.javatuples.Pair;
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


    @Bean
    public CommandLineRunner init() {
        return args -> batch();
    }


    public void batch() {
        mergeBatchRacingDetail();
    }

    // レース

    /**
     * 完了
     */
    private void mergeBatchRacingDetail() {
        // こっちで問題なし
        Flux<RacingDetail> flux = fileSource.getRacingDetail()
                .filter(entity -> !entity.getDataDiv().equals("0"));
        merge().accept(flux);
    }

    /**
     * 完了
     */
    private void mergeBatchRacingHorseDetail() {
        Flux<RacingHorseDetail> flux = fileSource.getRacingHorseDetail()
                // 木曜データの場合（dataDiv = 1）はないと思うのでfilterかけない。
                .filter(entity -> !entity.getDataDiv().equals("0"));
        merge().accept(flux);
    }

    /**
     * 完了
     */
    private void batchRacingRefund() {
        Flux<RacingRefund> flux = fileSource.getRacingRefund()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    RacingRefund.CompositeId compositeId = new RacingRefund.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setBetting(entity.getBetting());
                    return jpaEntitySink.notExists(entity, compositeId);
                });
        persist().accept(flux);
    }

    /**
     * 完了
     */
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

    /**
     * 未対応
     */
    private void batchRacingVote() {
        Flux<RacingVote> flux = fileSource.getRacingVote()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getRaceId()));
        persist().accept(flux);
    }


    // オッズ

    private void oddsFilterAndPersist(Flux<RacingOdds> oddsFlux) {
        persist().accept(oddsFlux
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> {
                    RacingOdds.CompositeId compositeId = new RacingOdds.CompositeId();
                    compositeId.setRaceId(entity.getRaceId());
                    compositeId.setBetting(entity.getBetting());
                    return jpaEntitySink.notExists(entity, compositeId);
                })
        );
    }

    /**
     * 完了
     */
    private void batchWinsShowBracketQ() {
        Flux<Pair<RacingOdds, RacingOdds>> flux = fileSource.getWinsShowBracketQ();
        // 単勝
        oddsFilterAndPersist(flux.map(Pair::getValue1));
        // 複勝
        oddsFilterAndPersist(flux.map(Pair::getValue2));
    }

    /**
     * 完了
     */
    private void batchQuinella() {
        oddsFilterAndPersist(fileSource.getQuinella());
    }

    /**
     * 完了
     */
    private void batchQuinellaPlace() {
        oddsFilterAndPersist(fileSource.getQuinellaPlace());
    }

    /**
     * 完了
     */
    private void batchExacta() {
        oddsFilterAndPersist(fileSource.getExacta());
    }

    /**
     * 完了
     */
    private void batchTrio() {
        oddsFilterAndPersist(fileSource.getTrio());
    }

    private void batchTrifecta() {
        oddsFilterAndPersist(fileSource.getTrifecta());
    }

    // 血統

    /**
     * 完了
     */
    private void batchBloodAncestry() {
        Flux<BloodAncestry> flux = fileSource.getBloodAncestry()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()));
        persist().accept(flux);
    }

    /**
     * 完了
     */
    private void mergeBatchBloodBreeding() {
        Flux<BloodBreeding> flux = fileSource.getBloodBreeding()
                .filter(entity -> !entity.getDataDiv().equals("0"));
//                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreedingNo()));
        merge().accept(flux);
    }

    /**
     * 完了
     */
    private void mergeBatchBloodLine() {
        Flux<BloodLine> flux = fileSource.getBloodLine()
                .filter(entity -> !entity.getDataDiv().equals("0"));
//                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBloodlineNo()));
        merge().accept(flux);
    }


    // 馬

    /**
     * mergeで完了（頻繁に更新される）
     */
    private void batchRaceHorse() {
        Flux<DiffRaceHorse> flux = fileSource.getRaceHorse()
                .filter(entity -> !entity.getDataDiv().equals("0"));
        merge().accept(flux);
    }

    /**
     * 完了（重複なし）
     */
    private void batchJockey() {
        Flux<DiffJockey> flux = fileSource.getJockey()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getJockeyCd()));
        merge().accept(flux);
    }

    /**
     * 完了
     */
    private void batchTrainer() {
        Flux<DiffTrainer> flux = fileSource.getTrainer()
                .filter(entity -> !entity.getDataDiv().equals("0"));
//                .filter(entity -> jpaEntitySink.notExists(entity, entity.getTrainerCd()));
        merge().accept(flux);
    }

    /**
     * 完了（重複なし）
     */
    private void batchBreeder() {
        Flux<DiffBreeder> flux = fileSource.getBreeder()
                .filter(entity -> !entity.getDataDiv().equals("0"))
                .filter(entity -> jpaEntitySink.notExists(entity, entity.getBreederCd()));
        merge().accept(flux);
    }

    /**
     * 完了
     */
    private void batchOwner() {
        Flux<DiffOwner> flux = fileSource.getOwner()
                .filter(entity -> !entity.getDataDiv().equals("0"));
//                .filter(entity -> jpaEntitySink.notExists(entity, entity.getOwnerCd()));
        merge().accept(flux);
    }

    /**
     * 完了
     */
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
        persist().accept(flux);
    }


    private Consumer<Flux<?>> persist() {
        return entityFlux -> entityFlux
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


    private Consumer<Flux<?>> merge() {
        return entityFlux -> entityFlux
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

//package org.uma.platform.feed.application.component;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.stereotype.Component;
//import org.uma.platform.feed.application.configuration.SetupConfiguration;
//import org.uma.platform.feed.application.repository.impl.*;
//import org.uma.platform.feed.application.util.JvLinkUtil;
//import reactor.core.Disposable;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//import reactor.util.function.Tuple2;
//import reactor.util.function.Tuples;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Profile("setup")
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JvSetupReactiveCommandLineRunner2 implements CommandLineRunner {
//
//    private final JvSetupRunner jvSetupRunner;
//    private final SetupConfiguration setUpConfiguration;
//
//
//    private static class MySubscriber<T> implements Subscriber<T> {
//
//        @Override
//        public void onSubscribe(Subscription s) {
//            log.info("  --> onSubscribe");
//            s.request(Long.MAX_VALUE);
//        }
//
//        @Override
//        public void onNext(T data) {
////            log.info("  --> onNext: {}", data);
//        }
//
//        @Override
//        public void onError(Throwable throwable) {
//            log.error("  --> Error: ", throwable);
//        }
//
//        @Override
//        public void onComplete() {
//            log.info("  --> セットアップ完了");
//        }
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        // 起動時は、慌ただしいので、5秒待つ。
//        Thread.sleep(5000L);
//
//        Flux.just(setUpConfiguration.getYyyyMMddHHmmss())
//                .doOnNext(yyyyMMddHHmmss -> log.info("{}までのデータセットアップを開始します", yyyyMMddHHmmss))
//                .flatMap(jvSetupRunner::batchRacingDetails)
//                .flatMap(jvSetupRunner::batchHorseRacingDetails)
//                .flatMap(jvSetupRunner::batchRaceRefund)
//                .flatMap(jvSetupRunner::batchVoteCount)
//                .flatMap(jvSetupRunner::batchOddsWinsPlaceBracketQuinella)
//                .flatMap(jvSetupRunner::batchOddsQuinella)
//                .flatMap(jvSetupRunner::batchAncestry)
//                .flatMap(jvSetupRunner::batchBreedingHorse)
//                .flatMap(jvSetupRunner::batchOffspring)
//                .flatMap(jvSetupRunner::batchRaceHorse)
//                .flatMap(jvSetupRunner::batchJockey)
//                .flatMap(jvSetupRunner::batchTrainer)
//                .flatMap(jvSetupRunner::batchOwner)
//                .flatMap(jvSetupRunner::batchBreeder)
//                .flatMap(jvSetupRunner::batchCourse)
//                .subscribe(new MySubscriber<>());
//
//    }
//
//
//    @Component
//    @RequiredArgsConstructor
//    private static class JvSetupRunner {
//
//        /**
//         * Reactive Mongo Client
//         */
//        private final ReactiveMongoTemplate reactiveTemplate;
//
//        /**
//         * JvLinkStoredRepositories
//         */
//        private final JvStoredRacingDetailsRepository racingDetailsRepository;
//        private final JvStoredHorseRacingDetailsRepository horseRacingDetailsRepository;
//        private final JvStoredRaceRefundRepository raceRefundRepository;
//        private final JvStoredVoteCountRepository voteCountRepository;
//        private final JvStoredOddsWinsPlaceBracketQuinellaRepository oddsWinsPlaceBracketQuinellaRepository;
//        private final JvStoredOddsQuinellaRepository oddsQuinellaRepository;
//        private final JvStoredAncestryRepository ancestryRepository;
//        private final JvStoredBreedingHorseRepository breedingHorseRepository;
//        private final JvStoredOffspringRepository offspringRepository;
//        private final JvStoredRaceHorseRepository raceHorseRepository;
//        private final JvStoredJockeyRepository jockeyRepository;
//        private final JvStoredTrainerRepository trainerRepository;
//        private final JvStoredOwnerRepository ownerRepository;
//        private final JvStoredBreederRepository breederRepository;
//        private final JvStoredCourseRepository courseRepository;
//
//
//        private <T> Flux<T> insert(Tuple2<List<T>, String> tuples) {
//            return reactiveTemplate.insert(tuples.getT1(), tuples.getT2());
//
////             一つのドキュメントに対して、transaction別いらない。
////                .inTransaction()
////                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
//        }
//
//        private static <T> Flux<T> logAndNullCheck(T anyModel) {
//            return Flux.just(anyModel)
//                    .doOnNext(model -> log.info("{}", model))
//                    .doOnNext(JvLinkUtil::fieldNotNull);
//        }
//
//
//        public Mono<LocalDateTime> batchRacingDetails(LocalDateTime yyyyMMddHHmmss) {
//            return racingDetailsRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "racingDetails"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchHorseRacingDetails(LocalDateTime yyyyMMddHHmmss) {
//            return horseRacingDetailsRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "horseRacingDetails"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchRaceRefund(LocalDateTime yyyyMMddHHmmss) {
//            return raceRefundRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "raceRefund"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        /**
//         * dataDiv=9 (レース中止)のデータを入れておくか？ あらかじめフィルターしておくか？
//         * →RacingDetailsには、データとして格納しておくが、VoteCountにはデータを入れない。
//         * つまりフィルターしておく！
//         */
//        public Mono<LocalDateTime> batchVoteCount(LocalDateTime yyyyMMddHHmmss) {
//            return voteCountRepository.readFlux(yyyyMMddHHmmss)
//                    .filter(model -> !"9".equals(model.getDataDiv()))
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(20)
//                    .map(chunk -> Tuples.of(chunk, "voteCount"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        /**
//         * VoteCountと同様の思想
//         */
//        public Mono<LocalDateTime> batchOddsWinsPlaceBracketQuinella(LocalDateTime yyyyMMddHHmmss) {
//            return oddsWinsPlaceBracketQuinellaRepository.readFlux(yyyyMMddHHmmss)
//                    .filter(model -> !"9".equals(model.getDataDiv()))
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "oddsWinsPlaceBracketQuinella"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        /**
//         * VoteCountと同様の思想
//         */
//        public Mono<LocalDateTime> batchOddsQuinella(LocalDateTime yyyyMMddHHmmss) {
//            return oddsQuinellaRepository.readFlux(yyyyMMddHHmmss)
//                    .filter(model -> !"9".equals(model.getDataDiv()))
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "oddsQuinella"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//
//        public Mono<LocalDateTime> batchAncestry(LocalDateTime yyyyMMddHHmmss) {
//            return ancestryRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "ancestry"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchBreedingHorse(LocalDateTime yyyyMMddHHmmss) {
//            return breedingHorseRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "breedingHorse"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchOffspring(LocalDateTime yyyyMMddHHmmss) {
//            return offspringRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "offspring"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//
//        public Mono<LocalDateTime> batchRaceHorse(LocalDateTime yyyyMMddHHmmss) {
//            return raceHorseRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "raceHorse"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchJockey(LocalDateTime yyyyMMddHHmmss) {
//            return jockeyRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "jockey"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchTrainer(LocalDateTime yyyyMMddHHmmss) {
//            return trainerRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "trainer"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchOwner(LocalDateTime yyyyMMddHHmmss) {
//            return ownerRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "owner"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchBreeder(LocalDateTime yyyyMMddHHmmss) {
//            return breederRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "breeder"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//
//        public Mono<LocalDateTime> batchCourse(LocalDateTime yyyyMMddHHmmss) {
//            return courseRepository.readFlux(yyyyMMddHHmmss)
//                    .flatMap(JvSetupRunner::logAndNullCheck)
//                    .buffer(50)
//                    .map(chunk -> Tuples.of(chunk, "course"))
//                    .flatMap(this::insert)
//                    .then(Mono.just(yyyyMMddHHmmss));
//        }
//    }
//
//}
//

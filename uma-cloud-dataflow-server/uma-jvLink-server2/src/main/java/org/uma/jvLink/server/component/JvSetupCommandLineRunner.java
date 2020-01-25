//package org.uma.platform.feed.application.component;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//import org.uma.platform.common.model.*;
//import org.uma.platform.common.model.odds.Quinella;
//import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
//import org.uma.platform.feed.application.configuration.SetupConfiguration;
//import org.uma.platform.feed.application.repository.impl.*;
//import org.uma.platform.feed.application.util.JvLinkUtil;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Profile("setup")
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JvSetupCommandLineRunner implements CommandLineRunner {
//
//    private final SetupConfiguration setUpConfiguration;
//
//    /**
//     * {@link JvSetupCommandLineRunner.JvSetupRunnerConfiguration}
//     */
//    private final Map<String, JvSetupRunner> jvSetupRunners;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (setUpConfiguration.getDataTypes() == null) {
//            throw new IllegalStateException("データ種別が選択されていません。");
//        }
//
//        List<JvSetupRunner> executors = jvSetupRunners.entrySet().stream()
//                .filter(e -> setUpConfiguration.getDataTypes().contains(e.getKey()))
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toList());
//
//
//        log.info("セットアップ開始");
//
//        for (JvSetupRunner jvSetupRunner : executors) {
//            jvSetupRunner.run();
//        }
//
//        log.info("セットアップ完了");
//    }
//
//
//    @FunctionalInterface
//    private interface JvSetupRunner {
//        /**
//         * 引数なし 戻り値なし。
//         */
//        void run();
//    }
//
//
//    @Configuration
//    @RequiredArgsConstructor
//    private static class JvSetupRunnerConfiguration {
//
//        private final SetupConfiguration setUpConfiguration;
//
//        /**
//         * Non ReactiveMongo Client
//         */
//        private final MongoTemplate mongoTemplate;
//
//
//        private static <T> Stream<T> logAndNullCheck(T anyModel) {
//            return Stream.of(anyModel)
//                    .peek(model -> log.info("{}", model))
//                    .peek(JvLinkUtil::fieldNotNull);
//        }
//
//
//        /**
//         * 非効率だが、1 thread 1行ずつ、dbに書き込みを行う。
//         * （書き込みスレッドだけ別にしようかな。。）
//         */
//
//        @Bean("RacingDetails")
//        public JvSetupRunner jvSetupRunnerRacingDetails(
//                JvStoredRacingDetailsRepository repository) {
//            return () -> {
//                try (Stream<RacingDetails> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "racingDetails"));
//                }
//            };
//        }
//
//        @Bean("HorseRacingDetails")
//        public JvSetupRunner jvSetupRunnerHorseRacingDetails(
//                JvStoredHorseRacingDetailsRepository repository) {
//            return () -> {
//                try (Stream<HorseRacingDetails> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.filter(model -> !"9".equals(model.getDataDiv()))
//                            .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "horseRacingDetails"));
//                }
//            };
//        }
//
//        @Bean("RaceRefund")
//        public JvSetupRunner jvSetupRunnerRaceRefund(
//                JvStoredRaceRefundRepository repository) {
//            return () -> {
//                try (Stream<RaceRefund> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.filter(model -> !"9".equals(model.getDataDiv()))
//                            .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "raceRefund"));
//                }
//            };
//        }
//
//        /**
//         * dataDiv=9 (レース中止)のデータを入れておくか？ あらかじめフィルターしておくか？
//         * →RacingDetailsには、データとして格納しておくが、VoteCountにはデータを入れない。
//         * つまりフィルターしておく！
//         */
//        @Bean("VoteCount")
//        public JvSetupRunner jvSetupRunnerVoteCount(
//                JvStoredVoteCountRepository repository) {
//            return () -> {
//                try (Stream<VoteCount> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.filter(model -> !"9".equals(model.getDataDiv()))
//                            .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "voteCount"));
//
//                }
//            };
//        }
//
//        /**
//         * VoteCountと同様の思想
//         */
//        @Bean("WinsPlaceBracketQuinella")
//        public JvSetupRunner jvSetupRunnerWinsPlaceBracketQuinella(
//                JvStoredOddsWinsPlaceBracketQuinellaRepository repository) {
//            return () -> {
//                try (Stream<WinsPlaceBracketQuinella> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.filter(model -> !"9".equals(model.getDataDiv()))
//                            .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "voteCount"));
//                }
//            };
//        }
//
//        /**
//         * VoteCountと同様の思想
//         */
//        @Bean("Quinella")
//        public JvSetupRunner jvSetupRunnerQuinella(
//                JvStoredOddsQuinellaRepository repository) {
//            return () -> {
//                try (Stream<Quinella> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.filter(model -> !"9".equals(model.getDataDiv()))
//                            .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "voteCount"));
//                }
//            };
//        }
//
//        @Bean("Ancestry")
//        public JvSetupRunner jvSetupRunnerAncestry(
//                JvStoredAncestryRepository repository) {
//            return () -> {
//                try (Stream<Ancestry> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "ancestry"));
//                }
//            };
//        }
//
//        @Bean("BreedingHorse")
//        public JvSetupRunner jvSetupRunnerBreedingHorse(
//                JvStoredBreedingHorseRepository repository) {
//            return () -> {
//                try (Stream<BreedingHorse> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "breedingHorse"));
//                }
//            };
//        }
//
//        @Bean("Offspring")
//        public JvSetupRunner jvSetupRunnerOffspring(
//                JvStoredOffspringRepository repository) {
//            return () -> {
//                try (Stream<Offspring> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "offspring"));
//                }
//            };
//        }
//
//        @Bean("RaceHorse")
//        public JvSetupRunner jvSetupRunnerRaceHorse(
//                JvStoredRaceHorseRepository repository) {
//            return () -> {
//                try (Stream<RaceHorse> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "raceHorse"));
//                }
//            };
//        }
//
//        @Bean("Jockey")
//        public JvSetupRunner jvSetupRunnerJockey(
//                JvStoredJockeyRepository repository) {
//            return () -> {
//                try (Stream<Jockey> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "jockey"));
//                }
//            };
//        }
//
//        @Bean("Trainer")
//        public JvSetupRunner jvSetupRunnerTrainer(
//                JvStoredTrainerRepository repository) {
//            return () -> {
//                try (Stream<Trainer> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "trainer"));
//                }
//            };
//        }
//
//        @Bean("Owner")
//        public JvSetupRunner jvSetupRunnerOwner(
//                JvStoredOwnerRepository repository) {
//            return () -> {
//                try (Stream<Owner> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "owner"));
//                }
//            };
//        }
//
//        @Bean("Breeder")
//        public JvSetupRunner jvSetupRunnerBreeder(
//                JvStoredBreederRepository repository) {
//            return () -> {
//                try (Stream<Breeder> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "breeder"));
//                }
//            };
//        }
//
//        @Bean("Course")
//        public JvSetupRunner jvSetupRunnerCourse(
//                JvStoredCourseRepository repository) {
//            return () -> {
//                try (Stream<Course> stream = repository
//                        .readStream(setUpConfiguration.getYyyyMMddHHmmss())) {
//                    stream.flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
//                            .forEach(model -> mongoTemplate.insert(model, "course"));
//                }
//            };
//        }
//
//    }
//
//}

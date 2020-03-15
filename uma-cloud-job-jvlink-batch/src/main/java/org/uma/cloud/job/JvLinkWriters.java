package org.uma.cloud.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.uma.cloud.common.model.Ancestry;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.BreedingHorse;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.HorseRacingDetails;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Offspring;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RaceHorseExclusion;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.VoteCount;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.r2dbc.query.Criteria.where;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JvLinkWriters {

    private final ResourceLoader resourceLoader;

    private final DatabaseClient client;


    /**
     * JvLinkWriter interface use R2DBC!!!
     */
    public abstract static class JvLinkReactiveItemWriter<T extends BaseModel> implements ItemWriter<T> {
    }


    /**
     * {@link JvLinkProcessors.JvLinkFunctionItemProcessor}にて、{@link BaseModel#isNecessary()}のデータを取り除く。
     */

    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter() {
        return new RepositoryItemWriterBuilder<Ancestry>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return new RepositoryItemWriterBuilder<Breeder>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter() {
        return new RepositoryItemWriterBuilder<BreedingHorse>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return new RepositoryItemWriterBuilder<Course>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter() {
        return new RepositoryItemWriterBuilder<HorseRacingDetails>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return new RepositoryItemWriterBuilder<Jockey>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter() {
        return new RepositoryItemWriterBuilder<Offspring>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        return new RepositoryItemWriterBuilder<Owner>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        return new RepositoryItemWriterBuilder<RaceHorse>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter() {
        return new JvLinkReactiveItemWriter<RaceHorseExclusion>() {
            @Override
            public void write(List<? extends RaceHorseExclusion> items) throws Exception {

                // raceId horseId

            }
        };

    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter() {
        return new JvLinkReactiveItemWriter<>() {
            @Override
            public void write(List<? extends RaceRefund> items) throws Exception {
                Flux.fromIterable(items)
                        .publishOn(Schedulers.immediate())
                        .subscribeOn(Schedulers.immediate())
                        .map(RaceRefund::getRaceId)
                        .collectList()
                        .flatMapMany(raceIds -> client.select().from(RaceRefund.class)
                                .matching(where("raceId").in(raceIds))
                                .as(RaceRefund.class)
                                .all())
                        .doOnNext(selected -> log.info("{}", selected))
                        .flatMap(selected -> Flux.fromIterable(items)
                                // selectしたもの以外を返す。
                                .filter(item -> !Objects.equals(item.getRaceId(), selected.getRaceId())))
                        .flatMap(model -> client.insert()
                                .into(RaceRefund.class)
                                .using(model)
                                .then())
                        .subscribe();
            }
        };

    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return new JvLinkReactiveItemWriter<>() {
            @Override
            public void write(List<? extends RacingDetails> items) throws Exception {
                Flux.fromIterable(items)
                        .publishOn(Schedulers.immediate())
                        .subscribeOn(Schedulers.immediate())
                        .map(RacingDetails::getRaceId)
                        .collectList()
                        .flatMapMany(raceIds -> client.select().from(RacingDetails.class)
                                .matching(where("raceId").in(raceIds))
                                .as(RacingDetails.class)
                                .all())
                        .doOnNext(selected -> log.info("{}", selected))
                        .flatMap(selected -> Flux.fromIterable(items)
                                // selectしたもの以外を返す。
                                .filter(item -> !Objects.equals(item.getRaceId(), selected.getRaceId())))
                        .flatMap(model -> client.insert()
                                .into(RacingDetails.class)
                                .using(model)
                                .then())
                        .subscribe();
            }
        };
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return new JvLinkReactiveItemWriter<>() {
            @Override
            public void write(List<? extends Trainer> items) throws Exception {
                Flux.fromIterable(items)
                        .publishOn(Schedulers.immediate())
                        .subscribeOn(Schedulers.immediate())
                        .map(Trainer::getTrainerCd)
                        .collectList()
                        .flatMapMany(trainerIds -> client.select().from(Trainer.class)
                                .matching(where("trainerId").in(trainerIds))
                                .as(Trainer.class)
                                .all())
                        .doOnNext(selected -> log.info("{}", selected))
                        .flatMap(selected -> Flux.fromIterable(items)
                                // selectしたもの以外を返す。
                                .filter(item -> !Objects.equals(item.getTrainerCd(), selected.getTrainerCd())))
                        .flatMap(model -> client.insert()
                                .into(Trainer.class)
                                .using(model)
                                .then())
                        .subscribe();
            }
        };
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter() {
        return new JvLinkReactiveItemWriter<>() {
            @Override
            public void write(List<? extends VoteCount> items) throws Exception {
                Flux.fromIterable(items)
                        .publishOn(Schedulers.immediate())
                        .subscribeOn(Schedulers.immediate())
                        .map(VoteCount::getRaceId)
                        .collectList()
                        .flatMapMany(raceIds -> client.select().from(VoteCount.class)
                                .matching(where("raceId").in(raceIds))
                                .as(VoteCount.class)
                                .all())
                        .doOnNext(selected -> log.info("{}", selected))
                        .flatMap(selected -> Flux.fromIterable(items)
                                // selectしたもの以外を返す。
                                .filter(item -> !Objects.equals(item.getRaceId(), selected.getRaceId())))
                        .flatMap(model -> client.insert()
                                .into(VoteCount.class)
                                .using(model)
                                .then())
                        .subscribe();
            }
        };

    }


//    @Bean
//    public ItemWriter<RacingDetails> racingDetailsItemWriter(MongoOperations templete) {
//                return new JsonFileItemWriterBuilder<RacingDetails>()
//                .jsonObjectMarshaller(JvLinkModelUtil::toJson)
//                .resource(resourceLoader.getResource("file:///tmp/once.json"))
//                .name(RacingDetails.class.getSimpleName())
//                .build();
//    }


//    public static class JvLinkReactiveItemWriter<T extends BaseModel> implements ItemWriter<T> {
//
//        private final DatabaseClient client;
//
//        public JvLinkReactiveItemWriter(DatabaseClient client) {
//            this.client = client;
//        }
//
//        @Override
//        public void write(List<? extends T> items) throws Exception {
//            Mono<Person> first = client.select()
//                    .from("legoset")
//                    .matching(where("firstname").is("John")
//                            .and("lastname").in("Doe", "White"))
//                    .orderBy(desc("id"))
//                    .as(Person.class)
//                    .fetch()
//                    .one();
//        }
//    }


}


package org.uma.cloud.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
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
import org.uma.cloud.job.repository.AncestryRepository;
import org.uma.cloud.job.repository.BreederRepository;
import org.uma.cloud.job.repository.BreedingHorseRepository;
import org.uma.cloud.job.repository.CourseRepository;
import org.uma.cloud.job.repository.HorseRacingDetailsRepository;
import org.uma.cloud.job.repository.JockeyRepository;
import org.uma.cloud.job.repository.OffspringRepository;
import org.uma.cloud.job.repository.OwnerRepository;
import org.uma.cloud.job.repository.RaceHorseExclusionRepository;
import org.uma.cloud.job.repository.RaceHorseRepository;
import org.uma.cloud.job.repository.RaceRefundRepository;
import org.uma.cloud.job.repository.RacingDetailsRepository;
import org.uma.cloud.job.repository.TrainerRepository;
import org.uma.cloud.job.repository.VoteCountRepository;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class JvLinkWriters {

    private final ResourceLoader resourceLoader;

    /**
     * たぶんデータ重複するものもあるけど、全部、素直に突っ込む。
     * <p>
     * ただし、、、
     * {@link JvLinkProcessors.JvLinkFunctionItemProcessor}にて、
     * {@link BaseModel#isNecessary()}のデータを、取り除く。
     */
    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter(AncestryRepository repository) {
        return new RepositoryItemWriterBuilder<Ancestry>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter(BreederRepository repository) {
        return new RepositoryItemWriterBuilder<Breeder>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter(BreedingHorseRepository repository) {
        return new RepositoryItemWriterBuilder<BreedingHorse>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter(CourseRepository repository) {
        return new RepositoryItemWriterBuilder<Course>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter(HorseRacingDetailsRepository repository) {
        return new RepositoryItemWriterBuilder<HorseRacingDetails>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter(JockeyRepository repository) {
        return new RepositoryItemWriterBuilder<Jockey>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter(OffspringRepository repository) {
        return new RepositoryItemWriterBuilder<Offspring>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter(OwnerRepository repository) {
        return new RepositoryItemWriterBuilder<Owner>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter(RaceHorseRepository repository) {
        return new RepositoryItemWriterBuilder<RaceHorse>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter(RaceHorseExclusionRepository repository) {
        return new RepositoryItemWriterBuilder<RaceHorseExclusion>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter(RaceRefundRepository repository) {
        return new RepositoryItemWriterBuilder<RaceRefund>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter(RacingDetailsRepository repository) {
        return new RepositoryItemWriterBuilder<RacingDetails>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter(TrainerRepository repository) {
        return new RepositoryItemWriterBuilder<Trainer>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter(VoteCountRepository repository) {
        return new RepositoryItemWriterBuilder<VoteCount>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }


//    @Bean
//    public ItemWriter<RacingDetails> racingDetailsItemWriter(MongoOperations templete) {
//                return new JsonFileItemWriterBuilder<RacingDetails>()
//                .jsonObjectMarshaller(JvLinkModelUtil::toJson)
//                .resource(resourceLoader.getResource("file:///tmp/once.json"))
//                .name(RacingDetails.class.getSimpleName())
//                .build();
//    }

    @RequiredArgsConstructor
    public static class JvLinkReactiveItemWriter<T extends BaseModel> implements ItemWriter<T> {

        private final DatabaseClient client;

        private Class<T> clazz;

        private Criteria matching;


        @Override
        public void write(List<? extends T> items) throws Exception {
            client.select().from(clazz)
                    .matching(matching)
//                    .orderBy(desc("id"))
                    .as(clazz)
                    .all();

        }

        public JvLinkReactiveItemWriter<T> selectFrom(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public JvLinkReactiveItemWriter<T> where(Criteria matching) {
            this.matching = matching;
            return this;
        }


    }


}


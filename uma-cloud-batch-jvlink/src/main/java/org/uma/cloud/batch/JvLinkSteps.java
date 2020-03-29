package org.uma.cloud.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.uma.cloud.batch.listener.JvLinkSkipPolicy;
import org.uma.cloud.batch.listener.JvLinkStepExecutionListener;
import org.uma.cloud.batch.listener.JvLinkWriterListener;
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
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Configuration
public class JvLinkSteps {

    private static final int CHUNK_SIZE = 100;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<String> reader;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JvLinkStepExecutionListener jvLinkStepExecutionListener;

    @Autowired
    private JvLinkSkipPolicy jvLinkSkipPolicy;

    private JpaTransactionManager jpaTransactionManager;

    @PostConstruct
    void init() {
        this.jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
    }


    private <T extends BaseModel> Step createStep(
            ItemProcessor<String, T> processor,
            ItemWriter<T> writer,
            Class<T> name) {
        return stepBuilderFactory.get(name.getSimpleName())
                .<String, T>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(jpaTransactionManager)
                .listener(jvLinkStepExecutionListener)
                .listener(new JvLinkWriterListener<>())
                .faultTolerant()
                .skipPolicy(jvLinkSkipPolicy)
                .build();
    }


    @Bean(name = "ancestry")
    public Step ancestryStep(ItemProcessor<String, Ancestry> processor, ItemWriter<Ancestry> writer) throws Exception {
        return createStep(processor, writer, Ancestry.class);
    }

    @Bean(name = "breeder")
    public Step breederStep(ItemProcessor<String, Breeder> processor, ItemWriter<Breeder> writer) throws Exception {
        return createStep(processor, writer, Breeder.class);
    }

    @Bean(name = "breedingHorse")
    public Step breedingHorseStep(ItemProcessor<String, BreedingHorse> processor, ItemWriter<BreedingHorse> writer) throws Exception {
        return createStep(processor, writer, BreedingHorse.class);
    }

    @Bean(name = "course")
    public Step courseStep(ItemProcessor<String, Course> processor, ItemWriter<Course> writer) throws Exception {
        return createStep(processor, writer, Course.class);
    }

    @Bean(name = "horseRacingDetails")
    public Step horseRacingDetailsStep(ItemProcessor<String, HorseRacingDetails> processor, ItemWriter<HorseRacingDetails> writer) throws Exception {
        return createStep(processor, writer, HorseRacingDetails.class);
    }

    @Bean(name = "jockey")
    public Step jockeyStep(ItemProcessor<String, Jockey> processor, ItemWriter<Jockey> writer) throws Exception {
        return createStep(processor, writer, Jockey.class);
    }

    @Bean(name = "offspring")
    public Step offspringStep(ItemProcessor<String, Offspring> processor, ItemWriter<Offspring> writer) throws Exception {
        return createStep(processor, writer, Offspring.class);
    }

    @Bean(name = "owner")
    public Step ownerStep(ItemProcessor<String, Owner> processor, ItemWriter<Owner> writer) throws Exception {
        return createStep(processor, writer, Owner.class);
    }

    @Bean(name = "raceHorse")
    public Step raceHorseStep(ItemProcessor<String, RaceHorse> processor, ItemWriter<RaceHorse> writer) throws Exception {
        return createStep(processor, writer, RaceHorse.class);
    }

    @Bean(name = "raceHorseExclusion")
    public Step raceHorseExclusionStep(ItemProcessor<String, RaceHorseExclusion> processor, ItemWriter<RaceHorseExclusion> writer) throws Exception {
        return createStep(processor, writer, RaceHorseExclusion.class);
    }

    @Bean(name = "raceRefund")
    public Step raceRefundStep(ItemProcessor<String, RaceRefund> processor, ItemWriter<RaceRefund> writer) throws Exception {
        return createStep(processor, writer, RaceRefund.class);
    }

    @Bean(name = "racingDetails")
    public Step racingDetailsStep(ItemProcessor<String, RacingDetails> processor, ItemWriter<RacingDetails> writer) throws Exception {
        return createStep(processor, writer, RacingDetails.class);
    }

    @Bean(name = "trainer")
    public Step trainerStep(ItemProcessor<String, Trainer> processor, ItemWriter<Trainer> writer) throws Exception {
        return createStep(processor, writer, Trainer.class);
    }

    @Bean(name = "voteCount")
    public Step voteCountStep(ItemProcessor<String, VoteCount> processor, ItemWriter<VoteCount> writer) throws Exception {
        return createStep(processor, writer, VoteCount.class);
    }

    @Bean(name = "winsPlaceBracketQuinella")
    public Step winsPlaceBracketQuinellaStep(ItemProcessor<String, WinsPlaceBracketQuinella> processor, ItemWriter<WinsPlaceBracketQuinella> writer) throws Exception {
        return createStep(processor, writer, WinsPlaceBracketQuinella.class);
    }

    @Bean(name = "quinella")
    public Step quinellaStep(ItemProcessor<String, Quinella> processor, ItemWriter<Quinella> writer) throws Exception {
        return createStep(processor, writer, Quinella.class);
    }

    @Bean(name = "quinellaPlace")
    public Step quinellaPlaceStep(ItemProcessor<String, QuinellaPlace> processor, ItemWriter<QuinellaPlace> writer) throws Exception {
        return createStep(processor, writer, QuinellaPlace.class);
    }

    @Bean(name = "exacta")
    public Step exactaStep(ItemProcessor<String, Exacta> processor, ItemWriter<Exacta> writer) throws Exception {
        return createStep(processor, writer, Exacta.class);
    }

    @Bean(name = "trio")
    public Step trioStep(ItemProcessor<String, Trio> processor, ItemWriter<Trio> writer) throws Exception {
        return createStep(processor, writer, Trio.class);
    }

    @Bean(name = "trifecta")
    public Step trifectaStep(ItemProcessor<String, Trifecta> processor, ItemWriter<Trifecta> writer) throws Exception {
        return createStep(processor, writer, Trifecta.class);
    }


    // taskletの例
//    @Bean
//    public Step step1(ItemReader<String> reader,
//                     ItemProcessor<String, RacingDetails> processor,
//                     ItemWriter<RacingDetails> writer) {
//
//        return stepBuilderFactory.get(RacingDetails.class.getSimpleName())
//                .tasklet((contribution, chunkContext) -> null)
//                .build();
//
//    }

}

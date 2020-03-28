package org.uma.cloud.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.batch.listener.JvLinkProcessorListener;
import org.uma.cloud.batch.listener.JvLinkStepExecutionListener;
import org.uma.cloud.batch.listener.JvLinkWriterListener;
import org.uma.cloud.common.model.Ancestry;
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

@Configuration
public class JvLinkSteps {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<String> reader;


    @Bean(name = "ancestry")
    public Step ancestryStep(ItemProcessor<String, Ancestry> processor, ItemWriter<Ancestry> writer) throws Exception {
        return stepBuilderFactory.get(Ancestry.class.getSimpleName())
                .<String, Ancestry>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "breeder")
    public Step breederStep(ItemProcessor<String, Breeder> processor, ItemWriter<Breeder> writer) throws Exception {
        return stepBuilderFactory.get(Breeder.class.getSimpleName())
                .<String, Breeder>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "breedingHorse")
    public Step breedingHorseStep(ItemProcessor<String, BreedingHorse> processor, ItemWriter<BreedingHorse> writer) throws Exception {
        return stepBuilderFactory.get(BreedingHorse.class.getSimpleName())
                .<String, BreedingHorse>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "course")
    public Step courseStep(ItemProcessor<String, Course> processor, ItemWriter<Course> writer) throws Exception {
        return stepBuilderFactory.get(Course.class.getSimpleName())
                .<String, Course>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "horseRacingDetails")
    public Step horseRacingDetailsStep(ItemProcessor<String, HorseRacingDetails> processor, ItemWriter<HorseRacingDetails> writer) throws Exception {
        return stepBuilderFactory.get(HorseRacingDetails.class.getSimpleName())
                .<String, HorseRacingDetails>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "jockey")
    public Step jockeyStep(ItemProcessor<String, Jockey> processor, ItemWriter<Jockey> writer) throws Exception {
        return stepBuilderFactory.get(Jockey.class.getSimpleName())
                .<String, Jockey>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "offspring")
    public Step offspringStep(ItemProcessor<String, Offspring> processor, ItemWriter<Offspring> writer) throws Exception {
        return stepBuilderFactory.get(Offspring.class.getSimpleName())
                .<String, Offspring>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "owner")
    public Step ownerStep(ItemProcessor<String, Owner> processor, ItemWriter<Owner> writer) throws Exception {
        return stepBuilderFactory.get(Owner.class.getSimpleName())
                .<String, Owner>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "raceHorse")
    public Step raceHorseStep(ItemProcessor<String, RaceHorse> processor, ItemWriter<RaceHorse> writer) throws Exception {
        return stepBuilderFactory.get(RaceHorse.class.getSimpleName())
                .<String, RaceHorse>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "raceHorseExclusion")
    public Step raceHorseExclusionStep(ItemProcessor<String, RaceHorseExclusion> processor, ItemWriter<RaceHorseExclusion> writer) throws Exception {
        return stepBuilderFactory.get(RaceHorseExclusion.class.getSimpleName())
                .<String, RaceHorseExclusion>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "raceRefund")
    public Step raceRefundStep(ItemProcessor<String, RaceRefund> processor, ItemWriter<RaceRefund> writer) throws Exception {
        return stepBuilderFactory.get(RaceRefund.class.getSimpleName())
                .<String, RaceRefund>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "racingDetails")
    public Step racingDetailsStep(ItemProcessor<String, RacingDetails> processor, ItemWriter<RacingDetails> writer) throws Exception {
        return stepBuilderFactory.get(RacingDetails.class.getSimpleName())
                .<String, RacingDetails>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "trainer")
    public Step trainerStep(ItemProcessor<String, Trainer> processor, ItemWriter<Trainer> writer) throws Exception {
        return stepBuilderFactory.get(Trainer.class.getSimpleName())
                .<String, Trainer>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "voteCount")
    public Step voteCountStep(ItemProcessor<String, VoteCount> processor, ItemWriter<VoteCount> writer) throws Exception {
        return stepBuilderFactory.get(VoteCount.class.getSimpleName())
                .<String, VoteCount>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "winsPlaceBracketQuinella")
    public Step winsPlaceBracketQuinellaStep(ItemProcessor<String, WinsPlaceBracketQuinella> processor, ItemWriter<WinsPlaceBracketQuinella> writer) throws Exception {
        return stepBuilderFactory.get(WinsPlaceBracketQuinella.class.getSimpleName())
                .<String, WinsPlaceBracketQuinella>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "quinella")
    public Step quinellaStep(ItemProcessor<String, Quinella> processor, ItemWriter<Quinella> writer) throws Exception {
        return stepBuilderFactory.get(Quinella.class.getSimpleName())
                .<String, Quinella>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "quinellaPlace")
    public Step quinellaPlaceStep(ItemProcessor<String, QuinellaPlace> processor, ItemWriter<QuinellaPlace> writer) throws Exception {
        return stepBuilderFactory.get(QuinellaPlace.class.getSimpleName())
                .<String, QuinellaPlace>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "exacta")
    public Step exactaStep(ItemProcessor<String, Exacta> processor, ItemWriter<Exacta> writer) throws Exception {
        return stepBuilderFactory.get(Exacta.class.getSimpleName())
                .<String, Exacta>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "trio")
    public Step trioStep(ItemProcessor<String, Trio> processor, ItemWriter<Trio> writer) throws Exception {
        return stepBuilderFactory.get(Trio.class.getSimpleName())
                .<String, Trio>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
    }

    @Bean(name = "trifecta")
    public Step trifectaStep(ItemProcessor<String, Trifecta> processor, ItemWriter<Trifecta> writer) throws Exception {
        return stepBuilderFactory.get(Trifecta.class.getSimpleName())
                .<String, Trifecta>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkStepExecutionListener())
                .listener(new JvLinkProcessorListener())
                .listener(new JvLinkWriterListener())
                .faultTolerant()
                .skipPolicy(new JvLinkSkipPolicy())
                .build();
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

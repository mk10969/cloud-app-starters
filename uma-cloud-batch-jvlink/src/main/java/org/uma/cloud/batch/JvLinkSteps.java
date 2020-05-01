package org.uma.cloud.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.batch.listener.JvLinkSkipListener;
import org.uma.cloud.batch.listener.JvLinkSkipPolicy;
import org.uma.cloud.batch.listener.JvLinkStepExecutionListener;
import org.uma.cloud.batch.listener.JvLinkWriterListener;
import org.uma.cloud.common.model.BaseModel;
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

@Configuration
public class JvLinkSteps {

    private static final int CHUNK_SIZE = 100;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<String> reader;

    @Autowired
    private JvLinkStepExecutionListener jvLinkStepExecutionListener;

    @Autowired
    private JvLinkSkipPolicy jvLinkSkipPolicy;


    private <T extends BaseModel> Step createStep(
            ItemProcessor<String, T> processor,
            ItemWriter<T> writer,
            Class<T> name) {
        return stepBuilderFactory.get(name.getSimpleName())
                .<String, T>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(jvLinkStepExecutionListener)
                .listener(new JvLinkWriterListener<>())
                .listener(new JvLinkSkipListener<>())
                .faultTolerant()
                .skipPolicy(jvLinkSkipPolicy)
                .build();
    }


    @Bean
    public Step bloodAncestry(ItemProcessor<String, BloodAncestry> processor, ItemWriter<BloodAncestry> writer) throws Exception {
        return createStep(processor, writer, BloodAncestry.class);
    }

    @Bean
    public Step breeder(ItemProcessor<String, Breeder> processor, ItemWriter<Breeder> writer) throws Exception {
        return createStep(processor, writer, Breeder.class);
    }

    @Bean
    public Step bloodBreeding(ItemProcessor<String, BloodBreeding> processor, ItemWriter<BloodBreeding> writer) throws Exception {
        return createStep(processor, writer, BloodBreeding.class);
    }

    @Bean
    public Step course(ItemProcessor<String, Course> processor, ItemWriter<Course> writer) throws Exception {
        return createStep(processor, writer, Course.class);
    }

    @Bean
    public Step racingHorseDetail(ItemProcessor<String, RacingHorseDetail> processor, ItemWriter<RacingHorseDetail> writer) throws Exception {
        return createStep(processor, writer, RacingHorseDetail.class);
    }

    @Bean
    public Step jockey(ItemProcessor<String, Jockey> processor, ItemWriter<Jockey> writer) throws Exception {
        return createStep(processor, writer, Jockey.class);
    }

    @Bean
    public Step bloodLine(ItemProcessor<String, BloodLine> processor, ItemWriter<BloodLine> writer) throws Exception {
        return createStep(processor, writer, BloodLine.class);
    }

    @Bean
    public Step ownerStep(ItemProcessor<String, Owner> processor, ItemWriter<Owner> writer) throws Exception {
        return createStep(processor, writer, Owner.class);
    }

    @Bean
    public Step raceHorse(ItemProcessor<String, RaceHorse> processor, ItemWriter<RaceHorse> writer) throws Exception {
        return createStep(processor, writer, RaceHorse.class);
    }

    @Bean
    public Step racingHorseExclusion(ItemProcessor<String, RacingHorseExclusion> processor, ItemWriter<RacingHorseExclusion> writer) throws Exception {
        return createStep(processor, writer, RacingHorseExclusion.class);
    }

    @Bean
    public Step racingRefund(ItemProcessor<String, RacingRefund> processor, ItemWriter<RacingRefund> writer) throws Exception {
        return createStep(processor, writer, RacingRefund.class);
    }

    @Bean
    public Step racingDetail(ItemProcessor<String, RacingDetail> processor, ItemWriter<RacingDetail> writer) throws Exception {
        return createStep(processor, writer, RacingDetail.class);
    }

    @Bean
    public Step trainer(ItemProcessor<String, Trainer> processor, ItemWriter<Trainer> writer) throws Exception {
        return createStep(processor, writer, Trainer.class);
    }

    @Bean
    public Step racingVote(ItemProcessor<String, RacingVote> processor, ItemWriter<RacingVote> writer) throws Exception {
        return createStep(processor, writer, RacingVote.class);
    }

    @Bean
    public Step winsShowBracketQ(ItemProcessor<String, WinsShowBracketQ> processor, ItemWriter<WinsShowBracketQ> writer) throws Exception {
        return createStep(processor, writer, WinsShowBracketQ.class);
    }

    @Bean
    public Step quinella(ItemProcessor<String, Quinella> processor, ItemWriter<Quinella> writer) throws Exception {
        return createStep(processor, writer, Quinella.class);
    }

    @Bean
    public Step quinellaPlace(ItemProcessor<String, QuinellaPlace> processor, ItemWriter<QuinellaPlace> writer) throws Exception {
        return createStep(processor, writer, QuinellaPlace.class);
    }

    @Bean
    public Step exacta(ItemProcessor<String, Exacta> processor, ItemWriter<Exacta> writer) throws Exception {
        return createStep(processor, writer, Exacta.class);
    }

    @Bean
    public Step trio(ItemProcessor<String, Trio> processor, ItemWriter<Trio> writer) throws Exception {
        return createStep(processor, writer, Trio.class);
    }

    @Bean
    public Step trifecta(ItemProcessor<String, Trifecta> processor, ItemWriter<Trifecta> writer) throws Exception {
        return createStep(processor, writer, Trifecta.class);
    }

}

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
import org.uma.cloud.common.entity.BaseModel;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.entity.OddsWinsShowBracketQ;

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
    public Step breeder(ItemProcessor<String, DiffBreeder> processor, ItemWriter<DiffBreeder> writer) throws Exception {
        return createStep(processor, writer, DiffBreeder.class);
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
    public Step jockey(ItemProcessor<String, DiffJockey> processor, ItemWriter<DiffJockey> writer) throws Exception {
        return createStep(processor, writer, DiffJockey.class);
    }

    @Bean
    public Step bloodLine(ItemProcessor<String, BloodLine> processor, ItemWriter<BloodLine> writer) throws Exception {
        return createStep(processor, writer, BloodLine.class);
    }

    @Bean
    public Step ownerStep(ItemProcessor<String, DiffOwner> processor, ItemWriter<DiffOwner> writer) throws Exception {
        return createStep(processor, writer, DiffOwner.class);
    }

    @Bean
    public Step raceHorse(ItemProcessor<String, DiffRaceHorse> processor, ItemWriter<DiffRaceHorse> writer) throws Exception {
        return createStep(processor, writer, DiffRaceHorse.class);
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
    public Step trainer(ItemProcessor<String, DiffTrainer> processor, ItemWriter<DiffTrainer> writer) throws Exception {
        return createStep(processor, writer, DiffTrainer.class);
    }

    @Bean
    public Step racingVote(ItemProcessor<String, RacingVote> processor, ItemWriter<RacingVote> writer) throws Exception {
        return createStep(processor, writer, RacingVote.class);
    }

    @Bean
    public Step winsShowBracketQ(ItemProcessor<String, OddsWinsShowBracketQ> processor, ItemWriter<OddsWinsShowBracketQ> writer) throws Exception {
        return createStep(processor, writer, OddsWinsShowBracketQ.class);
    }

    @Bean
    public Step quinella(ItemProcessor<String, OddsQuinella> processor, ItemWriter<OddsQuinella> writer) throws Exception {
        return createStep(processor, writer, OddsQuinella.class);
    }

    @Bean
    public Step quinellaPlace(ItemProcessor<String, OddsQuinellaPlace> processor, ItemWriter<OddsQuinellaPlace> writer) throws Exception {
        return createStep(processor, writer, OddsQuinellaPlace.class);
    }

    @Bean
    public Step exacta(ItemProcessor<String, OddsExacta> processor, ItemWriter<OddsExacta> writer) throws Exception {
        return createStep(processor, writer, OddsExacta.class);
    }

    @Bean
    public Step trio(ItemProcessor<String, OddsTrio> processor, ItemWriter<OddsTrio> writer) throws Exception {
        return createStep(processor, writer, OddsTrio.class);
    }

    @Bean
    public Step trifecta(ItemProcessor<String, OddsTrifecta> processor, ItemWriter<OddsTrifecta> writer) throws Exception {
        return createStep(processor, writer, OddsTrifecta.class);
    }

}

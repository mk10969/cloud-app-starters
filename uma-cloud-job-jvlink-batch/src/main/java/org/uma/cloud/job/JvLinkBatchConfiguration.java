package org.uma.cloud.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetails;

@Configuration
@EnableTask
@EnableBatchProcessing
@RequiredArgsConstructor
public class JvLinkBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("JvLinkBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(ItemReader<String> reader,
                     ItemProcessor<String, RacingDetails> processor,
                     ItemWriter<RacingDetails> writer) {

        return stepBuilderFactory.get(RacingDetails.class.getSimpleName())
                .<String, RacingDetails>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new JvLinkBatchProcessorListener())
                .faultTolerant()
                .skipPolicy(new JvLinkBatchSkipPolicy())
                .build();
    }


}

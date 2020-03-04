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
import org.uma.cloud.job.listener.JvLinkProcessorListener;
import org.uma.cloud.job.listener.JvLinkStepExecutionListener;
import org.uma.cloud.job.listener.JvLinkWriterListener;

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

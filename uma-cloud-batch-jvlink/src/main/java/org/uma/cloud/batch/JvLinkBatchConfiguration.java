package org.uma.cloud.batch;

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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.uma.cloud.batch.listener.JvLinkProcessorListener;
import org.uma.cloud.batch.listener.JvLinkStepExecutionListener;
import org.uma.cloud.batch.listener.JvLinkWriterListener;
import org.uma.cloud.common.model.RacingDetails;

@Configuration
@EnableTask
@EnableBatchProcessing
@EnableConfigurationProperties(JvLinkBatchProperties.class)
@EnableJpaRepositories
@EntityScan("org.uma.cloud.common.model")
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
                     ItemWriter<RacingDetails> writer) throws Exception {

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

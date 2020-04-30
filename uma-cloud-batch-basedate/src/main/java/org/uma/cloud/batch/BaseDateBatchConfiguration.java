package org.uma.cloud.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessBaseDate;
import org.uma.cloud.common.service.business.BusinessBaseDateService;

@Configuration
public class BaseDateBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BusinessBaseDateService baseDateService;


    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return jobBuilderFactory.get("BaseDateBatchKob")
                .incrementer(new RunIdIncrementer())
                .repository(jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("baseDate Insert")
                .tasklet((contribution, chunkContext) -> {
                    BusinessBaseDate baseDate = baseDateService.insertBaseDate();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // データとれた！！！！OK
//    @Bean
//    public Step stepRead() {
//        return stepBuilderFactory.get("baseDate Read")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println(service.getLatestBaseDate());
//                    return RepeatStatus.FINISHED;
//                })
//                .transactionManager(jpaTransactionManager)
//                .build();
//    }

}

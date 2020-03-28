package org.uma.cloud.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(JvLinkBatchProperties.class)
public class JvLinkBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Map<String, Step> stepMap;

    @Autowired
    private JvLinkBatchProperties jvLinkBatchProperties;


    @Bean
    public Job job(JobRepository jobRepository) {
        Step step = findStep().orElseThrow(() ->
                new IllegalStateException("propertiesの指定に誤りがあります。次から指定してください。: " + stepMap.keySet()));

        return jobBuilderFactory.get("JvLinkBatchJob")
                .incrementer(new RunIdIncrementer())
                .repository(jobRepository)
                .start(step)
                .build();
    }

    private Optional<Step> findStep() {
        return stepMap.entrySet().stream()
                .filter(step -> step.getKey().equals(jvLinkBatchProperties.getModelName()))
                .map(Map.Entry::getValue)
                .findFirst();
    }

}

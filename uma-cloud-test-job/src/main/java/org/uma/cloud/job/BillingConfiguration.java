package org.uma.cloud.job;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;


@Configuration
@EnableTask
@EnableBatchProcessing
public class BillingConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${usage.file.name:classpath:usageinfo.json}")
    private Resource usageResource;


//    public Job job1() {
//
//    }

    @Bean
    public ItemReader<Usage> jsonItemReader() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<Usage> jacksonJsonObjectReader = new JacksonJsonObjectReader<>(Usage.class);
        jacksonJsonObjectReader.setMapper(objectMapper);

        return new JsonItemReaderBuilder<Usage>()
                .jsonObjectReader(jacksonJsonObjectReader)
                .resource(usageResource)
                .name("UsageJsonItemReader")
                .build();
    }

    @Bean
    public ItemProcessor<Usage, Bill> billItemProcessor() {
        return new BillProcessor();
    }

    @Bean
    public ItemWriter<Bill> itemWriter(DataSource dataSource) {
//        JdbcBatchItemWriterBuilder jdbcBatchItemWriterBuilder = new JdbcBatchItemWriterBuilder<Bill>().beanMapped();

        JsonObjectMarshaller<Bill> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        Resource billResource = ResourceUtils.getFile();

        return new JsonFileItemWriter<>(billResource, jsonObjectMarshaller);

    }

}

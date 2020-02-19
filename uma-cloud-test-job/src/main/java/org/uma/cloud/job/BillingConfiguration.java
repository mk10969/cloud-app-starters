package org.uma.cloud.job;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;


@Configuration
@EnableTask
@EnableBatchProcessing
public class BillingConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(
            JsonItemReader<Usage> reader,
            ItemProcessor<Usage, Bill> processor,
            JsonFileItemWriter<Bill> writer) {

        ItemWriteListener<Bill> listener = new ItemWriteListener<>() {
            @Override
            public void beforeWrite(List<? extends Bill> items) {
                System.out.println("before:" + items);
            }

            @Override
            public void afterWrite(List<? extends Bill> items) {
                System.out.println("after:" + items);
            }

            @Override
            public void onWriteError(Exception exception, List<? extends Bill> items) {

            }
        };


        Step step = stepBuilderFactory.get("BillProcessing")
                .<Usage, Bill>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();

        return jobBuilderFactory.get("BillJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public JsonItemReader<Usage> jsonItemReader() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<Usage> jacksonJsonObjectReader = new JacksonJsonObjectReader<>(Usage.class);
        jacksonJsonObjectReader.setMapper(objectMapper);

        return new JsonItemReaderBuilder<Usage>()
                .jsonObjectReader(jacksonJsonObjectReader)
                .resource(new ClassPathResource("usageinfo.json"))
                .name("UsageJsonItemReader")
                .build();
    }

    @Bean
    public ItemProcessor<Usage, Bill> billItemProcessor() {
        return new BillProcessor();
    }

    @Bean
    public JsonFileItemWriter<Bill> itemWriter() {
//        JdbcBatchItemWriterBuilder jdbcBatchItemWriterBuilder = new JdbcBatchItemWriterBuilder<Bill>().beanMapped();

        return new JsonFileItemWriterBuilder<Bill>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
//                .resource(resourceLoader.getResource("classpath:output.json"))
                .resource(new ClassPathResource("output.json"))
                .name("BillJsonFileItemWriter")
                .build();
    }

}

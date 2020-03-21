package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.batch.repository.RacingDetailsRepository;
import org.uma.cloud.common.model.RacingDetails;

@Configuration
public class JvLinkWriters {

//    @Bean
//    public ItemWriter<RacingDetails> racingDetailsItemWriter(RacingDetailsRepository repository) {
//        return new RepositoryItemWriterBuilder<RacingDetails>()
//                .repository(repository)
//                .methodName("save")
//                .build();
//    }

    @Autowired
    public RacingDetailsRepository repository;

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return items -> repository.saveAll(items);
    }

}

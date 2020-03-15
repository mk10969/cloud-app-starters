package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.RacingDetails;

@Configuration
public class JvLinkWriters {


    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return items -> items.forEach(System.out::println);
    }


}

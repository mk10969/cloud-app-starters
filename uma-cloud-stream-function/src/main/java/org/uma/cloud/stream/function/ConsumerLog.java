package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerLog {

    @Bean
    Consumer<String> log() {
        return log::info;
    }

}

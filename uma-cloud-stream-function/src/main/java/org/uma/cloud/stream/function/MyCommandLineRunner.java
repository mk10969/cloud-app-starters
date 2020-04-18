package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.utils.javatuples.Pair;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ConsumerCommandLineRunner {

    @Autowired
    private Supplier<Flux<Pair<String, LocalDateTime>>> updateBusinessRace;


    /**
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> updateBusinessRace.get()
                .toStream()
                .forEach(i -> log.info("今週のレース: {} {}", i.getValue1(), i.getValue2()));
    }

}

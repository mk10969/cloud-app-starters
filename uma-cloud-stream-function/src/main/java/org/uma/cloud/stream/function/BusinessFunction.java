package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.stream.service.BusinessRxService;
import org.uma.cloud.stream.service.JvLinkWebService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Configuration
public class BusinessFunction {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRxService rxService;


    /**
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> updateRacing().apply(jvLinkWebService.getRaceIds()).toStream()
                .forEach(raceId -> log.info("今週のレース: {}", raceId));
    }


    @Bean
    public Function<Flux<String>, Flux<String>> updateRacing() {
        return raceId -> raceId
                .flatMap(rxService::updateOneRacing)
                .map(BusinessRacing::getRaceId);
    }


    @Bean
    public Function<Flux<String>, Mono<Void>> updateRacingHorse() {
        return raceId -> raceId
                .flatMap(rxService::updateAllRacingHorse)
                .then();
    }


    @Bean
    public Function<Flux<JvWatchEventFunction.Event>, Flux<String>> updateRacingRefund() {
        return event -> event
                .map(JvWatchEventFunction.Event::getRaceId)
                .flatMap(rxService::updateRacingRefund)
                .map(BusinessRacingRefund::getRaceId);
    }


    /**
     * データ区分　2 or 6でフィルターでOK
     * 9 がレース中止なので、これはエラー処理に加えておこう！
     */
}

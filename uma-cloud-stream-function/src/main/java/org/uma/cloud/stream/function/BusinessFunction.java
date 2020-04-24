package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.service.business.BusinessRacingHorseService;
import org.uma.cloud.common.service.business.BusinessRacingRefundService;
import org.uma.cloud.common.service.business.BusinessRacingService;
import org.uma.cloud.stream.service.JvLinkWebService;
import org.uma.cloud.stream.util.BusinessMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class BusinessFunction {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRacingService racingService;

    @Autowired
    private BusinessRacingHorseService racingHorseService;

    @Autowired
    private BusinessRacingRefundService racingRefundService;

    /**
     * Steam起動時に、今週のレースIDとレース開始時刻モデルを生成する。
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.init", name = "enabled", havingValue = "true")
    public CommandLineRunner initThisWeekRace() {
        return args -> updateBusinessRace().get()
                .toStream()
                .forEach(raceId -> log.info("今週のレース: {}", raceId));
    }

    /**
     * レースを更新する
     */
    @Bean
    public Supplier<Flux<String>> updateBusinessRace() {
        return () -> jvLinkWebService.raceDetailWithFriday()
                .map(BusinessMapper::toBusinessRacing)
                .doOnNext(racingService::update) //新しくなったら更新する。exist check不要
                .map(BusinessRacing::getRaceId);
    }

    /**
     * TODO: 変更がかかった時に、走らせたいね！
     */
    @Bean
    public Function<Flux<String>, Mono<Void>> updateBusinessRacingHorse() {
        return raceId -> raceId
                .flatMap(jvLinkWebService::racingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .doOnNext(racingHorseService::update)
                .then();
    }


    /**
     * データ区分　2 or 6でフィルターでOK
     * 9 がレース中止なので、これはエラー処理に加えておこう！
     */
}

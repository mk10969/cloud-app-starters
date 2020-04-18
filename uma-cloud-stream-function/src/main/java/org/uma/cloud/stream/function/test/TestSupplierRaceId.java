package org.uma.cloud.stream.function.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRacing;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class TestSupplierRaceId {

    @PollableBean
    public Supplier<Flux<String>> testRaceId() {
        return () -> Flux.defer(fluxSupplier())
                .map(BusinessRacing::getRaceId)
                .sort()
                .delayElements(Duration.ofMillis(300L)) //データをdelayさせることができる。
                .log();
    }

    private Supplier<Flux<BusinessRacing>> fluxSupplier() {
        return () -> baseDate().zipWith(startTime(), (a, b) -> {
            BusinessRacing model = new BusinessRacing();
            model.setRaceId(a);
            model.setRaceStartDateTime(b);
            return model;
        });
    }

    private Flux<String> baseDate() {
        return Flux.fromIterable(Arrays.asList(
//                "2020041203010201", "2020041203010202", "2020041203010203", "2020041203010204",
//                "2020041203010205", "2020041203010206", "2020041203010207", "2020041203010208",
//                "2020041203010209", "2020041203010210", "2020041203010211", "2020041203010212",
//                "2020041206030601", "2020041206030602", "2020041206030603", "2020041206030604",
//                "2020041206030605", "2020041206030606", "2020041206030607", "2020041206030608",
//                "2020041206030609", "2020041206030610", "2020041206030611", "2020041206030612",
//                "2020041209020601", "2020041209020602", "2020041209020603", "2020041209020604",
                "2020041209020605", "2020041209020606", "2020041209020607", "2020041209020608",
                "2020041209020609", "2020041209020610", "2020041209020611", "2020041209020612"
        ));
    }

    private Flux<LocalDateTime> startTime() {
        return Flux.fromIterable(Arrays.asList(
//                "2020-04-12 09:50:00", "2020-04-12 10:15:00", "2020-04-12 10:45:00", "2020-04-12 11:15:00",
//                "2020-04-12 12:05:00", "2020-04-12 12:35:00", "2020-04-12 13:05:00", "2020-04-12 13:35:00",
//                "2020-04-12 14:05:00", "2020-04-12 14:40:00", "2020-04-12 15:20:00", "2020-04-12 16:00:00",
//                "2020-04-12 09:55:00", "2020-04-12 10:25:00", "2020-04-12 10:55:00", "2020-04-12 11:25:00",
//                "2020-04-12 12:15:00", "2020-04-12 12:45:00", "2020-04-12 13:15:00", "2020-04-12 13:45:00",
//                "2020-04-12 14:15:00", "2020-04-12 14:50:00", "2020-04-12 15:30:00", "2020-04-12 16:10:00",
//                "2020-04-12 10:05:00", "2020-04-12 10:35:00", "2020-04-12 11:05:00", "2020-04-12 11:35:00",
                "2020-04-12 12:25:00", "2020-04-12 12:55:00", "2020-04-12 13:25:00", "2020-04-12 13:55:00",
                "2020-04-12 14:25:00", "2020-04-12 15:00:00", "2020-04-12 15:40:00", "2020-04-12 16:25:00"))
                .map(TestSupplierRaceId::toLocalDateTime);
    }

    private static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

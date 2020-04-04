package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.common.configuration.JvLinkFunction;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.stream.subscriber.DefaultSubscriber;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class SupplierJvLinkRaceId {

    @Autowired
    private WebClient webClient;

    @Autowired
    private JvLinkFunction jvLinkFunction;


    private static final Supplier<Long> now = System::currentTimeMillis;

//    public Supplier<String> getRaceId() {
//        () -> {
//        }
//    }

    @PostConstruct
    void init() {
        webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/racingDetails/thisWeek").build())
                .retrieve()
                .bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray)
                .map(jvLinkFunction.decode().andThen(jvLinkFunction::racingDetailsFunction))
                .doOnNext(System.out::println)
                .map(i -> i.getRaceId() + ":" + i.getHoldingDate() + ":" + i.getStartTime())
                .toStream()
                .forEach(System.out::println);
    }


    private Flux<RaceRefund> raceRefund(String raceId) {
        return webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/raceRefund")
                        .queryParam("raceId", raceId)
                        .build())
                .retrieve()
                .bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray)
                .map(jvLinkFunction.decode().andThen(jvLinkFunction::raceRefundFunction));
    }

    private void reqeust() {
        webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/raceRefund/{epochSecond}")
                        .build(now.get()))
                .retrieve()
                .bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray)
                .subscribe(new DefaultSubscriber<>());
    }


}

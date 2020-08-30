package org.uma.cloud.stream.type;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.OddsExacta;
import org.uma.cloud.common.model.OddsQuinella;
import org.uma.cloud.common.model.OddsQuinellaPlace;
import org.uma.cloud.common.model.OddsWinsShowBracketQ;
import org.uma.cloud.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class TimeSeriesSink {

    @Autowired
    private InfluxDB influxDB;


    public void writePoint(Point point) {
        influxDB.write(point);
    }


    public Mono<String> oddsToPoint(OddsWinsShowBracketQ oddsWinsShowBracketQ) {
        // 枠連は捨てる。
        // winsPlaceBracketQuinella.getBracketQuinellaOdds();

        long timestamp = DateUtil.toEpochMilli(oddsWinsShowBracketQ.timestamp());

        Flux<Point> winFlux = Flux.fromStream(oddsWinsShowBracketQ.getWinOdds().stream())
                .map(winOdds -> Point.measurement(winOdds.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsWinsShowBracketQ.getRaceId())
                        .tag("horseNo", winOdds.getHorseNo())
                        .addField("odds", winOdds.getOdds())
                        .addField("rank", winOdds.getBetRank())
                        .build());

        Flux<Point> placeFlux = Flux.fromStream(oddsWinsShowBracketQ.getShowOdds().stream())
                .map(placeOdds -> Point.measurement(placeOdds.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsWinsShowBracketQ.getRaceId())
                        .tag("horseNo", placeOdds.getHorseNo())
                        .addField("oddsMin", placeOdds.getOddsMin())
                        .addField("oddsMax", placeOdds.getOddsMax())
                        .addField("rank", placeOdds.getBetRank())
                        .build());
        // 合わせる。
        return winFlux.concatWith(placeFlux)
                .doOnNext(this::writePoint)
                .then(Mono.just(oddsWinsShowBracketQ.getRaceId()));
    }


    public Mono<String> oddsToPoint(OddsQuinella oddsQuinella) {
        long timestamp = DateUtil.toEpochMilli(oddsQuinella.timestamp());

        return Flux.fromStream(oddsQuinella.getQuinellaOdds().stream())
                .map(quinellaOdds -> Point.measurement(oddsQuinella.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsQuinella.getRaceId())
                        .tag("pair", quinellaOdds.getPairNo().toString())
                        .addField("odds", quinellaOdds.getOdds())
                        .addField("rank", quinellaOdds.getBetRank())
                        .build())
                .doOnNext(this::writePoint)
                .then(Mono.just(oddsQuinella.getRaceId()));
    }

    public Mono<String> oddsToPoint(OddsQuinellaPlace oddsQuinellaPlace) {
        long timestamp = DateUtil.toEpochMilli(oddsQuinellaPlace.timestamp());

        return Flux.fromStream(oddsQuinellaPlace.getQuinellaPlaceOdds().stream())
                .map(quinellaPlaceOdds -> Point.measurement(oddsQuinellaPlace.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsQuinellaPlace.getRaceId())
                        .tag("pair", quinellaPlaceOdds.getPairNo().toString())
                        .addField("oddsMin", quinellaPlaceOdds.getOddsMin())
                        .addField("oddsMax", quinellaPlaceOdds.getOddsMax())
                        .addField("rank", quinellaPlaceOdds.getBetRank())
                        .build())
                .doOnNext(this::writePoint)
                .then(Mono.just(oddsQuinellaPlace.getRaceId()));
    }

    public Mono<String> oddsToPoint(OddsExacta oddsExacta) {
        long timestamp = DateUtil.toEpochMilli(oddsExacta.timestamp());

        return Flux.fromStream(oddsExacta.getExactaOdds().stream())
                .map(exactaOdds -> Point.measurement(oddsExacta.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsExacta.getRaceId())
                        .tag("pair", exactaOdds.getPairNo().toString())
                        .addField("odds", exactaOdds.getOdds())
                        .addField("rank", exactaOdds.getBetRank())
                        .build())
                .doOnNext(this::writePoint)
                .then(Mono.just(oddsExacta.getRaceId()));
    }

}

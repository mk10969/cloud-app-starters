package org.uma.cloud.stream.type;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.entity.OddsQuinella;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.entity.OddsShow;
import org.uma.cloud.common.entity.OddsWin;
import org.uma.cloud.common.utils.javatuples.Pair;
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


    public Mono<String> oddsToPoint(Pair<OddsWin, OddsShow> pair) {
        // 枠連は捨てる。

        OddsWin win = pair.getValue1();
        Flux<Point> winFlux = Flux.fromStream(pair.getValue1().getWinOdds().stream())
                .map(winOdds -> Point.measurement(winOdds.getClass().getSimpleName())
                        .time(DateUtil.toEpochMilli(win.timestamp()), TimeUnit.MILLISECONDS)
                        .tag("raceId", win.getRaceId())
                        .tag("horseNo", winOdds.getHorseNo())
                        .addField("odds", winOdds.getOdds())
                        .addField("rank", winOdds.getBetRank())
                        .build());

        OddsShow oddsShow = pair.getValue2();
        Flux<Point> showFlux = Flux.fromStream(pair.getValue2().getShowOdds().stream())
                .map(showOdds -> Point.measurement(showOdds.getClass().getSimpleName())
                        .time(DateUtil.toEpochMilli(oddsShow.timestamp()), TimeUnit.MILLISECONDS)
                        .tag("raceId", oddsShow.getRaceId())
                        .tag("horseNo", showOdds.getHorseNo())
                        .addField("oddsMin", showOdds.getOddsMin())
                        .addField("oddsMax", showOdds.getOddsMax())
                        .addField("rank", showOdds.getBetRank())
                        .build());
        // 合わせる。
        return winFlux.concatWith(showFlux)
                .doOnNext(this::writePoint)
                .then(Mono.just(win.getRaceId()));
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

package org.uma.cloud.stream.type;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.RacingOdds;
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


    public Mono<String> oddsToPoint(Pair<RacingOdds, RacingOdds> pair) {
        RacingOdds win = pair.getValue1();
        Flux<Point> winFlux = Flux.fromStream(pair.getValue1().getOddsTable().stream())
                .map(winOdds -> Point.measurement(winOdds.getClass().getSimpleName())
                        .time(DateUtil.toEpochMilli(win.timestamp()), TimeUnit.MILLISECONDS)
                        .tag("raceId", win.getRaceId())
                        .tag("betting", String.valueOf(win.getBetting()))
                        .tag("pairHorseNo", winOdds.getPairHorseNo())
                        .addField("oddsMin", winOdds.getOddsMin())
                        .addField("oddsMax", winOdds.getOddsMax())
                        .addField("rank", winOdds.getBetRank())
                        .build());

        RacingOdds show = pair.getValue2();
        Flux<Point> showFlux = Flux.fromStream(pair.getValue2().getOddsTable().stream())
                .map(showOdds -> Point.measurement(showOdds.getClass().getSimpleName())
                        .time(DateUtil.toEpochMilli(show.timestamp()), TimeUnit.MILLISECONDS)
                        .tag("raceId", show.getRaceId())
                        .tag("betting", String.valueOf(show.getBetting()))
                        .tag("pairHorseNo", showOdds.getPairHorseNo())
                        .addField("oddsMin", showOdds.getOddsMin())
                        .addField("oddsMax", showOdds.getOddsMax())
                        .addField("rank", showOdds.getBetRank())
                        .build());
        // 合わせる。
        return winFlux.concatWith(showFlux)
                .doOnNext(this::writePoint)
                .then(Mono.just(win.getRaceId()));
    }


    public Mono<String> oddsToPoint(RacingOdds racingOdds) {
        return Flux.fromStream(racingOdds.getOddsTable().stream())
                .map(odds -> Point.measurement(racingOdds.getClass().getSimpleName())
                        .time(DateUtil.toEpochMilli(racingOdds.timestamp()), TimeUnit.MILLISECONDS)
                        .tag("raceId", racingOdds.getRaceId())
                        .tag("betting", String.valueOf(racingOdds.getBetting()))
                        .tag("pairHorseNo", odds.getPairHorseNo())
                        .addField("oddsMin", odds.getOddsMin())
                        .addField("oddsMax", odds.getOddsMax())
                        .addField("rank", odds.getBetRank())
                        .build())
                .doOnNext(this::writePoint)
                .then(Mono.just(racingOdds.getRaceId()));
    }
}

package org.uma.cloud.stream.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.cloud.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@Service
public class TimeSeriesService {

    @Autowired
    private InfluxDB influxDB;


    public void writePoint(Point point) {
        influxDB.write(point);
    }


    public Flux<Point> oddsToPoint(WinsPlaceBracketQuinella winsPlaceBracketQuinella) {
        // 枠連は捨てる。
        // winsPlaceBracketQuinella.getBracketQuinellaOdds();

        long timestamp = DateUtil.toEpochMilli(winsPlaceBracketQuinella.getTimestamp());

        Flux<Point> winFlux = Flux.fromStream(winsPlaceBracketQuinella.getWinOdds().stream())
                .map(winOdds -> Point.measurement(winOdds.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", winsPlaceBracketQuinella.getRaceId())
                        .tag("horseNo", winOdds.getHorseNo())
                        .addField("odds", winOdds.getOdds())
                        .addField("rank", winOdds.getBetRank())
                        .build());

        Flux<Point> placeFlux = Flux.fromStream(winsPlaceBracketQuinella.getPlaceOdds().stream())
                .map(placeOdds -> Point.measurement(placeOdds.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", winsPlaceBracketQuinella.getRaceId())
                        .tag("horseNo", placeOdds.getHorseNo())
                        .addField("oddsMin", placeOdds.getOddsMin())
                        .addField("oddsMax", placeOdds.getOddsMax())
                        .addField("rank", placeOdds.getBetRank())
                        .build());
        // 合わせる。
        return winFlux.concatWith(placeFlux);
    }


    /**
     * TODO: tagは検討→今のところ、おそらくユニーク。ただどのデータかわかりにくい・・・
     */
    public Flux<Point> oddsToPoint(Quinella quinella) {
        long timestamp = DateUtil.toEpochMilli(quinella.getTimestamp());

        return Flux.fromStream(quinella.getQuinellaOdds().stream())
                .map(quinellaOdds -> Point.measurement(quinella.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", quinella.getRaceId())
                        .tag("pair", quinellaOdds.getPairNo().toString())
                        .addField("odds", quinellaOdds.getOdds())
                        .addField("rank", quinellaOdds.getBetRank())
                        .build());
    }

    public Flux<Point> oddsToPoint(QuinellaPlace quinellaPlace) {
        long timestamp = DateUtil.toEpochMilli(quinellaPlace.getTimestamp());

        return Flux.fromStream(quinellaPlace.getQuinellaPlaceOdds().stream())
                .map(quinellaPlaceOdds -> Point.measurement(quinellaPlace.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", quinellaPlace.getRaceId())
                        .tag("pair", quinellaPlaceOdds.getPairNo().toString())
                        .addField("oddsMin", quinellaPlaceOdds.getOddsMin())
                        .addField("oddsMax", quinellaPlaceOdds.getOddsMax())
                        .addField("rank", quinellaPlaceOdds.getBetRank())
                        .build());
    }

    public Flux<Point> oddsToPoint(Exacta exacta) {
        long timestamp = DateUtil.toEpochMilli(exacta.getTimestamp());

        return Flux.fromStream(exacta.getExactaOdds().stream())
                .map(exactaOdds -> Point.measurement(exacta.getClass().getSimpleName())
                        .time(timestamp, TimeUnit.MILLISECONDS)
                        .tag("raceId", exacta.getRaceId())
                        .tag("pair", exactaOdds.getPairNo().toString())
                        .addField("odds", exactaOdds.getOdds())
                        .addField("rank", exactaOdds.getBetRank())
                        .build());
    }

}

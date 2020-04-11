package org.uma.cloud.stream.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@Service
public class TimeSeriesService {

    @Autowired
    private InfluxDB influxDB;

//    public Point toPoint(Odds odds) {
//        long timestamp = DateUtil.toEpochMilli(odds.getTimestamp());
//
//        return Point.measurement(odds.getClass().getSimpleName())
//                .time(timestamp, TimeUnit.MILLISECONDS)
//                .tag("location", "santa_monica")
//                .addField("level description", "below 3 feet")
//                .addField("water_level", 2.064d)
//                .build();
//    }

    /**
     * Quinellaの中にあるオッズのデータを、One PointずつStream処理する。
     * TODO: tagは検討→今のところ、おそらくユニーク。ただどのデータかわかりにくい・・・
     */
    public Flux<Point> toPointQuinella(Quinella quinella) {
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

}

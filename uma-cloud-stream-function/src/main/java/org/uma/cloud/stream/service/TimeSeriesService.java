package org.uma.cloud.stream.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.odds.Quinella;

import java.util.concurrent.TimeUnit;

@Service
public class TimeSeriesService {

    @Autowired
    private InfluxDB influxDB;

//    public Point toPoint(Quinella quinella) {
//        return Point.measurement(quinella.getClass().getSimpleName())
//                .time(quinella.getAnnounceDate(), TimeUnit.MILLISECONDS)
//                .tag("location", "santa_monica")
//                .addField("level description", "below 3 feet")
//                .addField("water_level", 2.064d)
//                .build();
//    }

}

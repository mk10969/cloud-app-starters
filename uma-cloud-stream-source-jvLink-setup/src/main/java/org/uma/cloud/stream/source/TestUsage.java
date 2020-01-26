package org.uma.cloud.stream.source;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;


@EnableBinding(Source.class)
@EnableScheduling
@EnableConfigurationProperties(JvLinkSetupProperties.class)
public class TestUsage {

    @Autowired
    private Source source;

    @Autowired
    private JvLinkSetupProperties configuration;

    private String[] users = {"user1", "user2", "user3", "user4", "user5"};


    public static void main(String[] args) {
        SpringApplication.run(TestUsage.class, args);
    }


    @Scheduled(fixedDelay = 1000)
    public void sendEvents() {
        UsageDetail usageDetail = new UsageDetail();
        usageDetail.setUserId(this.users[new Random().nextInt(5)]);
        usageDetail.setDuration(new Random().nextInt(300));
        usageDetail.setData(new Random().nextInt(700));
        usageDetail.setYyyyMMdd(configuration.getYyyyMMdd());
        usageDetail.setFlag(configuration.isRACE());
        this.source.output().send(MessageBuilder.withPayload(usageDetail).build());
    }

    @Data
    static class UsageDetail {
        private String userId;
        private Integer duration;
        private Integer data;
        private String yyyyMMdd;
        private boolean flag;
    }

}

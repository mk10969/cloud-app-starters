package org.uma.cloud.stream.source;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.Random;


@EnableBinding(Source.class)
@EnableConfigurationProperties(TestStreamSourceProperties.class)
public class TestStreamSourceConfiguration {

    @Autowired
    private TestStreamSourceProperties testStreamSourceProperties;

    private String[] users = {"user1", "user2", "user3", "user4", "user5"};


    @Bean
    @InboundChannelAdapter(
            value = Source.OUTPUT,
            poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1")
    )
    public MessageSource<UsageDetail> testSource() {
        return () -> {
            UsageDetail usageDetail = new UsageDetail();
            usageDetail.setUserId(this.users[new Random().nextInt(5)]);
            usageDetail.setDuration(new Random().nextInt(300));
            usageDetail.setData(new Random().nextInt(700));
            usageDetail.setYyyyMMdd(testStreamSourceProperties.getYyyyMMdd());
            usageDetail.setFlag(testStreamSourceProperties.isRACE());
            return new GenericMessage<>(usageDetail);
        };
    }


//    @StreamEmitter
//    @Output(Source.OUTPUT)
//    @Bean
//    public Publisher<Message<UsageDetail>> emit() {
//        return IntegrationFlows.from(() -> {
//                    UsageDetail usageDetail = new UsageDetail();
//                    usageDetail.setUserId(this.users[new Random().nextInt(5)]);
//                    usageDetail.setDuration(new Random().nextInt(300));
//                    usageDetail.setData(new Random().nextInt(700));
//                    usageDetail.setYyyyMMdd(configuration.getYyyyMMdd());
//                    usageDetail.setFlag(configuration.isRACE());
//                    return new GenericMessage<UsageDetail>(usageDetail);
//                }, e -> e.poller(p -> p.fixedDelay(10L, TimeUnit.SECONDS))
//        ).toReactivePublisher();
//    }

    @Data
    static class UsageDetail {
        private String userId;
        private Integer duration;
        private Integer data;
        private String yyyyMMdd;
        private boolean flag;
    }

}

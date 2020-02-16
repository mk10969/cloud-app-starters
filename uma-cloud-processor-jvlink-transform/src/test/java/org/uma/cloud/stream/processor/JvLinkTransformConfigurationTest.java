package org.uma.cloud.stream.processor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.TimeUnit;


@SpringBootTest
class JvLinkTransformConfigurationTest {

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;


    @Test
    public void testUsageCostProcessor() throws Exception {
        Message<String> inputMessage = MessageBuilder
                .withPayload("UkE3MjAwMjEyMjEyMDAwMDEwNTA2MDEwMTAxNjAwMDCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDAwMDAgIDEyMDAzMzAwMDcwMzAwMDAwMDcwM4FAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQIFAgUCBQDEwMDAwMDAwMjQwMCAgICAwMDA1MTAwMDAwMDIwMDAwMDAwMTMwMDAwMDAwNzcwMDAwMDA1MTAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMTAwMDAwMDAwODA4MDgxMDExMjMxMDgxMTUxMjMxMzYwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMDAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgMA0K")
                .build();
        this.processor.input().send(inputMessage);
        Message<Object> outputMessage = (Message<Object>) this.messageCollector
                .forChannel(this.processor.output()).poll(1, TimeUnit.SECONDS);

        System.out.println(outputMessage.getPayload());

    }

}
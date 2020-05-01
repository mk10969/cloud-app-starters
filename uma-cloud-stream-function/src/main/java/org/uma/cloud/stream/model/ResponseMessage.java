package org.uma.cloud.stream.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ResponseMessage {

    private final String data;

    private final String message;

    @JsonCreator
    public ResponseMessage(
            @JsonProperty("data") String data,
            @JsonProperty("message") String message) {
        this.data = data;
        this.message = message;
    }
}

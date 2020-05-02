package org.uma.cloud.stream.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.uma.cloud.common.code.RecordSpec;

import java.util.Objects;

import static org.uma.cloud.common.code.RecordSpec.HR;

@Getter
@ToString
public class EventMessage {

    private final RecordSpec recordSpec;

    private final String eventId;

    @JsonCreator
    public EventMessage(
            @JsonProperty("recordSpec") String recordSpec,
            @JsonProperty("eventId") String eventId) {
        this.recordSpec = RecordSpec.of(recordSpec);
        this.eventId = Objects.requireNonNull(eventId);
    }

    @JsonIgnore
    public boolean isRacingRefund() {
        return HR == this.recordSpec;
    }

    @JsonIgnore
    public boolean isNotRacingRefund() {
        return !this.isRacingRefund();
    }
}


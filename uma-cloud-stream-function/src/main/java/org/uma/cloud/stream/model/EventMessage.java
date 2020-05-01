package org.uma.cloud.stream.model;

import lombok.Getter;
import lombok.ToString;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.util.Objects;

import static org.uma.cloud.common.recordSpec.RecordSpec.HR;

@Getter
@ToString
public class Event {

    private final RecordSpec recordSpec;

    private final String raceId;

    public Event(String eventId) {
        String[] tmp = eventId.split(":");
        this.recordSpec = RecordSpec.of(tmp[0]);
        this.raceId = Objects.requireNonNull(tmp[1]);
    }

    public boolean isRacingRefund() {
        return HR == this.recordSpec;
    }

    public boolean isNotRacingRefund() {
        return !this.isRacingRefund();
    }
}


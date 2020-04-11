package org.uma.cloud.common.model.odds;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Odds {

    LocalDate getHoldingDate();

    String getAnnounceDate();


    default LocalDateTime getTimestamp() {
        int year = this.getHoldingDate().getYear();
        String MMddHHmm = this.getAnnounceDate();
        int month = Integer.parseInt(MMddHHmm.substring(0, 2));
        int day = Integer.parseInt(MMddHHmm.substring(2, 4));
        int hour = Integer.parseInt(MMddHHmm.substring(4, 6));
        int minute = Integer.parseInt(MMddHHmm.substring(6, 8));
        return LocalDateTime.of(year, month, day, hour, minute);
    }

}

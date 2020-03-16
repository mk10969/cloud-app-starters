package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.server.repository.impl.JvRtHorseRacingDetailsRepository;
import org.uma.jvLink.server.repository.impl.JvRtRaceRefundRepository;
import org.uma.jvLink.server.repository.impl.JvRtRacingDetailsRepository;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;

import java.util.List;

@RestController
@RequestMapping("/rt/race")
@RequiredArgsConstructor
public class JvRtRaceController {

    /**
     * リアルタイム レース詳細情報
     */
    private final JvRtRacingDetailsRepository racingDetailsRepository;

    /**
     * リアルタイム レース毎 競走馬情報
     */
    private final JvRtHorseRacingDetailsRepository horseRacingDetailsRepository;

    /**
     * リアルタイム レース払戻
     */
    private final JvRtRaceRefundRepository raceRefundRepository;


    @GetMapping("/racingDetails")
    public List<RacingDetails> findRacingDetails(@RequestParam("raceId") String raceId) {
        return racingDetailsRepository.readLine(() -> raceId);
    }

    @GetMapping("/horseRacingDetails")
    public List<HorseRacingDetails> findHorseRacingDetails(@RequestParam("raceId") String raceId) {
        return horseRacingDetailsRepository.readLine(() -> raceId);
    }

    @GetMapping("/raceRefund")
    public List<RaceRefund> findRaceRefund(@RequestParam("raceId") String raceId) {
        return raceRefundRepository.readLine(() -> raceId);
    }

}

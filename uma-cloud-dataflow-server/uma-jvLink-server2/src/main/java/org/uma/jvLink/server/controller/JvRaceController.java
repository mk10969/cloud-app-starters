package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.server.repository.impl.JvStoredHorseRacingDetailsRepository;
import org.uma.jvLink.server.repository.impl.JvStoredRaceRefundRepository;
import org.uma.jvLink.server.repository.impl.JvStoredRacingDetailsRepository;
import org.uma.jvLink.server.repository.impl.JvStoredVoteCountRepository;
import org.uma.jvLink.core.config.option.Option;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/race")
@RequiredArgsConstructor
public class JvRaceController {

    /**
     * レース詳細情報
     */
    private final JvStoredRacingDetailsRepository racingDetailsRepository;

    /**
     * レース毎 競走馬情報
     */
    private final JvStoredHorseRacingDetailsRepository horseRacingDetailsRepository;

    /**
     * レース払戻
     */
    private final JvStoredRaceRefundRepository raceRefundRepository;

    /**
     * レース投票数
     */
    private final JvStoredVoteCountRepository voteCountRepository;

    /**
     * 起動時に一度のみ評価する
     */
    private static final LocalDateTime lastWeek = DateUtil.lastWeek();


    @GetMapping("/racingDetails")
    public List<RacingDetails> findRacingDetailsOnThisWeek() {
        return racingDetailsRepository.readLines(lastWeek, Option.THIS_WEEK);
    }

    @GetMapping("/horseRacingDetails")
    public List<HorseRacingDetails> findHorseRacingDetailsOnThisWeek() {
        return horseRacingDetailsRepository.readLines(lastWeek, Option.THIS_WEEK);
    }

    @GetMapping("/raceRefund")
    public List<RaceRefund> findRaceRefundOnThisWeek() {
        return raceRefundRepository.readLines(lastWeek, Option.THIS_WEEK);
    }

    @GetMapping("/voteCount")
    public List<VoteCount> findVoteCountOnThisWeek() {
        return voteCountRepository.readLines(lastWeek, Option.THIS_WEEK);
    }


    @GetMapping("/racingDetails/{epochSecond}")
    public List<RacingDetails> findRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return racingDetailsRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/horseRacingDetails/{epochSecond}")
    public List<HorseRacingDetails> findHorseRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return horseRacingDetailsRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/raceRefund/{epochSecond}")
    public List<RaceRefund> findRaceRefund(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return raceRefundRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/voteCount/{epochSecond}")
    public List<VoteCount> findVoteCount(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return voteCountRepository.readLines(dateTime, Option.STANDARD);
    }


}

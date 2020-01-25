package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.core.server.repository.impl.*;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.server.repository.impl.*;
import org.uma.platform.common.model.*;
import org.uma.platform.common.utils.lang.DateUtil;
import org.uma.platform.feed.application.repository.impl.*;
import org.uma.platform.jvlink.server.repository.impl.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/diff")
@RequiredArgsConstructor
public class JvDiffController {

    /**
     * 競走馬
     */
    private final JvStoredRaceHorseRepository raceHorseRepository;

    /**
     * 騎手
     */
    private final JvStoredJockeyRepository jockeyRepository;

    /**
     * 調教師
     */
    private final JvStoredTrainerRepository trainerRepository;

    /**
     * 馬主
     */
    private final JvStoredOwnerRepository ownerRepository;

    /**
     * 生産者
     */
    private final JvStoredBreederRepository breederRepository;


    @GetMapping("/raceHorse/{epochSecond}")
    public List<RaceHorse> findRaceHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return raceHorseRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/jockey/{epochSecond}")
    public List<Jockey> findJockey(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return jockeyRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/trainer/{epochSecond}")
    public List<Trainer> findTrainer(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return trainerRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/owner/{epochSecond}")
    public List<Owner> findOwner(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return ownerRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/breeder/{epochSecond}")
    public List<Breeder> findBreeder(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return breederRepository.readLines(dateTime, Option.STANDARD);
    }


}

package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.server.repository.impl.JvStoredAncestryRepository;
import org.uma.jvLink.server.repository.impl.JvStoredBreedingHorseRepository;
import org.uma.jvLink.server.repository.impl.JvStoredOffspringRepository;
import org.uma.jvLink.core.config.option.Option;
import org.uma.platform.common.model.Ancestry;
import org.uma.platform.common.model.BreedingHorse;
import org.uma.platform.common.model.Offspring;
import org.uma.platform.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blood")
@RequiredArgsConstructor
public class JvBloodController {

    /**
     * 系統情報
     */
    private final JvStoredAncestryRepository ancestryRepository;

    /**
     * 繁殖馬情報
     */
    private final JvStoredBreedingHorseRepository breedingHorseRepository;

    /**
     * 産駒情報
     */
    private final JvStoredOffspringRepository offspringRepository;


    @GetMapping("/ancestry/{epochSecond}")
    public List<Ancestry> findAncestry(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return ancestryRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    public List<BreedingHorse> findBreedingHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return breedingHorseRepository.readLines(dateTime, Option.STANDARD);
    }

    @GetMapping("/offspring/{epochSecond}")
    public List<Offspring> findOffspring(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return offspringRepository.readLines(dateTime, Option.STANDARD);
    }


}

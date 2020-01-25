package org.uma.jvLink.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uma.jvLink.server.repository.impl.JvRtOddsQuinellaRepository;
import org.uma.jvLink.server.repository.impl.JvRtOddsWinsPlaceBracketQuinellaRepository;
import org.uma.platform.common.model.odds.Quinella;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;

import java.util.List;

@RestController
@RequestMapping("/rt/odds")
@RequiredArgsConstructor
public class JvRtOddsController {

    /**
     * リアルタイム 単勝・複勝・枠連オッズ
     */
    private final JvRtOddsWinsPlaceBracketQuinellaRepository winsPlaceBracketQuinellaRepository;

    /**
     * リアルタイム 馬連オッズ
     */
    private final JvRtOddsQuinellaRepository quinellaRepository;


    @GetMapping("/winsPlaceBracketQuinella")
    public List<WinsPlaceBracketQuinella> findWinsPlaceBracketQuinella(
            @RequestParam("raceId") String raceId){

        return winsPlaceBracketQuinellaRepository.readLine(()-> raceId);
    }

    @GetMapping("/quinella")
    public List<Quinella> findQuinella(
            @RequestParam("raceId") String raceId){

        return quinellaRepository.readLine(()-> raceId);
    }


}

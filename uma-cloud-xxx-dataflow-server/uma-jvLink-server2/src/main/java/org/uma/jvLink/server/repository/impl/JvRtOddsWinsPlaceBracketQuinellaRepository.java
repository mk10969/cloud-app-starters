package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.server.repository.JvLinkRealTimeRepository;
import org.uma.jvLink.server.repository.JvLinkRepository;
import org.uma.jvLink.core.config.option.RealTimeKey;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JvRtOddsWinsPlaceBracketQuinellaRepository
        implements JvLinkRealTimeRepository<WinsPlaceBracketQuinella> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B31_O1")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<WinsPlaceBracketQuinella> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .peek(model -> model.getWinOdds().removeIf(JvLinkRepository::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(JvLinkRepository::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(JvLinkRepository::bracketQuinellaOddsFilter))
                .collect(ImmutableList.toImmutableList());
    }
    
}

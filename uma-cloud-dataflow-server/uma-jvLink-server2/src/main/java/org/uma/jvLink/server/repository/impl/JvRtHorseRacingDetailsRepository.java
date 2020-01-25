package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.server.repository.JvLinkRealTimeRepository;
import org.uma.jvLink.core.config.option.RealTimeKey;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.HorseRacingDetails;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JvRtHorseRacingDetailsRepository
        implements JvLinkRealTimeRepository<HorseRacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B15_SE")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<HorseRacingDetails> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), HorseRacingDetails.class))
                .collect(ImmutableList.toImmutableList());

    }
}

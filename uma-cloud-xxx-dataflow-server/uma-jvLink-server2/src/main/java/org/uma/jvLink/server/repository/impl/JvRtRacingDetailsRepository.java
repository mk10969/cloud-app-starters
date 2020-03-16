package org.uma.jvLink.server.repository.impl;


import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.core.config.option.RealTimeKey;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.RacingDetails;
import org.uma.jvLink.server.repository.JvLinkRealTimeRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JvRtRacingDetailsRepository implements JvLinkRealTimeRepository<RacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B15_RA")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<RacingDetails> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RacingDetails.class))
                .collect(ImmutableList.toImmutableList());
    }
}

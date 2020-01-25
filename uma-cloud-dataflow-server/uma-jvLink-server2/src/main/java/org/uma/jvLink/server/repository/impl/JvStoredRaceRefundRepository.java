package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.server.repository.JvLinkRepository;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.RaceRefund;
import org.uma.jvLink.server.repository.JvLinkStoredRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredRaceRefundRepository implements JvLinkStoredRepository<RaceRefund> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_HR")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<RaceRefund> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .peek(model -> model.getRefundWins().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundBracketQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellaPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundSpares().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundExactas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrios().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrifectas().removeIf(JvLinkRepository::refundFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<RaceRefund> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readForSetup(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .peek(model -> model.getRefundWins().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundBracketQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellaPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundSpares().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundExactas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrios().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrifectas().removeIf(JvLinkRepository::refundFilter));
    }

    @Override
    public Flux<RaceRefund> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .doOnNext(model -> model.getRefundWins().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundPlaces().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundBracketQuinellas().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundQuinellas().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundQuinellaPlaces().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundSpares().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundExactas().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundTrios().removeIf(JvLinkRepository::refundFilter))
                .doOnNext(model -> model.getRefundTrifectas().removeIf(JvLinkRepository::refundFilter));
    }

}

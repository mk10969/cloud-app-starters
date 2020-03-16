package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.VoteCount;
import org.uma.jvLink.server.repository.JvLinkRepository;
import org.uma.jvLink.server.repository.JvLinkStoredRepository;
import org.uma.jvLink.core.JvLinkClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredVoteCountRepository implements JvLinkStoredRepository<VoteCount> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_H1")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<VoteCount> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .peek(model -> model.getVoteCountWins().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountPlaces().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountBracketQuinellas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountQuinellas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountQuinellaPlaces().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountExactas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountTrios().removeIf(JvLinkRepository::voteFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<VoteCount> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readForSetup(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .peek(model -> model.getVoteCountWins().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountPlaces().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountBracketQuinellas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountQuinellas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountQuinellaPlaces().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountExactas().removeIf(JvLinkRepository::voteFilter))
                .peek(model -> model.getVoteCountTrios().removeIf(JvLinkRepository::voteFilter));
    }

    @Override
    public Flux<VoteCount> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .doOnNext(model -> model.getVoteCountWins().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountPlaces().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountBracketQuinellas().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountQuinellas().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountQuinellaPlaces().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountExactas().removeIf(JvLinkRepository::voteFilter))
                .doOnNext(model -> model.getVoteCountTrios().removeIf(JvLinkRepository::voteFilter));
    }


}

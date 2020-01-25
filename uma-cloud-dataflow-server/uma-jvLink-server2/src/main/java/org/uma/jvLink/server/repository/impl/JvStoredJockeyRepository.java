package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.Jockey;
import org.uma.jvLink.server.repository.JvLinkStoredRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredJockeyRepository implements JvLinkStoredRepository<Jockey> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("DIFF_KS")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<Jockey> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Jockey.class))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<Jockey> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readForSetup(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Jockey.class));
    }

    @Override
    public Flux<Jockey> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Jockey.class));
    }
}

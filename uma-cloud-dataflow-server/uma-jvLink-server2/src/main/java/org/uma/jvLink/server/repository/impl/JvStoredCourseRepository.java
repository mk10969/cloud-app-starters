package org.uma.jvLink.server.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.server.component.JvLinkModelMapper;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.Course;
import org.uma.jvLink.server.repository.JvLinkStoredRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredCourseRepository implements JvLinkStoredRepository<Course> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("COMM_CS")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<Course> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Course.class))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<Course> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readForSetup(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Course.class));
    }

    @Override
    public Flux<Course> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Course.class));
    }
}

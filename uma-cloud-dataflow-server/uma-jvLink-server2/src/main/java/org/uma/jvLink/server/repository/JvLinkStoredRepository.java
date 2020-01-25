package org.uma.jvLink.server.repository;

import org.uma.jvLink.core.config.option.Option;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface JvLinkStoredRepository<T> {

    /**
     * バッチ用
     *
     * @param dateTime
     * @param option
     * @return
     */
    List<T> readLines(LocalDateTime dateTime, Option option);

    /**
     * セットアップ用
     *
     * @param dateTime
     * @return Streamを返却
     * AutoClosableを実装しているので、try-with-resource文を利用してください。
     */
    Stream<T> readStream(LocalDateTime dateTime);

    /**
     * セットアップ用（代替）
     *
     * @param dateTime
     * @return Fluxを返却する。
     */
    Flux<T> readFlux(LocalDateTime dateTime);


}

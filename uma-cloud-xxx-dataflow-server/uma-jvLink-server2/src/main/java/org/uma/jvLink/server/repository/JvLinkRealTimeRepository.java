package org.uma.jvLink.server.repository;

import org.uma.jvLink.core.config.option.RealTimeKey;

import java.util.List;

public interface JvLinkRealTimeRepository<T> {

    List<T> readLine(RealTimeKey realTimeKey);

}

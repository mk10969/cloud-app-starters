package org.uma.cloud.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.uma.cloud.common.model.BaseModel;

import java.util.List;

@Slf4j
public class JvLinkWriterListener<T extends BaseModel> implements ItemWriteListener<T> {

    @Override
    public void beforeWrite(List<? extends T> items) {
        // no ops
    }

    @Override
    public void afterWrite(List<? extends T> items) {
        if (log.isDebugEnabled()) {
            items.forEach(item -> log.info("{}", item));
        }
    }

    @Override
    public void onWriteError(Exception exception, List<? extends T> items) {
        log.error("Write Error: ", exception.getCause());
        log.info("Failed Write Data: {}", items);
    }
}

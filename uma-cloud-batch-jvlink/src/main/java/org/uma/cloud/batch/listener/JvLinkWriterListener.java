package org.uma.cloud.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.uma.cloud.common.model.BaseModel;

import java.util.List;

@Slf4j
public class JvLinkWriterListener implements ItemWriteListener<BaseModel> {

    @Override
    public void beforeWrite(List<? extends BaseModel> items) {

    }

    @Override
    public void afterWrite(List<? extends BaseModel> items) {
        // no ops
    }

    @Override
    public void onWriteError(Exception exception, List<? extends BaseModel> items) {
        log.error("Write Error: ", exception.getCause());
        log.info("Failed Write Data: {}", items);
    }

}

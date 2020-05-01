package org.uma.cloud.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.uma.cloud.common.model.BaseModel;

@Slf4j
public class JvLinkSkipListener<S extends BaseModel> implements SkipListener<String, S> {

    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(S item, Throwable t) {
        log.warn("Skip Write: {}", item);
    }

    @Override
    public void onSkipInProcess(String item, Throwable t) {

    }
}

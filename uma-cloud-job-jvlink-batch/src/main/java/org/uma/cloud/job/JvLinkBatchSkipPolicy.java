package org.uma.cloud.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;


@Slf4j
public class JvLinkBatchSkipPolicy implements SkipPolicy {


    @Override
    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {

        /**
         * JvLinkRecord定義の設定ミス
         * @see org.uma.cloud.common.component.JvLinkRecordProperties
         *
         * Convertに失敗
         * @see org.uma.cloud.common.component.JvLinkModelMapperProperties
         */
        if (t instanceof JvLinkModelMappingException) {
            log.error("Mapping Error: ", t);
            log.info("Failed Mapping Data: {}", ((JvLinkModelMappingException) t).getLineData());
            return true;

        } else if () {

        }


        return false;


    }
}

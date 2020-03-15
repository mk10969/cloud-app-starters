package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;
import org.uma.cloud.common.utils.exception.JvLinkModelNullPointException;


@Slf4j
public class JvLinkSkipPolicy implements SkipPolicy {

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

            /**
             * 基本的にフィールドのnullは、認めていない。
             * nullの場合、プログラムロジックに問題がある可能性が高い。
             */
        } else if (t instanceof JvLinkModelNullPointException) {
            log.error("NullPoint Error", t);
            log.info("Failed NullPoint Data: {}", ((JvLinkModelNullPointException) t).getLineData());
            return true;
        }

        return false;
    }

}

package org.uma.cloud.job;

import org.springframework.batch.core.ItemProcessListener;
import org.uma.cloud.common.utils.exception.JvLinkModelNullPointException;
import org.uma.cloud.common.utils.lang.JvLinkModelUtil;

public class JvLinkBatchProcessorListener implements ItemProcessListener<String, Object> {

    @Override
    public void beforeProcess(String item) {
        // no ops
    }

    /**
     * {@link JvLinkProcessor}でtransformした後のmodelのフィールドに、nullがないかチェックを行う。
     * <p>
     * 例外的に、下記のフォールドは、nullを許容する。
     * {@link org.uma.cloud.common.utils.lang.JvLinkModelUtil#excludeList}
     *
     * @throws JvLinkModelNullPointException
     */
    @Override
    public void afterProcess(String item, Object result) {
        try {
            JvLinkModelUtil.fieldNotNull(result);

        } catch (NullPointerException e) {
            throw new JvLinkModelNullPointException(e, result.toString());
        }
    }

    @Override
    public void onProcessError(String item, Exception e) {
        // no ops
    }

}

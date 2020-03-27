package org.uma.cloud.batch.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.uma.cloud.batch.JvLinkProcessors;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.utils.exception.JvLinkModelNullPointException;
import org.uma.cloud.common.utils.lang.ModelUtil;


public class JvLinkProcessorListener implements ItemProcessListener<String, BaseModel> {

    @Override
    public void beforeProcess(String item) {
        // no ops
    }

    /**
     * {@link JvLinkProcessors}でtransformした後のmodelのフィールドに、nullがないかチェックを行う。
     * <p>
     * 例外的に、下記のフォールドは、nullを許容する。
     * {@link ModelUtil#excludeList}
     *
     * @throws JvLinkModelNullPointException
     */
    @Override
    public void afterProcess(String item, BaseModel result) {
        // result、nullの可能性があるので、その場合、何もしない。
        if (result == null) {
            return;
        }

        try {
            // modelのフィールド、nullチェック。
            ModelUtil.fieldNotNull(result);
        } catch (NullPointerException e) {
            throw new JvLinkModelNullPointException(e, result.toString());
        }
    }

    @Override
    public void onProcessError(String item, Exception e) {
        // no ops
    }

}

package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.utils.lang.ModelUtil;

import java.time.LocalDate;

@Getter
public class JvLinkBase {
    private RecordSpec recordType;
    private LocalDate dataCreateDate;

    @Override
    public String toString() {
        return ModelUtil.toJson(this);
    }
}

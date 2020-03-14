package org.uma.cloud.common.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JvLInkModelMapperConfigurationTest {

    @Test
    void test_() {
        String str = "999999";
        StringBuilder sb1 = new StringBuilder(str);
        sb1.insert(str.length() - 1, ".");
        Assertions.assertEquals("99999.9", sb1.toString());
    }


}
package org.uma.cloud.common.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class JvLInkModelMapperConfigurationTest {

    @Test
    void test_() {
        String str = "999999";
        StringBuilder sb1 = new StringBuilder(str);
        sb1.insert(str.length() - 1, ".");
        Assertions.assertEquals("99999.9", sb1.toString());
    }


    @Test
    public void ofGet() {
        int i = 1;
        LocalTime time = LocalTime.of(0, 23, 45, i*100*1000*1000);
        System.out.println(time);
    }

    @Test
    void test_float() {

        String str = "99.9";
        System.out.println(Float.valueOf(str));

    }
}
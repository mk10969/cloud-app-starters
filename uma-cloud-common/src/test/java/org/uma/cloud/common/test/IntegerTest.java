package org.uma.platform.common.test;

//import org.apache.commons.lang3.StringUtils;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uma.platform.common.utils.lang.DateUtil;

public class IntegerTest {

//    @Test
//    void test_文字列から数字になるかも() {
//        // そりゃそうや。。。
//        String str = " ";
//        assertThrows(NumberFormatException.class, () -> Integer.valueOf(str));
//        assertEquals(StringUtils.isBlank(str), true);
//        assertEquals(Integer.valueOf("011111"), 11111);
//        assertEquals(StringUtils.isNumeric(str), false);
//        assertEquals(StringUtils.isNumericSpace(str), true); // 数字 or スペースで true
//        assertEquals(StringUtils.isAlphaSpace(str), true);
//
//
//    }

    @Test
    void test_LocalDate() {
        // 当たり前のnullぽ
        DateUtil.format("yyyyMMddHHmmss", null);
    }

}

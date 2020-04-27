package org.uma.cloud.common.utils.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilTest {

    @Test
    void test_0パディング() {
        Integer num = 1;
        assertEquals(String.format("%05d", num), "00001");
    }

    @Test
    void test_文字の長さ() {
        String str1 = "あいうえo";
        String str2 = "absdあ";
        assertEquals(5, str1.length());
        assertEquals(5, str2.length());
    }

}
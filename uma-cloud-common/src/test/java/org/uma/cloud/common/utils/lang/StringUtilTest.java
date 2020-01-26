package org.uma.cloud.common.utils.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilTest {

    @Test
    void test_0パディング() {
        Integer num = 1;
        assertEquals(String.format("%05d", num), "00001");
    }

}
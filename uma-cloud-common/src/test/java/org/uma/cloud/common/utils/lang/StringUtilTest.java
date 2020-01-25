package org.uma.platform.common.utils.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void test_0パディング(){
        Integer num = 1;
        assertEquals(String.format("%05d", num), "00001");
    }



}
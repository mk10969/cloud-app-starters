package org.uma.platform.common.utils.javatuples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PairTest {


    @Test
    void test() {
        String str = "0212";
        Pair<String, String> pair = Pair.with(str.substring(0, 2), str.substring(2, 4));
        Assertions.assertEquals("02", pair.getValue1());
        Assertions.assertEquals("12", pair.getValue2());
    }


}
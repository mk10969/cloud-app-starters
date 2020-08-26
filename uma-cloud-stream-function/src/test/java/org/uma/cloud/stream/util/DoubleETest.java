package org.uma.cloud.stream.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleETest {


    @Test
    void test_double() {

        double d1 = 0.00000000000000000000000000000000000000000123456789012345678901234567890d;
        double d2 = 1234567890123456789012345678900000000000000000000000000000000000000000000d;
        double d3 = 1234567890.1234567890123456789d;

        System.out.println(d1);
        // 1.2345678901234568E-42
        System.out.println(d2);
        // 1.2345678901234568E72
        System.out.println(d3);
        // 1.2345678901234567E9

        System.out.println(BigDecimal.valueOf(d1).toPlainString());
        // 0.0000000000000000000000000000000000000000012345678901234568
        System.out.println(BigDecimal.valueOf(d2).toPlainString());
        // 1234567890123456800000000000000000000000000000000000000000000000000000000
        System.out.println(BigDecimal.valueOf(d3).toPlainString());
        // 1234567890.1234567


        System.out.println(BigDecimal.valueOf(d1).setScale(5, RoundingMode.UP).toPlainString());
        System.out.println(BigDecimal.valueOf(d2).setScale(5, RoundingMode.UP).toPlainString());
        System.out.println(BigDecimal.valueOf(d3).setScale(5, RoundingMode.UP).toPlainString());

    }
}

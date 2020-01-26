package org.uma.cloud.common.utils.lang;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteUtilTest {

    @Test
    void test() {
        String a = "abcdef";
        String b = "abc";
        String c = "abd";

        boolean bool = startsWith(a.getBytes(), 0, b.getBytes());
        assertTrue(bool);
        boolean bool2 = startsWith(a.getBytes(), 0, c.getBytes());
        assertFalse(bool2);
    }

    public static boolean startsWith(byte[] source, int offset, byte[] match) {
        if (match.length > (source.length - offset)) {
            return false;
        }
        return IntStream.range(0, match.length)
                .allMatch(i -> source[offset + i] == match[i]);
    }

}
package org.uma.platform.common.utils.lang;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class ByteUtilTest {
    @Test
    void test() {
        String a = "abcdef";
        String b = "abc";
        String c = "abd";

        boolean bool = startsWith(a.getBytes(), 0, b.getBytes());
        System.out.println(bool);
        boolean bool2 = startsWith(a.getBytes(), 0, c.getBytes());
        System.out.println(bool2);
    }

    public static boolean startsWith(byte[] source, int offset, byte[] match) {
        if (match.length > (source.length - offset)) {
            return false;
        }
        return IntStream.range(0, match.length)
                .allMatch(i -> source[offset + i] == match[i]);
    }

}
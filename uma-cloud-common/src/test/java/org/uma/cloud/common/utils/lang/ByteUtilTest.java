package org.uma.cloud.common.utils.lang;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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


    @Test
    void test_base64() throws UnsupportedEncodingException {
        String name = "RA220200321202003220901091120049阪神大賞典　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　THE HANSHIN DAISHOTEN                                                                                                                                                                                                                                                                                                                                                   阪神大賞典　　　　　阪神大賞典　阪大賞1068B 14N012000000999999999　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　300000001700A   006700000027000000170000001000000006700000000000000000000000000000000000000000000000000000000000000070000000200000001000000000000000000000000000000000000000000015350000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000                                                                      00                                                                      00                                                                      00                                                                      0";
        byte[] aaa = Base64.getEncoder().encode(name.getBytes("MS932"));
        String aaa1 = new String(aaa, StandardCharsets.UTF_8);

        byte[] bbb = Base64.getDecoder().decode(aaa1);
        String bbb1 = new String(bbb, "MS932");
        System.out.println(bbb1);
    }


}
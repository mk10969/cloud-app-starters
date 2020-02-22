package org.uma.cloud.common.utils.lang;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.IntStream;

public class ByteUtil {

    private ByteUtil() {
    }

    public static boolean startsWith(byte[] source, byte[] match) {
        return startsWith(source, 0, match);
    }

    public static boolean startsWith(byte[] source, int offset, byte[] match) {
        if (match.length > (source.length - offset)) {
            return false;
        }
        return IntStream.range(0, match.length).allMatch(i -> source[offset + i] == match[i]);
    }

    public static String base64EncodeToString(byte[] src) {
        return new String(base64Encode(src), StandardCharsets.ISO_8859_1);
    }

    public static byte[] base64Encode(byte[] src) {
        return Base64.getEncoder().encode(src);
    }

    public static byte[] base64Decode(String src){
       return Base64.getDecoder().decode(src.getBytes(StandardCharsets.ISO_8859_1));
    }

}

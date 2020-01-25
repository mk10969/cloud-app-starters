package org.uma.platform.common.utils.lang;

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
        return IntStream.range(0, match.length)
                .allMatch(i -> source[offset + i] == match[i]);
    }
}

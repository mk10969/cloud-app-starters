package org.uma.cloud.common.utils.lang;

import org.uma.cloud.common.utils.exception.UnsupportedEncodingRuntimeException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Objects;


@Deprecated
public class StringUtil {


    public static int getBytesLength(String target) {
        if (target == null) {
            return 0;
        }
        return target.getBytes().length;
    }

    public static int getBytesLength(String target, Charset charset) {
        Objects.requireNonNull(target);
        try {
            return target.getBytes(charset.name()).length;
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    public static byte[] getBytes(String target, Charset charset) {
        try {
            return target.getBytes(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

    public static String getString(byte[] target, Charset charset) {
        try {
            return new String(target, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingRuntimeException(e);
        }
    }

}

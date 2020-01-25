package org.uma.cloud.common.utils.lang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.uma.cloud.common.utils.exception.UnsupportedEncodingRuntimeException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Objects;

public class StringUtil {

    /**
     * nullのフィールドを、jsonに含める設定を追加。
     */
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();


    private StringUtil() {
    }


    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


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

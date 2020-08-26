package org.uma.cloud.stream.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;

public class JavaNetUriTest {

    @Test
    void testJavaNetUri() {
        String stringUri = "http://aaa-bbb.com:8080";
        try {
            System.out.println("a");
            URI uri = new URI(stringUri);
            Assertions.assertEquals("aaa-bbb.com", uri.getHost());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testJavaNetUri2() {
        String stringUri = "http://aaa_bbb.com:8080";
        URI uri = URI.create(stringUri);
        Assertions.assertEquals(null, uri.getHost());

    }

    @Test
    void testJavaNetUri3() {
        patchUriField(2147483648L, "L_MARK");
        patchUriField(35184372088832L, "H_MARK");

        String stringUri = "http://aaa_bbb.com:8080";
        try {
            URI uri = new URI(stringUri);
            System.out.println(uri.getHost());
//            Assertions.assertEquals("aaa-bbb.com", uri.getHost());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void patchUriField(long maskValue, String fieldName) {

        try {
            Field field = URI.class.getDeclaredField(fieldName);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.setAccessible(true);
            field.setLong(null, maskValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

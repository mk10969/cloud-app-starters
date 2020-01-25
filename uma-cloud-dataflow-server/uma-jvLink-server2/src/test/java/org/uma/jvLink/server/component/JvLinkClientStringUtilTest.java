package org.uma.jvLink.server.component;

import org.junit.jupiter.api.Test;
import org.uma.jvLink.server.util.JvLinkUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JvLinkClientStringUtilTest {

    @Test
    void test_空判定(){
        String str = " ";

        if (str == ""){
            System.out.println("OK");
        }

        if (str.isEmpty()){
            System.out.println("OK");
        }
    }


    @Test
    void test_空() {
        String str = "";
        byte[] bytes = JvLinkUtil.toByte(str);

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> JvLinkUtil.sliceAndToString(bytes, 2, 3)
        );
    }

    @Test
    void test_abstractClassNew(){
        assertEquals(Hoge.call(), "called");
    }

    public static abstract class Hoge{
        public static String call(){
            return "called";
        }
    }


}
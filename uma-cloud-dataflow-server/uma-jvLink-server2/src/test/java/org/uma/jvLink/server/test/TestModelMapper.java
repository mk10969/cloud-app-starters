package org.uma.jvLink.server.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestModelMapper {

    @Test
    void tetest() {
        TestJavaBean.TestEnum testEnum = TestJavaBean.TestEnum.GOOD;
        Assertions.assertEquals("GOOD", testEnum.name());

    }

    @Test
    void test() {
        // setterが必要かつ、
        // Mapキー名とJavaBeanフィール名が完全一致する必要がある。
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(toStringLocalDate);
        modelMapper.addConverter(toStringArray);

        Map<String, Object> map = new HashMap<>();

        map.put("id", 101001);
        map.put("name", "publish");
        map.put("price", "01");
        map.put("amount", 500);
        map.put("localDate", "20180101");
        map.put("company", new String[]{"aaa", "bbbb"});
        map.put("groups", "1,2,3,4,5");
        map.put("testEnum", "nnnnn");

        TestJavaBean mapedJavaBean = modelMapper.map(map, TestJavaBean.class);

        // いらないデータをマッピングから外すことができるのか。
        System.out.println(mapedJavaBean.toString());

    }

    private Converter<String, LocalDate> toStringLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            return LocalDate.parse(source, DateTimeFormatter.BASIC_ISO_DATE);
        }
    };

    private Converter<String, String[]> toStringArray = new AbstractConverter<String, String[]>() {
        @Override
        protected String[] convert(String source) {
            Objects.requireNonNull(source);
            return source.split(",");
        }
    };


}

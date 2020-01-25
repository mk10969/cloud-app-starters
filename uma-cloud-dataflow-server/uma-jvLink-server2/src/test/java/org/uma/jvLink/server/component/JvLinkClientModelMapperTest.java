package org.uma.jvLink.server.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.model.HorseRacingDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvLinkClientModelMapperTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Test
    void test_() {
        assertEquals(1, 1);
    }

    @Test
    void test_HorseRacingDetailsModelを変換() throws InvocationTargetException, IllegalAccessException {

        Method method = Arrays.stream(jvLinkModelMapper.getClass().getDeclaredMethods())
                .filter(i -> i.getName().equals("findOne"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        method.setAccessible(true);
        Object a = method.invoke(jvLinkModelMapper, HorseRacingDetails.class);
        System.out.println(a);
    }

    // 大丈夫そうでした。
    @Test
    void test_全モデル() {
        String packageName = "org.uma.daiwaScarlet.model";
        Arrays.stream(jvLinkModelMapper.getClass().getDeclaredMethods())
                .filter(i -> i.getName().equals("findOne"))
                .peek(i -> i.setAccessible(true))
                .flatMap(method -> ReflectionUtils.getClassesFrom(packageName)
                        .stream()
                        .filter(j -> !j.getName().contains("Test"))
                        .map(clazz -> invoke(jvLinkModelMapper, method, clazz))
                )
                .forEach(System.out::println);

    }

    // 小細工
    private static Object invoke(Object obj, Method method, Object args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new IllegalArgumentException();
        }
    }

    @Test
    void testa_モデルクラス名表示() {
        String packageName = "org.uma.daiwaScarlet.model";
        ReflectionUtils.getClassesFrom(packageName)
                .stream()
                .map(Class::getName)
                .map(i -> i.replace("Model", "")
                        .replace("org.uma.daiwaScarlet.model.", ""))
                .forEach(System.out::println);

    }

    @Test
    void test_パッケージ表示() {
        String packageName = "org.uma.daiwaScarlet.repository.impl";
        ReflectionUtils.getClassesFrom(packageName)
                .stream()
                .map(Class::getName)
                .filter(i -> !i.contains("Test"))
                .map(i -> i.replace("JvStored", "")
                        .replace(packageName + ".", ""))
                .forEach(System.out::println);
    }

}
package org.uma.cloud.stream.function;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JvRacingFunctionTest {

    public static Set<Class<?>> getClassesFrom(String packagee) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return ClassPath.from(loader)
                    .getTopLevelClasses(packagee)
                    .stream()
                    .map(info -> info.load())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    public static Set<Method> getMethodsFrom(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredMethods())
                .collect(Collectors.toSet());
    }

    public static Set<Field> getFieldsFrom(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredFields())
                .collect(Collectors.toSet());
    }

    @Test
    void test() {
        assertEquals(1, 1);
    }

    @Test
    void test_メソッド名一覧取得() {
        getMethodsFrom(JvRacingFunction.class).stream()
                .map(Method::getName)
                .filter(i -> !"debug".equals(i))
                .filter(i -> !i.startsWith("lambda$"))
                .sorted()
                .forEach(System.out::println);
    }

}
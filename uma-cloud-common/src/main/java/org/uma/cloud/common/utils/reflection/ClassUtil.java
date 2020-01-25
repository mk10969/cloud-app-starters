package org.uma.platform.common.utils.reflection;

import org.uma.platform.common.utils.exception.IllegalAccessRuntimeException;
import org.uma.platform.common.utils.exception.InstantiationRuntimeException;
import org.uma.platform.common.utils.exception.InvocationTargetRuntimeException;
import org.uma.platform.common.utils.exception.NoSuchMethodRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ClassUtil {

    protected ClassUtil() {
    }

    public static <T> Constructor<T> getConstructor(Class<T> target, Class<?>... parameterTypes) {
        try {
            return target.getConstructor(parameterTypes);
        } catch (SecurityException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> target) {
        return getConstructor(target, new Class<?>[]{});
    }

    public static <T> T newInstance(Class<T> target, Object... parameters) {
        Constructor<T> constructor = getConstructor(target, convertTypeArray(parameters));
        try {
            return constructor.newInstance(parameters);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (InstantiationException e) {
            throw new InstantiationRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> target) {
        return newInstance(target, new Object[]{});
    }

    private static Class<?>[] convertTypeArray(Object[] parameters) {
        Class<?>[] classArray = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classArray[i] = parameters[i].getClass();
        }
        return classArray;
    }

}

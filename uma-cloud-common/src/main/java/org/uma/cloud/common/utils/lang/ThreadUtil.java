package org.uma.platform.common.utils.lang;

import org.uma.platform.common.utils.exception.InterruptedRuntimeException;

public class ThreadUtil {

    private ThreadUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new InterruptedRuntimeException(e);
        }
    }

}

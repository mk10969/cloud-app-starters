package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class InterruptedRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 2160812585326398796L;

    public InterruptedRuntimeException(String message) {
        super(message);
    }

    public InterruptedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptedRuntimeException(Throwable cause) {
        super(cause);
    }
}

package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class InstantiationRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 8748773090881328461L;

    public InstantiationRuntimeException(String message) {
        super(message);
    }

    public InstantiationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstantiationRuntimeException(Throwable cause) {
        super(cause);
    }
}

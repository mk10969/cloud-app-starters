package org.uma.cloud.common.utils.exception;

public class JvLinkModelMappingException extends RuntimeException {

    private final String lineData;

    public String getLineData() {
        return this.lineData;
    }

    public JvLinkModelMappingException(String message, String lineData) {
        super(message);
        this.lineData = lineData;
    }

    public JvLinkModelMappingException(String message, Throwable cause, String lineData) {
        super(message, cause);
        this.lineData = lineData;
    }

    public JvLinkModelMappingException(Throwable cause, String lineData) {
        super(cause);
        this.lineData = lineData;
    }

}

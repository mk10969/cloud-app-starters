package org.uma.cloud.common.utils.exception;

public class JvLinkModelNullPointException extends RuntimeException {

    private final String lineData;

    public String getLineData() {
        return this.lineData;
    }

    public JvLinkModelNullPointException(String message, String lineData) {
        super(message);
        this.lineData = lineData;
    }

    public JvLinkModelNullPointException(String message, Throwable cause, String lineData) {
        super(message, cause);
        this.lineData = lineData;
    }

    public JvLinkModelNullPointException(Throwable cause, String lineData) {
        super(cause);
        this.lineData = lineData;
    }
}

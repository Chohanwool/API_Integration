package com.ApiIntegration.Util;

/**
 * 공통 Exception
 *
 *
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private boolean isAlertAndBack = false;

    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super();
        this.message = message;
    }

    public CommonException(String message, boolean isAlertAndBack) {
        super();
        this.message = message;
        this.isAlertAndBack = isAlertAndBack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAlertAndBack() {
        return isAlertAndBack;
    }

    public void setAlertAndBack(boolean isAlertAndBack) {
        this.isAlertAndBack = isAlertAndBack;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

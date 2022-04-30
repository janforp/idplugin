package com.janita.idplugin.common.exception;

public class PluginRuntimeException extends RuntimeException {

    public PluginRuntimeException(String message) {
        super(message);
    }

    public PluginRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PluginRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
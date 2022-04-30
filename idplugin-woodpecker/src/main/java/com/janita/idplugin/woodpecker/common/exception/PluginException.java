package com.janita.idplugin.woodpecker.common.exception;

public class PluginException extends Exception {

    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PluginException(Throwable throwable) {
        super(throwable);
    }
}
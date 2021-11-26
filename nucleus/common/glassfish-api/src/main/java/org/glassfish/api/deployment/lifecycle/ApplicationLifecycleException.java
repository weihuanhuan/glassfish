package org.glassfish.api.deployment.lifecycle;

public class ApplicationLifecycleException extends Exception {

    public ApplicationLifecycleException() {
    }

    public ApplicationLifecycleException(String message) {
        super(message);
    }

    public ApplicationLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationLifecycleException(Throwable cause) {
        super(cause);
    }

    public ApplicationLifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

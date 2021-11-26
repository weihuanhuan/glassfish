package org.glassfish.internal.data;

public class ApplicationListener {

    private final String listenerClass;
    private final String runAsPrincipalName;

    public ApplicationListener(String listenerClass, String runAsPrincipalName) {
        this.listenerClass = listenerClass;
        this.runAsPrincipalName = runAsPrincipalName;
    }

    public String getListenerClass() {
        return listenerClass;
    }

    public String getRunAsPrincipalName() {
        return runAsPrincipalName;
    }

}

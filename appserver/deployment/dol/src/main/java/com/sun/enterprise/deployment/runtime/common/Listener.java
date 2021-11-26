package com.sun.enterprise.deployment.runtime.common;

import com.sun.enterprise.deployment.runtime.RuntimeDescriptor;

/**
 * This is the in memory representation of the listener information.
 * Note that we are keeping just the literal Strings in this object.
 */
public class Listener extends RuntimeDescriptor {

    private String listenerClass = null; //mandatory element
    private String listenerUri = null;
    private String runAsPrincipalName = null;

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public String getListenerUri() {
        return listenerUri;
    }

    public void setListenerUri(String listenerUri) {
        this.listenerUri = listenerUri;
    }

    public String getRunAsPrincipalName() {
        return runAsPrincipalName;
    }

    public void setRunAsPrincipalName(String runAsPrincipalName) {
        this.runAsPrincipalName = runAsPrincipalName;
    }
}

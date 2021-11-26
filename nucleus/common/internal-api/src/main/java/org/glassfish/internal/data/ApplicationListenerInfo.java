package org.glassfish.internal.data;


import java.util.LinkedList;

public class ApplicationListenerInfo {

    final private LinkedList<ApplicationListener> listeners = new LinkedList<>();

    public void addListener(ApplicationListener listener) {
        listeners.add(listener);
    }

    public LinkedList<ApplicationListener> getListeners() {
        return listeners;
    }
}

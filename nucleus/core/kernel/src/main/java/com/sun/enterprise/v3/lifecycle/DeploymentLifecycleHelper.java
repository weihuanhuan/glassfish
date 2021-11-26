package com.sun.enterprise.v3.lifecycle;

import org.glassfish.api.deployment.lifecycle.ApplicationLifecycleEvent;
import org.glassfish.api.deployment.lifecycle.ApplicationLifecycleException;
import org.glassfish.api.deployment.lifecycle.ApplicationLifecycleListener;
import org.glassfish.api.deployment.lifecycle.DeploymentOperationType;
import org.glassfish.internal.data.ApplicationInfo;
import org.glassfish.internal.data.ApplicationListener;
import org.glassfish.internal.data.ApplicationListenerInfo;
import org.glassfish.kernel.KernelLoggerInfo;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeploymentLifecycleHelper {

    protected Logger logger = KernelLoggerInfo.getLogger();

    public void loadLifecycleListener(ApplicationInfo applicationInfo, ApplicationListenerInfo listenerInfo) {
        List<ApplicationListener> listeners = listenerInfo.getListeners();
        if (listeners == null || listeners.isEmpty()) {
            return;
        }

        ClassLoader appClassLoader = applicationInfo.getAppClassLoader();
        if (appClassLoader == null) {
            ApplicationLifecycleException exception = new ApplicationLifecycleException("There is no application classloader to load lifecycle listener class!");
            logger.log(Level.SEVERE, KernelLoggerInfo.lifecycleException, exception);
            return;
        }

        for (ApplicationListener listener : listeners) {
            String listenerClass = listener.getListenerClass();
            if (listenerClass == null || listenerClass.isEmpty()) {
                logger.log(Level.WARNING, "There is an empty lifecycle listener class, just skip it!");
                continue;
            }

            try {
                Class<?> aClass = appClassLoader.loadClass(listenerClass);
                Object instance = aClass.newInstance();
                if (!(instance instanceof ApplicationLifecycleListener)) {
                    logger.log(Level.WARNING, "Instantiated lifecycle listener do not implement %s , just skip it!");
                    continue;
                }
                applicationInfo.addListener((ApplicationLifecycleListener) instance);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                ApplicationLifecycleException exception = new ApplicationLifecycleException("Failed to initialize lifecycle listener instance!", e);
                logger.log(Level.SEVERE, KernelLoggerInfo.lifecycleException, exception);
            }
        }
    }

    public void invokeLifecycleListenerBefore(ApplicationInfo applicationInfo, DeploymentOperationType type) {
        DeploymentLifecycleContext lifecycleContext = new DeploymentLifecycleContext(applicationInfo);

        List<ApplicationLifecycleListener> lifecycleListeners = getApplicationLifecycleListeners(applicationInfo);
        for (ApplicationLifecycleListener listener : lifecycleListeners) {
            try {
                invokeBefore(listener, type, lifecycleContext);
            } catch (Throwable e) {
                logger.log(Level.WARNING, String.format("Failed to invoke pre method of lifecycle listener within deploy operation %s", type.name()), e);
            }
        }
    }

    private void invokeBefore(ApplicationLifecycleListener listener, DeploymentOperationType type, DeploymentLifecycleContext context)
            throws ApplicationLifecycleException {
        ApplicationLifecycleEvent event = new ApplicationLifecycleEvent(context, type);
        switch (type) {
            case START:
                listener.preStart(event);
                break;
            case STOP:
                listener.preStop(event);
                break;
        }
    }

    public void invokeLifecycleListenerAfter(ApplicationInfo applicationInfo, DeploymentOperationType type) {
        DeploymentLifecycleContext lifecycleContext = new DeploymentLifecycleContext(applicationInfo);

        List<ApplicationLifecycleListener> lifecycleListeners = getApplicationLifecycleListeners(applicationInfo);
        for (ApplicationLifecycleListener listener : lifecycleListeners) {
            try {
                invokeAfter(listener, type, lifecycleContext);
            } catch (Throwable e) {
                logger.log(Level.WARNING, String.format("Failed to invoke post method of lifecycle listener within deploy operation %s", type.name()), e);
            }
        }
    }

    private void invokeAfter(ApplicationLifecycleListener listener, DeploymentOperationType type, DeploymentLifecycleContext context)
            throws ApplicationLifecycleException {
        ApplicationLifecycleEvent event = new ApplicationLifecycleEvent(context, type);
        switch (type) {
            case START:
                listener.postStart(event);
                break;
            case STOP:
                listener.postStop(event);
                break;
        }
    }

    public void unloadLifecycleListener(ApplicationInfo applicationInfo) {
        List<ApplicationLifecycleListener> listeners = getApplicationLifecycleListeners(applicationInfo);
        if (!listeners.isEmpty()) {
            listeners.clear();
        }
    }

    private List<ApplicationLifecycleListener> getApplicationLifecycleListeners(ApplicationInfo applicationInfo) {
        List<ApplicationLifecycleListener> listeners = applicationInfo.getListeners();
        if (listeners == null || listeners.isEmpty()) {
            return Collections.emptyList();
        }
        return listeners;
    }
}

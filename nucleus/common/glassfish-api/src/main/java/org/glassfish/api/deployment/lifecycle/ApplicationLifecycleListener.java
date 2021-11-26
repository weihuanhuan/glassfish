package org.glassfish.api.deployment.lifecycle;

public interface ApplicationLifecycleListener {

    public void preStart(ApplicationLifecycleEvent event) throws ApplicationLifecycleException;

    public void postStart(ApplicationLifecycleEvent event) throws ApplicationLifecycleException;

    public void preStop(ApplicationLifecycleEvent event) throws ApplicationLifecycleException;

    public void postStop(ApplicationLifecycleEvent event) throws ApplicationLifecycleException;

}

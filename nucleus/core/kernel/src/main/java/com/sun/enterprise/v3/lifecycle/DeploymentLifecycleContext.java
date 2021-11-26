package com.sun.enterprise.v3.lifecycle;

import org.glassfish.api.deployment.archive.ReadableArchive;
import org.glassfish.api.deployment.lifecycle.ApplicationContext;
import org.glassfish.internal.data.ApplicationInfo;

import java.net.URI;

public class DeploymentLifecycleContext implements ApplicationContext {

    private final ApplicationInfo applicationInfo;

    public DeploymentLifecycleContext(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    @Override
    public String getApplicationName() {
        return applicationInfo.getName();
    }

    @Override
    public String getApplicationId() {
        ReadableArchive source = applicationInfo.getSource();
        if (source == null) {
            return getApplicationName();
        }

        URI uri = source.getURI();
        if (uri == null) {
            return getApplicationName();
        }
        return uri.toString();
    }

    @Override
    public ClassLoader getAppClassLoader() {
        return applicationInfo.getAppClassLoader();
    }

}

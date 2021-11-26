package org.glassfish.api.deployment.lifecycle;

public interface ApplicationContext {

    String getApplicationName();

    String getApplicationId();

    ClassLoader getAppClassLoader();

}

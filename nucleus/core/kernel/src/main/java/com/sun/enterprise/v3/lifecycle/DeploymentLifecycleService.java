package com.sun.enterprise.v3.lifecycle;

import org.glassfish.api.deployment.lifecycle.DeploymentOperationType;
import org.glassfish.internal.data.ApplicationInfo;
import org.glassfish.internal.data.ApplicationListenerInfo;
import org.glassfish.internal.deployment.ApplicationLifecycleInterceptor;
import org.glassfish.internal.deployment.ExtendedDeploymentContext;
import org.glassfish.kernel.KernelLoggerInfo;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Singleton;
import java.util.logging.Logger;

@Service
@Singleton
public class DeploymentLifecycleService implements ApplicationLifecycleInterceptor {

    protected Logger logger = KernelLoggerInfo.getLogger();

    private final DeploymentLifecycleMapper mapper = new DeploymentLifecycleMapper();

    private final DeploymentLifecycleHelper helper = new DeploymentLifecycleHelper();

    @Override
    public void before(ExtendedDeploymentContext.Phase phase, ExtendedDeploymentContext context) {
        if (context == null || !supportedPhase(phase)) {
            return;
        }

        ApplicationListenerInfo listenerInfo = context.getModuleMetaData(ApplicationListenerInfo.class);
        ApplicationInfo applicationInfo = context.getModuleMetaData(ApplicationInfo.class);
        if (!shouldProcess(applicationInfo, listenerInfo, phase)) {
            return;
        }

        switch (phase) {
            case LOAD:
                helper.loadLifecycleListener(applicationInfo, listenerInfo);
                break;
            case START:
            case STOP:
                DeploymentOperationType operationType = mapper.toDeploymentOperationType(phase);
                if (!shouldInvoke(operationType)) {
                    break;
                }
                helper.invokeLifecycleListenerBefore(applicationInfo, operationType);
                break;
            default:
                break;
        }
    }

    @Override
    public void after(ExtendedDeploymentContext.Phase phase, ExtendedDeploymentContext context) {
        if (context == null || !supportedPhase(phase)) {
            return;
        }

        ApplicationInfo applicationInfo = context.getModuleMetaData(ApplicationInfo.class);
        if (!shouldProcess(applicationInfo, null, phase)) {
            return;
        }

        switch (phase) {
            case UNLOAD:
                helper.unloadLifecycleListener(applicationInfo);
                break;
            case START:
            case STOP:
                DeploymentOperationType operationType = mapper.toDeploymentOperationType(phase);
                if (!shouldInvoke(operationType)) {
                    break;
                }
                helper.invokeLifecycleListenerAfter(applicationInfo, operationType);
            default:
                break;
        }
    }

    private boolean supportedPhase(ExtendedDeploymentContext.Phase phase) {
        switch (phase) {
            case START:
            case STOP:
            case UNLOAD:
            case LOAD:
                return true;
            default:
                return false;
        }
    }

    private boolean shouldProcess(ApplicationInfo applicationInfo, ApplicationListenerInfo listenerInfo, ExtendedDeploymentContext.Phase phase) {
        //listener info process
        if (listenerInfo == null) {
            switch (phase) {
                case START:
                case STOP:
                case UNLOAD:
                    break;
                case LOAD:
                default:
                    return false;
            }
        }

        //it is not an ear application
        if (applicationInfo == null) {
            return false;
        }
        return true;
    }

    private boolean shouldInvoke(DeploymentOperationType operationType) {
        switch (operationType) {
            case START:
            case STOP:
                return true;
            case UNKNOWN:
            default:
                return false;
        }
    }

}

package com.sun.enterprise.v3.lifecycle;

import org.glassfish.api.deployment.lifecycle.DeploymentOperationType;
import org.glassfish.internal.deployment.ExtendedDeploymentContext;

public class DeploymentLifecycleMapper {

    public DeploymentOperationType toDeploymentOperationType(ExtendedDeploymentContext.Phase phase) {
        switch (phase) {
            case START:
                return DeploymentOperationType.START;
            case STOP:
                return DeploymentOperationType.STOP;
        }
        return DeploymentOperationType.UNKNOWN;
    }

}

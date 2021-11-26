package org.glassfish.api.deployment.lifecycle;

public enum DeploymentOperationType {

    START(7),
    STOP(8),
    UNKNOWN(-1);

    private final int deploymentTask;

    DeploymentOperationType(int task) {
        this.deploymentTask = task;
    }

}

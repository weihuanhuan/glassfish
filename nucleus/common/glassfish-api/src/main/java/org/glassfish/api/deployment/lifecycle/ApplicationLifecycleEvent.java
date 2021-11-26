package org.glassfish.api.deployment.lifecycle;

public class ApplicationLifecycleEvent {

    private final DeploymentOperationType deploymentOperation;

    private final ApplicationContext context;

    public ApplicationLifecycleEvent(ApplicationContext ctx, DeploymentOperationType deploymentOperation) {
        this.context = ctx;
        this.deploymentOperation = deploymentOperation;
    }

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    public String toString() {
        return "ApplicationLifecycleEvent{" + this.context + "}";
    }

    public DeploymentOperationType getDeploymentOperation() {
        return this.deploymentOperation;
    }

}

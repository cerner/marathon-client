package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class RestartPolicy {
    private Integer activeDeadlineSeconds;
    private String policy;

    public Integer getActiveDeadlineSeconds() {
        return activeDeadlineSeconds;
    }

    public void setActiveDeadlineSeconds(Integer activeDeadlineSeconds) {
        this.activeDeadlineSeconds = activeDeadlineSeconds;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

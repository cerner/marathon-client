package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class JobTask {
    private String startedAt;
    private String id;
    private String status;

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

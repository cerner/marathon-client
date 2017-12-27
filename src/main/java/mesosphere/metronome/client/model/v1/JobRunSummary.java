package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class JobRunSummary {
    private String id;
    private String createdAt;
    private String finishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

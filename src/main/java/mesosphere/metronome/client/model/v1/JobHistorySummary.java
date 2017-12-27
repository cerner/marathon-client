package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.List;

import mesosphere.client.common.ModelUtils;

public class JobHistorySummary {
    private Integer successCount;
    private Integer failureCount;
    private String lastSuccessAt;
    private String lastFailureAt;

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public String getLastSuccessAt() {
        return lastSuccessAt;
    }

    public void setLastSuccessAt(String lastSuccessAt) {
        this.lastSuccessAt = lastSuccessAt;
    }

    public String getLastFailureAt() {
        return lastFailureAt;
    }

    public void setLastFailureAt(String lastFailureAt) {
        this.lastFailureAt = lastFailureAt;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

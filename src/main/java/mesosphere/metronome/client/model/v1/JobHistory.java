package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.List;

import mesosphere.client.common.ModelUtils;

public class JobHistory extends JobHistorySummary {
    private List<JobRunSummary> successfulFinishedRuns;
    private List<JobRunSummary> failedFinishedRuns;

    public List<JobRunSummary> getSuccessfulFinishedRuns() {
        return successfulFinishedRuns;
    }

    public void addSuccessfulFinishedRun(JobRunSummary successfulFinishedRun) {
        if (this.successfulFinishedRuns == null) {
            this.successfulFinishedRuns = new ArrayList<>();
        }
        this.successfulFinishedRuns.add(successfulFinishedRun);
    }

    public void setSuccessfulFinishedRuns(List<JobRunSummary> successfulFinishedRuns) {
        this.successfulFinishedRuns = successfulFinishedRuns;
    }

    public List<JobRunSummary> getFailedFinishedRuns() {
        return failedFinishedRuns;
    }

    public void addFailedFinishedRun(JobRunSummary failedFinishedRun) {
        if (this.failedFinishedRuns == null) {
            this.failedFinishedRuns = new ArrayList<>();
        }
        this.failedFinishedRuns.add(failedFinishedRun);
    }

    public void setFailedFinishedRuns(List<JobRunSummary> failedFinishedRuns) {
        this.failedFinishedRuns = failedFinishedRuns;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

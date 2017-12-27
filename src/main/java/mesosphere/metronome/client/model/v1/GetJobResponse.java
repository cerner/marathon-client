package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.List;

import mesosphere.client.common.ModelUtils;

public class GetJobResponse extends Job {
    private List<JobRun> activeRuns;
    private JobHistory history;
    private JobHistorySummary historySummary;

    public List<JobRun> getActiveRuns() {
        return activeRuns;
    }

    public void setActiveRuns(List<JobRun> activeRuns) {
        this.activeRuns = activeRuns;
    }

    public void addActiveRun(JobRun activeRun) {
        if (this.activeRuns == null) {
            this.activeRuns = new ArrayList<>();
        }
        this.activeRuns.add(activeRun);
    }

    public JobHistory getHistory() {
        return history;
    }

    public void setHistory(JobHistory history) {
        this.history = history;
    }

    public JobHistorySummary getHistorySummary() {
        return historySummary;
    }

    public void setHistorySummary(JobHistorySummary historySummary) {
        this.historySummary = historySummary;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

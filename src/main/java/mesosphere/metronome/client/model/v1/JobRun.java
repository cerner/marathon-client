package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.List;

import mesosphere.client.common.ModelUtils;

public class JobRun {
    private String completedAt;
    private String createdAt;
    private String id;
    private String jobId;
    private String status;
    private List<JobTask> tasks;

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JobTask> getTasks() {
        return tasks;
    }

    public void addTask(JobTask task) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.add(task);
    }

    public void setTasks(List<JobTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

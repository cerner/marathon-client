package mesosphere.mesos.client.model;

import java.util.List;

public class MesosFramework {
    private String id;
    private String name;
    private List<MesosTask> completed_tasks;
    private List<MesosExecutor> completed_executors;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MesosTask> getCompleted_tasks() {
        return completed_tasks;
    }

    public List<MesosExecutor> getCompleted_executors() {
        return completed_executors;
    }
}

package mesosphere.mesos.client.model;

public class MesosTask {
    private String id;
    private String name;
    private String framework_id;
    private String slave_id;
    private String executor_id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFramework_id() {
        return framework_id;
    }

    public String getSlave_id() {
        return slave_id;
    }

    public String getExecutor_id() {
        return executor_id;
    }
}

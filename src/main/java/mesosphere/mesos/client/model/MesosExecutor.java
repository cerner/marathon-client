package mesosphere.mesos.client.model;

public class MesosExecutor {
    private String id;
    private String source;
    private String container;

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getContainer() {
        return container;
    }
}

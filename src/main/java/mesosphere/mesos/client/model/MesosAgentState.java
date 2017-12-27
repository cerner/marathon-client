package mesosphere.mesos.client.model;

import java.util.List;

public class MesosAgentState extends MesosState {
    private List<MesosFramework> completed_frameworks;

    public List<MesosFramework> getCompleted_frameworks() {
        return completed_frameworks;
    }
}

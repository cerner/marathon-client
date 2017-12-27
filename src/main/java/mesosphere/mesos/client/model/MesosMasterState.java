package mesosphere.mesos.client.model;

import java.util.List;

public class MesosMasterState extends MesosState {
    private List<MesosFramework> frameworks;

    public List<MesosFramework> getFrameworks() {
        return frameworks;
    }
}

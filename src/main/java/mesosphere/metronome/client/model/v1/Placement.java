package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.List;

import mesosphere.client.common.ModelUtils;

public class Placement {
    private List<Constraint> constraints;

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void addConstraint(Constraint constraint) {
        if (constraints == null) {
            constraints = new ArrayList<>();
        }
        this.constraints.add(constraint);
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

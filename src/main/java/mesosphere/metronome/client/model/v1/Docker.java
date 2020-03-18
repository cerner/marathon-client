package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

import java.util.Collection;

public class Docker {
    private String image;
    private Collection<Parameter> parameters;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

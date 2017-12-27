package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class Docker {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

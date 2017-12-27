package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class Constraint {
    private String attribute;
    private String operator;
    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}

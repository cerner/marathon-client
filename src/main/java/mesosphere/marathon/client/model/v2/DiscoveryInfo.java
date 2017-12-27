package mesosphere.marathon.client.model.v2;

import java.util.ArrayList;
import java.util.Collection;

import mesosphere.client.common.ModelUtils;

public class DiscoveryInfo {
	private Collection<Port> ports;

	public Collection<Port> getPorts() {
		return ports;
	}

	public void setPorts(Collection<Port> ports) {
		this.ports = ports;
	}

	public void addPort(Port port) {
		if (ports == null) {
			ports = new ArrayList<Port>();
		}
		ports.add(port);
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}

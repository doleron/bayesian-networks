package bayesian.networks.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BayesianNetwork {

	private String name;
	private Map<String, BayesianNetworkNode> nodesMap = new HashMap<>();

	public String getName() {
		return name;
	}

	public void addNode(BayesianNetworkNode node) {
		if (node != null) {
			this.nodesMap.put(node.getRandomVariable().getIdentifier(), node);
		}
	}
	
	public BayesianNetworkNode getNode(String identifier) {
		return nodesMap.get(identifier);
	}
	
	public Collection<BayesianNetworkNode> getNodes() {
		return this.nodesMap.values();
	}

}

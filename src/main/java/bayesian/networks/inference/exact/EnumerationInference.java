package bayesian.networks.inference.exact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bayesian.networks.core.BayesianNetwork;
import bayesian.networks.core.BayesianNetworkNode;
import bayesian.networks.inference.Inference;
import bayesian.networks.inference.NodeSorter;
import bayesian.networks.stats.DiscreteDistribution;

public class EnumerationInference implements Inference {
	
	private BayesianNetwork bayesianNetwork;
	
	private List<BayesianNetworkNode> sorted;

	public EnumerationInference(BayesianNetwork bayesianNetwork) {
		super();
		this.bayesianNetwork = bayesianNetwork;
		this.sorted = new NodeSorter().sort(this.bayesianNetwork);
	}

	@Override
	public DiscreteDistribution doInference(Map<BayesianNetworkNode, String> evidence, BayesianNetworkNode query) {
		DiscreteDistribution result = new DiscreteDistribution();
		List<String> values = query.getDomain().getValues();
		for(String value : values) {
			Map<BayesianNetworkNode, String> clone = new HashMap<>(evidence);
			clone.put(query, value);
			double probability = probability(this.sorted, clone);
			ArrayList<String> states = new ArrayList<>();
			states.add(value);
			DiscreteDistribution.Entry entry = result.new Entry(states);
			result.add(entry, probability);
		}
		result.normalize();
		return result;
	}
	
	private double probability(List<BayesianNetworkNode> nodes, Map<BayesianNetworkNode, String> evidence) {
		double result = 1.0;
		if(!nodes.isEmpty()) {
			List<BayesianNetworkNode> remainNodes = new ArrayList<>(nodes);
			BayesianNetworkNode node = remainNodes.remove(0);
			if(evidence.containsKey(node)) {
				result = node.getProbability(evidence) * probability(remainNodes, evidence);
			} else {
				List<String> values = node.getDomain().getValues();
				
				result = values.stream().mapToDouble(value -> {
					Map<BayesianNetworkNode, String> clone = new HashMap<>(evidence);
					clone.put(node, value);
					return node.getProbability(clone) * probability(remainNodes, clone);
				}).sum();
				
			}
		}
		return result;
	}

}

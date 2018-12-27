package bayesian.networks.inference;

import java.util.Map;

import bayesian.networks.core.BayesianNetworkNode;
import bayesian.networks.stats.DiscreteDistribution;

public interface Inference {
	
	DiscreteDistribution doInference(Map<BayesianNetworkNode, String> evidence, BayesianNetworkNode query);

}

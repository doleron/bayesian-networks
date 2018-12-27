package bayesian.networks.inference;

import java.util.Map;

import bayesian.networks.stats.DiscreteDistribution;
import bayesian.networks.stats.RandomVariable;

public interface Inference {
	
	DiscreteDistribution doInference(Map<RandomVariable, String> evidence, RandomVariable query);

}

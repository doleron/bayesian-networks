package bayesian.networks.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import bayesian.networks.stats.DiscreteDistribution;
import bayesian.networks.stats.Domain;

public class BayesianNetworkNode {
	
	private Long id;
	
	private String identifier;
	private Domain domain;
	
	private List<BayesianNetworkNode> parents;
	
	private DiscreteDistribution cpt;

	public BayesianNetworkNode(Long id, String identifier, Domain domain, List<BayesianNetworkNode> parents) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.domain = domain;
		this.setParents(parents);
	}
	
	public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	public Domain getDomain() {
		return domain;
	}

	public List<BayesianNetworkNode> getParents() {
		return parents;
	}
	
	public void setParents(List<BayesianNetworkNode> parents) {
		if(parents != null) {
			this.parents = Collections.unmodifiableList(parents);
		}
	}
	
	public DiscreteDistribution getCpt() {
		return cpt;
	}
	
	public void setCpt(DiscreteDistribution cpt) {
		this.cpt = cpt;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof BayesianNetworkNode) {
			BayesianNetworkNode other = (BayesianNetworkNode) obj;
			result = this.identifier.equals(other.identifier);
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		int result = 37;
		if(this.identifier != null) {
			result = this.identifier.hashCode();
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "id " + this.getId();
		if(this.identifier != null) {
			result = result + " - " + this.identifier;
		} else {
			result = result + " - no-var";
		}
		return result;
	}

	public double getProbability(Map<BayesianNetworkNode, String> evidence) {
		ArrayList<String> states = new ArrayList<>(this.parents.size() + 1);
		for(BayesianNetworkNode parent : this.parents) {
			String value = evidence.get(parent);
			if(value == null) {
				throw new IllegalArgumentException("Evidence has't a value for " + parent.getIdentifier());
			}
			states.add(value);
		}
		String value = evidence.get(this);
		states.add(value);
		DiscreteDistribution.Entry entry = this.cpt.new Entry(states);
		Double result = this.cpt.getValue(entry);
		return result;
	}

}

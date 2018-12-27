package bayesian.networks.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscreteDistribution {

	public class Entry {

		private List<String> states;

		public Entry(List<String> states) {
			super();
			this.states = states;
		}

		public List<String> getStates() {
			return states;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			states.forEach(value -> {
				builder.append(value);
				builder.append("\t");
			});
			return builder.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((states == null) ? 0 : states.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (states == null) {
				if (other.states != null)
					return false;
			} else if (!states.equals(other.states))
				return false;
			return true;
		}

		private DiscreteDistribution getOuterType() {
			return DiscreteDistribution.this;
		}

	}

	private Map<Entry, Double> map = new HashMap<>();

	public void add(Entry entry, Double value) {
		this.map.put(entry, value);
	}

	public Double getValue(Entry entry) {
		return this.map.get(entry);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		this.map.entrySet().forEach(entry -> {
			builder.append(entry.getKey().toString());
			builder.append("\t");
			builder.append(entry.getValue().toString());
			builder.append("\n");
		});
		
		return builder.toString();
	}

	public void normalize() {
		double sum = this.map.values().stream().mapToDouble(f -> f.doubleValue()).sum();
		this.map.keySet().forEach(key -> {
			Double value = this.map.get(key);
			value = value / sum;
			this.map.put(key, value);
		});
	}

}

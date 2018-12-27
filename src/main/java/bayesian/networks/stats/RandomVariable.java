package bayesian.networks.stats;

public class RandomVariable {

	private String identifier;
	private Domain domain;

	public RandomVariable(String identifier, Domain domain) {
		super();
		this.identifier = identifier;
		this.domain = domain;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Domain getDomain() {
		return domain;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof RandomVariable) {
			RandomVariable other = (RandomVariable) obj;
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
		String result = "no-name";
		if(this.identifier != null) {
			result = this.identifier;
		}
		return result;
	}

}

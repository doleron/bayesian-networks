package bayesian.networks.stats;

import java.util.Collections;
import java.util.List;

public class Domain {
	
	private List<String> values;
	
	public Domain(List<String> values) {
		super();
		this.values = Collections.unmodifiableList(values);
	}

	public List<String> getValues() {
		return values;
	}

}

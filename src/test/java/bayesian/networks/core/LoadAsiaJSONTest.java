package bayesian.networks.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import bayesian.networks.io.JSONBNParser;

public class LoadAsiaJSONTest {

	@Test
	public void loadTest() throws ParseException, FileNotFoundException, IOException {

		JSONBNParser jsonbnParser = new JSONBNParser();

		BayesianNetwork network = jsonbnParser.parse("networks/asia.json");

		Collection<BayesianNetworkNode> nodes = network.getNodes();

		assertNotNull("nodes list not found", nodes);

		assertEquals(8, nodes.size());

		checkNode(network, "asia", 0);
		checkNode(network, "smoke", 0);
		checkNode(network, "tub", 1);
		checkNode(network, "lung", 1);
		checkNode(network, "bronc", 1);
		checkNode(network, "either", 2);
		checkNode(network, "xray", 1);
		checkNode(network, "dysp", 2);
	}

	private void checkNode(BayesianNetwork network, String nodeIdentifier, int expectedParentsSize) {
		BayesianNetworkNode node = network.getNode(nodeIdentifier);
		assertNotNull(nodeIdentifier + " node not found", node);

		List<BayesianNetworkNode> parents = node.getParents();
		assertNotNull(nodeIdentifier + " parents can't be null", parents);

		assertEquals(expectedParentsSize, parents.size());
	}

}

package bayesian.networks.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import bayesian.networks.inference.NodeSorter;
import bayesian.networks.io.JSONBNParser;

public class SortAsiaTest {

	@Test
	public void test() throws FileNotFoundException, ParseException, IOException{

		JSONBNParser jsonbnParser = new JSONBNParser();

		BayesianNetwork network = jsonbnParser.parse("networks/asia.json");

		List<BayesianNetworkNode> sortedNodes = new NodeSorter().sort(network);

		assertNotNull(sortedNodes);

		assertEquals(8, sortedNodes.size());

		List<String> expectedSequence = Arrays.asList("asia", "tub", "smoke", "lung", "either", "xray", "bronc",
				"dysp");

		IntStream.range(0, expectedSequence.size()).forEach(i -> assertEquals("element " + i + " doesn't match",
				expectedSequence.get(i), sortedNodes.get(i).getRandomVariable().getIdentifier()));

	}

}

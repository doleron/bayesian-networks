package bayesian.networks.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import bayesian.networks.inference.Inference;
import bayesian.networks.inference.exact.EnumerationInference;
import bayesian.networks.io.JSONBNParser;
import bayesian.networks.stats.DiscreteDistribution;

public class InferenceAsiaTest {
	
	private static BayesianNetwork network;
	private static Inference inference;
	
	@BeforeClass
	public static void setup() throws FileNotFoundException, ParseException, IOException {
		JSONBNParser jsonbnParser = new JSONBNParser();

		network = jsonbnParser.parse("networks/asia.json");
		
		inference = new EnumerationInference(network);
	}

	@Test
	public void testEmptyEvidence() throws FileNotFoundException, ParseException, IOException{

		Map<BayesianNetworkNode, String> evidence = new HashMap<>();

		BayesianNetworkNode query = network.getNode("either");
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.0648, probability.getValue(entry), 1e-4);

	}

	@Test
	public void testAsia() throws FileNotFoundException, ParseException, IOException{

		Map<BayesianNetworkNode, String> evidence = new HashMap<>();

		BayesianNetworkNode asia = network.getNode("asia");
		evidence.put(asia, "yes");
		
		BayesianNetworkNode query = network.getNode("either");
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.1023, probability.getValue(entry), 1e-4);

	}

	@Test
	public void testAsiaAndSmoke() throws FileNotFoundException, ParseException, IOException{

		Map<BayesianNetworkNode, String> evidence = new HashMap<>();

		BayesianNetworkNode asia = network.getNode("asia");
		evidence.put(asia, "yes");
		BayesianNetworkNode smoke = network.getNode("smoke");
		evidence.put(smoke, "yes");
		
		BayesianNetworkNode query = network.getNode("either");
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.145, probability.getValue(entry), 1e-4);

	}
	
	@Test
	public void testAsiaSmokeAndXray() throws FileNotFoundException, ParseException, IOException{

		Map<BayesianNetworkNode, String> evidence = new HashMap<>();

		BayesianNetworkNode asia = network.getNode("asia");
		evidence.put(asia, "yes");
		BayesianNetworkNode smoke = network.getNode("smoke");
		evidence.put(smoke, "yes");
		BayesianNetworkNode xray = network.getNode("xray");
		evidence.put(xray, "yes");
		
		BayesianNetworkNode query = network.getNode("either");
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.7687, probability.getValue(entry), 1e-4);

	}
	
	@Test
	public void testAsiaSmokeXrayAndDiyspnea() throws FileNotFoundException, ParseException, IOException{

		Map<BayesianNetworkNode, String> evidence = new HashMap<>();

		BayesianNetworkNode asia = network.getNode("asia");
		evidence.put(asia, "yes");
		BayesianNetworkNode smoke = network.getNode("smoke");
		evidence.put(smoke, "yes");
		BayesianNetworkNode xray = network.getNode("xray");
		evidence.put(xray, "yes");
		BayesianNetworkNode dysp = network.getNode("dysp");
		evidence.put(dysp, "yes");
		
		BayesianNetworkNode query = network.getNode("either");
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.8398, probability.getValue(entry), 1e-4);

	}

}

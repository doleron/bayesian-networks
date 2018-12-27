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
import bayesian.networks.stats.RandomVariable;

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

		Map<RandomVariable, String> evidence = new HashMap<>();

		RandomVariable query = network.getNode("either").getRandomVariable();
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.0648, probability.getValue(entry), 1e-4);

	}

	@Test
	public void testAsia() throws FileNotFoundException, ParseException, IOException{

		Map<RandomVariable, String> evidence = new HashMap<>();

		RandomVariable asia = network.getNode("asia").getRandomVariable();
		evidence.put(asia, "yes");
		
		RandomVariable query = network.getNode("either").getRandomVariable();
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.1023, probability.getValue(entry), 1e-4);

	}

	@Test
	public void testAsiaAndSmoke() throws FileNotFoundException, ParseException, IOException{

		Map<RandomVariable, String> evidence = new HashMap<>();

		RandomVariable asia = network.getNode("asia").getRandomVariable();
		evidence.put(asia, "yes");
		RandomVariable smoke = network.getNode("smoke").getRandomVariable();
		evidence.put(smoke, "yes");
		
		RandomVariable query = network.getNode("either").getRandomVariable();
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.145, probability.getValue(entry), 1e-4);

	}
	
	@Test
	public void testAsiaSmokeAndXray() throws FileNotFoundException, ParseException, IOException{

		Map<RandomVariable, String> evidence = new HashMap<>();

		RandomVariable asia = network.getNode("asia").getRandomVariable();
		evidence.put(asia, "yes");
		RandomVariable smoke = network.getNode("smoke").getRandomVariable();
		evidence.put(smoke, "yes");
		RandomVariable xray = network.getNode("xray").getRandomVariable();
		evidence.put(xray, "yes");
		
		RandomVariable query = network.getNode("either").getRandomVariable();
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.7687, probability.getValue(entry), 1e-4);

	}
	
	@Test
	public void testAsiaSmokeXrayAndDiyspnea() throws FileNotFoundException, ParseException, IOException{

		Map<RandomVariable, String> evidence = new HashMap<>();

		RandomVariable asia = network.getNode("asia").getRandomVariable();
		evidence.put(asia, "yes");
		RandomVariable smoke = network.getNode("smoke").getRandomVariable();
		evidence.put(smoke, "yes");
		RandomVariable xray = network.getNode("xray").getRandomVariable();
		evidence.put(xray, "yes");
		RandomVariable dysp = network.getNode("dysp").getRandomVariable();
		evidence.put(dysp, "yes");
		
		RandomVariable query = network.getNode("either").getRandomVariable();
		
		DiscreteDistribution probability = inference.doInference(evidence, query);
		
		assertNotNull(probability);
		DiscreteDistribution.Entry entry = probability.new Entry(Arrays.asList("yes"));
		assertEquals(0.8398, probability.getValue(entry), 1e-4);

	}

}

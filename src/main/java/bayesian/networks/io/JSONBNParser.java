package bayesian.networks.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import bayesian.networks.core.BayesianNetwork;
import bayesian.networks.core.BayesianNetworkNode;
import bayesian.networks.stats.DiscreteDistribution;
import bayesian.networks.stats.Domain;
import bayesian.networks.stats.RandomVariable;

public class JSONBNParser {

	private final class NodeComparatorById implements Comparator<BayesianNetworkNode> {
		@Override
		public int compare(BayesianNetworkNode o1, BayesianNetworkNode o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}

	final NodeComparatorById nodeComparatorById = new NodeComparatorById();

	JSONParser parser = new JSONParser();

	public BayesianNetwork parse(String filepath) throws ParseException, FileNotFoundException, IOException {

		try (InputStream fileInputStream = new FileInputStream(filepath);
				InputStreamReader input = new InputStreamReader(fileInputStream)) {

			JSONObject root = (JSONObject) parser.parse(input);

			BayesianNetwork result = new BayesianNetwork();

			JSONArray jsonNodes = (JSONArray) root.get("nodes");

			// first enumerate all nodes

			for (Object jsonNodeObject : jsonNodes) {
				JSONObject jsonNode = (JSONObject) jsonNodeObject;

				Long id = (Long) jsonNode.get("id");

				String identifier = (String) jsonNode.get("title");

				JSONArray jsonNodesValues = (JSONArray) jsonNode.get("values");

				List<String> values = new ArrayList<>(jsonNodesValues.size());

				for (Object jsonNodesValue : jsonNodesValues) {
					String value = (String) jsonNodesValue;
					values.add(value);
				}

				Domain domain = new Domain(values);
				RandomVariable randomVariable = new RandomVariable(identifier, domain);
				BayesianNetworkNode node = new BayesianNetworkNode(id, randomVariable, null);
				result.addNode(node);

			}

			JSONArray edges = (JSONArray) root.get("edges");

			Map<String, List<BayesianNetworkNode>> mapParents = new HashMap<>();

			for (Object edgeObject : edges) {
				JSONObject edge = (JSONObject) edgeObject;

				JSONObject source = (JSONObject) edge.get("source");
				String parentIdentifier = (String) source.get("title");

				JSONObject target = (JSONObject) edge.get("target");
				String nodeIdentifier = (String) target.get("title");

				List<BayesianNetworkNode> parents = mapParents.get(nodeIdentifier);
				if (parents == null) {
					BayesianNetworkNode node = result.getNode(nodeIdentifier);
					if (node == null) {
						throw new IllegalArgumentException(parentIdentifier + " node not found");
					}
					parents = new LinkedList<>();
					mapParents.put(nodeIdentifier, parents);
				}
				BayesianNetworkNode parent = result.getNode(parentIdentifier);
				if (parent == null) {
					throw new IllegalArgumentException(parentIdentifier + " parent node not found");
				}
				parents.add(parent);
			}

			// second step is set the parent relationship

			Collection<BayesianNetworkNode> nodes = result.getNodes();

			for (BayesianNetworkNode node : nodes) {
				List<BayesianNetworkNode> parents = mapParents.get(node.getRandomVariable().getIdentifier());
				if (parents != null) {
					Collections.sort(parents, nodeComparatorById);
					node.setParents(parents);
				} else {
					node.setParents(new ArrayList<>(0));
				}
			}

			// finally set up the CPT tables

			for (Object jsonNodeObject : jsonNodes) {

				JSONObject jsonNode = (JSONObject) jsonNodeObject;
				String identifier = (String) jsonNode.get("title");
				BayesianNetworkNode node = result.getNode(identifier);
				List<BayesianNetworkNode> parents = node.getParents();

				JSONObject tblObj = (JSONObject) jsonNode.get("tbl");
				DiscreteDistribution cpt = new DiscreteDistribution();

				ArrayList<BayesianNetworkNode> remainParents = new ArrayList<BayesianNetworkNode>(parents);
				loadCPTEntry(new ArrayList<String>(), remainParents, node, tblObj, cpt);

			}

			return result;
		}
	}

	private void loadCPTEntry(List<String> states, List<BayesianNetworkNode> remainParents, BayesianNetworkNode node,
			JSONObject jsonObject, DiscreteDistribution cpt) {

		if (remainParents.isEmpty()) {
			List<String> values = node.getRandomVariable().getDomain().getValues();
			for (String value : values) {
				
				List<String> newStates = new ArrayList<>(states);
				newStates.add(value);
				String key = node.getId() + value;
				JSONArray entryValue = (JSONArray) jsonObject.get(key);
				String probString = (String)entryValue.get(0);
				Double prob = Double.parseDouble(probString);
				DiscreteDistribution.Entry entry = cpt.new Entry(newStates);
				cpt.add(entry, prob);
				node.setCpt(cpt);
			}
		} else {
			BayesianNetworkNode first = remainParents.remove(0);
			List<String> values = first.getRandomVariable().getDomain().getValues();
			for (String value : values) {
				List<String> newStates = new ArrayList<>(states);
				newStates.add(value);
				List<BayesianNetworkNode> newRemains = new ArrayList<>(remainParents);
				JSONObject nestedJsonObject = (JSONObject) jsonObject.get(first.getId() + value);
				loadCPTEntry(newStates, newRemains, node, nestedJsonObject, cpt);
			}
		}

	}

}

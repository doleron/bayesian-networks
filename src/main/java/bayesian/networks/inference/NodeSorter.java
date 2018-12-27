package bayesian.networks.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bayesian.networks.core.BayesianNetwork;
import bayesian.networks.core.BayesianNetworkNode;

/**
 * 
 * @author luizdoleron
 *
 * class to perform the topologyc sort of the nodes of a bayesian network using the Tarjan's algorithm
 * 
 * @see https://www.youtube.com/watch?v=QnWDU1wcsPA
 *
 */

public class NodeSorter {
	
	public List<BayesianNetworkNode> sort(BayesianNetwork network) {
		Collection<BayesianNetworkNode> nodes = network.getNodes();
		List<BayesianNetworkNode> result = new ArrayList<>(nodes.size());
		Set<BayesianNetworkNode> visited = new HashSet<>();
		
		nodes.forEach(node -> visit(node, result, visited));
		
		return result;
	}
	
	/**
	 * 
	 * auxiliary method to mark as visited a node after visit its parents
	 * 
	 * in the original algorithm the visited node is added in the begin of the return list
	 * However, in this implementation the node is included in the end in order to obtain the list in reverse order
	 * 
	 */
	private void visit(BayesianNetworkNode node, List<BayesianNetworkNode> sortedList, Set<BayesianNetworkNode> visited) {
		if(!visited.contains(node)) {
			node.getParents().forEach(parent -> visit(parent, sortedList, visited));
			visited.add(node);
			
			sortedList.add(node);
		}
	}

}

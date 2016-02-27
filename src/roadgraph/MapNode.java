package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author Nelson Nunes
 * 
 * A class which represents an intersection
 *
 */

public class MapNode {
	
	List<MapEdge> neighbors;
	
	public MapNode() {
		neighbors = new ArrayList<>();
	}
	
	public void addEdge(GeographicPoint endVertex, String roadName, String roadType, double length){
		MapEdge edge = new MapEdge(endVertex, roadName, roadType, length);
		neighbors.add(edge);
	}
	
	public int getNumEdges(){
		return neighbors.size();
	}
	
	public List<MapEdge> getNeighbors(){
		return neighbors;
	}
		
}

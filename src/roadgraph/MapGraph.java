/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

	Map<GeographicPoint, MapNode> nodes;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		nodes = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return nodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return nodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int numEgdes = 0;
		
		for(GeographicPoint location : nodes.keySet()){
			numEgdes += nodes.get(location).getNumEdges();
		}
		
		return numEgdes;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if(nodes.containsKey(location) || location == null){
			return false;
		}
		else{
			nodes.put(location, new MapNode());
			return true;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if((from == null) || (to == null) || (roadName == null) || (roadType == null) || (length < 0) || (!nodes.containsKey(from)) || (!nodes.containsKey(to))){
			throw new IllegalArgumentException("Invalid edge.");
		}
		else{
			nodes.get(from).addEdge(to, roadName, roadType, length);
		}
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		
		List<GeographicPoint> queue = new ArrayList<>();
		Set<GeographicPoint> visitedNodes = new HashSet<>();
		Map<GeographicPoint, GeographicPoint> parentNodes = new HashMap<>();
		
		// Initialize queue
		queue.add(start);
		visitedNodes.add(start);
		
		while(!queue.isEmpty()){
			
			GeographicPoint curr = queue.get(0);
			// Hook for visualization.
			nodeSearched.accept(curr);
			queue.remove(0);
			System.out.println("Current Node: " + curr);
			
			if((curr.getX() == goal.getX()) && (curr.getY() == goal.getY())){
				System.out.println("Found Goal");
				return processPath(start, goal, parentNodes);
			}
			
			for(MapEdge edge : nodes.get(curr).getNeighbors()){
				GeographicPoint endVertex = edge.getEndVertex();
				
				if(!visitedNodes.contains(endVertex)){
					visitedNodes.add(endVertex);
					queue.add(endVertex);
					parentNodes.put(endVertex, curr);
				}
				
			}
			
		}
		
		return null;
	}
	
	/** Find the path from goal node to start node
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentNodes
	 * @return The list of nodes that form the path from
	 *   start to goal (including both start and goal).
	 */
	private List<GeographicPoint> processPath(GeographicPoint start, GeographicPoint goal, Map<GeographicPoint, GeographicPoint> parentNodes){
		
		GeographicPoint current = goal;
		GeographicPoint parent = parentNodes.get(current);
		
		List<GeographicPoint> path = new ArrayList<GeographicPoint>();
		path.add(current);
		path.add(parent);
		
		while(parent != start){
			 current = parent;
			 parent = parentNodes.get(current);
			 path.add(parent);
		}
		
		Collections.reverse(path);
		
		return path;
	}

	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}
	
	/** Print the graph structure. Useful for debug.
	 * 
	 */
	public void printGraph(){
		for(GeographicPoint location : nodes.keySet()){
			System.out.println(location.getX() + "," + location.getY() + " ->");
			for(MapEdge edge : nodes.get(location).getNeighbors()){
				System.out.println(edge.getEndVertex() + ", ");
			}
			System.out.println("\n");
		}
	}

	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		System.out.println("Vertices = " + theMap.getNumVertices());
		System.out.println("Edges = " + theMap.getNumEdges());
		theMap.printGraph();
		
		
		List<GeographicPoint> path = theMap.bfs(new GeographicPoint(5,1), new GeographicPoint(8,-1));
		System.out.println(path);
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}

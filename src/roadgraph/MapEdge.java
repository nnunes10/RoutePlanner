package roadgraph;

import geography.GeographicPoint;

/**
 * @author Nelson Nunes
 * 
 * A class which represents a road between two nodes
 *
 */

public class MapEdge {
	
	GeographicPoint endVertex;
	
	String roadName;
	String roadType;
	
	double length;
	
	public MapEdge(GeographicPoint endVertex, String roadName, String roadType, double length){
		this.endVertex = endVertex;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}

	public GeographicPoint getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(GeographicPoint endVertex) {
		this.endVertex = endVertex;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
}

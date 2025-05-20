package pathfinder;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import model.Factory;

//graphe orienté pondéré.

public class FactoryToGraph {
	
	private Graph<Position, DefaultWeightedEdge> graph;
	//on crée un ensemble de positions autorisées pour forcer le passage par les portes par ex
	private Set<Position> allowedVertices;
	private Factory factory;
	
	public FactoryToGraph(Factory factory) {
		this.graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.allowedVertices = new HashSet<>();
		this.factory = factory;
		createGraph(factory);
	}
	
	public void createGraph(Factory factory) {
		int xBottomRight = factory.getWidth();
		int yBottomRight = factory.getHeight();
		
		for (int x = 0; x <= xBottomRight; x++) {
            for (int y = 0; y <= yBottomRight; y++) {
                Position pos = new Position(x, y);
                this.graph.addVertex(pos);

                if (y > 0) {
                    Position topNeighbor = new Position(x, y - 1);
                    DefaultWeightedEdge edge1 = this.graph.addEdge(pos, topNeighbor);
                    DefaultWeightedEdge edge2 = this.graph.addEdge(topNeighbor, pos);
                    this.graph.setEdgeWeight(edge1, 1);
                    this.graph.setEdgeWeight(edge2, 1);
                }
                if (x > 0) {
                    Position leftNeighbor = new Position(x - 1, y);
                    DefaultWeightedEdge edge1 = this.graph.addEdge(pos, leftNeighbor);
                    DefaultWeightedEdge edge2 = this.graph.addEdge(leftNeighbor, pos);
                    this.graph.setEdgeWeight(edge1, 1);
                    this.graph.setEdgeWeight(edge2, 1);
                }
                if (y > 0 && x > 0) {
                    Position topLeftNeighbor = new Position(x - 1, y - 1);
                    Position topNeighbor = new Position(x, y - 1);
                    Position leftNeighbor = new Position(x - 1, y);
                    
                    DefaultWeightedEdge edge1 = this.graph.addEdge(pos, topLeftNeighbor);
                    DefaultWeightedEdge edge2 = this.graph.addEdge(topLeftNeighbor, pos);
                    this.graph.setEdgeWeight(edge1, Math.sqrt(2));
                    this.graph.setEdgeWeight(edge2, Math.sqrt(2));
                    
                    DefaultWeightedEdge edge3 = this.graph.addEdge(leftNeighbor, topNeighbor);
                    DefaultWeightedEdge edge4 = this.graph.addEdge(topNeighbor, leftNeighbor);
                    this.graph.setEdgeWeight(edge3, Math.sqrt(2));
                    this.graph.setEdgeWeight(edge4, Math.sqrt(2));
                    
                }   
                
            }
        }		
	}
	
	//sous-graphe à partir des positions autorisées dans l'usine
     
	public Graph<Position, DefaultWeightedEdge> createSubGraph() {
        
        this.allowedVertices = new HashSet<>(factory.allOverlay());
        Graph<Position, DefaultWeightedEdge> copiedGraph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (Position vertex : graph.vertexSet()) {
            if (allowedVertices.contains(vertex)) {
                copiedGraph.addVertex(vertex);
            }
        }
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            Position source = graph.getEdgeSource(edge);
            Position target = graph.getEdgeTarget(edge);
            if (allowedVertices.contains(source) && allowedVertices.contains(target)) {
                copiedGraph.addEdge(source, target, edge);
                copiedGraph.setEdgeWeight(edge, graph.getEdgeWeight(edge));
            }
        }
        return copiedGraph;
    }

	
	public Graph<Position, DefaultWeightedEdge> getGraph() {
		return this.graph;
	}
	
}

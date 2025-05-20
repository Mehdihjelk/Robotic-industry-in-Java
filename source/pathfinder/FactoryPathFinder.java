package pathfinder;

import java.util.List;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;


public class FactoryPathFinder implements InterfaceFactoryPathFinder {

	private static final Logger LOGGER = Logger.getLogger(FactoryPathFinder.class.getName());
	

	private FactoryToGraph factoryToGraph;
    //on utilise Dijkstra pour trouver le chemin le plus court
	private DijkstraShortestPath<Position, DefaultWeightedEdge> shortestPath;
	
	
	public FactoryPathFinder(FactoryToGraph factoryToGraph) {
		this.factoryToGraph = factoryToGraph;
	}

	//Trouve un chemin entre la position source et la position cible.

	@Override
    public List<Position> findPath(Position sourcePosition, Position targetPosition) {
        LOGGER.info("Finding path from " + sourcePosition + " to " + targetPosition);

		Graph<Position, DefaultWeightedEdge> subGraph = factoryToGraph.createSubGraph();
		this.shortestPath = new DijkstraShortestPath<Position, DefaultWeightedEdge>((Graph<Position, DefaultWeightedEdge>) subGraph);
        GraphPath<Position, DefaultWeightedEdge> path =  this.shortestPath.getPath(sourcePosition, targetPosition);
        
        if (path == null) {
            LOGGER.warning("No path found from " + sourcePosition + " to " + targetPosition);
            return null;
        }
        return path.getVertexList();
    }
}
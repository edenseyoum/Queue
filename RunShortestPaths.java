import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class RunShortestPaths
{
    public static void main(String[] args)
    {
	String source = args[0];
	String destination = args[1];

	// create a map from vertices to maps from vertices to weights
	Map<String, Map<String, Double>> edges = new HashMap<>();
	
	// read input
	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	    {
		String from = in.next();
		String to = in.next();
		double w = in.nextDouble();

		if (w < 0)
		    {
			throw new RuntimeException("negative weight: " + from + ", " + to + " " + w);
		    }
		
		// add the edge in the forward direction
		if (!edges.containsKey(from))
		    {
			// haven't seen the from vertex before; add it
			edges.put(from, new HashMap<>());
		    }
		edges.get(from).put(to, w);

		// add the edge in the backwards direction
		// (so effectively we're building an undirected graph)
		if (!edges.containsKey(to))
		    {
			// haven't seen the to vertex before; add it
			edges.put(to, new HashMap<>());
		    }
		edges.get(to).put(from, w);
	    }

	SingleSourceShortestPaths<String> p = new DijkstraSingleSourceShortestPaths<>(edges, source);
	List<String> path = p.getPath(destination);
	if (path != null)
	    {
		System.out.println(p.getDistance(destination) + " " + path);
	    }
	else
	    {
		System.out.println(path);
	    }
    }
}


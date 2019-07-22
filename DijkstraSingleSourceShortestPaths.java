import java.util.*;

public class DijkstraSingleSourceShortestPaths<E> implements SingleSourceShortestPaths<E>{

    private PriorityQueue<E> q;
    private HashMap<E, E> pred;
    private HashMap<E, Double> distance;
    private HashMap<E, Boolean> seen;


    public DijkstraSingleSourceShortestPaths(Map<E, Map<E, Double>> m ,E source){

        q = new BinaryHeapPriorityQueue<E>();
	//q = new SortedArrayPriorityQueue<E>();

	pred = new HashMap<E, E>();
	distance = new HashMap<E, Double>();
	seen = new HashMap<E, Boolean>();

	//m.put(source, null);

	for(E u : m.keySet()){
	    q.add(u, Double.POSITIVE_INFINITY);
	    distance.put(u, Double.POSITIVE_INFINITY);
	}

	q.add(source, 0);
	distance.put(source, 0.0);  

	while(q.size() > 0){
	    double t =q.peekMin();
	    E vertex = (E)q.extractMin();
	    seen.put(vertex, true);

	    for(E v: m.get(vertex).keySet()){
		double p = t + m.get(vertex).get(v);


		if(seen.containsKey(v)){
		}

 		else if(!q.contains(v)){
		    q.add(v, p);
		    pred.put(v, vertex);
		    distance.put(v, p);
		}

		else if(q.contains(v)){
		    if(q.getPriority(v) > p){
			q.decreasePriority(v, p);
			pred.put(v,vertex);
			distance.put(v, p);
		    }
		}
	    }
	}


    }

	    


    


    public List<E> getPath(E dest){
	List<E> path = new ArrayList<E>();
	List<E> temp = new ArrayList<E>();
	temp.add(dest);
	E city = dest;
	
	while(pred.containsKey(city)){
		//temp.add(city);
	    temp.add(pred.get(city));
		city = pred.get(city);
	    }
	for(int i = temp.size()-1; i>=0; i--){
	    path.add(temp.get(i));
	}

	 
	    return path;

    }


    public double getDistance(E dest){

	return distance.get(dest);
    }






}



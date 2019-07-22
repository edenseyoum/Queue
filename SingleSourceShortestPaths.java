import java.util.List;

/**
 * The result of running single-source shortest paths.
 *
 * @param E the type of the labels on the vertices
 * @version 0.1 2017-04-12
 */

public interface SingleSourceShortestPaths<E>
{
    /**
     * Returns the list of vertices on a shortest path from the source
     * to the given destination.  If there is no path then the return
     * value should be null.
     *
     * @param dest the label of a vertex; not null
     * @return a list containing the vertices on a shortest path from
     * the source to the given destination, or null of there is no path
     */
    public List<E> getPath(E dest);

    /**
     * Returns the total cost of a shortest path from the source
     * to the given destination.
     *
     * @param dest the label of a vertex to which there is a path from
     * the source; not null
     * @return the total cost of a shortest path fro the source to the
     * given destination
     */
    public double getDistance(E dest);
}
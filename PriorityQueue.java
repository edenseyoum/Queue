/**
 * A priority queue.  Each item in the queue is referenced by
 * an integer index in the range [0, n).
 *
 * @version 0.1 2017-04-03
 */

public interface PriorityQueue<K>
{
    /**
     * Removes the item with the lowest priority from this queue and
     * returns its index.
     *
     * @return the item with the lowest priority
     */
    public K extractMin();

    /**
     * Returns the lowest priority of all items in this queue.
     *
     * @return the value of the lowest priority
     */
    public double peekMin();

    /**
     * Adds the given item to this queue with the given priority.
     * If the item is already in this queue then the effect is the
     * same as decreasePriority.
     *
     * @param item an item, not null
     * @param pri its priority
     */
    public void add(K item, double pri);

    /**
     * Returns the priority of the given item.
     *
     * @param item an item in this queue
     * @return the priority of that item
     */
    public double getPriority(K item);

    /**
     * Decreases the priority of the given item to
     * given value.  There is no effect if the item is not present
     * or if the new priority is higher than the old.
     *
     * @param item an item in this queue
     * @param pri its new priority
     */
    public void decreasePriority(K item, double pri);

    /**
     * Determines if this queue contains the item with the given index.
     *
     * @parma item an item; not null
     * @return true if and only if this queue contains the item with that index
     */
    public boolean contains(K item);

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size();
}

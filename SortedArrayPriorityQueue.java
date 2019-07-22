import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A priority queue. The queue is implemented
 * with an array (inside an ArrayList) of items sorted by priority.
 * Note that if we sort the priorities in reverse order then we
 * can improve the running time of extractMin (thanks Lynn and Eric!)
 *
 * @param K the type of the items
 * @version 0.1 2017-04-04
 */

public class SortedArrayPriorityQueue<K> implements PriorityQueue<K>
{
    /**
     * The (item index, priority) entries.
     */
    private List<Entry<K>> queue;

    /**
     * For each item, the index into the queue where its entry is.
     */
    private Map<K, Integer> handles;

    /**
     * Creates an empty priority queue.
     */
    public SortedArrayPriorityQueue()
    {
	queue = new ArrayList<Entry<K>>();
	handles = new HashMap<K, Integer>();
    }

    /**
     * Removes the item with the lowest priority from this queue and
     * returns its index.
     *
     * @return the index of the item with the lowest priority
     */
    public K extractMin()
    {
	if (queue.size() == 0)
	    {
		throw new IllegalStateException("empty queue");
	    }

	K item = queue.remove(0).item;
	handles.remove(item);

	// update the handles
	updateHandles(0, queue.size());
	
	return item;
    }

    /**
     * Updates the handles for all the items with entries between
     * the given indices in the queue, inclusice for the from index
     * and exclusive for the to index.
     *
     * @param from an index into the queue
     * @param to an index into the queue or the size of the queue
     */
    private void updateHandles(int from, int to)
    {
	for (int i = from; i < to; i++)
	    {
		handles.put(queue.get(i).item, i);
	    }
    }

    /**
     * Returns the lowest priority of all items in this queue.
     *
     * @return the value of the lowest priority
     */
    public double peekMin()
    {
	if (queue.size() == 0)
	    {
		throw new IllegalStateException("empty queue");
	    }

	return queue.get(0).priority;
    }

    /**
     * Adds the given item to this queue with the given priority.
     * If the item is already in this queue then the effect is the
     * same as decreasePriority.
     *
     * @param item an item
     * @param pri its priority
     */
    public void add(K item, double pri)
    {
	if (contains(item))
	    {
		decreasePriority(item, pri);
	    }
	else
	    {
		// make new entry
		Entry<K> e = new Entry<K>(item, pri);

		// find its spot
		int insertionPoint = Collections.binarySearch(queue, e);
		if (insertionPoint < 0)
		    {
			// binary search returns a negative number to indicate not
			// found but goes at index -(return value + 1)
			insertionPoint = -(insertionPoint + 1);
		    }
		
		// add new entry at that spot
		queue.add(insertionPoint, e);

		updateHandles(insertionPoint, queue.size());
	    }
    }

    /**
     * Returns the priority of the item with the given index.
     *
     * @param item an item in this queue
     * @return the priority of that item
     */
    public double getPriority(K item)
    {
	int i = findItem(item);
	
	if (i != -1)
	    {
		return queue.get(i).priority;
	    }
	else
	    {
		throw new NoSuchElementException(String.valueOf(item));
	    }
    }

    /**
     * Returns the index in this queue where the given item's entry is,
     * or -1 if there is no such item.
     *
     * @param item an item
     */
    private int findItem(K item)
    {
	if (handles.containsKey(item))
	    {
		return handles.get(item);
	    }
	else
	    {
		return -1;
	    }
    }

    /**
     * Decreases the priority of the item with the given index to the
     * given value.  There is no effect if the item is not present
     * or if the new priority is higher than the old.
     *
     * @param item an item in this queue
     * @param pri its new priority
     */
    public void decreasePriority(K item, double pri)
    {
	int i = findItem(item);

	if (i == -1)
	    {
		throw new NoSuchElementException(String.valueOf(item));
	    }

	if (pri < queue.get(i).priority)
	    {
		int oldPos = i;

		// update priority
		queue.get(i).priority = pri;

		// swap backwards until in the right place
		while (i > 0 && pri < queue.get(i - 1).priority)
		    {
			Entry<K> temp = queue.get(i - 1);
			queue.set(i - 1, queue.get(i));
			queue.set(i, temp);
			i--;
		    }

		updateHandles(i, oldPos + 1);
	    }
    }

    /**
     * Determines if this queue contains the item with the given index.
     *
     * @parma item an item
     * @return true if and only if this queue contains the item with that index
     */
    public boolean contains(K item)
    {
	return findItem(item) != -1;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size()
    {
	return queue.size();
    }

    /**
     * Returns a printable representation of this priority queue.
     *
     * @return a printable representation of this priority queue.
     */
    public String toString()
    {
	return queue.toString() + " " + handles.toString();
    }

    /**
     * An entry in a sorted-array-based priority queue.
     */
    private class Entry<K> implements Comparable<Entry<K>>
    {
	/**
	 * The index of the item this entry is for.
	 */
	private K item;

	/**
	 * The priority of the item this entry is for.
	 */
	private double priority;

	/**
	 * Creates a new entry.
	 */
	public Entry(K i, double p)
	{
	    item = i;
	    priority = p;
	}

	/**
	 * Determines if this entry comes before the given entry.
	 * Entries are compared based on their priorities, with
	 * lower values coming first.
	 *
	 * @param e an entry; not null
	 * @return a negative number, zero, or a positive number depending
	 * on whether this entry comes before, is tied with, or comes after
	 * the given entry
	 */
	public int compareTo(Entry<K> e)
	{
	    return (int)Math.signum(priority - e.priority);
	}

	/**
	 * Returns a printable representation of this entry.
	 */
	public String toString()
	{
	    return "[" + item + ", " + priority + "]";
	}
    }
}
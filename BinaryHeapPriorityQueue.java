import java.util.*;

/**
 * An indexed priority queue (one for which the items are referred to by
 * integer indices) implemented using a binary heap.  This implementation
 * does not store handles, but instead uses sequential search to find items
 * that are already in the queue.  This makes the running time of add,
 * getPriority, and decreasePriority Theta(n) worst case instead of
 * Theta(log n); add handles to get to the latter.
 *
 * @version 0.2 2017-04-13 based on code in ODS
 */

public class BinaryHeapPriorityQueue<K> implements PriorityQueue<K>
{
    /**
     * The array of elements.
     */
    private Entry[] elements;

    /**
     * The number of items currently in this queue.
     */
    private int size;

    private Map<K, Integer> handles;


    
    /**
     * Creates an empty queue.
     */
    public BinaryHeapPriorityQueue()
    {
	elements = new Entry[10];
	size = 0;

	handles= new HashMap<K, Integer>();
    }

    /**
     * Returns the index of the left child of the node at the given index.
     *
     * @param i a nonnegative integer
     */
    public static int left(int i)
    {
	return 2 * i + 1;
    }
    
    /**
     * Returns the index of the right child of the node at the given index.
     *
     * @param i a nonnegative integer
     */
    public static int right(int i)
    {
	return 2 * i + 2;
    }
    
    /**
     * Returns the index of the parent of the node at the given index.
     *
     * @param i a positive integer
     */
    public static int parent(int i)
    {
	return (i - 1) / 2;
    }
    
    /**
     * Returns the lowest priority of all items in this queue.
     *
     * @return the value of the lowest priority
     */
    public double peekMin()
    {
	if (size == 0)
	    {
		throw new NoSuchElementException();
	    }

	// min priority is in the root; the root is at index 0 in the array
	return elements[0].priority;
    }
	
    /**
     * Removes the item with the lowest priority from this queue and
     * returns its index.
     *
     * @return the index of the item with the lowest priority
     */
    public K extractMin()
    {
	if (size == 0)
	    {
		throw new NoSuchElementException();
	    }
	
	// get min item from the root
	K item = (K)elements[0].item;
	//K item = (K)(elements[i].item);

 
	
	// swap last element to replace root

	elements[0] = elements[size-1];
	size--;



	handles.put((K)elements[0].item, 0);
	handles.remove(item);

	
	// fix any order violations between new root and its descendants
	trickleDown(0);
	
	return item;
    }
    
    /**
     * Restores the heap order property at the given index.
     *
     * @param i an index into elements that might be larger than
     * its two children and other descendants; this must be the
     * only violation of the heap order property anywhere in the heap
     */
    private void trickleDown(int i)
    {
	do
	    {
		int smallest = -1;
		int r = right(i);
		if (r < size && elements[r].compareTo(elements[i]) < 0)
		    {
			// two children; entry at i is out of order with right child;
			// determine which child is the smallest
			int l = left(i);
			if (elements[l].compareTo(elements[r]) < 0)
			    {
				// left child is smallest
				smallest = l;
			    }
			else
			    {
				// right child is smallest
				smallest = r;
			    }
		    }
		else
		    {
			// one or zero children; if one child determine if it is smaller
			int l = left(i);
			if (l < size && elements[l].compareTo(elements[i]) < 0)
			    {
				// left (only) child is smaller
				smallest = l;
			    }
		    }
		
		// if there was an order violation then swap
		if (smallest != -1)
		    {
			swap(i, smallest);
		    }
		
		// move down (or off heap if no order violation)
		i = smallest;
	    } while (i != -1);
    }
    
    /**
     * Adds the given item to this queue with the given priority.
     * If the item is already in this queue then the effect is the
     * same as decreasePriority.
     *
     * @param i a nonnegative index
     * @param pri its priority
     */
    public void add(K item, double pri)
    {

	/*
	int i = findItem(item);

	if (i != -1)
	    {
		decreasePriority(item, pri);
	    }
	*/

	if(contains(item)){
	    decreasePriority(item,pri);
	}

	else
	    {
		// make sure there's enough space (amortized O(1) time)
		if (size == elements.length)
		    {
			embiggen();
		    }
			
		handles.put(item, size);
		// add new item at the end
		elements[size] = new Entry(item, pri);
		size++;
			
		// fix any order property violations between item and its ancestors
		bubbleUp(size - 1);
	    }
    }
	
    /**
     * Restores the heap order property at the given index.
     *
     * @param i an index into elements that might contain and entry
     * with a smaller priority than its parent and ancestors; this must
     * be the only violation of the heap order property anywhere in the heap
     */
    private void bubbleUp(int i)
    {
	// get index of parent
	int p = parent(i);
		
	// repeat until at root or priority is >= parent
	while (i > 0 && elements[i].compareTo(elements[p]) < 0)
	    {
		// swap with parent
		swap(i, p);
			
		// move up
		i = p;
		p = parent(i);
	    }
    }
	
    /**
     * Swaps the given entries.
     *
     * @param i an index in the array of elements
     * @param j an index in the array of elements
     */
    private void swap(int i, int j)
    {

	handles.remove(i);
	handles.remove(j);

	Entry temp = elements[i];
	elements[i] = elements[j];
	elements[j] = temp;

	handles.put((K)elements[i].item, i);
	handles.put((K)elements[j].item, j);
    }
	
    /**
     * Determines if this queue contains the item with the given index.
     *
     * @parma i a nonnegative index
     * @return true if and only if this queue contains the item with that index
     */
    public boolean contains(K item)
    {
	return findItem(item) != -1;
    }
	
    /**
     * Returns the index into elements of the entry containing the given
     * item, or -1 if there is no such item.
     *
     * @param item an item
     * @return the index of that item, or -1
     */
    private int findItem(K item)
    {
	if(handles.containsKey(item)){
	    return handles.get(item);
	}

	else{
	    return -1;
	}

    }

    /**
     * Decreases the priority of the item with the given index to the
     * given value.  There is no effect if the item is not present
     * or if the new priority is higher than the old.
     *
     * @param i the index of an item in this queue
     * @param pri its new priority
     */
    public void decreasePriority(K item, double pri)
    {
	int i = findItem(item);
		
	if (i == -1)
	    {
		throw new NoSuchElementException();
	    }
	if (elements[i].priority > pri)
	    {
		elements[i].priority = pri;
		bubbleUp(i);
	    }
    }

    /**
     * Returns the priority of the item with the given index.
     *
     * @param i the index of an item in this queue
     * @return the priority of that item
     */
    public double getPriority(K item)
    {
	int i = findItem(item);
		
	if (i == -1)
	    {
		throw new NoSuchElementException();
	    }
	return elements[i].priority;
    }
	
    private void embiggen()
    {
	Entry[] bigger = new Entry[elements.length * 2];
	for (int i = 0; i < elements.length; i++)
	    {
		bigger[i] = elements[i];
	    }
	elements = bigger;
    }
	
    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size()
    {
	return size;
    }
	
    /**
     * Returns a printable representation of this queue.  The result will
     * reflect the order that items and stored in the array.
     *
     * @return a printable representation of this queue
     */
    public String toString()
    {
	return Arrays.toString(elements);
    }
	
    /**
     * An entry containing an item and its associated priority.
     */
    private static class Entry<K> implements Comparable<Entry>
    {
	private K item;
	private double priority;

	/**
	 * Creates an entry with the given item and priority.
	 *
	 * @param i an integer
	 * @param p a number
	 */
	public Entry(K i, double p)
	    {
		item = i;
		priority = p;
	    }
	
	/**
	 * Compares this entry to the given entry based on their priorities.
	 *
	 * @param o an entry; not null
	 * @return a negative number if the priority in this entry is less
	 * than the priority in the given entry; 0 if they are the same;
	 * something positive if greater
	 */
	public int compareTo(Entry o)
	{
	    return (int)Math.signum(priority - o.priority);
	}
		
	/**
	 * Returns a printable representation of this entry.
	 *
	 * @return a printable representation of this entry
	 */
	public String toString()
	{
	    return "<" + item + ":" + priority + ">";
	}
    }
}
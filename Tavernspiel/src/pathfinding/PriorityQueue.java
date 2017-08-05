
package pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.RandomAccess;

/**
 *
 * @author Adam Whittaker
 * @param <T> The Class of the element to be sorted.
 * 
 * Auto-sorts elements as they are added using itself as a comparator.
 */
public class PriorityQueue<T> extends ArrayList<T> implements Comparator<T>, Queue<T>, RandomAccess{

    /**
     * @param e The element to offer.
     * @return true
     * @deprecated
     * @see add(T e);
     */
    @Override
    public boolean offer(T e){
        return add(e);
    }

    /**
     * @deprecated
     * @see super.remove(0);
     * @return The head of this queue.
     */
    @Override
    public T remove(){
        return super.remove(0);
    }

    /**
     * Removes and returns the head of this queue.
     * @return The head of this queue.
     */
    @Override
    public T poll(){
        if(isEmpty()) return null;
        return super.remove(0);
    }

    /**
     * @deprecated
     * @see get(0)
     * @return
     */
    @Override
    public T element(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @deprecated
     * @see get(0)
     * @return The head of this queue
     */
    @Override
    public T peek(){
        if(isEmpty()) return null;
        return get(0);
    }
    
    protected interface Compare{
        <T> long enumerate(T element);
    }
    private final Compare compare;
    
    public PriorityQueue(Compare comp){
        super();
        compare = comp;
    }
    
    public PriorityQueue(Collection<? extends T> clctn, Compare comp){
        super(clctn);
        compare = comp;
        sort();
    }
    
    public int length(){
        return size();
    }

    public final void sort(){
        super.sort(this);
    }

    @Override
    public int compare(T t, T t1){
        long comp = compare.enumerate(t);
        long comp1 = compare.enumerate(t1);
        return comp>comp1 ? 1 : comp<comp1 ? -1 : 0;
    }
    
    /**
     * Enumerates an object with the Comparator's
     * @param t The object to be enumerated.
     * @return The enumeration of that object.
     */
    public long enumerate(T t){
        return compare.enumerate(t);
    }
    
    @Override
    public boolean add(T element){
        for(int n=0;n<size();n++){
            if(compare(element, get(n))!=1){
                add(n, element);
                return true;
            }
        }
        super.add(element);
        return true;
    }
    
}


package items;

import java.util.LinkedList;
import java.util.List;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Item generation probabilities.
 */
public class ItemMap{
    
    private final Distribution distrib;
    private final Item[] items;
    private final Distribution[] stackSizes;
    public int lower, upper;
    
    /**
     * Creates a new Instance.
     * @param chances The generation chances
     * @param it The Items that could be generated
     * @param l The minimum amount of Items.
     * @param u The maximum amount of Items.
     */
    public ItemMap(int[] chances, Item[] it, int l, int u){
        distrib = chances==null ? null : new Distribution(chances);
        items = it;
        lower = l;
        stackSizes = null;
        upper = u;
    }
    
    /**
     * Creates a new Instance.
     * @param chances The generation chances
     * @param it The Items that could be generated
     * @param stk The stack size chance of each Item.
     * @param l The minimum amount of Items.
     * @param u The maximum amount of Items.
     */
    public ItemMap(int[] chances, Item[] it, Distribution[] stk, int l, int u){
        distrib = chances==null ? null : new Distribution(chances);
        items = it;
        lower = l;
        stackSizes = stk;
        upper = u;
    }
    
    @Unfinished("This is a placeholder constructor.")
    @Deprecated
    public ItemMap(){
        distrib = new Distribution(new int[]{});
        items = new Item[]{};
        stackSizes = null;
    }
    
    /**
     * Generates a list of Items.
     * @return
     */
    @Unfinished("No new Items are created")
    public List<Item> genList(){
        List<Item> ret = new LinkedList<>();
        if(stackSizes==null) for(int n=0, max=Distribution.getRandomInt(lower, upper);n<max;n++){
            ret.add(items[(int)distrib.next()]);
        }else for(int n=0, max=Distribution.getRandomInt(lower, upper);n<max;n++){
            Item i = items[(int)distrib.next()];
            if(i.stackable) i.quantity = (int) stackSizes[n].next();
            ret.add(i);
        }
        return ret;
    }
    
}

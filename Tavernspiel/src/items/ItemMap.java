
package items;

import java.util.LinkedList;
import java.util.List;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class ItemMap{
    
    private final Distribution distrib;
    private final Item[] items;
    private final Distribution[] stackSizes;
    public int lower, upper;
    
    public ItemMap(int[] chances, Item[] it, int l, int u){
        distrib = new Distribution(chances);
        items = it;
        lower = l;
        stackSizes = null;
        upper = u;
    }
    
    public ItemMap(int[] chances, Item[] it, Distribution[] stk, int l, int u){
        distrib = new Distribution(chances);
        items = it;
        lower = l;
        stackSizes = stk;
        upper = u;
    }
    
    public List<Item> genList(){
        List<Item> ret = new LinkedList<>();
        if(stackSizes==null) for(int n=0, max=Distribution.getRandomInt(lower, upper);n<max;n++){
            ret.add(items[(int)distrib.next()]);
        }else for(int n=0, max=Distribution.getRandomInt(lower, upper);n<max;n++){
            Item i = items[(int)distrib.next()];
            i.quantity = (int) stackSizes[n].next();
            ret.add(i);
        }
        return ret;
    }
    
}

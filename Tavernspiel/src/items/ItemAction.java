
package items;

import java.io.Serializable;


/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the actions that an Item can take.
 */
public class ItemAction implements Serializable{
    
    private final static long serialVersionUID = -832184255;
    
    private final String action;
    private final Item item;
    private String data[];
    
    /**
     * Creates a new instance.
     * @param str The name of the action.
     * @param i The Item.
     */
    public ItemAction(String str, Item i){
        action = str;
        item = i;
    }
    
    /**
     * Creates a new instance.
     * @param str The name of the action.
     * @param i The Item.
     * @param d The data associated with this action.
     */
    public ItemAction(String str, Item i, String... d){
        action = str;
        data = d;
        item = i;
    }
    
    /**
     * Gets the name of this Object.
     * @return The name.
     */
    public String getName(){
        return action;
    }
    
    /**
     * Gets the data associated with this Object.
     * @return The name.
     */
    public String[] getData(){
        return data;
    }
    
    /**
     * Gets the Item associated with this Object.
     * @return The Item.
     */
    public Item getItem(){
        return item;
    }
    
    /**
     * Returns the array of default ItemActions.
     * @param i The Item.
     * @return The array.
     */
    protected static ItemAction[] getDefaultActions(Item i){
        return new ItemAction[]{new ItemAction("THROW", i), new ItemAction("DROP", i)};
    }
    
    /**
     * Returns an ItemAction array of the given length, with THROW and DROP 
     * being the first choices.
     * @param length The length of the array.
     * @param i The Item.
     * @return an ItemAction array.
     */
    protected static ItemAction[] getArray(int length, Item i){
        ItemAction[] ret = new ItemAction[length];
        ret[0] = new ItemAction("THROW", i);
        ret[1] = new ItemAction("DROP", i);
        return ret;
    }
    
    /**
     * Converts the given ItemAction array to an array of Strings.
     * @param ary The array to convert.
     * @return A String array with each element representing the action of each 
     * ItemAction.
     */
    public static String[] toStringArray(ItemAction[] ary){
        String[] ret = new String[ary.length];
        for(int n=0;n<ary.length;n++){
            ret[n] = ary[n].action;
        }
        return ret;
    }
    
}

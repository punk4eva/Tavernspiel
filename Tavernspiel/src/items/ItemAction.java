
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
    private String data[];
    
    /**
     * Creates a new instance.
     * @param str The name of the action.
     */
    public ItemAction(String str){
        action = str;
    }
    
    /**
     * Creates a new instance.
     * @param str The name of the action.
     * @param d The data associated with this action.
     */
    public ItemAction(String str, String... d){
        action = str;
        data = d;
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
     * Returns the array of default ItemActions.
     * @return The array.
     */
    protected static ItemAction[] getDefaultActions(){
        return new ItemAction[]{new ItemAction("THROW"), new ItemAction("DROP")};
    }
    
    /**
     * Returns an ItemAction array of the given length, with THROW and DROP 
     * being the first choices.
     * @param length The length of the array.
     * @return an ItemAction array.
     */
    protected static ItemAction[] getArray(int length){
        ItemAction[] ret = new ItemAction[length];
        ret[0] = new ItemAction("THROW");
        ret[1] = new ItemAction("DROP");
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

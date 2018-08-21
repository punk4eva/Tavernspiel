
package items.actions;

import creatures.Creature;
import items.Item;
import java.io.Serializable;


/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the actions that an Item can take.
 */
public abstract class ItemAction implements Serializable{
    
    private final static long serialVersionUID = -832184255;
    
    public final String name;
    public double turnMult;
    
    /**
     * 
     * @param str
     * @param tM
     */
    public ItemAction(String str, double tM){
        name = str;
        turnMult = tM;
    }
    
    /**
     * 
     * @param str
     */
    public ItemAction(String str){
        name = str;
        turnMult = 1;
    }
    
    /**
     * Returns the array of default ItemActions.
     * @param i The Item.
     * @return The array.
     */
    public static ItemAction[] getDefaultActions(Item i){
        return new ItemAction[]{THROW, DROP};
    }
    
    /**
     * Returns an ItemAction array of the given length, with THROW and DROP 
     * being the first choices.
     * @param length The length of the array.
     * @param i The Item.
     * @return an ItemAction array.
     */
    public static ItemAction[] getArray(int length, Item i){
        ItemAction[] ret = new ItemAction[length];
        ret[0] = THROW;
        ret[1] = DROP;
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
            ret[n] = ary[n].name;
        }
        return ret;
    }
    
    /**
     * Acts on an Item and a Creature.
     * @param i
     * @param c
     * @param x
     * @param y
     * @param slot
     * @param data
     */
    public abstract void act(Item i, Creature c, int x, int y, int slot, Object... data);
    
    public static final ItemAction THROW = new ThrowAction(),
            DROP = new DropAction(), EQUIP = new EquipAction(), 
            UNEQUIP = new UnequipAction(), READ = new ReadAction(),
            DRINK = new DrinkAction();
    
}

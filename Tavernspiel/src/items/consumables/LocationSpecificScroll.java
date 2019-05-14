package items.consumables;

import ai.PlayerAI;
import creatureLogic.Action.ActionOnItem;
import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import items.actions.ItemAction;
import items.builders.ScrollBuilder.ScrollRecord;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * A Scroll that requires a location to work on.
 */
public abstract class LocationSpecificScroll extends Scroll{
    
    private final static long serialVersionUID = 5843820943899L;
    
    private boolean used = false;

    /**
     * Creates a new instance.
     * @param n The name of this Item.
     * @param desc
     * @param sp
     */
    public LocationSpecificScroll(String n, String desc, ScrollRecord sp){
        super(n, desc, sp);
        actions[2] = ItemAction.LOCATION_READ;
    }

    @Override
    @Catch("Exception should never be thrown if done right.")
    public boolean use(Creature c){
        if(c instanceof Hero){
            ((PlayerAI)c.attributes.ai).nextAction = new ActionOnItem(actions[2], this, (Hero) c, -1, -1, -16);
            ((PlayerAI)c.attributes.ai).alertAction();
        }else new RuntimeException("Creature is using LocationSpecificScroll.use()").printStackTrace(Main.exceptionStream);
        boolean u = used;
        used = false;
        return u;
    }
    
    /**
     * A use() method specifically for a tile coordinate. 
     * @param c The reader.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return Whether the scroll has been consumed during use.
     */
    public abstract boolean use(Creature c, int x, int y);
    
}


package creatureLogic;

import creatures.Creature;
import gui.Window;
import items.Item;
import items.actions.ItemAction;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an action taken by a Creature.
 */
public abstract class Action implements Runnable{
    
    public double turns;
    
    /**
     * Creates a new instance defaulting to one hero speed turn.
     */
    public Action(){
        this(Window.main.player.attributes.speed);
    }
    
    /**
     * Creates a new instance.
     * @param t The number of turns this action takes.
     */
    public Action(double t){
        turns = t;
    }
    
    public static class ActionOnItem extends Action{
        
        private final ItemAction action;
        private final Item item;
        private final Creature creature;
        private final int x, y, slot;
        private final Object[] data;
        
        /**
         *
         * @param iA
         * @param _i
         * @param _c
         * @param _x
         * @param _y
         * @param _slot
         * @param _data
         */
        public ActionOnItem(ItemAction iA, Item _i, Creature _c, int _x, int _y, int _slot, Object... _data){
            action = iA;
            item = _i;
            creature = _c;
            x = _x;
            y = _y;
            slot = _slot;
            data = _data;
            turns = creature.attributes.speed*action.turnMult;
        }

        @Override
        public void run(){
            action.act(item, creature, x, y, slot, data);
        }
    
    }
    
}

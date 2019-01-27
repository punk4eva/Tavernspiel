
package creatureLogic;

import ai.PlayerAI;
import creatures.Creature;
import creatures.Hero;
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
    
    /**
     * This class encapsulates all the information needed to execute an 
     * ItemAction.
     */
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
            action.act(item, creature, slot, x, y, data);
        }
    
    }
    
    /**
     * This class allows handles the action of movement.
     */
    public static class MoveAction extends Action{
        
        private final Integer[] dir;
        private final Hero hero;
        
        public MoveAction(Hero h, Integer[] m){
            dir = m;
            hero = h;
        }

        @Override
        public void run(){
            ((PlayerAI)hero.attributes.ai).animateMotion(hero.x+dir[0], hero.y+dir[1]);
            hero.changeAnimation("stand");
        }
    
    }
    
}

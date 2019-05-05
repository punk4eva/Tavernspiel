
package ai;

import containers.FloorCrate;
import creatureLogic.Attack.CreatureAttack;
import creatures.Creature;
import creatures.Hero;
import dialogues.assets.StatisticsDialogue;
import gui.Window;
import gui.mainToolbox.Main;
import items.Item;
import level.Area;
import logic.ConstantFields;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * AIBaseActions but tailored for the Hero.
 */
public class AIPlayerActions extends AIBaseActions{
    
    private final static long serialVersionUID = 1749310132;
    
    /**
     * Picks up an Item from the floor.
     * @param c The Creature.
     * @throws NullPointerException if there is no Receptacle.
     */
    @Override
    @Unfinished("Add 'pickup' sound effect")
    public void pickUp(Creature c, FloorCrate r){
        Item i = r.pickUp(c.area);
        if(!c.inventory.add(i)){
            c.area.plop(i, c.x, c.y);
            Main.addMessage(ConstantFields.badColor, "Your pack is too full for the " +
                    i.toString(3));
        }else Main.soundSystem.playSFX("pickUp.wav");
    }
    
    /**
     * Interacts with this Tile.
     * @param c
     * @param area
     * @param x
     * @param y
     */
    @Override
    public void interact(Creature c, Area area, int x, int y){
        if(area.map[y][x].interactable!=null){
            double turns = area.map[y][x].interactable.interactTurns();
            area.map[y][x].interactable.interact(c, area);
            c.attributes.ai.skipping += turns*c.attributes.health.miscSpeed;
            Window.main.setTurnsPassed(turns*c.attributes.health.miscSpeed);
        }else new StatisticsDialogue((Hero)c).next();
    }
    
}

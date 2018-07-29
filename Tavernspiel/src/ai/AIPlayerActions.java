
package ai;

import creatureLogic.Attack.CreatureAttack;
import creatureLogic.EnClass;
import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import items.Item;
import items.consumables.LocationSpecificScroll;
import items.consumables.LocationSpecificScroll.LocationViewable;
import java.util.List;
import listeners.ScreenListener;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * AIBaseActions but tailored for the Hero.
 */
public class AIPlayerActions extends AIBaseActions{
    
    public static calcDexterity dexterityCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);
    @Override
    public void resetDexterityCalculation(){accuracyCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);}
    
    @Override
    public void attack(CreatureAttack attack, Creature attacked){
        if(successfulHit(attack, attacked)){
            attacked.takeDamage(attack); 
        }
    }
    
    /**
     * Tests whether a hit was successful.
     * @param attack
     * @param attacked
     * @return True if it was, false if not.
     */
    public static boolean successfulHit(CreatureAttack attack, Hero attacked){
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attack.accuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    /**
     * Picks up an Item from the floor.
     * @param c The Creature.
     * @throws NullPointerException if there is no Receptacle.
     */
    @Override
    @Unfinished("Add 'pickup' sound effect")
    public void pickUp(Creature c){
        Item i = c.area.pickUp(c.x, c.y);
        if(!c.inventory.add(i)){
            c.area.plop(i, c.x, c.y);
            Main.addMessage("red", "Your pack is too full for the " +
                    i.toString(3));
        }else Window.main.soundSystem.playSFX("pickUp.wav");
    }   

    /**
     * Handles throwing Items.
     * @param h The Hero
     * @param item The Item
     */
    public void throwItem(Hero h, Item item){
        Window.main.setViewable(new LocationSpecificScroll(null, "", null, false){
            @Override
            public boolean use(Creature c, int x, int y){
                throw new UnsupportedOperationException("AIPlayerActions.locationSelect.use() should remain unused!");
            }
        }.new LocationViewable(
                new ScreenListener(){
            @Override
            public void screenClicked(Screen.ScreenEvent sc){
                System.out.println(sc.getName());
                switch(sc.getName()){
                    case "backLocation":
                        throwItem(h, item, sc.x, sc.y);
                    case "locationPopupX":
                        Window.main.removeViewable();
                        break;
                }
            }
        }
        ){
            @Override
            public List<Screen> getScreens(){
                return screens;
            }
        });
    }
    
}

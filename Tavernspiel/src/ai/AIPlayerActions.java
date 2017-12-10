
package ai;

import creatureLogic.Attack;
import creatureLogic.EnClass;
import creatures.Creature;
import creatures.Hero;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import gui.Screen;
import gui.Window;
import items.Item;
import items.consumables.LocationSpecificScroll;
import java.util.List;
import logic.Distribution;
import logic.Utils.Unfinished;
import items.consumables.LocationSpecificScroll.LocationViewable;
import listeners.ScreenListener;

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
    public void attack(Attack attack, Creature attacked){
        if(successfulHit(attack, attacked)){
            attacked.getAttacked(attack); 
        }
    }
    
    /**
     * Tests whether a hit was successful.
     * @param attack
     * @param attacked
     * @return True if it was, false if not.
     */
    public static boolean successfulHit(Attack attack, Hero attacked){
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attack.accuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    private static void expendTurns(Creature c, double t){
        ((PlayerAI)((Hero) c).attributes.ai).expendTurns(t);
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
        expendTurns(c, 1);
        try{
            c.inventory.push(i);
            Window.main.soundSystem.playSFX("pickUp.wav");
        }catch(ReceptacleOverflowException e){
            c.area.plop(i, c.x, c.y);
            MainClass.messageQueue.add("red", "Your pack is too full for the " +
                    i.toString(3));
        }
    }
    
    @Override
    public void throwItem(Creature c, Item i, int x, int y){
        super.throwItem(c, i, x, y);
        expendTurns(c, 1);
    }
    
    @Override
    public void dropItem(Creature c, Item i){
        super.dropItem(c, i);
        expendTurns(c, 1);
    }    

    public void throwItem(Hero h, Item item){
        Window.main.addViewable(new LocationSpecificScroll(null, "", null, false){
            @Override
            public void use(Creature c, int x, int y){
                throw new UnsupportedOperationException("AIPlayerActions.locationSelect.use() should remain unused!");
            }
        }.new LocationViewable(new ScreenListener(){
            @Override
            public void screenClicked(Screen.ScreenEvent sc){
                switch(sc.getName()){
                    case "backLocation":
                        Integer c[] = MainClass.translateMouseCoords(sc.getMouseEvent().getX(), sc.getMouseEvent().getY());
                        throwItem(h, item, c[0], c[1]);
                    case "locationPopupX":
                        Window.main.removeTopViewable();
                        break;
                }
            }
        }){
            @Override
            public List<Screen> getScreens(){
                return screens;
            }
        });
    }
    
}

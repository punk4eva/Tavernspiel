
package items.equipment;

import creatureLogic.Description;
import creatures.Creature;
import creatures.Hero;
import static gui.LocationViewable.LOCATION_SELECT;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import items.builders.WandBuilder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Wand.
 */
public class Wand extends RangedWeapon implements ScreenListener{
    
    private final static long serialVersionUID = 8628773459L;
    
    private transient Hero hero;
    private final String wandPowerName;
    public transient WandPower wandPower;
    
    /**
     * Semi-initializes an instance.
     * @param obj The Profile of the Wand.
     * @param dur Its durability.
     * @param sp Its speed. 
     * @param name The name of the power.
     */
    public Wand(String name, Object[] obj, int dur, double sp){
        super(-1, "Wand of " + name, new Description("wands", (String) obj[0]), (Supplier<ImageIcon>) obj[1], dur, null, -1, sp);
        description.type = "wands";
        wandPowerName = name;
        wandPower = WandBuilder.powerMap.get(wandPowerName);
    }
    
    /**
     * Fires this wand.
     * @param h The shooter.
     */
    public void fire(Hero h){
        hero = h;
        LOCATION_SELECT.setData(this, "Select a tile to shoot.", null);
        Window.main.setViewable(LOCATION_SELECT);
    }
    
    /**
     * Fires this wand.
     * @param c The shooter.
     * @param x The x destination.
     * @param y The y destination.
     */
    public void fire(Creature c, int x, int y){
        Main.animator.drawWandArc(this, c.x, c.y, x, y);
        throw new UnsupportedOperationException("@Unfinished");
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
        switch(sc.getName()){
            case "backLocation":
                fire(hero, sc.x, sc.y);
            case "locationPopupX":
                Window.main.removeViewable();
                break;
        }
    }
    
    @Override
    public void upgrade(){
        throw new UnsupportedOperationException();
    }
    
    
    
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException{
        in.defaultReadObject();
        wandPower = WandBuilder.powerMap.get(wandPowerName);
    }
    
}

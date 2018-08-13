
package items.equipment;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import items.builders.WandBuilder;
import items.consumables.LocationSpecificScroll;
import items.consumables.LocationSpecificScroll.LocationViewable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
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
    private String wandPowerName;
    public transient WandPower wandPower;
    
    private static final LocationViewable locationSelect = 
            new LocationSpecificScroll(null, "", null, false){
        private final static long serialVersionUID = 58098765439L;
        @Override
        public boolean use(Creature c, int x, int y){
            throw new UnsupportedOperationException("Wand.locationSelect.use() should remain unused!");
        }
    }
        .new LocationViewable(null){
        @Override
        public List<Screen> getScreens(){
            return screens;
        }
    };
    
    /**
     * Semi-initializes an instance.
     * @param obj The Profile of the Wand.
     * @param dur Its durability.
     * @param sp Its speed. 
     * @param name The name of the power.
     */
    public Wand(String name, Object[] obj, int dur, double sp){
        super("Wand of " + name, (String) obj[0], (Supplier<ImageIcon>) obj[1], dur, null, -1, sp);
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
        locationSelect.changeListener(this);
        Window.main.setViewable(locationSelect);
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
    
    
    
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException{
        in.defaultReadObject();
        wandPower = WandBuilder.powerMap.get(wandPowerName);
    }
    
}

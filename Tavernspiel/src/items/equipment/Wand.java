
package items.equipment;

import animation.Animation;
import animation.MiscAnimator;
import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import items.ItemBuilder;
import items.consumables.LocationSpecificScroll;
import items.consumables.LocationSpecificScroll.LocationViewable;
import java.util.List;
import javax.swing.ImageIcon;
import level.Area;
import listeners.AreaEvent;
import listeners.ScreenListener;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Wand.
 */
public class Wand extends RangedWeapon implements ScreenListener{
    
    public double rechargeSpeed = 1;
    public Formula rechargeFormula = new Formula(1,0,1,0);
    public final Animation firingAnimation;
    public int range = -1; //-1 if N/A.
    public Formula rangeFormula = null; //Null if no formula.
    public AreaEvent areaEvent; //Null if no AreaEvent.
    public final int blockingLevel;
    /**
     * 0: Travels through anything.
     * 1: Stops at destination.
     * 2: Stops at non-treadable tiles, but not destination.
     * 3: Stops at creatures and non-treadable tiles, but not destination.
     * 4: Stops at non-treadable tiles and destination.
     * 5: Stops at creatures and non-treadable tiles and destination.
     */
    
    private Hero hero;
    private Area area;
    private final LocationViewable locationSelect;
    
    /**
     * @param s The name of the Wand.
     * @param desc The description.
     * @param ic Its icon.
     * @param dur Its durability.
     * @param d Its action distribution.
     * @param sp Its speed. 
     */
    public Wand(String s, String desc, ImageIcon ic, int dur, Distribution d, double sp){
        super(s, desc, ic, dur, d, -1, sp);
        firingAnimation = ItemBuilder.getWandAnimation(s);
        blockingLevel = ItemBuilder.getWandBlockingLevel(s);
        areaEvent = ItemBuilder.getWandAreaEvent(s);
        locationSelect = new LocationSpecificScroll(null, "", null, false){
            @Override
            public boolean use(Creature c, int x, int y){
                throw new UnsupportedOperationException("Wand.locationSelect.use() should remain unused!");
            }
        }.new LocationViewable(this){
            @Override
            public List<Screen> getScreens(){
                return screens;
            }
        };
    }
    
    /**
     * Fires this wand.
     * @param h The shooter.
     */
    public void fire(Hero h){
        hero = h;
        area = h.area;
        Window.main.addViewable(locationSelect);
    }
    
    /**
     * Fires this wand.
     * @param c The shooter.
     * @param x The x destination.
     * @param y The y destination.
     */
    public void fire(Creature c, int x, int y){
        Main.animator.drawWandArc(this, c.x, c.y, x, y);
        if(areaEvent!=null){
            areaEvent.setArea(c.area);
            areaEvent.setXY(x, y);
            areaEvent.notifyEvent();
        }
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
        switch(sc.getName()){
            case "backLocation":
                if(hero==null||area==null) new RuntimeException("hero/area uninitialized in LocationSpecificScroll.screenClicked()!").printStackTrace(Main.exceptionStream);
                fire(hero, sc.x, sc.y);
            case "locationPopupX":
                Window.main.removeTopViewable();
                break;
        }
    }
    
    
    
}

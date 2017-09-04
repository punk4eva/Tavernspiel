
package items.equipment;

import animation.Animation;
import creatures.Creature;
import creatures.Hero;
import gui.MainClass;
import gui.Screen;
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
    private MainClass main;
    private final LocationViewable locationSelect;
    protected final List<Screen> screens;
    
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
        locationSelect = new LocationSpecificScroll(null, null, null, false){
            @Override
            public void use(Creature c, int x, int y){
                throw new UnsupportedOperationException("Wand.locationSelect.use() should remain unused!");
            }
        }.new LocationViewable(this){
            @Override
            public List<Screen> getScreens(){
                return screens;
            }
        };
        screens = locationSelect.getScreenList();
    }
    
    public void fire(Hero h){
        hero = h;
        area = h.area;
        main = hero.getMainClass();
        main.addViewable(locationSelect);
    }
    
    public void fire(Creature c, int x, int y){
        main.drawWandArc(this, c.x, c.y, x, y);
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
                int c[] = MainClass.translateMouseCoords(sc.getMouseEvent().getX(), sc.getMouseEvent().getY());
                if(hero==null||area==null) new RuntimeException("hero/area uninitialized in LocationSpecificScroll.screenClicked()!").printStackTrace(MainClass.exceptionStream);
                fire(hero, c[0], c[1]);
            case "locationPopupX":
                main.removeTopViewable();
                break;
        }
    }
    
    
    
}

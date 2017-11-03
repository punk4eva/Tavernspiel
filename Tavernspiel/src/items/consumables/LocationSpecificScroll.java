package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import gui.Screen.ScreenEvent;
import gui.Viewable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import level.Area;
import listeners.ScreenListener;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * A Scroll that requires a location to work on.
 */
public abstract class LocationSpecificScroll extends Scroll implements ScreenListener{
    
    protected Hero hero;
    private Area area;
    private MainClass main;

    /**
     * Creates a new instance.
     * @param n The name of this Item.
     * @param desc The description of this Item.
     * @param i The image of this Item.
     * @param idd Whether this Consumable is identified.
     */
    public LocationSpecificScroll(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd);
        locationSelect = new LocationViewable(this);
    }
    
    protected final LocationViewable locationSelect;
    
    /**
     * This class is the location Viewable.
     */
    public class LocationViewable implements Viewable{
        
        private final ScreenListener listener;
        protected final List<Screen> screens;
        
        /**
         * Creates a new instance.
         * @param sl The ScreenListener associated with this Viewable.
         */
        public LocationViewable(ScreenListener sl){
            listener = sl;
            int bw = MainClass.WIDTH/2-72, bh = MainClass.HEIGHT-64;
            screens = new LinkedList<>();
            screens.add(new Screen("backLocation", 0, 0, MainClass.WIDTH, MainClass.HEIGHT, listener));
            screens.add(new Screen("locationPopup", bw, bh, 144, 48, listener));
            screens.add(new Screen("locationPopupX", bw+108, bh+12, 24, 24, listener));
        }
        
        @Override
        public List<Screen> getScreens(){
            return screens;
        }

        @Override
        public void paint(Graphics g){
            int bw = MainClass.WIDTH/2-72, bh = MainClass.HEIGHT-64;
            g.setColor(new Color(230, 20, 20, 164));
            g.fill3DRect(bw, bh, 144, 48, false);
            g.setColor(new Color(230, 25, 25));
            g.fill3DRect(bw+108, bh+12, 24, 24, true);
            g.setColor(Color.yellow);
            g.drawString("Select a location", bw+8, bh+8);
            g.drawString("X", bw+116, bh+20);
        }
        
    };

    @Override
    @Catch("Exception should never be thrown if done right.")
    public void use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            area = c.area;
            main = hero.getMainClass();
            main.addViewable(locationSelect);
        }else new RuntimeException("Creature is using LocationSpecificScroll.use()").printStackTrace(MainClass.exceptionStream);
    }
    
    /**
     * A use() method specifically for an Item. 
     * @param c The reader.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public abstract void use(Creature c, int x, int y);
    
    @Override
    @Catch("Exception should never be thrown if done right.")
    public void screenClicked(ScreenEvent sc){
        switch(sc.getName()){
            case "backLocation":
                Integer c[] = MainClass.translateMouseCoords(sc.getMouseEvent().getX(), sc.getMouseEvent().getY());
                if(hero==null||area==null) new RuntimeException("hero/area uninitialized in LocationSpecificScroll.screenClicked()!").printStackTrace(MainClass.exceptionStream);
                use(hero, c[0], c[1]);
            case "locationPopupX":
                main.removeTopViewable();
                break;
        }
    }
    
}

package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.Viewable;
import gui.Window;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.swing.ImageIcon;
import level.Area;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * A Scroll that requires a location to work on.
 */
public abstract class LocationSpecificScroll extends Scroll implements ScreenListener{
    
    private final static long serialVersionUID = 5843820943899L;
    
    protected Hero hero;
    private Area area;
    private boolean used = false;
    private transient CyclicBarrier barrier = new CyclicBarrier(2);

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
            int bw = Main.WIDTH/2-72, bh = Main.HEIGHT-64;
            screens = new LinkedList<>();
            screens.add(new Screen("locationPopupX", bw+108, bh+8, 24, 24, listener));
            screens.add(new Screen("locationPopup", bw, bh, 144, 48, listener));
            screens.add(new Screen("backLocation", 0, 0, Main.WIDTH, Main.HEIGHT, listener));
        }
        
        @Override
        public List<Screen> getScreens(){
            return screens;
        }

        @Override
        public void paint(Graphics g){
            int bw = Main.WIDTH/2-72, bh = Main.HEIGHT-64;
            g.setColor(new Color(230, 20, 20, 164));
            g.fill3DRect(bw, bh, 144, 48, false);
            g.setColor(new Color(230, 25, 25));
            g.fill3DRect(bw+108, bh+8, 24, 24, true);
            g.setColor(ConstantFields.plainColor);
            g.drawString("Select a location", bw+8, bh+20);
            g.drawString("X", bw+116, bh+20);
        }
        
    };

    @Override
    @Catch("Exception should never be thrown if done right.")
    public boolean use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            area = c.area;
            Window.main.setViewable(locationSelect);
            try{
                barrier.await();
            }catch(InterruptedException | BrokenBarrierException ex){}
        }else new RuntimeException("Creature is using LocationSpecificScroll.use()").printStackTrace(Main.exceptionStream);
        barrier.reset();
        boolean u = used;
        used = false;
        return u;
    }
    
    /**
     * A use() method specifically for a tile coordinate. 
     * @param c The reader.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return Whether the scroll has been consumed during use.
     */
    public abstract boolean use(Creature c, int x, int y);
    
    @Override
    @Catch("Exception should never be thrown if done right.")
    public void screenClicked(ScreenEvent sc){
        switch(sc.getName()){
            case "backLocation":
                if(hero==null||area==null) new RuntimeException("hero/area uninitialized in LocationSpecificScroll.screenClicked()!").printStackTrace(Main.exceptionStream);
                used = use(hero, sc.x, sc.y);
            case "locationPopupX":
                Window.main.removeViewable();
                try{
                    barrier.await();
                }catch(InterruptedException | BrokenBarrierException ex){}
                break;
        }
    }
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        barrier = new CyclicBarrier(2);
    }
    
}

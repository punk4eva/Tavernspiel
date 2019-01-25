package items.consumables;

import creatures.Creature;
import creatures.Hero;
import static gui.LocationViewable.LOCATION_SELECT;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen.ScreenEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
    
    private final static long serialVersionUID = 5843820943899L;
    
    protected transient Hero hero;
    private transient Area area;
    private boolean used = false;
    private transient CyclicBarrier barrier = new CyclicBarrier(2);

    /**
     * Creates a new instance.
     * @param n The name of this Item.
     * @param desc
     * @param sp
     */
    public LocationSpecificScroll(String n, String desc, ScrollProfile sp){
        super(n, desc, sp);
    }

    @Override
    @Catch("Exception should never be thrown if done right.")
    public boolean use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            area = c.area;
            LOCATION_SELECT.setData(this, "Select a tile.", null);
            Window.main.setViewable(LOCATION_SELECT);
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


package gui;

import creatures.Creature;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * This class is the location Viewable.
 */
public class LocationViewable implements Viewable, ScreenListener{
    
    public final static LocationViewable LOCATION_SELECT = new LocationViewable(null);

    private ScreenListener listener;
    private String message;
    private Creature creature;
    private ClickPredicate clickPredicate;
    protected final List<Screen> screens;

    /**
     * Creates a new instance.
     * @param sl The ScreenListener associated with this Viewable.
     */
    public LocationViewable(ScreenListener sl){
        listener = sl;
        int bw = Main.WIDTH/2-72, bh = Main.HEIGHT-64;
        screens = new LinkedList<>();
        screens.add(new Screen("locationPopupX", bw+108, bh+8, 24, 24, this));
        screens.add(new Screen("locationPopup", bw, bh, 144, 48, this));
        screens.add(new Screen("backLocation", 0, 0, Main.WIDTH, Main.HEIGHT, this));
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
        g.drawString(message, bw+8, bh+20);
        g.drawString("X", bw+116, bh+20);
    }
    
    /**
     * Changes the data.
     * @param l The ScreenListener
     * @param mes The message to display.
     * @param a
     * @param c The predicate determining whether a tile is clickable.
     */
    public void setData(ScreenListener l, String mes, Creature a, ClickPredicate... c){
        listener = l;
        creature = a;
        message = mes;
        if(c.length!=0) clickPredicate = c[0];
    }

    @Override
    public void screenClicked(ScreenEvent sc){
        if(clickPredicate==null||clickPredicate.test(creature, sc.x, sc.y)) listener.screenClicked(sc);
    }
    
    public static interface ClickPredicate{
        public boolean test(Creature c, int x, int y);
    }

};

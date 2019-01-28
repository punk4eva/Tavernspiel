
package gui;

import creatures.Creature;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
    private int messageWidth, bw, bh = Main.HEIGHT-96;

    /**
     * Creates a new instance.
     * @param sl The ScreenListener associated with this Viewable.
     */
    public LocationViewable(ScreenListener sl){
        listener = sl;
        screens = new LinkedList<>();
        screens.add(new Screen("locationPopupX", -1, -1, 1, 1, this));
        screens.add(new Screen("locationPopup", -1, -1, 1, 1, this));
        screens.add(new Screen("backLocation", 0, 0, Main.WIDTH, Main.HEIGHT, this));
    }

    @Override
    public List<Screen> getScreens(){
        return screens;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(230, 20, 20, 164));
        g.fill3DRect(bw, bh, messageWidth+32, 40, false);
        g.setColor(new Color(230, 25, 25));
        g.fill3DRect(bw+messageWidth, bh+8, 24, 24, true);
        g.setColor(ConstantFields.plainColor);
        g.drawString(message, bw+8, bh+24);
        g.drawString("X", bw+messageWidth+6, bh+24);
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
        recalibrateScreens();
        if(c.length!=0) clickPredicate = c[0];
    }
    
    private void recalibrateScreens(){
        Rectangle rec = ConstantFields.textFont.getStringBounds(message, ConstantFields.frc).getBounds();
        messageWidth = rec.width+16;
        bw = Main.WIDTH/2-messageWidth/2-16;
        Screen sc1 = screens.get(1), sc0 = screens.get(0);
        sc1.reposition(bw, bh, messageWidth+32, 48);
        sc0.reposition(bw+messageWidth, bh+8, 24, 24);
    }

    @Override
    public void screenClicked(ScreenEvent sc){
        if(clickPredicate==null||clickPredicate.test(creature, sc.x, sc.y)) listener.screenClicked(sc);
    }
    
    /**
     * The predicate which determines which tiles can be clicked on.
     */
    public static interface ClickPredicate{
        /**
         * Returns whether the given tiles can be clicked on.
         * @param c The clicker.
         * @param x The x that was clicked on.
         * @param y The y that was clicked on.
         * @return
         */
        public boolean test(Creature c, int x, int y);
    }

};

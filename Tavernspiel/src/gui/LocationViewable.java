
package gui;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
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
public class LocationViewable implements Viewable{
    
    public final static LocationViewable locationSelect = new LocationViewable(null);

    private ScreenListener listener;
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

    /**
     * Changes the ScreenListener.
     * @param l
     */
    public void changeListener(ScreenListener l){
        listener = l;
        screens.forEach((sc) -> {
            sc.changeScreenListener(l);
        });
    }

};

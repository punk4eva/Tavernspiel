
package items;

import animation.Animation;
import items.consumables.Potion;
import items.consumables.Scroll;
import items.equipment.Ring;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import listeners.AreaEvent;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * A static convenience class for building Items.
 */
public final class ItemBuilder{
    
    public static final BufferedImage items;
    static{
        ImageIcon ic = new ImageIcon("graphics/items.png");
        items = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = items.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
    }

    @Unfinished
    public static ItemMap getStandardItemMap(){
        //return new ItemMap();
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Unfinished
    public static ItemMap getGardenItemMap(){
        //return new ItemMap();
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private ItemBuilder(){}
    
    public static Item amulet(){
        return new Item("amulet", "Description.", new ImageIcon("graphics/amulet.png"));
    }

    public static Item get(String substring){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Animation getWandAnimation(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int getWandBlockingLevel(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static AreaEvent getWandAreaEvent(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Ring> getListOfAllRings(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Potion> getListOfAllPotions(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static List<Scroll> getListOfAllScrolls(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static ImageIcon getIcon(int x, int y){
        return new ImageIcon(items.getSubimage(x*16, y*16, 16, 16));
    }
    
    public static Item genRandom(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Unfinished("Uncomment bookmarked line.")
    public static List<Item> genRandom(int amount){
        LinkedList<Item> ret = new LinkedList<>();
        //for(int n=0;n<amount;n++) ret.add(genRandom());
        return ret;
    }
    
}

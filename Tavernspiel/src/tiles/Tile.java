
package tiles;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;
import level.RoomBuilder;
import logic.Distribution;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Tile implements Serializable, Comparable<Tile>{

    private final static long serialVersionUID = 2606798;
    
    protected ImageIcon image;
    public String name;
    public boolean treadable;
    public boolean flammable;
    public boolean transparent;
    
    public Tile(String n, ImageIcon ic){
        image = ic;
        name = n;
        treadable = true;
        flammable = false;
        transparent = true;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, boolean f, boolean tr){
        image = ic;
        name = n;
        treadable = t;
        flammable = f;
        transparent = tr;
    }
    
    public Tile(String tile, Location loc, boolean t, boolean f, boolean tr){
        image = ImageHandler.getImage(tile, loc);
        name = tile;
        treadable = t;
        flammable = f;
        transparent = tr;
    }
    
    /**
     * Checks whether the Tile is the same type as another with the given name.
     * @param str The name of the comparing Tile.
     * @return
     */
    public boolean equals(String str){
        return str.compareToIgnoreCase(name)==0;
    }

    @Override
    public int compareTo(Tile t){
        return name.compareToIgnoreCase(t.name);
    }
    
    public static Tile wall(Location loc){
        if(Distribution.chance(1, 22)) return new Tile("specialwall", loc, false, false, false);
        return new Tile("wall", loc, false, false, false);
    }
    
    public static Tile floor(Location loc){
        if(Distribution.chance(1, 30)) return RoomBuilder.getRandomTrap(loc);
        if(Distribution.chance(1, 22)) return new Tile("decofloor", loc, true, false, true);
        return new Tile("floor", loc, true, false, true);
    }
    
    public void paint(Graphics g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
}

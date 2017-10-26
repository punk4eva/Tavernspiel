
package tiles;

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
    
    public ImageIcon image;
    public String name;
    public boolean treadable;
    public boolean flammable;
    
    public Tile(String n, ImageIcon ic){
        image = ic;
        name = n;
        treadable = true;
        flammable = false;
    }
    
    public Tile(Tile t){
        image = t.image;
        name = t.name;
        treadable = t.treadable;
        flammable = t.flammable;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, boolean f){
        image = ic;
        name = n;
        treadable = t;
        flammable = f;
    }
    
    public Tile(String tile, Location loc, boolean t, boolean f){
        image = ImageHandler.getImage(tile, loc);
        name = tile;
        treadable = t;
        flammable = f;
    }
    
    public boolean equals(String str){
        return str.compareToIgnoreCase(name)==0;
    }

    @Override
    public int compareTo(Tile t){
        return name.compareToIgnoreCase(t.name);
    }
    
    public static Tile wall(Location loc){
        if(Distribution.chance(1, 10)) return new Tile("specialwall", loc, false, false);
        return new Tile("wall", loc, false, false);
    }
    
    public static Tile floor(Location loc){
        if(Distribution.chance(1, 30)) return RoomBuilder.getRandomTrap(loc);
        if(Distribution.chance(1, 10)) return new Tile("decofloor", loc, true, false);
        return new Tile("floor", loc, true, false);
    }
    
}

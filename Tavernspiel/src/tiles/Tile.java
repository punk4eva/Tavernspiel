
package tiles;

import java.awt.Image;
import java.io.Serializable;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Tile implements Serializable, Comparable<Tile>{

    private final static long serialVersionUID = 2606798;
    
    public Image image;
    public String name;
    public boolean treadable = true;
    public boolean flammable = false;
    
    public Tile(String n, Image ic){
        image = ic;
        name = n;
    }
    
    public Tile(Tile t){
        image = t.image;
        name = t.name;
        treadable = t.treadable;
        flammable = t.flammable;
    }
    
    public Tile(String n, Image ic, boolean t, boolean f){
        image = ic;
        name = n;
        treadable = t;
        flammable = f;
    }
    
    public Tile(String tile, Location loc){
        image = ImageHandler.getImage(tile, loc);
        name = tile;
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
    
}

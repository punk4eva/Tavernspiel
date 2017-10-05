
package tiles;

import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Tile implements Serializable, Comparable<Tile>{

    private final static long serialVersionUID = 2606798;
    
    public ImageIcon image;
    public String name;
    public boolean treadable = true;
    public boolean flammable = false;
    
    public Tile(String n, ImageIcon ic){
        image = ic;
        name = n;
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
    
    public Tile(String tile, Location loc){
        image = ImageHandler.getImage(tile, loc);
        name = tile;
        if(tile.equals("wall")||tile.equals("specialwall")||tile.equals("barricade")||
                tile.equals("statue")||tile.equals("specialstatue")||tile.equals("bookshelf")) treadable = false;
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

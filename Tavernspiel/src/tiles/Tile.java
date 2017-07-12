
package tiles;

import containers.Receptacle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import level.Location;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Tile extends JButton{

    public String name;
    public boolean treadable = true;
    public boolean flammable = false;
    public Gas gas = null;
    public Receptacle receptacle;
    
    public Tile(String n, ImageIcon ic){
        super(ic);
        name = n;
    }
    
    public Tile(Tile t){
        super(t.getIcon());
        name = t.name;
        treadable = t.treadable;
        flammable = t.flammable;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, boolean f){
        super(ic);
        name = n;
        treadable = t;
        flammable = f;
    }
    
    public Tile(String tile, Location loc){
        super(ImageHandler.getImageIcon(tile, loc));
        name = tile;
    }
    
    public Tile(String tile, Location loc, boolean t, boolean f){
        super(ImageHandler.getImageIcon(tile, loc));
        name = tile;
        treadable = t;
        flammable = f;
    }
    
    public Tile(String t, Icon ic){
        super(ic);
        name = t;
    }
    
    public boolean equals(Tile t){
        return t.name.equals(name);
    }
    
    public boolean equals(String str){
        return str.equals(name);
    }
    
}

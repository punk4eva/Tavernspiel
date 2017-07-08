
package tiles;

import containers.Receptacle;
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
    public Gas gas = null;
    public Receptacle receptacle;
    
    public Tile(String n, ImageIcon ic){
        super(ic);
        name = n;
    }
    
    public Tile(String n, ImageIcon ic, boolean t){
        super(ic);
        name = n;
        treadable = t;
    }
    
    public Tile(String tile, Location loc){
        super(ImageHandler.getImageIcon(tile, loc));
        name = tile;
    }
    
    public boolean equals(Tile t){
        return t.name.equals(name);
    }
    
    public boolean equals(String str){
        return str.equals(name);
    }
    
}

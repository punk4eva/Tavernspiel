
package tiles;

import javax.swing.ImageIcon;
import logic.Gas;

/**
 *
 * @author Adam Whittaker
 */
public class Tile{

    public String name;
    public ImageIcon icon;
    public int stage = 1;
    public boolean treadable = true;
    public Gas gas = null;
    
    public Tile(String n, ImageIcon ic){
        name = n;
        icon = ic;
    }
    
    public Tile(String n, ImageIcon ic, boolean t){
        name = n;
        icon = ic;
        treadable = t;
    }
    
    public Tile(String n, ImageIcon ic, int st){
        name = n;
        icon = ic;
        stage = st;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, int st){
        name = n;
        icon = ic;
        treadable = t;
        stage = st;
    }
    
}

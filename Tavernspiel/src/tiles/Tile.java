
package tiles;

import containers.Receptacle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import logic.Gas;

/**
 *
 * @author Adam Whittaker
 */
public class Tile extends JButton{

    public String name;
    public int stage = 1;
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
    
    public Tile(String n, ImageIcon ic, int st){
        super(ic);
        name = n;
        stage = st;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, int st){
        super(ic);
        name = n;
        treadable = t;
        stage = st;
    }
    
}


package tiles;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import java.awt.Color;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class HiddenTile extends Tile implements Serializable{
    
    private final ImageIcon realIcon; 
    public boolean hidden = true;
    public final boolean reallyFlammable;
    public final boolean reallyTreadable;
    public final boolean reallyTransparent;

    public HiddenTile(String imposterTileName, boolean t, boolean f, boolean tr, String realName, Location loc, boolean reallyFlam, boolean reallyTread, boolean reallyTrans){
        super(imposterTileName, loc, t, f, tr);
        name = realName;
        realIcon = loc.getImage(realName);
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        reallyTransparent = reallyTrans;
    }
    
    public HiddenTile(String imposterTileName, boolean t, boolean f, boolean tr, String realName, Location loc, boolean hid, boolean reallyFlam, boolean reallyTread, boolean reallyTrans){
        super(imposterTileName, loc, t, f, tr);
        name = realName;
        realIcon = loc.getImage(realName);
        hidden = hid;
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        reallyTransparent = reallyTrans;
        if(!hid){
            flammable = reallyFlammable;
            transparent = reallyTrans;
            treadable = reallyTreadable; 
        }
    }
    
    public HiddenTile(String realName, ImageIcon icon, Location loc, boolean hid, boolean reallyFlam, boolean reallyTread, boolean reallyTrans){
        super(realName, icon, false, false, false);
        realIcon = loc.getImage(realName);
        hidden = hid;
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        reallyTransparent = reallyTrans;
        if(!hid){
            transparent = reallyTransparent;
            flammable = reallyFlammable;
            treadable = reallyTreadable; 
        }
    }
    
    public HiddenTile(String realName, Location loc, boolean reallyFlam, boolean reallyTread, boolean reallyTrans){
        super(realName, loc, reallyTread, reallyFlam, reallyTrans);
        hidden = false;
        realIcon = loc.getImage(realName);
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        reallyTransparent = reallyTrans;
    }
    
    public HiddenTile(HiddenTile tile){
        super(tile.name, tile.image, false, false, false);
        reallyFlammable = tile.reallyFlammable;
        reallyTreadable = tile.reallyTreadable;
        hidden = tile.hidden;
        realIcon = tile.realIcon;
        reallyTransparent = tile.reallyTransparent;
        if(!hidden){
            transparent = reallyTransparent;
            flammable = reallyFlammable;
            treadable = reallyTreadable; 
        }
    }
    
    
    public void find(Creature c){
        if(c instanceof Hero) Main.addMessage(Color.ORANGE, "You notice something...");
        hidden = false;
        flammable = reallyFlammable;
        treadable = reallyTreadable;
        image = realIcon;
    }
    
}

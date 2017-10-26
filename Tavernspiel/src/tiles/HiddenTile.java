
package tiles;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class HiddenTile extends Tile{
    
    private final ImageIcon realIcon; 
    public boolean hidden = true;
    public final boolean reallyFlammable;
    public final boolean reallyTreadable;

    public HiddenTile(String imposterTileName, boolean t, boolean f, String realName, Location loc, boolean reallyFlam, boolean reallyTread){
        super(imposterTileName, loc, t, f);
        name = realName;
        realIcon = ImageHandler.getImage(realName, loc);
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
    }
    
    public HiddenTile(String imposterTileName, boolean t, boolean f, String realName, Location loc, boolean hid, boolean reallyFlam, boolean reallyTread){
        super(imposterTileName, loc, t, f);
        name = realName;
        realIcon = ImageHandler.getImage(realName, loc);
        hidden = hid;
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        if(!hid){
            flammable = reallyFlammable;
            treadable = reallyTreadable; 
        }
    }
    
    public HiddenTile(String realName, ImageIcon icon, Location loc, boolean hid, boolean reallyFlam, boolean reallyTread){
        super(realName, icon);
        realIcon = ImageHandler.getImage(realName, loc);
        hidden = hid;
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        if(!hid){
            flammable = reallyFlammable;
            treadable = reallyTreadable; 
        }
    }
    
    public HiddenTile(String realName, Location loc, boolean reallyFlam, boolean reallyTread){
        super(realName, loc, reallyTread, reallyFlam);
        hidden = false;
        realIcon = ImageHandler.getImage(realName, loc);
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
    }
    
    public HiddenTile(HiddenTile tile){
        super(tile.name, tile.image);
        reallyFlammable = tile.reallyFlammable;
        reallyTreadable = tile.reallyTreadable;
        hidden = tile.hidden;
        realIcon = tile.realIcon;
        if(!hidden){
            flammable = reallyFlammable;
            treadable = reallyTreadable; 
        }
    }
    
    
    public void find(Creature c){
        if(c instanceof Hero) Window.main.messageQueue.add("orange", "You notice something...");
        hidden = false;
        flammable = reallyFlammable;
        treadable = reallyTreadable;
        image = realIcon;
    }
    
}

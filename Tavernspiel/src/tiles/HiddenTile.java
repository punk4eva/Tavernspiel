
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
 * 
 * A tile that might be hidden and needs to be found.
 */
public class HiddenTile extends Tile implements Serializable{
    
    private final ImageIcon realIcon; 
    public boolean hidden;
    public final boolean reallyFlammable;
    public final boolean reallyTreadable;
    public final boolean reallyTransparent;
    public final String realName;
    
    public HiddenTile(Location loc, boolean hid, String imposterTileName, boolean t, boolean f, boolean tr, String rN, boolean reallyTread, boolean reallyFlam, boolean reallyTrans){
        super(imposterTileName, loc, t, f, tr);
        realName = rN;
        realIcon = loc.getImage(realName);
        hidden = hid;
        reallyFlammable = reallyFlam;
        reallyTreadable = reallyTread;
        reallyTransparent = reallyTrans;
        if(!hid){
            flammable = reallyFlammable;
            transparent = reallyTransparent;
            treadable = reallyTreadable; 
            image = realIcon;
            name = realName;
            description = TileDescriptionBuilder.getDescription(name, Location.SHKODER_LOCATION);
        }
    }
    
    /**
     * Puts this Tile into the "found" state.
     * @param c The finder.
     */
    public void find(Creature c){
        if(c instanceof Hero) Main.addMessage(Color.ORANGE, "You notice something...");
        hidden = false;
        flammable = reallyFlammable;
        treadable = reallyTreadable;
        image = realIcon;
        name = realName;
        description = TileDescriptionBuilder.getDescription(name, Location.SHKODER_LOCATION);
    }
    
}

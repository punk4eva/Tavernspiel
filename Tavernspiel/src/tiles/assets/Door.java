
package tiles.assets;

import creatures.Creature;
import gui.mainToolbox.Main;
import items.misc.Key.KeyType;
import javax.swing.ImageIcon;
import level.Area;
import level.Location;
import listeners.Interactable;
import listeners.StepListener;
import logic.ConstantFields;
import tiles.HiddenTile;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends HiddenTile implements StepListener, Interactable{
    
    protected final ImageIcon open;
    protected final ImageIcon closed;
    protected final ImageIcon locked;
    public boolean isLocked = false;
    public boolean isOpen = false;
    private final KeyType keyType;
    private final int depth;
    
    public Door(Location loc){
        super(loc, loc.feeling.doorHideChance.chance(), "wall", false, false, false, "door", true, true, false);
        open = loc.getImage("opendoor");
        closed = loc.getImage("door");
        locked = loc.getImage("lockeddoor");
        depth = loc.depth;
        keyType = KeyType.IRON;
    }
    
    public Door(Location loc, boolean lock, boolean hid, KeyType k){
        super(loc, hid, "wall", false, false, false, lock ? "lockeddoor" : "door", !lock, !lock, false);
        if(lock) isLocked = true;
        open = loc.getImage("opendoor");
        closed = loc.getImage("door");
        locked = loc.getImage("lockeddoor");
        depth = loc.depth;
        keyType = k;
        if(isLocked) interactable = this;
    }
    
    
    
    @Override
    public void interact(Creature c, Area a){
        if(c.inventory.pollKey(keyType, depth)){
            treadable = true;
            flammable = true;
            isLocked = false;
            image = closed;
        }else{
            Main.addMessage(ConstantFields.interestColor, "You have used an incorrect key on this door!");
        }
    }
    
    public void open(){
        isOpen = true;
        transparent = true;
        image = open;
    }
    
    public void close(){
        isOpen = false;
        transparent = false;
        image = closed;
    }

    @Override
    public void steppedOn(Creature c){
        open();
    }

    public void stepOff(Creature c){
        if(c.area.getReceptacle(c.x, c.y)==null) close();
    }

    @Override
    public double interactTurns(){
        return 1.0;
    }
    
    private static String getDescription(boolean lock){
        return "UNFINISHED";
    }
    
}

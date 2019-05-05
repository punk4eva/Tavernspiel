
package containers;

import creatures.Creature;
import items.Item;
import items.misc.Key.KeyType;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Area;
import level.Location;
import logic.Distribution;
import logic.SoundHandler;
import pathfinding.Point.ExtendedDirection;

/**
 *
 * @author Adam Whittaker
 */
public class LockedChest extends Chest{
    
    private final static long serialVersionUID = 285389597;
    private KeyType keyType = KeyType.GOLDEN;
    
    public LockedChest(String locName, Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)
                () -> Location.getLockedChestIcon(locName), 
                "You can't open this chest without a key.", item, x, y);
    }
    
    /**
     * Creates a new chest instance containing an item.
     * @param ic The icon.
     * @param desc The description.
     * @param item The item within.
     * @param x
     * @param y
     */
    public LockedChest(Supplier<ImageIcon> ic, String desc, Item item, int x, int y){
        super(ic, desc, item, x, y);
    }
    
    @Override
    public void interact(Creature c, Area area){
        if(c.inventory.pollKey(keyType, area.depth)){
            area.removeReceptacle(x, y);
            forEach((i) -> {
                area.plop(i, x, y);
            });
            SoundHandler.SFX("Misc/openLockedChest.wav");
        }
    }
    
    /**
     * Plops the Item on an adjacent Tile.
     * @param x
     * @param y
     * @param area
     * @param i
     */
    public static void replop(int x, int y, Area area, Item i){
        LinkedList<ExtendedDirection> list = new LinkedList<>();
        for(ExtendedDirection dir : ExtendedDirection.values())
            if(area.map[y+dir.y][x+dir.x].treadable) list.add(dir);
        if(list.isEmpty()) throw new IllegalStateException("Illegal replop() context");
        ExtendedDirection dir = list.get(Distribution.R.nextInt(list.size()));
        area.plop(i, x+dir.x, y+dir.y);
    }
    
}

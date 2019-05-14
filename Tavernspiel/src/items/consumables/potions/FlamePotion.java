
package items.consumables.potions;

import blob.Fire;
import creatures.Creature;
import items.consumables.Potion;
import items.consumables.PotionProfile;
import pathfinding.Point.ExtendedDirection;

/**
 *
 * @author Adam Whittaker
 */
public class FlamePotion extends Potion{

    public FlamePotion(PotionProfile pp){
        super("Flame Potion", "This is a terribly volatile battle concoction in a compressed "
                + "bottle designed to burst open violently after being thrown, allowing the "
                + "liquid to react with the air and consume the surroundings in flame.", pp, Type.VOLATILE);
    }

    @Override
    public void drinkPotion(Creature c){
        throwPotion(c, c.x, c.y);
    }

    @Override
    public void throwPotion(Creature c, int x, int y){
        if(c.area.map[y][x].treadable) c.area.addObject(new Fire(c.area.location, x, y, c.area.depth));
        for(ExtendedDirection dir : ExtendedDirection.values()) if(c.area.map[y+dir.y][x+dir.x].treadable)
            c.area.addObject(new Fire(c.area.location, x+dir.x, y+dir.y, c.area.depth));
    }
    
}

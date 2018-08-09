
package listeners;

import creatures.Creature;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public interface Interactable{
    public void interact(Creature c, Area a);
}

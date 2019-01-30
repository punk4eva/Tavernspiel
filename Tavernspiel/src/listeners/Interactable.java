
package listeners;

import creatures.Creature;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * A physical Object that the Hero can interact with.
 */
public interface Interactable{
    public void interact(Creature c, Area a);
    public double interactTurns();
}

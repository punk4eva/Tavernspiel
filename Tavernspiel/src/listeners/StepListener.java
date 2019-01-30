
package listeners;

import creatures.Creature;

/**
 *
 * @author Adam Whittaker
 * 
 * A Tile that needs to know when something steps on it.
 */
public interface StepListener{
    void steppedOn(Creature c);
}

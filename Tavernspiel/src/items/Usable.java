
package items;

import creatures.Creature;
import items.actions.ItemAction;

/**
 *
 * @author Adam Whittaker
 * 
 * An Object that has a "use function" to put in the Quickslot.
 */
public interface Usable{
    boolean defaultUse(Creature c, Object... data);
    boolean use(Creature c, ItemAction act, Object... data);
}

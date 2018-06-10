
package items;

import creatures.Creature;

public interface Usable{
    boolean defaultUse(Creature c, Object... data);
    boolean use(Creature c, ItemAction act, Object... data);
}

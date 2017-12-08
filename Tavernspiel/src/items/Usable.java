
package items;

import creatures.Creature;

public interface Usable{
    void defaultUse(Creature c, Object... data);
    void use(Creature c, ItemAction act, Object... data);
}

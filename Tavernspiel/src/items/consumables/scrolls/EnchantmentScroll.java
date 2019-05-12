
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentScroll extends Scroll{

    public EnchantmentScroll(ScrollRecord sp){
        super("Scroll of Enchantment", "There is a potent enchantment on this parchment which, when read out loud, will be reaffixed to a peace of equipment of the reader's choice.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

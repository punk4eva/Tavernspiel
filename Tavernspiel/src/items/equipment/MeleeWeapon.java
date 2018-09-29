
package items.equipment;

import creatureLogic.Description;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Melee weapon.
 */
public class MeleeWeapon extends HeldWeapon{
    
    private final static long serialVersionUID = 222324352203459L;
    
    public final double accuracy;
    public final double speed;
    public final int reach;
    public final double damageBlock;
    public final double quality;
    
    public MeleeWeapon(double q, String n, int _x, int _y, Description desc, int dur, int st, int lo, int up, int re, double ac, double sp, double bl){
        super(n, desc, (Serializable & Supplier<ImageIcon>)() -> 
                        ItemBuilder.getIcon(_x, _y), dur, new Distribution(lo, up), st);
        accuracy = ac;
        speed = sp;
        reach = re;
        damageBlock = bl;
        quality = q;
        actions = standardActions(3, this);
    }

    @Override
    public void upgrade(){
        level++;
        damageDistrib.add(quality);
        maxDurability += 5;
        durability = maxDurability;
        testEnchantment();
    }
    
}

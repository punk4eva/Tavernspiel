
package enchantments;

import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfUnholiness extends WeaponEnchantment{
    
    public EnchantmentOfUnholiness(int level){
        super("Unholiness", new Description("enchantments", "The malevolent energies in the enchantment are itching for the next kill."), 
                new Distribution(0, (int)(level*20.0)), level, AttackType.DEMONIC,
                EnchantmentAffinity.SACRIFICIAL);
        hueR1=96;
        hueG1=24;
        hueB1=27;
        hueR2=145;
        hueG2=26;
        hueB2=41;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, (int)(level*20.0));
    }
    
}

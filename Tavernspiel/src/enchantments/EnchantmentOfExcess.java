
package enchantments;

import creatureLogic.Description;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfExcess extends WeaponEnchantment{
    
    private Formula damageExtra;
    private int limitHp;
    
    public EnchantmentOfExcess(double level){
        super("Excess", new Description("enchantments", "The powerful enchantment on this weapon increases its damage against weaker enemies, and higher "
                + "levels of this enchantment can even kill multiple enemies in one hit."),
                null, level);
        damageExtra = new Formula(360.0, 0.0, true);
        limitHp = (int)Math.ceil(72*level);
        hueR1=76;
        hueG1=76;
        hueB1=76;
        hueR2=165;
        hueG2=165;
        hueB2=165;
    }
    
    public int getExtraDamage(int hp){
        return new Distribution(0, 1+damageExtra.getInt(hp)*level).nextInt();
    }
    
    public int getLimitHp(){
        return limitHp;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        damageExtra = new Formula(360.0, 0.0, true);
        limitHp = (int)Math.ceil(72*level);
    }
    
}

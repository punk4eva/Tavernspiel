
package enchantments;

import creatureLogic.Attack;
import creatureLogic.Description;
import creatures.Creature;
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
        damageExtra = new Formula(1.0/360.0, 0.0);
        limitHp = (int)Math.ceil(72*level);
        hueR1=76;
        hueG1=76;
        hueB1=76;
        hueR2=165;
        hueG2=165;
        hueB2=165;
    }
    
    public int getExtraDamage(int hp){
        return new Distribution(0, 1+damageExtra.get(hp)*level).nextInt();
    }
    
    public int getLimitHp(){
        return limitHp;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        damageExtra = new Formula(1.0/360.0, 0.0);
        limitHp = (int)Math.ceil(72*level);
    }

    @Override
    public void onHit(Creature victim, Attack attack){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

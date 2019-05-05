
package enchantments;

import creatureLogic.Attack;
import creatureLogic.Description;
import creatures.Creature;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfBleeding extends WeaponEnchantment{
    
    public EnchantmentOfBleeding(double level){
        super("Bleeding", new Description("enchantments", "The curse on this weapon makes blood which it touches thinner, allowing it to flow freely."),
                new Distribution(1.0, 10.0-(level*10.0)), level);
        hueR1=130;
        hueG1=45;
        hueB1=26;
        hueR2=216;
        hueG2=45;
        hueB2=6;
    }

    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
    @Override
    protected int[] getHue1(double n){
        double progress = n/9;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new int[]{R, 45, B, 128};
    }
    
    @Override
    protected int[] getHue2(double n){
        double progress = (n+5)%10;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new int[]{R, 45, B, 128};
    }

    @Override
    public void onHit(Creature victim, Attack attack){
        if(shouldActivate()){
            //victim.addBuff(BuffBuilder.bleeding(level*0.6*attack.damage));
        }
    }
    
}

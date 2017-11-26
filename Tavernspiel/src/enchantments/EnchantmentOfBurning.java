
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfBurning extends WeaponEnchantment{
    
    public EnchantmentOfBurning(double level){
        super("Burning", new Description("enchantments", "The hex sets foes ablaze."),
                new Distribution(1, 10.0-(level*10.0)), level);
        hueR1=196;
        hueG1=58;
        hueR2=234;
        hueG2=70;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
    @Override
    protected int[] getHue1(double n){
        double mult = n/9.0;
        int R = (int)(((double)hueR2-hueR1)*mult)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*mult)+hueG1;
        return new int[]{R, G, 0, 128};
    }

    @Override
    protected int[] getHue2(double n){
        double mult = ((n+5)%10)/9.0;
        int R = (int)(((double)hueR2-hueR1)*mult)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*mult)+hueG1;
        return new int[]{R, G, 0, 128};
    }
    
}

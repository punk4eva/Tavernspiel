
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfSlowness extends WeaponEnchantment{
    
    public EnchantmentOfSlowness(double level){
        super("Slowness", new Description("enchantments", "This weapon increases the stress of movement for it's enemies."),
                new Distribution(1, 10.0-(level*10.0)), level);
        hueR1=127;
        hueG1=120;
        hueB1=13;
        hueR2=206;
        hueG2=167;
        hueB2=13;
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
        return new int[]{R, G, 13, 128};
    }

    @Override
    protected int[] getHue2(double n){
        double mult = ((n+5)%10)/9.0;
        int R = (int)(((double)hueR2-hueR1)*mult)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*mult)+hueG1;
        return new int[]{R, G, 13, 128};
    }
    
}

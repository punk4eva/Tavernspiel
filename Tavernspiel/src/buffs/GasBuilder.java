
package buffs;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import blob.Blob;
import creatureLogic.Description;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds gases.
 */
public class GasBuilder{
    
    public static Blob gardengas(int x, int y){
        Blob g = new Blob("shadowmelded", 
                new Description("gas", "Cleansing shafts of light pierce the vegetation."),
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(AnimationBuilder.getAnimation("shadowmelded")), 1, x, y){
                    @Override
                    public void turn(double delta){}
                };
        return g;
    }
    
}

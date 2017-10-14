
package buffs;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import creatureLogic.Description;
import blob.Potpourri;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds gases.
 */
public class GasBuilder{
    
    public static Potpourri gardengas(int x, int y){
        Potpourri g = new Potpourri("shadowmelded", 
                new Description("gas", "Cleansing shafts of light pierce the vegetation."),
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(AnimationBuilder.getAnimation("shadowmelded")), 1, x, y){
                    @Override
                    public void turn(double delta){}
                };
        return g;
    }
    
}

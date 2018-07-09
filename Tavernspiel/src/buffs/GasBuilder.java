
package buffs;

import animation.Animation;
import animation.DrawnAnimation;
import animation.GameObjectAnimator;
import blob.Blob;
import creatureLogic.Description;
import java.awt.Color;
import java.awt.Graphics;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds gases.
 */
public abstract class GasBuilder{
    
    public static Blob gardengas(int x, int y){
        Blob g = new Blob("shadowmelded", 
                new Description("gas", "Cleansing shafts of light pierce the vegetation."),
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(shadowmeldedAnimation()), 1, x, y){
                    @Override
                    public void turn(double delta){}
                };
        return g;
    }
    
    @Unfinished("Placeholder Animation")
    private static Animation shadowmeldedAnimation(){
        return new DrawnAnimation(-1, null){
            @Override
            public void animate(Graphics g, int x, int y){
                g.setColor(Color.WHITE);
                g.fillOval(x+4, y+4, 8, 8);
            }
        
        };
    }
    
}

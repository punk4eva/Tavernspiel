
package creatures.enemies;

import ai.IntelligentAI3;
import animation.GameObjectAnimator;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatures.Creature;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Gula.
 */
public class Gula extends Creature{
    
    public Gula(Description desc, BufferedImage img/**Remove*/, int level){
        super("Gula", new Description("creatures", "Gula are gluttonous creatures, and nothing will sate their appetite.",
                "They are commonly summoned by sages as part of a war effort, and can be extremely dangerous to " +
                "unarmed opponents, although their main function is resource gathering. This Gula is probably a remenant of a past war.")
                , new Attributes(new IntelligentAI3(), 1, 1, 0.8+0.05*level, 1, //Read the javadocs.
                0.025, 8+2*level, 7, 1+level/2), 
                new GameObjectAnimator(img, //sprite sheet goes here.
                new String[]{"<name of anim. 1>", "etc."}, new int[]{1, 2, 3})); //The int array is the length in frames of each animation. 
    }
    
}

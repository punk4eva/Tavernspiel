
package creatures.enemies;

import ai.IntelligentAI3;
import animation.GameObjectAnimator;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatures.Creature;
import javax.swing.ImageIcon;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Gula.
 */
public class Gula extends Creature{
    
    /**
     * Creates a new instance.
     * @param level The level.
     */
    public Gula(int level){
        super("Gula", new Description("creatures", "Gula are gluttonous creatures, and nothing will sate their appetite.",
                "They are commonly summoned by sages as part of a war effort, and can be extremely dangerous to " +
                "unarmed opponents, although their main function is resource gathering. This Gula is probably a remenant of a past war.")
                , new Attributes(new IntelligentAI3(), 1, 1, 0.8+0.05*level, 1,
                0.025, 8+2*level, 7, 1+level/2), 
                new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
    }
    
}

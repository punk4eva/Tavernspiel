
package creatures.enemies;

import ai.intelligence.IntelligentAI3;
import animation.CreatureAnimator;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatures.Creature;
import javax.swing.ImageIcon;
import logic.Distribution.NormalProb;
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
                "They were commonly summoned by sages as part of a war effort, and can be extremely dangerous to " +
                "unarmed opponents even though their main function is resource gathering. This Gula is probably a remenant of a past war."),
                new Attributes(new IntelligentAI3(), 0.075, 7+2*level, 1, 1, 4+2*level, new NormalProb(1+0.05*level,1), new NormalProb(1+0.05*level,1), new NormalProb(1+0.05*level,1), new NormalProb(1+0.05*level,1.8)), 
                new CreatureAnimator(ImageUtils.convertToBuffered(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
    }
    
}

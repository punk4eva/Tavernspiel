package creatures.passives;

import animation.CreatureAnimator;
import creatureLogic.Attack;
import creatureLogic.Attack.CreatureAttack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatures.Creature;
import creatures.Hero;
import javax.swing.ImageIcon;
import logic.ImageUtils;

/**
 *
 * @author Charlie Hands and Adam Whittaker
 * 
 * This is the base class for all passive-type NPCs.
 */
public class PassiveCreature extends Creature{
    
    public final String[] speechOptions;
    public final boolean mortal;
    
    public PassiveCreature(String name, Attributes ab, boolean k, String... spo){
        super(name, new Description("creatures", ""), ab, 
                new CreatureAnimator(ImageUtils.convertToBuffered(new ImageIcon("graphics/spritesheets/tree.png")), new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
        speechOptions = spo;
        mortal = k;
    }
    
    @Override
    public void takeDamage(Attack attack){
        if(attack instanceof CreatureAttack && ((CreatureAttack)attack).attacker instanceof Hero){// new NPCSpeech(speechOptions[rand.nextInt(speechOptions.length)]).activate(Window.main);
        }else if(mortal){
            attributes.hp -= attack.damage;
            if(attributes.hp<=0){
                if(inventory.contains("ankh")){
                    throw new UnsupportedOperationException("Not supported yet!");
                }else{
                    if(attack instanceof CreatureAttack)
                        ((CreatureAttack)attack).attacker.gainXP(attributes.xpOnDeath);
                    die();
                }
            }
        }
    }
    
}

package creatures.passives;

import ai.intelligence.IntelligentAI1;
import animation.GameObjectAnimator;
import creatureLogic.Attack;
import creatureLogic.Attack.CreatureAttack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatures.Creature;
import creatures.Hero;
import javax.swing.ImageIcon;
import level.Area;
import logic.ImageUtils;

/**
 *
 * @author Charlie Hands
 */
public class PassiveCreature extends Creature{
    
    public final String[] speechOptions;
    public final boolean killable;
    
    public PassiveCreature(String name, boolean k, String... spo){
        super(name, new Description("creatures", ""), new Attributes(new PassiveAI(),1,1,1,1,0.025,20,10,0), 
                new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")), new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
        speechOptions = spo;
        killable = k;
    }
    
    @Override
    public void takeDamage(Attack attack){
        if(attack instanceof CreatureAttack && ((CreatureAttack)attack).attacker instanceof Hero){// new NPCSpeech(speechOptions[rand.nextInt(speechOptions.length)]).activate(Window.main);
        }else if(killable){
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
    
    private static class PassiveAI extends IntelligentAI1{
        
        @Override
        public void turn(Creature c, Area area){
            throw new UnsupportedOperationException("Turn not initialized.");
        }
        
    }
    
}

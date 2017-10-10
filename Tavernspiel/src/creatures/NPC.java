package creatures;

import ai.IntelligentAI0;
import animation.GameObjectAnimator;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import dialogues.NPCSpeech;
import gui.Window;
import javax.swing.ImageIcon;
import logic.ImageUtils;
import java.util.Random;


/**
 *
 * @author Charlie Hands
 */
public class NPC extends Creature{
    String[] speechOptions;
    Random rand = new Random();
    
    public NPC(String name, String... speechOptions){
        super(name, new Description("creatures", "", ""), new Attributes(new IntelligentAI0(),1,1,1,1,1,1,1,1), new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")), new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
        this.speechOptions = speechOptions;
    }
    @Override
    public void getAttacked(Attack attack){
        if(attack.attacker instanceof Hero){}// new NPCSpeech(speechOptions[rand.nextInt(speechOptions.length)]).activate(Window.main);
        else{
            attributes.hp -= attack.damage;
            if(attributes.hp<=0){
                if(inventory.contains("ankh")){
                    throw new UnsupportedOperationException("Not supported yet!");
                }else{
                    attack.attacker.gainXP(attributes.xpOnDeath);
                    die();
                }
            }
        }
    }
}

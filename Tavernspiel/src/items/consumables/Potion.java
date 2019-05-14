
package items.consumables;

import ai.PlayerAI;
import creatureLogic.Action.ActionOnItem;
import creatureLogic.Description;
import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import items.Consumable;
import items.actions.ItemAction;
import items.builders.DescriptionBuilder;
import items.builders.PotionBuilder;
import logic.ConstantFields;
import logic.Utils.Unfinished;
/**
 *
 * @author Adam Whittaker
 * 
 * This class models a Potion.
 */
public abstract class Potion extends Consumable{
    
    private final static long serialVersionUID = 5884324288873289L;
    
    private final String tasteMessage;
    public final Type type;
    public enum Type{
        BENEFICIAL,VOLATILE,VERSATILE
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description of what the potion does.
     * @param pp The PotionProfile.
     * @param t The type of Potion.
     */
    public Potion(String n, String desc, PotionProfile pp, Type t){
        super(n, pp.unknownName, pp.description, new Description("potions", pp.description + "\n" + DescriptionBuilder.getPotionExtra()), 
                pp.loader, PotionBuilder.idMap.get(n));
        description.layers[0] += "\n" + desc;
        actions[0] = ItemAction.THROW_POTION;
        actions[2] = ItemAction.DRINK;
        tasteMessage = pp.tasteMessage;
        type = t;
        description.type = "potions";
    }
    
    @Override
    @Unfinished("Exception shouldn't be thrown if done right.")
    public boolean use(Creature c){
        if(!(c instanceof Hero)) throw new IllegalArgumentException("Creature is using Potion.use()");
        if(type.equals(Type.VOLATILE)){
            ((PlayerAI)c.attributes.ai).nextAction = new ActionOnItem(actions[0], this, (Hero) c, -1, -1, -18);
            ((PlayerAI)c.attributes.ai).alertAction();
        }else{
            Main.addMessage(ConstantFields.interestColor, tasteMessage);
            ((PlayerAI)c.attributes.ai).nextAction = new ActionOnItem(actions[2], this, (Hero) c, -1, -1, -17);
            ((PlayerAI)c.attributes.ai).alertAction();
        }
        return true;
    }
    
    public abstract void drinkPotion(Creature c);
    
    public abstract void throwPotion(Creature c, int x, int y);
    
}

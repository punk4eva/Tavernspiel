
package items.consumables;

import creatureLogic.Description;
import creatures.Creature;
import gui.mainToolbox.Main;
import items.Consumable;
import items.actions.ItemAction;
import items.builders.DescriptionBuilder;
import items.builders.PotionBuilder;
import logic.ConstantFields;

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
        actions[2] = ItemAction.DRINK;
        tasteMessage = pp.tasteMessage;
        type = t;
        description.type = "potions";
    }
    
    @Override
    public boolean use(Creature c){
        Main.addMessage(ConstantFields.interestColor, tasteMessage);
        return true;
    }
    
}

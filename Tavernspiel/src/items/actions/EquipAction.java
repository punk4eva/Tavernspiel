
package items.actions;

import creatures.Creature;
import items.Apparatus;
import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * This action represents equipping an Apparatus.
 */
public class EquipAction extends ItemAction{

    public EquipAction(){
        super("EQUIP", 3);
    }

    @Override
    public void act(Item item, Creature c, int slot, Object... data){
        if(data.length==0) c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)item, slot);
        else c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)item, slot, (Integer)data[0]);
    }
    
}

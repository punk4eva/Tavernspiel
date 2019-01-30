
package items.consumables.scrolls;

import animation.LoadableStillAnimation;
import creatures.Creature;
import gui.mainToolbox.Main;
import items.consumables.LocationSpecificScroll;
import items.consumables.ScrollProfile;
import items.equipment.Wand;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class CustomScroll extends LocationSpecificScroll{
    
    private Wand mimic; 

    public CustomScroll(ScrollProfile sp){
        super("Custom Scroll", "This Scroll can gather magic from areas of high concentration and store it within itself.", sp);
    }

    @Override
    public boolean use(Creature c, int x, int y){
        if(mimic==null){
            Main.addMessage(ConstantFields.interestColor, "The Scroll has not absorbed any magic yet.");
            return true;
        }else mimic.fire(c, x, y);
        return true;
    }
    
    public void absorb(Wand wand){
        mimic = wand;
        animation = new LoadableStillAnimation(ScrollProfile.getSmudgeIconSupplier());
        Main.addMessage(ConstantFields.interestColor, "Your scroll has absorbed some magic.");
    }
    
}

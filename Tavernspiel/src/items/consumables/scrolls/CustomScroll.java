
package items.consumables.scrolls;

import animation.StillAnimation;
import creatures.Creature;
import gui.MainClass;
import items.consumables.LocationSpecificScroll;
import items.equipment.Wand;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class CustomScroll extends LocationSpecificScroll{
    
    private Wand mimic; 

    public CustomScroll(Image i, boolean idd){
        super("Custom Scroll", "This Scroll can gather magic from areas of high concentration and store it within itself.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, int x, int y){
        if(mimic==null) MainClass.messageQueue.add("gold", "The Scroll has not absorbed any magic yet.");
        else mimic.fire(c, x, y);
    }
    
    public void absorb(Wand wand){
        mimic = wand;
        animation = new StillAnimation(hero.scrollBuilder.getRandomSmudge());
        MainClass.messageQueue.add("gold", "Your scroll has absorbed some magic.");
    }
    
}

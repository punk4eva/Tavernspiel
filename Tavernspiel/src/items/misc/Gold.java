
package items.misc;

import creatureLogic.Description;
import items.Item;
import items.ItemAction;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import static logic.Distribution.r;

/**
 *
 * @author Adam Whittaker
 */
public class Gold extends Item{
    
    private final static long serialVersionUID = 12314567L;
    
    public Gold(int quantity){
        super("Gold", new Description("gold", "This is a pile of " + quantity + 
                " gold coins."), (Serializable&Supplier<ImageIcon>)()->getIcon(quantity), quantity);
        actions = new ItemAction[]{};
    }
    
    private static ImageIcon getIcon(int q){
        if(q<10) return ItemBuilder.getIcon(0, 160);
        else if(q<30) return ItemBuilder.getIcon(16, 160);
        else if(q<100) return ItemBuilder.getIcon(32, 160);
        else if(q<300) return ItemBuilder.getIcon(48, 160);
        else if(q<500) return ItemBuilder.getIcon(64, 160);
        else if(q<1000) return ItemBuilder.getIcon(80, 160);
        else if(q<1600) return ItemBuilder.getIcon(96, 160);
        else return ItemBuilder.getIcon(112, 160);
    }
    
    public static Gold getGoldQuantity(int level){
        switch(level){
            case 0: return new Gold(r.nextInt(10));
            case 1: return new Gold(r.nextInt(20)+10);
            case 2: return new Gold(r.nextInt(70)+30);
            case 3: return new Gold(r.nextInt(200)+100);
            case 4: return new Gold(r.nextInt(200)+300);
            case 5: return new Gold(r.nextInt(500)+500);
            case 6: return new Gold(r.nextInt(600)+1000);
            case 7: return new Gold(r.nextInt(2000)+1600);
            default: throw new 
                IllegalStateException("Invalid Gold quantity: " + level);
        }
    }
    
}

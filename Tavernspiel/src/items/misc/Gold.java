
package items.misc;

import creatureLogic.Description;
import items.Item;
import items.actions.ItemAction;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import static logic.Distribution.R;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents Gold (currency).
 */
public class Gold extends Item{
    
    private final static long serialVersionUID = 12314567L;
    private final static ImageIcon GOLD1 = ItemBuilder.getIcon(0, 160),
            GOLD2 = ItemBuilder.getIcon(16, 160), 
            GOLD3 = ItemBuilder.getIcon(32, 160),
            GOLD4 = ItemBuilder.getIcon(48, 160),
            GOLD5 = ItemBuilder.getIcon(64, 160),
            GOLD6 = ItemBuilder.getIcon(80, 160),
            GOLD7 = ItemBuilder.getIcon(96, 160),
            GOLD8 = ItemBuilder.getIcon(112, 160);
    
    
    public Gold(int quantity){
        super("Gold", new Description("gold", "This is a pile of " + quantity + 
                " gold coins."), (Serializable&Supplier<ImageIcon>)()->getIcon(quantity), quantity);
        actions = new ItemAction[]{};
    }
    
    public static ImageIcon getIcon(int q){
        if(q<10) return GOLD1;
        else if(q<30) return GOLD2;
        else if(q<100) return GOLD3;
        else if(q<300) return GOLD4;
        else if(q<500) return GOLD5;
        else if(q<1000) return GOLD6;
        else if(q<1600) return GOLD7;
        else return GOLD8;
    }
    
    /**
     * Generates a random quantity of Gold.
     * @param level The scale on which Gold should be generated.
     * @return
     */
    public static Gold getGoldQuantity(int level){
        switch(level){
            case 0: return new Gold(R.nextInt(10));
            case 1: return new Gold(R.nextInt(20)+10);
            case 2: return new Gold(R.nextInt(70)+30);
            case 3: return new Gold(R.nextInt(200)+100);
            case 4: return new Gold(R.nextInt(200)+300);
            case 5: return new Gold(R.nextInt(500)+500);
            case 6: return new Gold(R.nextInt(600)+1000);
            case 7: return new Gold(R.nextInt(2000)+1600);
            default: throw new 
                IllegalStateException("Invalid Gold quantity: " + level);
        }
    }
    
}

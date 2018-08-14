
package items.consumables;

import items.builders.DescriptionBuilder;
import items.builders.ItemBuilder;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class ScrollProfile implements Serializable{
    
    private static final long serialVersionUID = 7832133355L;
    
    private static final HashMap<String, Dimension> runeMap = new HashMap<>();
    static{
        runeMap.put("GICH", new Dimension(0, 80));
        runeMap.put("META", new Dimension(16, 80));
        runeMap.put("ALIKI", new Dimension(32, 80));
        runeMap.put("SIMA", new Dimension(48, 80));
        runeMap.put("IRIGI", new Dimension(64, 80));
        runeMap.put("TILACHA", new Dimension(80, 80));
        runeMap.put("MIDI", new Dimension(96, 80));
        runeMap.put("MOTI", new Dimension(112, 80));
        runeMap.put("SMUDGE1", new Dimension(0, 96));
        runeMap.put("SMUDGE2", new Dimension(16, 96));
        runeMap.put("INEMA", new Dimension(32, 96));
        runeMap.put("KORO", new Dimension(48, 96));
        runeMap.put("BERI", new Dimension(64, 96));
        runeMap.put("REDO", new Dimension(80, 96));
        runeMap.put("SEMA", new Dimension(96, 96));
        runeMap.put("BLANK", new Dimension(112, 96));
        runeMap.put("FERESI", new Dimension(64, 176));
        runeMap.put("MOKATI", new Dimension(80, 176));
        runeMap.put("MAZORI", new Dimension(96, 176));
        runeMap.put("HULUMI", new Dimension(112, 176));
    }
    
    protected String unknownName;
    protected String description;
    protected Supplier<ImageIcon> loader;
    public boolean identified = false;
    
    public ScrollProfile(String uN){
        unknownName = "Scroll bearing the rune " + uN;
        description = "The " + DescriptionBuilder.word(DescriptionBuilder.rune) 
                + " rune of " + uN + " is inscribed on this parchment.";
        loader = (Serializable & Supplier<ImageIcon>) () -> {
            Dimension dim = runeMap.get(uN);
            return ItemBuilder.getIcon(dim.width, dim.height);
        };
    }
    
    private ScrollProfile(){}
    
    public static ScrollProfile getCustomProfile(){
        ScrollProfile sp = new ScrollProfile();
        sp.unknownName = "Blank Scroll";
        sp.description = "";
        sp.identified = true;
        sp.loader = (Serializable & Supplier<ImageIcon>) () -> 
            ItemBuilder.getIcon(112, 96);
        return sp;
    }
    
    public static ScrollProfile getSmudgeProfile(){
        ScrollProfile sp = new ScrollProfile();
        sp.unknownName = "Smudged Scroll";
        sp.description = "";
        if(Distribution.chance(1, 2)) 
            sp.loader = (Serializable & Supplier<ImageIcon>) () -> 
                    ItemBuilder.getIcon(0, 96);
        else sp.loader = (Serializable & Supplier<ImageIcon>) () -> 
                    ItemBuilder.getIcon(16, 96);
        return sp;
    }
    
    public static ImageIcon getSmudgeIcon(){
        if(Distribution.chance(1, 2)) return ItemBuilder.getIcon(0, 96);
        else return ItemBuilder.getIcon(16, 96);
    }
    
}

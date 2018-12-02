
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
        runeMap.put("GICH", new Dimension(0, 176));
        runeMap.put("META", new Dimension(16, 176));
        runeMap.put("ALIKI", new Dimension(32, 176));
        runeMap.put("SIMA", new Dimension(48, 176));
        runeMap.put("IRIGI", new Dimension(64, 176));
        runeMap.put("TILACHA", new Dimension(80, 176));
        runeMap.put("MIDI", new Dimension(96, 176));
        runeMap.put("MOTI", new Dimension(112, 176));
        runeMap.put("SMUDGE1", new Dimension(0, 192));
        runeMap.put("SMUDGE2", new Dimension(16, 192));
        runeMap.put("INEMA", new Dimension(32, 192));
        runeMap.put("KORO", new Dimension(48, 192));
        runeMap.put("BERI", new Dimension(64, 192));
        runeMap.put("REDO", new Dimension(80, 192));
        runeMap.put("SEMA", new Dimension(96, 192));
        runeMap.put("BLANK", new Dimension(112, 192));
        runeMap.put("FERESI", new Dimension(64, 272));
        runeMap.put("MOKATI", new Dimension(80, 272));
        runeMap.put("MAZORI", new Dimension(96, 272));
        runeMap.put("HULUMI", new Dimension(112, 272));
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

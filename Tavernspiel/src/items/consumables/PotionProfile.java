
package items.consumables;

import items.builders.DescriptionBuilder;
import items.builders.ItemBuilder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;
import static logic.ImageUtils.convertToBuffered;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class generates and stores information about a Potion.
 */
public class PotionProfile extends DescriptionBuilder{
    
    private static final long serialVersionUID = 78335422323455L;
    
    protected String tasteMessage;
    protected String unknownName;
    protected String description;
    protected Supplier<ImageIcon> loader;
    
    private static final int[] liquidRegex = new int[]{238,0,156},
            shadedLiquidRegex = new int[]{165,0,107};
    
    private static final String[] unknownContainers = {"Potion in a pyramidal tube", 
            "Potion in a heart shaped tube", "Potion in a star-shaped tube", 
            "Potion in a conical vial", "Potion in a circular vial", 
            "Potion in a skull shaped bottle", "Potion in a cubic bottle", 
            "Potion in a spherical jar"};
    
    private static final HashMap<String, Dimension> unknownToDimension = new HashMap<>();
    static{
        unknownToDimension.put("Potion in a pyramidal tube", new Dimension(0, 224));
        unknownToDimension.put("Potion in a heart shaped tube", new Dimension(16, 224));
        unknownToDimension.put("Potion in a star-shaped tube", new Dimension(32, 224));
        unknownToDimension.put("Potion in a conical vial", new Dimension(48, 224));
        unknownToDimension.put("Potion in a circular vial", new Dimension(64, 224));
        unknownToDimension.put("Potion in a skull shaped bottle", new Dimension(80, 224));
        unknownToDimension.put("Potion in a cubic bottle", new Dimension(96, 224));
        unknownToDimension.put("Potion in a spherical jar", new Dimension(112, 224));
        /*bareProfileMap.put("Deep Sleep Potion", new PotionProfile(new Description("potions", "This depressant is said to ease stresses in the mind which puts the victime to sleep temporarily.|It is not strong enough to keep the victim from waking upon receiving damage, though."), Type.VERSATILE));
        bareProfileMap.put("Merchant's Potion", new PotionProfile(new Description("potions", "This unfinished potion must be brewed with an item and then drunk to receive gold. It works by reacting with the quaffer's stomach acid and teleporting gold from the creator of the potion into the quaffer's stomach, which is then vomited or excreted.|Potions of this kind were once outlawed because the merchants could easily cheat the buyers by not teleporting money into their stomachs and thiefs could reverse engineer the " +
        "seals to teleport into the Kyoukan bank. The potions are now only legally sold by the government and the transaction scroll is dissolved inside the potion to prevent robbery.|Perhaps you can exploit the teleportation magic in this potion."), Type.BENEFICIAL));
        bareProfileMap.put("Photosynthesis Potion", new PotionProfile(new Description("potions", "The potion makes one green and allows them to photosynthesise until the effect wears off, reducing hunger and boosting regeneration.|Since " +
        "the drinker is more attuned with nature, they can camouflage themselves with vegetation. The potion is often used as shampoo for some reason."), Type.BENEFICIAL));
        bareProfileMap.put("Potion of Lyncanthropy", new PotionProfile(new Description("potions", "This fluid contains just enough werewolf blood to give someone the characteristics of a werewolf for a period of time.|More specifically, it increases, melee damage, health and speed, but health and hunger is lost unless enemies are killed actively."), Type.BENEFICIAL));*/
        //bareProfileMap.put("Smoke Potion", new PotionProfile(new Description("potions", "This liquid will react with open air, producing a lot od smoke.|It is is used in combat situations to disorient a group of attackers or to provide cover to flee.|Since the reaction involved is vigorous, the smoke produced will temporarily blind attackers."), Type.VERSATILE));
    }
    
    private static BufferedImage outfitImage(BufferedImage img, Color liq, Color frag){
        WritableRaster raster = img.getRaster();
        int[] liquid = new int[]{liq.getRed(), liq.getGreen(), liq.getBlue(), 0};
        int[] shadedLiquid = ItemBuilder.shade(liquid);
        int[] pixel = new int[4];
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                pixel = raster.getPixel(x, y, pixel);
                if(Utils.pixelColourEquals(pixel, liquidRegex)) raster.setPixel(x, y, liquid);
                else if(Utils.pixelColourEquals(pixel, shadedLiquidRegex)) raster.setPixel(x, y, shadedLiquid);
            }
        }
        if(frag==null) return img;
        int[] fragment = new int[]{frag.getRed(), frag.getGreen(), frag.getBlue(), 0};
        int[] fragmentRegex = new int[]{238,159,153};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                if(Utils.pixelColourEquals(raster.getPixel(x, y, pixel), fragmentRegex)) raster.setPixel(x, y, fragment);
            }
        }
        return img;
    }
        
    private static void temp(PotionProfile p){
        p.description += "The potion is a" + word(temp) + ", ";
    }

    private static String color(PotionProfile p){
        String ret = word(color);
        p.description += word(colorMod) + ret + " coloured liquid.\n";
        return ret;
    }

    private static String texture(PotionProfile p){
        String[] str = textureWord();
        p.description += "It " + str[0];
        if(str.length==2) return str[1];
        return null;
    }

    private static void smell(PotionProfile p){
        p.description += " and smells " + smellWord() + ".\n";
    }

    private static void viscosity(PotionProfile p){
        p.description += "The liquid itself is a " + word(viscosity)
                + " substance.\n";
    }

    private static Dimension shapeContainer(PotionProfile p, Distribution vChance){
        p.unknownName = unknownContainers[(int)vChance.next()];
        p.description += "A" + word(shapeMod) + " ";
        String col = word(color);
        p.description += word(container) + " with a " + col
                + " coloured " + word(stopper) + " stopper holds the potion.\n";
        Dimension d = unknownToDimension.get(p.unknownName);
        p.unknownName = col + " " + p.unknownName;
        return d;
    }

    private static void decoration(PotionProfile p){
        p.description += word(decoration) + "\n";
    }

    private static String taste(){
        return "The potion tasted " + tasteWord() + ".";
    }

    /**
     * Creates a random PotionProfile.
     * @param vileChance The chance of each vile being selected.
     * @return
     */
    public static PotionProfile getRandomProfile(Distribution vileChance){
        PotionProfile p = new PotionProfile();
        temp(p);
        String _color = color(p);
        String _texture = texture(p);
        smell(p);
        viscosity(p);
        Dimension d = shapeContainer(p, vileChance);
        decoration(p);
        p.loader = (Supplier<ImageIcon> & Serializable)() -> 
            new ImageIcon(outfitImage(convertToBuffered(ItemBuilder.getIcon(d.width, d.height)), 
                    ItemBuilder.getColor(_color), _texture==null ? 
                            null : ItemBuilder.getColor(_texture)));
        p.tasteMessage = taste();
        return p;   
    }
    
    private PotionProfile(){
        tasteMessage = "";
        unknownName = "";
        description = "";
    }
    
}

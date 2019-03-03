
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
            shadedLiquidRegex = new int[]{165,0,107},
            fragmentRegex = new int[]{238,159,153},
            shadedStopperRegex = new int[]{0,153,216},
            shadedStopperRegex2 = new int[]{0,153,215},
            stopperRegex = new int[]{0,196,232};
    
    private static final String[] unknownShapes = {"pyramidal", 
            "heart-shaped", "star-shaped", 
            "conical", "circular", 
            "skull-shaped", "cubic", 
            "spherical"};
    
    private static final HashMap<String, Dimension> unknownToDimension = new HashMap<>();
    static{
        unknownToDimension.put("pyramidal", new Dimension(0, 224));
        unknownToDimension.put("heart-shaped", new Dimension(16, 224));
        unknownToDimension.put("star-shaped", new Dimension(32, 224));
        unknownToDimension.put("conical", new Dimension(48, 224));
        unknownToDimension.put("circular", new Dimension(64, 224));
        unknownToDimension.put("skull-shaped", new Dimension(80, 224));
        unknownToDimension.put("cubic", new Dimension(96, 224));
        unknownToDimension.put("spherical", new Dimension(112, 224));
        /*bareProfileMap.put("Deep Sleep Potion", new PotionProfile(new Description("potions", "This depressant is said to ease stresses in the mind which puts the victime to sleep temporarily.|It is not strong enough to keep the victim from waking upon receiving damage, though."), Type.VERSATILE));
        bareProfileMap.put("Merchant's Potion", new PotionProfile(new Description("potions", "This unfinished potion must be brewed with an item and then drunk to receive gold. It works by reacting with the quaffer's stomach acid and teleporting gold from the creator of the potion into the quaffer's stomach, which is then vomited or excreted.|Potions of this kind were once outlawed because the merchants could easily cheat the buyers by not teleporting money into their stomachs and thiefs could reverse engineer the " +
        "seals to teleport into the Kyoukan bank. The potions are now only legally sold by the government and the transaction scroll is dissolved inside the potion to prevent robbery.|Perhaps you can exploit the teleportation magic in this potion."), Type.BENEFICIAL));
        bareProfileMap.put("Photosynthesis Potion", new PotionProfile(new Description("potions", "The potion makes one green and allows them to photosynthesise until the effect wears off, reducing hunger and boosting regeneration.|Since " +
        "the drinker is more attuned with nature, they can camouflage themselves with vegetation. The potion is often used as shampoo for some reason."), Type.BENEFICIAL));
        bareProfileMap.put("Potion of Lyncanthropy", new PotionProfile(new Description("potions", "This fluid contains just enough werewolf blood to give someone the characteristics of a werewolf for a period of time.|More specifically, it increases, melee damage, health and speed, but health and hunger is lost unless enemies are killed actively."), Type.BENEFICIAL));*/
        //bareProfileMap.put("Smoke Potion", new PotionProfile(new Description("potions", "This liquid will react with open air, producing a lot od smoke.|It is is used in combat situations to disorient a group of attackers or to provide cover to flee.|Since the reaction involved is vigorous, the smoke produced will temporarily blind attackers."), Type.VERSATILE));
    }
    
    private static BufferedImage outfitImage(BufferedImage img, Color liq, Color frag, Color sto){
        WritableRaster raster = img.getRaster();
        int[] liquid = new int[]{liq.getRed(), liq.getGreen(), liq.getBlue(), 255};
        int[] shadedLiquid = ItemBuilder.shade(liquid);
        int[] stopper = new int[]{sto.getRed(), sto.getGreen(), sto.getBlue(), 255};
        int[] shadedStopper = ItemBuilder.shade(stopper);
        int[] pixel = new int[4];
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                pixel = raster.getPixel(x, y, pixel);
                if(Utils.pixelColourEquals(pixel, liquidRegex)) raster.setPixel(x, y, liquid);
                else if(Utils.pixelColourEquals(pixel, shadedLiquidRegex)) raster.setPixel(x, y, shadedLiquid);
                else if(Utils.pixelColourEquals(pixel, stopperRegex)){
                    stopper[3] = pixel[3];
                    raster.setPixel(x, y, stopper);
                }else if(Utils.pixelColourEquals(pixel, shadedStopperRegex)||Utils.pixelColourEquals(pixel, shadedStopperRegex2)){
                    shadedStopper[3] = pixel[3];
                    raster.setPixel(x, y, shadedStopper);
                }
            }
        }
        if(frag==null) return img;
        int[] fragment = new int[]{frag.getRed(), frag.getGreen(), frag.getBlue(), 255};
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

    private static Object[] shapeContainer(PotionProfile p, Distribution vChance){
        Object[] ret = new Object[2];
        p.unknownName = unknownShapes[(int)vChance.next()];
        p.description += "A" + word(shapeMod) + " ";
        String cont = word(container), stopperColor = word(color);
        p.description += p.unknownName + " " + cont + " with a " + stopperColor
                + " coloured " + word(stopper) + " stopper holds the potion.\n";
        ret[0] = unknownToDimension.get(p.unknownName);
        ret[1] = stopperColor;
        p.unknownName = "Potion in a " + p.unknownName + " " + cont;
        return ret;
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
        Object[] cont = shapeContainer(p, vileChance);
        Dimension d = (Dimension) cont[0];
        
        decoration(p);
        p.loader = (Supplier<ImageIcon> & Serializable)() -> 
            new ImageIcon(outfitImage(convertToBuffered(ItemBuilder.getIcon(d.width, d.height)), 
                    ItemBuilder.getColor(_color), _texture==null ? 
                            null : ItemBuilder.getColor(_texture), ItemBuilder.getColor((String) cont[1])));
        p.tasteMessage = taste();
        return p;   
    }
    
    private PotionProfile(){
        tasteMessage = "";
        unknownName = "";
        description = "";
    }
    
}

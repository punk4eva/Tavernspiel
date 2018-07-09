
package items.consumables;

import creatureLogic.Description;
import items.ItemProfile;
import items.consumables.Potion.Type;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Utils;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class PotionProfile extends ItemProfile{
    
    private static final PotionDescriptionBuilder pdb = new PotionDescriptionBuilder();
    protected String tasteMessage;
    protected String unknownName;
    protected Type type;
    protected boolean identified;
    
    static final HashMap<String, PotionProfile> bareProfileMap = new HashMap<>();
    private static final HashMap<String, Dimension> unknownToDimension = new HashMap<>();
    static{
        unknownToDimension.put("Potion in a pyramidal tube", new Dimension(0, 128));
        unknownToDimension.put("Potion in a heart shaped tube", new Dimension(16, 128));
        unknownToDimension.put("Potion in a star-shaped tube", new Dimension(32, 128));
        unknownToDimension.put("Potion in a conical vial", new Dimension(48, 128));
        unknownToDimension.put("Potion in a circular vial", new Dimension(64, 128));
        unknownToDimension.put("Potion in a skull shaped bottle", new Dimension(80, 128));
        unknownToDimension.put("Potion in a cubic bottle", new Dimension(96, 128));
        unknownToDimension.put("Potion in a spherical jar", new Dimension(112, 128));
        bareProfileMap.put("Deep Sleep Potion", new PotionProfile(new Description("potions", "This depressant is said to ease stresses in the mind which puts the victime to sleep temporarily.|It is not strong enough to keep the victim from waking upon receiving damage, though."), Type.VERSATILE));
        bareProfileMap.put("Merchant's Potion", new PotionProfile(new Description("potions", "This unfinished potion must be brewed with an item and then drunk to receive gold. It works by reacting with the quaffer's stomach acid and teleporting gold from the creator of the potion into the quaffer's stomach, which is then vomited or excreted.|Potions of this kind were once outlawed because the merchants could easily cheat the buyers by not teleporting money into their stomachs and thiefs could reverse engineer the " +
                "seals to teleport into the Kyoukan bank. The potions are now only legally sold by the government and the transaction scroll is dissolved inside the potion to prevent robbery.|Perhaps you can exploit the teleportation magic in this potion."), Type.BENEFICIAL));
        bareProfileMap.put("Photosynthesis Potion", new PotionProfile(new Description("potions", "The potion makes one green and allows them to photosynthesise until the effect wears off, reducing hunger and boosting regeneration.|Since " +
                "the drinker is more attuned with nature, they can camouflage themselves with vegetation. The potion is often used as shampoo for some reason."), Type.BENEFICIAL));
        bareProfileMap.put("Potion of Lyncanthropy", new PotionProfile(new Description("potions", "This fluid contains just enough werewolf blood to give someone the characteristics of a werewolf for a period of time.|More specifically, it increases, melee damage, health and speed, but health and hunger is lost unless enemies are killed actively."), Type.BENEFICIAL));
        /*bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));
        bareProfileMap.put("Potion", new PotionProfile(new Description("potions", ""), Type.));*/
        bareProfileMap.put("Smoke Potion", new PotionProfile(new Description("potions", "This liquid will react with open air, producing a lot od smoke.|It is is used in combat situations to disorient a group of attackers or to provide cover to flee.|Since the reaction involved is vigorous, the smoke produced will temporarily blind attackers."), Type.VERSATILE));
    }
    
    private static BufferedImage outfitImage(BufferedImage img, Color liq, Color frag){
        WritableRaster raster = img.getRaster();
        int[] liquid = new int[]{liq.getRed(), liq.getGreen(), liq.getBlue()};
        int[] liquidRegex = new int[]{238,0,156};
        int[] shadedLiquid = shade(liquid);
        int[] shadedLiquidRegex = new int[]{165,0,107};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(Utils.pixelColourEquals(pixel, liquidRegex)) raster.setPixel(x, y, liquid);
                else if(Utils.pixelColourEquals(pixel, shadedLiquidRegex)) raster.setPixel(x, y, shadedLiquid);
            }
        }
        if(frag==null) return img;
        int[] fragment = new int[]{frag.getRed(), frag.getGreen(), frag.getBlue()};
        int[] fragmentRegex = new int[]{238,159,153};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                if(Utils.pixelColourEquals(raster.getPixel(x, y, (int[]) null), fragmentRegex)) raster.setPixel(x, y, fragment);
            }
        }
        return img;
    }
    
    private static final class PotionDescriptionBuilder extends DescriptionBuilder{
        
        private PotionDescriptionBuilder(){super();}
        
        void temp(){
            description += "The potion is a" + word(temp) + ", ";
        }

        String colour(){
            String ret = word(colour);
            description += word(colourMod) + ret + " coloured liquid.\n";
            return ret;
        }

        String texture(){
            String[] str = textureWord();
            description += "It " + str[0];
            if(str.length==2) return str[1];
            return null;
        }

        void smell(){
            description += " and smells " + word(smell) + ".\n";
        }

        void viscosity(){
            description += "The liquid itself is a " + word(viscosity)
                    + " substance.\n";
        }

        void shapeContainer(String name){
            description += "A" + word(shapeMod) + " ";
            String[] ary = name.split(" ");
            for(int n=1;n<ary.length;n++) description += ary[n] + " "; 
            description += word(container) + " with a " + word(colour)
                    + " coloured " + word(stopper) + " stopper holds the potion.\n";
        }

        void decoration(){
            description += word(decoration) + "\n";
        }

        String taste(){
            return "The potion tasted " + word(taste) + ".";
        }
        
        PotionProfile getProfile(String name, String un, boolean idd){
            temp();
            String _colour = colour();
            String _texture = texture();
            smell();
            viscosity();
            shapeContainer(name);
            decoration();
            return new PotionProfile(name, un, description, taste(), _colour, _texture, unknownToDimension.get(un), idd);   
        }
        
    }
    
    /**
     * Creates a random PotionProfile.
     * @param name The name of the Potion
     * @param unknownName The unknown name of the potion
     * @param idd Whether the potion is identified
     * @return
     */
    @Unfinished("Review")
    public static PotionProfile getRandomProfile(String name, String unknownName, boolean idd){
        PotionProfile bp = bareProfileMap.get(name);
        return pdb.getProfile(name, unknownName, idd);
    }
    
    PotionProfile(String nm, String un, String desc, String taste, String colour, String texture, Dimension dim, boolean idd){
        super(nm, new ImageIcon(outfitImage(getImage(dim.width, dim.height), getColour(colour), texture==null ? null : getColour(texture))),
                new Description("potions", desc));
        unknownName = un;
        tasteMessage = taste;
        identified = idd;
    }
    
    private PotionProfile(Description desc, Type t){
        super(null, null, desc);
        type = t;
    }
    
}


package items.equipment;

import creatureLogic.Description;
import enchantments.Enchantment;
import items.ItemProfile;
import static items.ItemProfile.shade;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Utils;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds the characteristics of a Ring.
 */
public class RingProfile extends ItemProfile{
    
    private static final RingDescriptionBuilder rdb = new RingDescriptionBuilder();
    protected int durability;
    protected Distribution distribution;
    protected Enchantment glyph;
    @Unfinished
    private static final HashMap<String, RingProfile> bareRingMap = new HashMap<>(); //maps name of ring with stats.
    static{
    
    }
    
    private static BufferedImage outfitImage(BufferedImage img, Color band, Color gem){
        WritableRaster raster = img.getRaster();
        int[] bandColor = new int[]{band.getRed(), band.getGreen(), band.getBlue(), band.getAlpha()};
        int[] bandRegex = new int[]{255,199,0};
        int[] shadedBandColor = shade(bandColor);
        int[] shadedBandRegex = new int[]{255,157,0};
        int[] brightenedBandColor = brighten(bandColor);
        int[] brightenedBandRegex = new int[]{255,215,60};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(Utils.pixelColourEquals(pixel, bandRegex)) raster.setPixel(x, y, bandColor);
                else if(Utils.pixelColourEquals(pixel, shadedBandRegex)) raster.setPixel(x, y, shadedBandColor);
                else if(Utils.pixelColourEquals(pixel, brightenedBandRegex)) raster.setPixel(x, y, brightenedBandColor);
            }
        }
        if(gem==null) return img;
        int[] gemColor = new int[]{gem.getRed(), gem.getGreen(), gem.getBlue(), gem.getAlpha()};
        int[] gemRegex = new int[]{238,0,156};
        int[] shadedGemColor = new int[]{gem.getRed(), gem.getGreen(), gem.getBlue(), gem.getAlpha()};
        int[] shadedGemRegex = new int[]{165,0,107};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(Utils.pixelColourEquals(pixel, gemRegex)) raster.setPixel(x, y, gemColor);
                else if(Utils.pixelColourEquals(pixel, shadedGemRegex)) raster.setPixel(x, y, shadedGemColor);
            }
        }
        return img;
    }
    
    private static final class RingDescriptionBuilder extends DescriptionBuilder{
        
        String colour(int type){
            String ret = word(colour);
            String mod = "";
            switch(type){
                case 2: mod = "braided "; break;
                case 3: mod = "transparent ";
            }
            description += "This is a " + mod + word(colourMod) + ret + " coloured ring";
            return ret;
        }
        
        String gemColour(int type){
            String ret = word(colour);
            String mod = "";
            switch(type){
                case 1: description += " with a " + word(colourMod) + ret + " coloured gem that softly glistens in the darkness."; break;
                case 2: description += " with " + word(colourMod) + ret + " coloured gems that stud the braids of the ring.";
            }
            return ret;
        }
        
        RingProfile getProfile(String name, int type, int dur, Distribution dist, Enchantment g){
            String bandColour = colour(type);
            switch(type){
                case 0: case 3://plain or transparent
                    return new RingProfile(name, description+".", outfitImage(getImage(16*type, 176), getColour(bandColour), null), dur, dist, g);
                default: //gemmed or braided
                    String gemColour = gemColour(type);
                    return new RingProfile(name, description, outfitImage(getImage(16*type, 176), getColour(bandColour), getColour(gemColour)), dur, dist, g);
            }
        }
        
    }
    
    /**
     * Gets a random RingProfile
     * @param name The name of the ring.
     * @param idd Whether it is identified.
     * @return The RingProfile.
     */
    public static RingProfile getRandomProfile(String name, boolean idd){
        RingProfile bp = bareRingMap.get(name);
        return rdb.getProfile(name, Distribution.r.nextInt(4), bp.durability, bp.distribution, bp.glyph);
    }
    
    RingProfile(String nm, String desc, BufferedImage im, int dur, Distribution dist, Enchantment g){
        super(new ImageIcon(im), nm, new Description("amulets", desc));
        glyph = g;
        distribution = dist;
        durability = dur;
    }
    
}

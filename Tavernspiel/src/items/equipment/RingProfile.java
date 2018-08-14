
package items.equipment;

import items.builders.DescriptionBuilder;
import items.builders.ItemBuilder;
import static items.builders.ItemBuilder.getColor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds the characteristics of a Ring.
 */
public class RingProfile extends DescriptionBuilder{
    
    private static final long serialVersionUID = 7889075L;
    
    private static final int[] brightenedBandRegex = new int[]{255,215,60},
            shadedBandRegex = new int[]{255,157,0}, bandRegex = new int[]{255,199,0},
            gemRegex = new int[]{238,0,156}, shadedGemRegex = new int[]{165,0,107};
    
    public String description = "";
    public Supplier<ImageIcon> loader;
    public boolean identified = false;
    
    private static BufferedImage outfitImage(BufferedImage img, Color band, Color gem){
        WritableRaster raster = img.getRaster();
        int[] bandColor = new int[]{band.getRed(), band.getGreen(), band.getBlue(), band.getAlpha()};
        int[] shadedBandColor = ItemBuilder.shade(bandColor);
        int[] brightenedBandColor = ItemBuilder.brighten(bandColor);
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
        int[] shadedGemColor = new int[]{gem.getRed(), gem.getGreen(), gem.getBlue(), gem.getAlpha()};
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(Utils.pixelColourEquals(pixel, gemRegex)) raster.setPixel(x, y, gemColor);
                else if(Utils.pixelColourEquals(pixel, shadedGemRegex)) raster.setPixel(x, y, shadedGemColor);
            }
        }
        return img;
    }
        
    private String color(int type){
        String ret = word(color);
        String mod = "";
        switch(type){
            case 2: mod = "braided "; break;
            case 3: mod = "transparent ";
        }
        description += "This is a " + mod + word(colorMod) + ret + " coloured ring";
        return ret;
    }

    private String gemColor(int type){
        String ret = word(color);
        switch(type){
            case 1: description += " with a " + word(colorMod) + ret + " coloured gem that softly glistens in the darkness."; break;
            case 2: description += " with " + word(colorMod) + ret + " coloured gems that stud the braids of the ring.";
        }
        return ret;
    }

    public static RingProfile getRandomProfile(Distribution ringDist){
        int type = (int)ringDist.next();
        RingProfile rp = new RingProfile();
        String bandColour = rp.color(type);
        switch(type){
            case 0: case 3: //plain or transparent
                rp.loader = (Serializable & Supplier<ImageIcon>)() -> 
                       new ImageIcon(outfitImage(ItemBuilder.getImage(16*type, 176), getColor(bandColour), null));
                return rp;
            default: //gemmed or braided
                String gemColour = rp.gemColor(type);
                rp.loader = (Serializable & Supplier<ImageIcon>)() -> 
                        new ImageIcon(outfitImage(ItemBuilder.getImage(16*type, 176), getColor(bandColour), getColor(gemColour)));
                return rp;
        }
    }
    
}

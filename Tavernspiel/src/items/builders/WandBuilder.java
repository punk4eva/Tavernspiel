
package items.builders;

import static items.builders.DescriptionBuilder.word;
import static items.builders.ItemBuilder.getColor;
import static logic.ImageUtils.convertToBuffered;
import items.equipment.Wand;
import items.equipment.WandPower;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location;
import logic.Distribution;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class WandBuilder{
    
    private WandBuilder(){}
    
    private static final int[] wood1Regex = new int[]{250,0,250},
            wood2Regex = new int[]{150,0,150},
            gem1Regex = new int[]{250,0,0},
            gem2Regex = new int[]{100,0,0},
            deco1Regex = new int[]{250,250,0},
            deco2Regex = new int[]{150,150,0};
    
    public static final String[] powerNames = {};
    public static final HashMap<String, WandPower> powerMap = new HashMap<>();
    static{
        
    }
    
    private static ImageIcon outfitImage(BufferedImage img, Color woodCol, 
            Color gemCol, Color decoCol1, Color decoCol2){
        WritableRaster raster = img.getRaster();
        int[] wood1 = new int[]{woodCol.getRed(), woodCol.getGreen(), woodCol.getBlue()},
                wood2 = ItemBuilder.shade(wood1), gem1 = 
                new int[]{gemCol.getRed(), gemCol.getGreen(), gemCol.getBlue()},
                gem2 = ItemBuilder.shade(gem1), deco1 = 
                new int[]{decoCol1.getRed(), decoCol1.getGreen(), decoCol1.getBlue()},
                deco2 = new int[]{decoCol2.getRed(), decoCol2.getGreen(), decoCol2.getBlue()};
        int[] pixel = new int[4];
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                pixel = raster.getPixel(x, y, pixel);
                if(Utils.pixelColourEquals(pixel, wood1Regex)) raster.setPixel(x, y, wood1);
                else if(Utils.pixelColourEquals(pixel, wood2Regex)) raster.setPixel(x, y, wood2);
                else if(Utils.pixelColourEquals(pixel, gem1Regex)) raster.setPixel(x, y, gem1);
                else if(Utils.pixelColourEquals(pixel, gem2Regex)) raster.setPixel(x, y, gem2);
                else if(Utils.pixelColourEquals(pixel, deco1Regex)) raster.setPixel(x, y, deco1);
                else if(Utils.pixelColourEquals(pixel, deco2Regex)) raster.setPixel(x, y, deco2);
            }
        }
        return new ImageIcon(img);
    }
    
    public static Object[] getAttackInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This dangerous looking battle wand was crafted to be able to "
            + "release the maximum amount of energy per blast. It has a sharp," + 
            gem + " colored gem and is made of " + wood + ". There are " + deco1
            + " and " + deco2 + " notches on one edge.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 32)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getElongatedInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "The wood of this wand was worked around an especially elongated"
            + " gemstone. It has a long, " + 
            gem + " colored gem and is made of " + wood + ". There are " + deco1
            + " and " + deco2 + " patterns next to the gem.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 16)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getStandardInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This wand looks unusually well crafted. It has a round, " + 
            gem + " colored gem and is made of " + wood + ". There are " + deco1
            + " and " + deco2 + " carvings along the shaft.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 0)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getEyeInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This wand is old: eye-shaped, "+gem+" gem holders are a mark of ancient "
            + "craftsmanship. It is made of " + wood + ". There is a " + deco1
            + " edge and " + deco2 + " grooves along the other side.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 48)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getSkullInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "The skull-shaped, "+gem+" gem of this wand seems like something that could be "
            + "worshipped as an idol. It is made of " + wood + ". There are " + deco1
            + " and " + deco2 + " lines on one edge.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 96)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getSwordInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This wand was built by warriors and has melee "
            + "capabilities. It is designed to be stored in a holster and is"
            + " made of strong " + wood + ", with a "+gem+" colored gem near "
            + "the tip. The sword has a " + deco1 + " and " + deco2 + " colored handle.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 80)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getThinInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This wand is so thin that you can barely see " +
            " it. It looks like a " + wood + " stick with a slender " + gem
            + " gem.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 64)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static Object[] getTwigInfo(Distribution woodDistrib){
        Object ret[] = new Object[2];
        String deco1 = word(DescriptionBuilder.color), 
                deco2 = word(DescriptionBuilder.color),
                gem = word(DescriptionBuilder.color),
                wood = DescriptionBuilder.wood[(int)woodDistrib.next()];
        ret[0] = "This wand is made of "+wood+" twigs that have been braided "
                + "together. It is has a small, "+gem+" gem lodged at the top. "
                + "There are " + deco1 + " and " + deco2 + " markings along the"
                + " length of the wand.";
        ret[1] = (Serializable & Supplier<ImageIcon>)() -> 
            outfitImage(convertToBuffered(ItemBuilder.getIcon(208, 112)), 
                    getColor(wood), getColor(gem), getColor(deco1), getColor(deco2));
        return ret;
    }
    
    public static String getRandomWandType(Distribution wandTypeChance){
        return powerNames[(int)wandTypeChance.next()];
    }
    
    public static Wand getRandomWand(Location loc){
        throw new UnsupportedOperationException();
    }
    
}

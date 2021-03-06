
package enchantments;

import animation.Animation;
import animation.SerialAnimation;
import buffs.Buff;
import creatureLogic.Description;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.ImageHandler;
import logic.ImageUtils;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents enchantments on weapons, armour, and effects on rings
 */
public abstract class Enchantment implements Serializable{
    
    private final static long serialVersionUID = 68907276;
    
    public final String name;
    public Buff buff;
    public final Description description;
    public final EnchantmentAffinity affinity;
    public ImageIcon overlay;
    public double level; //A double from 0 to 1.
    public Distribution action;
    public boolean unremovable = false;
    public boolean isKnownToBeCursed = false;
    protected int hueR1, hueR2, hueG1, hueG2, hueB1, hueB2;
    
    private final static int[] hue1Regex = new int[]{11, 18, 1, 13}, 
        hue2Regex = new int[]{13, 1, 18, 11};
    
    /**
     * The "spiritual type" of enchantment.
     */
    public enum EnchantmentAffinity{
        OFFENSIVE(-1), DEFENSIVE(1), HEALING(2), SACRIFICIAL(-2), FOCUS(-3), MIND(3), NULL(4);
        
        public final int code;
        private EnchantmentAffinity(int i){
            code = i;
        }
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param u Whether the glyph is unremovable (AKA a curse).
     */
    public Enchantment(String s, Description desc, Distribution d, boolean u){
        name = s;
        description = desc;
        action = d;
        unremovable = u;
        affinity = EnchantmentAffinity.NULL;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param l The level.
     */
    public Enchantment(String s, Description desc, Distribution d, double l){
        name = s;
        description = desc;
        action = d;
        level = l;
        affinity = EnchantmentAffinity.NULL;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param l The level.
     * @param u Whether the glyph is unremovable (AKA a curse).
     */
    public Enchantment(String s, Description desc, Distribution d, double l, boolean u){
        name = s;
        description = desc;
        action = d;
        level = l;
        affinity = EnchantmentAffinity.NULL;
        unremovable = u;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param u Whether the glyph is unremovable (AKA a curse).
     * @param aff The affinity of the Enchantment.
     */
    public Enchantment(String s, Description desc, Distribution d, boolean u, EnchantmentAffinity aff){
        name = s;
        description = desc;
        action = d;
        unremovable = u;
        affinity = aff;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param l The level.
     * @param aff The affinity of the Enchantment.
     */
    public Enchantment(String s, Description desc, Distribution d, double l, EnchantmentAffinity aff){
        name = s;
        description = desc;
        action = d;
        level = l;
        affinity = aff;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The action distribution.
     * @param l The level.
     * @param u Whether the glyph is unremovable (AKA a curse).
     * @param aff The affinity of the Enchantment.
     */
    public Enchantment(String s, Description desc, Distribution d, double l, boolean u, EnchantmentAffinity aff){
        name = s;
        description = desc;
        action = d;
        level = l;
        affinity = aff;
        unremovable = u;
    }
    
    /**
     * Updates the Enchantment with a new level.
     * @param lev The new level.
     */
    public abstract void update(int lev);
    
    /**
     * Penalizes the level of this Enchantment randomly.
     * @param context The context of the penalty.
     * @return Whether the level is now "very low" (below 0.1).
     */
    public boolean penalize(EnchantmentAffinity context){
        if(context.code==affinity.code) level *= 0.4+Distribution.R.nextDouble()*0.6;
        else if(context.code+affinity.code==0) level *= 0.1 + 0.75 * Distribution.R.nextDouble();
        else level *= 0.2+Distribution.R.nextDouble()*0.8;
        return level<0.1;
    }
    
    /**
     * Returns a colour representing this Enchantment.
     * @param n The progress of the animation.
     * @return An int array representing the color.
     */
    protected int[] getHue1(double n){
        double mult = n/9.0;
        int R = (int)(((double)hueR2-hueR1)*mult)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*mult)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*mult)+hueB1;
        return new int[]{R, G, B, 128};
    }

    /**
     * Returns a colour representing this Enchantment.
     * @param n The progress of the animation.
     * @return An int array representing the color analogous to getHue1().
     */
    protected int[] getHue2(double n){
        double mult = ((n+5)%10)/9.0;
        int R = (int)(((double)hueR2-hueR1)*mult)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*mult)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*mult)+hueB1;
        return new int[]{R, G, B, 128};
    }
    
    private ImageIcon outfitImage(ImageIcon img, int i){
        BufferedImage ret = ImageUtils.convertToBuffered(img);
        WritableRaster raster = ret.getRaster();
        int[] hue1 = getHue1(i);
        int[] hue2 = getHue2(i);
        int[] pixel = new int[4];
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                pixel = raster.getPixel(x, y, pixel);
                if(Utils.alphaColourEquals(pixel, hue1Regex)) raster.setPixel(x, y, hue1);
                else if(Utils.alphaColourEquals(pixel, hue2Regex)) raster.setPixel(x, y, hue2);
            }
        }
        return new ImageIcon(ret);
    }
    
    /**
     * Builds an Animation with hue change.
     * @param img The Image to overlay.
     * @return The Animation.
     */
    public Animation buildAnimation(ImageIcon img){
        if(overlay==null) overlay = new ImageIcon(ImageUtils.buildOverlay(ImageUtils.convertToBuffered(img)));
        ImageIcon[] icons = new ImageIcon[10];
        for(int n=0;n<10;n++) icons[n] = ImageHandler.combineIcons(img, outfitImage(overlay, n));
        return new SerialAnimation(icons, 10);
    }
    
    /**
     * The standard probability function determining whether the Enchantment
     * should activate or not.
     * @return true if it should activate.
     */
    protected boolean shouldActivate(){
        return 0.45*Math.pow(level, 2.28)+0.05>Distribution.R.nextDouble();
    }
    
}

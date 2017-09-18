
package enchantments;

import creatureLogic.Description;
import gui.MainClass;
import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents enchantments on weapons, armour, and effects on rings
 */
public abstract class Enchantment implements Serializable{
    
    private final static long serialVersionUID = 68907276;
    
    public final String name;
    public final Description description;
    public final EnchantmentAffinity affinity;
    public Image overlay1;
    public Image overlay2;
    public double level; //A double from 0 to 1.
    public Distribution action;
    public boolean unremovable = false;
    public boolean isKnownToBeCursed = false;
    protected int hueR1, hueR2, hueG1, hueG2, hueB1, hueB2;
    
    public enum EnchantmentAffinity{
        OFFENSIVE, DEFENSIVE, HEALING, FOCUS, SACRIFICIAL, MIND, NULL;
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
     * Returns a Color representing the general aura of this glyph.
     * @return the Color.
     */
    public Color getHue1(){
        double progress = MainClass.frameNumber/MainClass.frameDivisor;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*progress)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new Color(R, G, B, 128);
    }
    
    /**
     * Returns a Color representing the general aura of this glyph.
     * @return An analogous Color of Enchantment.getHue1().
     */
    public Color getHue2(){
        double progress = (MainClass.frameDivisor-MainClass.frameNumber)/MainClass.frameDivisor;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*progress)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new Color(R, G, B, 128);
    }
    
}

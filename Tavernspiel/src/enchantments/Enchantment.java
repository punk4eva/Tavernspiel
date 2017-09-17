
package enchantments;

import creatureLogic.Attack.AttackType;
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
public class Enchantment implements Serializable{
    
    private final static long serialVersionUID = 68907276;
    
    public String name;
    public Description description;
    public Image overlay1;
    public Image overlay2;
    public double level = 0.5;
    public Distribution action;
    public AttackType attackType;
    public boolean unremovable = false;
    public boolean isKnownToBeCursed = false;
    protected int hueR1, hueR2, hueG1, hueG2, hueB1, hueB2;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The action distribution.
     */
    public Enchantment(String s, Distribution d){
        name = s;
        action = d;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The action distribution.
     * @param u Whether the glyph is unremovable (AKA a curse).
     */
    public Enchantment(String s, Distribution d, boolean u){
        name = s;
        action = d;
        unremovable = u;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The action distribution.
     * @param l The level.
     */
    public Enchantment(String s, Distribution d, double l){
        name = s;
        action = d;
        level = l;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The action distribution.
     * @param l The level.
     * @param u Whether the glyph is unremovable (AKA a curse).
     */
    public Enchantment(String s, Distribution d, double l, boolean u){
        name = s;
        action = d;
        level = l;
        unremovable = u;
    }
    
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
     * @return The Colormate of Enchantment.getHue1().
     */
    public Color getHue2(){
        double progress = (MainClass.frameDivisor-MainClass.frameNumber)/MainClass.frameDivisor;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*progress)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new Color(R, G, B, 128);
    }
    
}

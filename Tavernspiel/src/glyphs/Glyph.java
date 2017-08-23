
package glyphs;

import gui.MainClass;
import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Glyph implements Serializable{
    
    public String name;
    public Image overlay1;
    public Image overlay2;
    public double level = 0.5;
    public Distribution action;
    public boolean unremovable = false;
    public boolean isKnownToBeCursed = false;
    protected int hueR1, hueR2, hueG1, hueG2, hueB1, hueB2;
    
    public Glyph(String s, Distribution d){
        name = s;
        action = d;
    }
    
    public Glyph(String s, Distribution d, boolean u){
        name = s;
        action = d;
        unremovable = u;
    }
    
    public Glyph(String s, Distribution d, double l){
        name = s;
        action = d;
        level = l;
    }
    
    public Glyph(String s, Distribution d, double l, boolean u){
        name = s;
        action = d;
        level = l;
        unremovable = u;
    }
    
    public Color getHue1(){
        double progress = MainClass.frameNumber/MainClass.frameDivisor;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*progress)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new Color(R, G, B, 128);
    }
    
    public Color getHue2(){
        double progress = (MainClass.frameDivisor-MainClass.frameNumber)/MainClass.frameDivisor;
        int R = (int)(((double)hueR2-hueR1)*progress)+hueR1;
        int G = (int)(((double)hueG2-hueG1)*progress)+hueG1;
        int B = (int)(((double)hueB2-hueB1)*progress)+hueB1;
        return new Color(R, G, B, 128);
    }
    
}

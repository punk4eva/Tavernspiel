
package glyphs;

import logic.Distribution;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 */
public class Glyph implements Fileable{
    
    public String name;
    public double level = 0.5;
    public Distribution action;
    public boolean unremovable = false;
    
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
    
    @Override
    public String toFileString(){
        return "<g>" + name + "," + level + "," + action.toFileString() + "," + 
                unremovable + "</g>";
    }

    public static Glyph getFromFileString(String filestring){
        String profile[] = filestring.substring(3, filestring.length()-4).split(",");
        return new Glyph(profile[0], Distribution.getFromFileString(profile[2]), 
                Double.parseDouble(profile[1]), Boolean.parseBoolean(profile[3]));
    }
    
}

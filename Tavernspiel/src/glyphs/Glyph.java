
package glyphs;

import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Glyph{
    
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
    
    public Glyph(String s, Distribution d, int l){
        name = s;
        action = d;
        level = l;
    }
    
    public Glyph(String s, Distribution d, int l, boolean u){
        name = s;
        action = d;
        level = l;
        unremovable = u;
    }
    
}

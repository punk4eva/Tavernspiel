
package items;

import glyphs.Glyph;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class Apparatus extends Item{
    
    public int durability;
    public int maxDurability;
    public Distribution action;
    public int level = 0;
    public Formula durabilityFormula;
    public Formula actionFormula;
    public Glyph glyph = null;
    public int strength = -1;
    
    public Apparatus(String n, int dur, Distribution a){
        super(n, false);
        durability = dur;
        maxDurability = dur;
        action = a;
    }
    
    public Apparatus(String n, int dur, Distribution a, int st){
        super(n, false);
        durability = dur;
        maxDurability = dur;
        action = a;
        strength = st;
    }
    
}

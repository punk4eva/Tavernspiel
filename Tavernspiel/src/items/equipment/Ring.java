
package items.equipment;

import glyphs.Glyph;
import items.Apparatus;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Ring extends Apparatus{
    
    public Ring(String n, int dur, Distribution a, Glyph g){
        super(n, dur, a);
        glyph = g;
    }
    
}

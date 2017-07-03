
package items.equipment;

import glyphs.Glyph;
import items.Apparatus;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Ring extends Apparatus{
    
    public Ring(String n, ImageIcon ic, int dur, Distribution a, Glyph g){
        super(n, ic, dur, a);
        glyph = g;
    }
    
}

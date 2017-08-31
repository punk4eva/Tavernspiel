
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
    
    public Ring(String n, String desc, ImageIcon ic, int dur, Distribution a, Glyph g){
        super(n, desc, ic, dur, a);
        glyph = g;
        description.type = "amulets";
    }
    
    public Ring(RingProfile rp){
        super(rp.getName(), rp.getDescription(), rp.getImage(), rp.durability, rp.distribution);
        glyph = rp.glyph;
    }
    
}

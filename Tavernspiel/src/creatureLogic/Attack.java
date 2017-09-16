
package creatureLogic;

import creatures.Creature;
import glyphs.Glyph;

/**
 *
 * @author Adam Whittaker
 */
public class Attack{
    
    public final Creature attacker;
    public final int damage;
    public final int accuracy;
    public final Glyph glyph;
    public final boolean usingMagic;
    public final AttackType type;
    
    /**
     * The type of attack.
     */
    public enum AttackType{
        PHYSICAL,FIRE,ELECTRICAL,PSYCHIC,HOLY,DEMONIC
    }
    
    /**
     * Creates a new instance.
     * @param a The attacker.
     * @param d The damage.
     * @param acc The accuracy.
     * @param g The Glyph.
     */
    public Attack(Creature a, int d, int acc, Glyph g){
        attacker = a;
        damage = d;
        accuracy = acc;
        glyph = g;
        type = g!=null&&g.attackType!=null ? g.attackType : AttackType.PHYSICAL;
        usingMagic = false;
    }
    
    /**
     * Creates a new instance (Wands and special).
     * @param a The attacker.
     * @param d The damage.
     * @param acc The accuracy.
     * @param g The Glyph.
     * @param t The type of attack.
     */
    public Attack(Creature a, int d, int acc, Glyph g, AttackType t){
        attacker = a;
        damage = d;
        accuracy = acc * 2;
        glyph = g;
        type = t;
        usingMagic = true;
    }
    
}

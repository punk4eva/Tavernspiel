
package creatureLogic;

import buffs.Injury;
import buffs.Injury.EnBodyPart;
import creatures.Creature;
import enchantments.WeaponEnchantment;
import logic.Distribution.NormalProb;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents any form of damage that a Creature might take.
 */
public class Attack{
    
    public NormalProb damage;
    public final Injury injury; //null if no injury
    public final boolean magic;
    public final AttackType type;
    public String name;
    
    /**
     * The type of attack.
     */
    public enum AttackType{
        PHYSICAL,FIRE,POISON,BLEEDING,ELECTRIC,PSYCHIC,HOLY,DEMONIC
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The name of this Attack.
     * @param inj The Injury.
     */
    public Attack(NormalProb d, String dm, Injury inj){
        damage = d;
        injury = inj;
        type = AttackType.PHYSICAL;
        magic = false;
        name = dm;
        injury.bodyPart = Injury.getRandomBodyPart();
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The name of this Attack.
     * @param t The type of attack.
     * @param inj The Injury.
     */
    public Attack(NormalProb d, String dm, AttackType t, Injury inj){
        damage = d;
        type = t;
        injury = inj;
        magic = false;
        name = dm;
        injury.bodyPart = Injury.getRandomBodyPart();
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The name of this Attack.
     * @param t The type of attack.
     * @param m Whether the Attack is magical.
     * @param b The body part.
     * @param inj The Injury.
     */
    public Attack(NormalProb d, String dm, AttackType t, boolean m, Injury inj, EnBodyPart b){
        damage = d;
        type = t;
        name = dm;
        magic = m;
        injury = inj;
        injury.bodyPart = b;
    }
    
    /**
     * This class represents an attack performed by a Creature.
     */
    public static class CreatureAttack extends Attack{
        
        public final Creature attacker;
        public final NormalProb accuracy;
        public final WeaponEnchantment enchantment;

        /**
         * Creates a new instance.
         * @param c The attacker.S
         * @param dm The name of this Attack.
         * @param acc The accuracy of the attack.
         * @param inj The Injury.
         */
        public CreatureAttack(Creature c, String dm, NormalProb acc, Injury inj){
            super(c.attributes.health.attack, dm, inj);
            attacker = c;
            accuracy = acc;
            enchantment = null;
            injury.bodyPart = Injury.getRandomBodyPart();
        }
        
        /**
         * Creates a new instance.
         * @param c The attacker.
         * @param dm The name of this Attack.
         * @param acc The accuracy of the attack.
         * @param t The type of attack.
         * @param m Whether the Attack is magical.
         * @param b The body part.
         * @param inj The Injury.
         */
        public CreatureAttack(Creature c, String dm, NormalProb acc, AttackType t, boolean m, Injury inj, EnBodyPart b){
            super(c.attributes.health.attack, dm, t, m, inj, b);
            attacker = c;
            accuracy = acc;
            enchantment = null;
        }
        
        /**
         * Creates a new instance.
         * @param c The attacker.
         * @param dm The name of this Attack.
         * @param acc The accuracy of the attack.
         * @param w The Enchantment on the weapon.
         * @param b The body part.
         * @param inj The Injury.
         */
        public CreatureAttack(Creature c, String dm, NormalProb acc, WeaponEnchantment w, Injury inj, EnBodyPart b){
            super(c.attributes.health.attack, dm, w.attackType, inj);
            attacker = c;
            injury.bodyPart = b;
            accuracy = acc;
            enchantment = w;
        }
        
    }
    
}

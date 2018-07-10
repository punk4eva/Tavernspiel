
package creatureLogic;

import creatures.Creature;
import enchantments.WeaponEnchantment;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents any form of damage that a Creature might take.
 */
public class Attack{
    
    public double damage;
    public final boolean magic;
    public final AttackType type;
    public String deathMessage;
    
    /**
     * The type of attack.
     */
    public enum AttackType{
        PHYSICAL,FIRE,ELECTRIC,PSYCHIC,HOLY,DEMONIC
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The message to display if the Hero is killed by this Attack.
     */
    public Attack(double d, String dm){
        damage = d;
        type = AttackType.PHYSICAL;
        magic = false;
        deathMessage = dm;
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The message to display if the Hero is killed by this Attack.
     * @param t The type of attack.
     */
    public Attack(double d, String dm, AttackType t){
        damage = d;
        type = t;
        magic = false;
        deathMessage = dm;
    }
    
    /**
     * Creates a new instance.
     * @param d The damage.
     * @param dm The message to display if the Hero is killed by this Attack.
     * @param t The type of attack.
     * @param m Whether the Attack is magical.
     */
    public Attack(double d, String dm, AttackType t, boolean m){
        damage = d;
        type = t;
        deathMessage = dm;
        magic = m;
    }
    
    /**
     * This class represents an attack performed by a Creature.
     */
    public static class CreatureAttack extends Attack{
        
        public final Creature attacker;
        public final double accuracy;
        public final WeaponEnchantment enchantment;

        /**
         * Creates a new instance.
         * @param c The attacker.
         * @param d The damage.
         * @param dm The message to display if the Hero is killed by this Attack.
         * @param acc The accuracy of the attack.
         */
        public CreatureAttack(Creature c, String dm, double d, double acc){
            super(d, dm);
            attacker = c;
            accuracy = acc;
            enchantment = null;
        }
        
        /**
         * Creates a new instance.
         * @param c The attacker.
         * @param d The damage.
         * @param dm The message to display if the Hero is killed by this Attack.
         * @param acc The accuracy of the attack.
         * @param t The type of attack.
         * @param m Whether the Attack is magical.
         */
        public CreatureAttack(Creature c, String dm, double d, double acc, AttackType t, boolean m){
            super(d, dm, t, m);
            attacker = c;
            accuracy = acc;
            if(magic) acc *= 2;
            enchantment = null;
        }
        
        /**
         * Creates a new instance.
         * @param c The attacker.
         * @param d The damage.
         * @param dm The message to display if the Hero is killed by this Attack.
         * @param acc The accuracy of the attack.
         * @param w The Enchantment on the weapon.
         */
        public CreatureAttack(Creature c, String dm, double d, double acc, WeaponEnchantment w){
            super(d, dm, w.attackType);
            attacker = c;
            accuracy = acc;
            enchantment = w;
        }
        
    }
    
}

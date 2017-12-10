
package items;

import animation.Animation;
import animation.StillAnimation;
import creatureLogic.Description;
import enchantments.Enchantment;
import gui.MainClass;
import items.equipment.Artifact;
import items.equipment.HeldWeapon;
import items.equipment.MeleeWeapon;
import items.equipment.Ring;
import javax.swing.ImageIcon;
import level.Location;
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
    public Formula[] actionFormulas;
    public Formula strengthFormula = null; //null if no strength required.
    public Enchantment enchantment;
    public int strength = -1;
    public int usesTillIdentify = 20;
    private final ImageIcon imageWithoutEnchantment;
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     */
    public Apparatus(String n, String desc, ImageIcon i, int dur, Distribution a){
        super(n, desc, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
        imageWithoutEnchantment = i;
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     * @param st The strength requirement.
     */
    public Apparatus(String n, String desc, ImageIcon i, int dur, Distribution a, int st){
        super(n, desc, i, false);
        durability = dur;
        imageWithoutEnchantment = i;
        maxDurability = dur;
        action = a;
        strength = st;
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     * @param st The strength requirement.
     */
    public Apparatus(String n, Description desc, ImageIcon i, int dur, Distribution a, int st){
        super(n, desc, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
        imageWithoutEnchantment = i;
        strength = st;
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     */
    public Apparatus(String n, Description desc, ImageIcon i, int dur, Distribution a){
        super(n, desc, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
        imageWithoutEnchantment = i;
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Animation of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     */
    public Apparatus(String n, Description desc, Animation i, int dur, Distribution a, int st){
        super(n, desc, i, false);
        durability = dur;
        strength = st;
        maxDurability = dur;
        action = a;
        imageWithoutEnchantment = i.frames[0];
    }
    
    /**
     * Updates fields.
     */
    public void updateFields(){
        maxDurability = durabilityFormula.getInt(level);
        if(strengthFormula!=null) strength = strengthFormula.getInt(level);
        action.updateFromFormula(level, actionFormulas);
    }
    
    /**
     * Upgrades this Apparatus.
     */
    public void upgrade(){
        level++;
        updateFields();
        durability = maxDurability;
        if(enchantment!=null && !(this instanceof Ring || this instanceof Artifact) && (level>11 || Distribution.chance(1, 12-level))){
            enchantment = null;
            MainClass.messageQueue.add("<html color=\"orange\">Interaction of different types of magic has erased the " + 
                    (this instanceof HeldWeapon ? "enchantment on your weapon!" : "glyph on your armour!"));
        }
    }
    
    /**
     * Gets the next double from the Distribution associated with this Apparatus.
     * @return A double from the Distribution.
     */
    public double nextAction(){
        return action.next();
    }
    
    /**
     * Gets the next int from the Distribution associated with this Apparatus.
     * @return An int from the Distribution.
     */
    public int nextIntAction(){
        return action.nextInt();
    }
    
    public void changeEnchantment(Enchantment ench){
        enchantment = ench;
        if(ench==null) animation = new StillAnimation(imageWithoutEnchantment);
        else animation = enchantment.buildAnimation(imageWithoutEnchantment);
    }
    
    public static ItemAction[] standardActions(int length, Item i){
        ItemAction[] ret = ItemAction.getArray(length, i);
        ret[2] = new ItemAction("EQUIP", i);
        return ret;
    }
    
    @Override
    public String toString(int level){
        if(level==0) return getClass().toString().substring(
                getClass().toString().lastIndexOf("."));
        String ret = name;
        switch(level){
            case 2:
                try{
                    return enchantment.unremovable ?
                            ("cursed " + ret) : ("enchanted " + ret);
                }catch(NullPointerException e){
                    //do nothing
                }
            case 3:
                try{
                    return enchantment.unremovable ?
                            ("cursed " + ret + " of " + enchantment.name) :
                            (ret + " of " + enchantment.name);
                }catch(NullPointerException e){
                    //do nothing
                }
            case 4:
                String add = this.level==0 ? "" : this.level<0 ? ""+this.level
                            : "+"+this.level;
                try{
                    return enchantment.unremovable ?
                            ("cursed " + ret + add + " of " +
                            enchantment.name) :
                            (ret + this.level + " of " +
                            enchantment.name);
                }catch(NullPointerException e){
                    return ret + add;
                }
            default:
                break;
        }
        return ret;
    }
    
    public static Apparatus getRandomArmour(int depth, Location loc){
        switch(Distribution.r.nextInt(4)){
            case 0: //helmet
            case 1: //chest
            case 2: //legs
            case 3: //boots
        }
        throw new UnsupportedOperationException("Not Supported Yet!");
    }
    
    public static HeldWeapon getRandomMeleeWeapon(int depth, Location loc){
        return new MeleeWeapon(loc.getWeaponEntry());
    }
    
}

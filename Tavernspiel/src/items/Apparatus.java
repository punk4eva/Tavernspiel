
package items;

import animation.Animation;
import animation.StillAnimation;
import creatureLogic.Description;
import enchantments.Enchantment;
import gui.mainToolbox.Main;
import items.equipment.Artifact;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Helmet;
import items.equipment.Leggings;
import items.equipment.MeleeWeapon;
import items.equipment.Ring;
import javax.swing.ImageIcon;
import level.Location;
import logic.Distribution;
import logic.Formula;
import logic.Utils.Unfinished;

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
    public double strength = -1;
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
     * @param st The strength requirement.
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
        maxDurability = (int)durabilityFormula.get(level);
        if(strengthFormula!=null) strength = (int)strengthFormula.get(level);
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
            Main.addMessage("<html color=\"orange\">Interaction of different types of magic has erased the " + 
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
    
    /**
     * Changes the Enchantment of this Apparatus.
     * @param ench
     */
    public void changeEnchantment(Enchantment ench){
        enchantment = ench;
        if(ench==null) animation = new StillAnimation(imageWithoutEnchantment);
        else animation = enchantment.buildAnimation(imageWithoutEnchantment);
    }
    
    /**
     *  Sets the ItemActions to unequipped status.
     */
    public void setToUnequipped(){
        actions[2] = new ItemAction("EQUIP", this);
    }
    
    /**
     * Sets the ItemActions to equipped status.
     */
    public void setToEquipped(){
        actions[2] = new ItemAction("UNEQUIP", this);
    }
    
    /**
     * Creates an ItemAction array for Apparatus with the given length.
     * @param length
     * @param i The Item.
     * @return The ItemAction array.
     */
    public static ItemAction[] standardActions(int length, Apparatus i){
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
    
    /**
     * Generates a random armor Object.
     * @param depth The depth
     * @param loc The Location
     * @return
     */
    @Unfinished("Flesh out generation algorithm using depth and hero")
    public static Apparatus getRandomArmour(int depth, Location loc){
        switch(Distribution.r.nextInt(4)){
            case 0: return Helmet.getArmour(loc.getArmourType());
            case 1: return Chestplate.getArmour(loc.getArmourType());
            case 2: return Leggings.getArmour(loc.getArmourType());
            default: return Boots.getArmour(loc.getArmourType());
        }
    }
    
    /**
     * Generates a random melee weapon Object.
     * @param depth The depth
     * @param loc The Location
     * @return
     */
    @Unfinished("Flesh out generation algorithm using depth and hero")
    public static HeldWeapon getRandomMeleeWeapon(int depth, Location loc){
        return new MeleeWeapon(loc.getWeaponEntry());
    }
    
}

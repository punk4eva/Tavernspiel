
package items;

import animation.LoadableStillAnimation;
import containers.Equipment;
import creatureLogic.Description;
import enchantments.Enchantment;
import enchantments.Enchantment.EnchantmentAffinity;
import gui.mainToolbox.Main;
import items.actions.ItemAction;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Helmet;
import items.equipment.Leggings;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location;
import logic.ConstantFields;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Apparatus extends Item{
    
    private final static long serialVersionUID = 308217;
    
    protected Equipment equipment;
    public int durability;
    public int maxDurability;
    public double level = 0;
    public Enchantment enchantment;
    public double strength = -1;
    public int usesTillIdentify = 10 + Distribution.r.nextInt(21);
    private final Supplier<ImageIcon> imageLoader;
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param st The strength requirement.
     */
    public Apparatus(String n, Description desc, Supplier<ImageIcon> lo, int dur, int st){
        super(n, desc, lo, false);
        durability = dur;
        maxDurability = dur;
        imageLoader = lo;
        strength = st;
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo The Image of the Item.
     * @param dur The durability of the Apparatus.
     */
    public Apparatus(String n, Description desc, Supplier<ImageIcon> lo, int dur){
        super(n, desc, lo, false);
        durability = dur;
        maxDurability = dur;
        imageLoader = lo;
    }
    
    public void use(){
        if(usesTillIdentify>0){
            usesTillIdentify--;
            if(usesTillIdentify==0) identify();
        }
        if(durability<=0) breakDown();
        else durability--;
    }
    
    public void breakDown(){
        Main.addMessage(ConstantFields.badColor, "Your " + name + " has broken.");
        equipment.plop(this);
    }
    
    /**
     * Upgrades this Apparatus.
     */
    public abstract void upgrade();
    
    public void testEnchantment(){
        if(enchantment!=null && (level>11 || Distribution.chance(1, 12-level))){
            if(enchantment.penalize(EnchantmentAffinity.OFFENSIVE)){
                enchantment = null;
                Main.addMessage(ConstantFields.badColor, "The upgrade has erased "
                        + "the enchantment on this " + name + ".");
            }
        }
    }
    
    /**
     * Changes the Enchantment of this Apparatus.
     * @param ench
     */
    public void changeEnchantment(Enchantment ench){
        enchantment = ench;
        if(ench==null) animation = new LoadableStillAnimation(imageLoader);
        else animation = enchantment.buildAnimation(imageLoader.get());
    }
    
    /**
     *  Sets the ItemActions to unequipped status.
     */
    public void setToUnequipped(){
        actions[2] = ItemAction.EQUIP;
        equipment = null;
    }
    
    /**
     * Sets the ItemActions to equipped status.
     * @param eq
     */
    public void setToEquipped(Equipment eq){
        actions[2] = ItemAction.UNEQUIP;
        equipment = eq;
    }
    
    @Override
    public void identify(){
        super.identify();
        usesTillIdentify = 0;
    }
    
    
    
    /**
     * Creates an ItemAction array for Apparatus with the given length.
     * @param length
     * @return The ItemAction array.
     */
    public static ItemAction[] standardActions(int length){
        ItemAction[] ret = ItemAction.getArray(length);
        ret[2] = ItemAction.EQUIP;
        return ret;
    }
    
    /**
     * Generates a random armor Object.
     * @param armorDist
     * @return
     */
    @Unfinished("Flesh out generation algorithm using depth and hero")
    public static Apparatus getRandomArmour(Distribution armorDist){
        switch(Distribution.r.nextInt(4)){
            case 0: return Helmet.getArmour(Equipment.getArmourType(armorDist));
            case 1: return Chestplate.getArmour(Equipment.getArmourType(armorDist));
            case 2: return Leggings.getArmour(Equipment.getArmourType(armorDist));
            default: return Boots.getArmour(Equipment.getArmourType(armorDist));
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
        return loc.getRandomWeapon();
    }
    
}

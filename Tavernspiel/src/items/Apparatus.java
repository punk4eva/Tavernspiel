
package items;

import creatureLogic.Description;
import glyphs.Glyph;
import gui.MainClass;
import items.equipment.Artifact;
import items.equipment.HeldWeapon;
import items.equipment.Ring;
import java.awt.Graphics;
import javax.swing.ImageIcon;
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
    public Glyph glyph = null;
    public int strength = -1;
    public int usesTillIdentify = 20;
    
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
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Image of the Item.
     * @param dur The durability of the Apparatus.
     * @param a The Distribution associated with this Apparatus.
     */
    public Apparatus(String n, String desc, ImageIcon i, int dur, Distribution a, int st){
        super(n, desc, i, false);
        durability = dur;
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
     */
    public Apparatus(String n, Description desc, ImageIcon i, int dur, Distribution a, int st){
        super(n, desc, i, false);
        durability = dur;
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
     */
    public Apparatus(String n, Description desc, ImageIcon i, int dur, Distribution a){
        super(n, desc, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
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
        if(glyph!=null && !(this instanceof Ring || this instanceof Artifact) && (level>11 || Distribution.chance(1, 12-level))){
            glyph = null;
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
    
    @Override
    public String toString(int level){
        if(level==0) return getClass().toString().substring(
                getClass().toString().lastIndexOf("."));
        String ret = name;
        switch(level){
            case 2:
                try{
                    return glyph.unremovable ?
                            ("cursed " + ret) : ("enchanted " + ret);
                }catch(NullPointerException e){
                    //do nothing
                }
            case 3:
                try{
                    return glyph.unremovable ?
                            ("cursed " + ret + " of " + glyph.name) :
                            (ret + " of " + glyph.name);
                }catch(NullPointerException e){
                    //do nothing
                }
            case 4:
                String add = this.level==0 ? "" : this.level<0 ? ""+this.level
                            : "+"+this.level;
                try{
                    return glyph.unremovable ?
                            ("cursed " + ret + add + " of " +
                            glyph.name) :
                            (ret + this.level + " of " +
                            glyph.name);
                }catch(NullPointerException e){
                    return ret + add;
                }
            default:
                break;
        }
        return ret;
    }

    @Override
    @Unfinished("Animate glyph")
    public void draw(Graphics g, int x, int y){
        g.drawImage(icon.getImage(), x, y, null);
        if(glyph!=null && (!glyph.unremovable || glyph.isKnownToBeCursed)){
            
        }
    }
    
}


package items;

import glyphs.Glyph;
import gui.MainClass;
import items.equipment.Artifact;
import items.equipment.HeldWeapon;
import items.equipment.Ring;
import javax.swing.ImageIcon;
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
    public Glyph glyph = null;
    public int strength = -1;
    
    public Apparatus(String n, ImageIcon i, int dur, Distribution a){
        super(n, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
    }
    
    public Apparatus(String n, ImageIcon i, int dur, Distribution a, int st){
        super(n, i, false);
        durability = dur;
        maxDurability = dur;
        action = a;
        strength = st;
    }
    
    
    public void updateFields(){
        maxDurability = durabilityFormula.getInt(level);
        if(strengthFormula!=null) strength = strengthFormula.getInt(level);
        action.updateFromFormula(level, actionFormulas);
    }
    
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
    
    
    @Override
    public String toFileString(){
        return "<<" + name + "," + durability + "," + level + "," + 
                glyph.toFileString() + ">>";
    }

    public static Apparatus getFromFileString(String filestring){
        String[] profile = filestring.substring(2, filestring.length()-2).split(",");
        Apparatus app = (Apparatus) ItemBuilder.get(profile[0]);
        app.durability = Integer.parseInt(profile[1]);
        app.level = Integer.parseInt(profile[2]);
        app.glyph = Glyph.getFromFileString(profile[3]);
        app.updateFields();
        return app;
    }
    
    public double nextAction(){
        return action.next();
    }
    
    public int nextIntAction(){
        return action.nextInt();
    }
    
}

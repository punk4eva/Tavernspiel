
package items;

import animation.Animation;
import animation.LoadableStillAnimation;
import creatureLogic.Description;
import creatures.Creature;
import items.actions.ItemAction;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Base class which handles items.
 */
public class Item implements Serializable{
    
    private final static long serialVersionUID = 2272891313459L;
    
    public Animation animation;
    public final String name;
    public final Description description;
    public int quantity = 1;
    public boolean stackable = true;
    public boolean flammable = false;
    public ItemAction actions[];
    protected Boolean cursed;
    protected boolean identified;
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param x
     * @param y
     */
    public Item(String n, String desc, int x, int y){
        name = n;
        description = Description.parseDescription(null, desc);
        animation = new LoadableStillAnimation((Serializable & Supplier<ImageIcon>)
                () -> ItemBuilder.getIcon(x, y));
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param lo
     * @param q The quantity
     */
    public Item(String n, String desc, Supplier<ImageIcon> lo, int q){
        name = n;
        description = Description.parseDescription(null, desc);
        animation = new LoadableStillAnimation(lo);
        quantity = q;
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param lo
     * @param q The quantity
     * @param flam Whether the Item is flammable.
     */
    public Item(String n, String desc, Supplier<ImageIcon> lo, int q, boolean flam){
        name = n;
        description = Description.parseDescription(null, desc);
        animation = new LoadableStillAnimation(lo);
        quantity = q;
        flammable = flam;
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param lo
     * @param st Whether the Item is stackable.
     */
    public Item(String n, String desc, Supplier<ImageIcon> lo, boolean st){
        name = n;
        description = Description.parseDescription(null, desc);
        animation = new LoadableStillAnimation(lo);
        stackable = st;
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param lo
     * @param s Whether the Item is stackable.
     */
    public Item(String n, Description desc, Supplier<ImageIcon> lo, boolean s){
        name = n;
        description = desc;
        animation = new LoadableStillAnimation(lo);
        stackable = s;
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param i The ImageIcon
     * @param s Whether the Item is stackable.
     */
    public Item(String n, Description desc, Animation i, boolean s){
        name = n;
        description = desc;
        animation = i;
        stackable = s;
    }
    
    /**
     * Creates an Instance.
     * @param n The name
     * @param desc The description
     * @param lo
     * @param q The quantity
     */
    public Item(String n, Description desc, Supplier<ImageIcon> lo, int q){
        name = n;
        description = desc;
        animation = new LoadableStillAnimation(lo);
        quantity = q;
    }
    
    /**
     * Gets a pronouned representation of this Item.
     * @param level The level of detail.
     * @param definite The pronoun.
     * @return A String
     */
    public String getPronounedName(int level, boolean definite){
        if(definite) return "the " + toString(level);
        else{
            String ret = toString(level);
            if(ret.charAt(0)=='y'||ret.charAt(0)=='e'||ret.charAt(0)=='u'||
                    ret.charAt(0)=='a'||ret.charAt(0)=='o'||ret.charAt(0)=='i')
                return "an " + ret;
            return "a " + ret;
        }
    }
    
    /**
     * Gets a pronouned representation of this Item.
     * @param level The level of detail.
     * @return A String
     */
    public String toString(int level){
        if(level==0) return getClass().toString().substring(
                getClass().toString().lastIndexOf("."));
        String ret = name;
        switch(level){
            case 2:
                try{
                    return ((Apparatus) this).enchantment.unremovable ?
                            ("cursed " + ret) : ("enchanted " + ret);
                }catch(ClassCastException | NullPointerException e){
                    //do nothing
                }
            case 3:
                try{
                    return ((Apparatus) this).enchantment.unremovable ?
                            ("cursed " + ret + " of " + ((Apparatus) this).enchantment.name) :
                            (ret + " of " + ((Apparatus) this).enchantment.name);
                }catch(ClassCastException | NullPointerException e){
                    //do nothing
                }
            case 4:
                String add = ((Apparatus) this).level==0 ? "" : ((Apparatus) this).level<0 ? ""+((Apparatus) this).level
                            : "+"+((Apparatus) this).level;
                try{
                    return ((Apparatus) this).enchantment.unremovable ?
                            ("cursed " + ret + add + " of " +
                            ((Apparatus) this).enchantment.name) :
                            (ret + ((Apparatus) this).level + " of " +
                            ((Apparatus) this).enchantment.name);
                }catch(NullPointerException e){
                    return ret + add;
                }
        }
        return ret;
    }
    
    /**
     * Checks whether something has been identified by the given Creature.
     * @param c The Creature to compare.
     * @return True if it has, false if not.
     */
    @Unfinished
    public boolean isIdentified(Creature c){
        if(!identified){
            if(this instanceof Apparatus && ((Apparatus) this).usesTillIdentify!=0){
                identified = false;
                return false;
            }
            if(c.attributes.ai.intelligence>6){ //@unfinished
                identified = false;
                return false;
            }
            identified = true;
        }
        return true;
    }
    
    /**
     * Checks if this Item is cursed.
     * @return True if it is, false if not.
     */
    public boolean hasKnownCurse(){
        if(cursed==null || !cursed){
            boolean b = this instanceof Apparatus && 
                ((Apparatus)this).enchantment!=null &&
                ((Apparatus)this).enchantment.isKnownToBeCursed;
            cursed = b;
            return b;
        }
        return true;
    }
    
    /**
     * Checks if the Item is identified by the hero.
     * @return True if it is, false if not.
     */
    public boolean isIdentified(){
        return identified;
    }
    
    public void identify(){
        identified = true;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
}

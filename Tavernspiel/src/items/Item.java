
package items;

import creatures.Creature;
import creatures.Hero;
import items.consumables.Potion;
import items.consumables.Scroll;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * Base class which handles items.
 */
public class Item implements Serializable{
    
    public Image icon;
    public final String name;
    public final String description;
    public int quantity = 1;
    public boolean stackable = true;
    public boolean flammable = false;
    public ItemAction actions[];
    private Boolean cursed, identified;
    
    public Item(String n, String desc, ImageIcon i){
        name = n;
        description = desc;
        icon = i.getImage();
    }
    
    public Item(String n, String desc, ImageIcon i, int q){
        name = n;
        description = desc;
        icon = i.getImage();
        quantity = q;
    }
    
    public Item(String n, String desc, ImageIcon i, int q, boolean flam){
        name = n;
        description = desc;
        icon = i.getImage();
        quantity = q;
        flammable = flam;
    }
    
    public Item(String n, String desc, ImageIcon i, boolean s){
        name = n;
        description = desc;
        icon = i.getImage();
        stackable = s;
    }
    
    public String getPronounedName(int level, String pro){
        if(pro.equals("the")) return "the " + toString(level);
        else{
            String ret = toString(level);
            if(ret.charAt(0)=='y'||ret.charAt(0)=='e'||ret.charAt(0)=='u'||
                    ret.charAt(0)=='a'||ret.charAt(0)=='o'||ret.charAt(0)=='i')
                return "an " + ret;
            return "a " + ret;
        }
    }
    
    public String toString(int level){
        if(level==0) return getClass().toString().substring(
                getClass().toString().lastIndexOf("."));
        String ret = name;
        switch(level){
            case 2:
                try{
                    return ((Apparatus) this).glyph.unremovable ?
                            ("cursed " + ret) : ("enchanted " + ret);
                }catch(ClassCastException | NullPointerException e){
                    //do nothing
                }
            case 3:
                try{
                    return ((Apparatus) this).glyph.unremovable ?
                            ("cursed " + ret + " of " + ((Apparatus) this).glyph.name) :
                            (ret + " of " + ((Apparatus) this).glyph.name);
                }catch(ClassCastException | NullPointerException e){
                    //do nothing
                }
            case 4:
                String add = ((Apparatus) this).level==0 ? "" : ((Apparatus) this).level<0 ? ""+((Apparatus) this).level
                            : "+"+((Apparatus) this).level;
                try{
                    return ((Apparatus) this).glyph.unremovable ?
                            ("cursed " + ret + add + " of " +
                            ((Apparatus) this).glyph.name) :
                            (ret + ((Apparatus) this).level + " of " +
                            ((Apparatus) this).glyph.name);
                }catch(NullPointerException e){
                    return ret + add;
                }
            default:
                break;
        }
        return ret;
    }
    
    public boolean isIdentified(Creature c){
        if(identified==null || !identified){
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
    
    public boolean hasKnownCurse(){
        if(cursed==null || !cursed){
            boolean b = this instanceof Apparatus && 
                ((Apparatus)this).glyph!=null &&
                ((Apparatus)this).glyph.isKnownToBeCursed;
            cursed = b;
            return b;
        }
        return true;
    }
    
    public boolean isIdentified(Hero h){
        if(identified==null || !identified){
            if(this instanceof Apparatus && ((Apparatus) this).usesTillIdentify!=0){
                identified = false;
                return false;
            }
            if(this instanceof Scroll && h.data.scrollsToIdentify.contains((Scroll)this)){
                identified = false;
                return false;
            }
            if(this instanceof Potion && h.data.potionsToIdentify.contains((Potion)this)){
                identified = false;
                return false;
            }
            identified = true;
        }
        return true;
    }
    
}

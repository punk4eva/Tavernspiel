
package items;

import gui.MainClass;
import java.awt.Image;
import javax.swing.ImageIcon;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * Base class which handles items.
 */
public class Item implements Fileable{
    
    public String name;
    public String description;
    public int ID;
    public Image icon;
    public int quantity = 1;
    public boolean stackable = true;
    public boolean flammable = false;
    public ItemAction actions[] = ItemAction.getDefaultActions();
    
    public Item(String n, ImageIcon i){
        name = n;
        icon = i.getImage();
        ID = MainClass.idhandler.genID();
    }
    
    public Item(String n, ImageIcon i, int q){
        name = n;
        icon = i.getImage();
        quantity = q;
        ID = MainClass.idhandler.genID();
    }
    
    public Item(String n, ImageIcon i, int q, boolean flam){
        name = n;
        icon = i.getImage();
        quantity = q;
        flammable = flam;
        ID = MainClass.idhandler.genID();
    }
    
    public Item(String n, ImageIcon i, boolean s){
        name = n;
        icon = i.getImage();
        stackable = s;
        ID = MainClass.idhandler.genID();
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
                try{
                    return ((Apparatus) this).glyph.unremovable ?
                            ("cursed " + ret + ((Apparatus) this).level + " of " +
                            ((Apparatus) this).glyph.name) :
                            (ret + ((Apparatus) this).level + " of " +
                            ((Apparatus) this).glyph.name);
                }catch(ClassCastException | NullPointerException e){
                    //do nothing
                }
            default:
                break;
        }
        return ret;
    }

    @Override
    public String toFileString(){
        return "<<" + name + ">>";
    }

    @Override
    public Item getFromFileString(String filestring){
        return ItemBuilder.get(filestring.substring(2, filestring.length()-2));
    }
    
}


package containers;

import creatures.Hero;
import dialogues.UnequipAmuletDialogue;
import gui.MainClass;
import items.Apparatus;
import items.equipment.Helmet;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Leggings;
import java.awt.Graphics;
import java.util.ArrayList;
import logic.ConstantFields;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public class Equipment extends Receptacle{
    
    public Equipment(){
        super(7, "ERROR: You shouldn't be reading this.", -1, -1);
        for(int n=0;n<7;n++) items.add(null);
    }
    
    public Equipment(ArrayList<Apparatus> i){
        super(7, i, "ERROR: You shouldn't be reading this.", -1, -1);
        while(items.size() < 7) items.add(null);
    }
    
    public HeldWeapon getWeapon(){
        return (HeldWeapon) items.get(0);
    }
    
    public Apparatus getAmulet1(){
        return (Apparatus) items.get(1);
    }
    
    public Apparatus getAmulet2(){
        return (Apparatus) items.get(2);
    }
    
    public Helmet getHelmet(){
        return (Helmet) items.get(3);
    }
    
    public Chestplate getChestplate(){
        return (Chestplate) items.get(4);
    }
    
    public Leggings getLeggings(){
        return (Leggings) items.get(5);
    }
    
    public Boots getBoots(){
        return (Boots) items.get(6);
    }
    
    public <T> T get(T ignore, int index){
        return (T) items.get(index);
    }
    
    @Override
    public String toFileString(){
        String ret = "{" + ID + "," + description + "," + x + "," + y +"|";
        return items.stream().map((item) -> item.toFileString()).reduce(ret, String::concat) + "}";
    }
    
    public static <T extends Receptacle> T getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not finished.");
    }
    
    public int strengthDifference(int strength){
        int ret = 0;
        for(int n=3;n<7;n++){
            if(items.get(n)!=null) ret += (strength - ((Apparatus) items.get(n)).strength);
        }
        return ret;
    }
    
    public Apparatus equip(MainClass main, Apparatus app, int... choiceOfAmulet){
        if(app instanceof HeldWeapon){
            HeldWeapon ret = (HeldWeapon) items.remove(0);
            items.add(0, app);
            return ret;
        }else if(app instanceof Helmet){
            Helmet ret = (Helmet) items.remove(3);
            items.add(3, app);
            return ret;
        }else if(app instanceof Chestplate){
            Chestplate ret = (Chestplate) items.remove(4);
            items.add(4, app);
            return ret;
        }else if(app instanceof Leggings){
            Leggings ret = (Leggings) items.remove(5);
            items.add(5, app);
            return ret;
        }else if(app instanceof Boots){
            Boots ret = (Boots) items.remove(6);
            items.add(6, app);
            return ret;
        }else if(items.get(1)==null){
            items.add(1, app);
            return null;
        }else if(items.get(2)==null){
            items.add(2, app);
            return null;
        }
        int choice;
        if(choiceOfAmulet.length==0)
            choice = 1 + new UnequipAmuletDialogue(items.get(1), items.get(2)).next(main);
        else choice = choiceOfAmulet[0];
        Apparatus ret = (Apparatus) items.remove(choice);
        items.add(choice, app);
        return ret;
    }

    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding, Hero owner){
        if(items.get(0)!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, items.get(0), owner);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, ConstantFields.weaponOutline);
        if(items.get(3)!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(3), owner);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.helmetOutline);
        if(items.get(4)!=null) ImageUtils.paintItemSquare(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(4), owner);
        else ImageUtils.paintOutline(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.chestplateOutline);
        if(items.get(5)!=null) ImageUtils.paintItemSquare(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(5), owner);
        else ImageUtils.paintOutline(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.leggingsOutline);
        if(items.get(6)!=null) ImageUtils.paintItemSquare(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(6), owner);
        else ImageUtils.paintOutline(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.bootsOutline);
        
        if(items.get(1)!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, items.get(1), owner);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
        if(items.get(2)!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, items.get(2), owner);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
    }
    
}

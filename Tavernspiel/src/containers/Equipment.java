
package containers;

import creatures.Hero;
import dialogues.UnequipAmuletDialogue;
import enchantments.WeaponEnchantment;
import gui.MainClass;
import gui.Screen;
import items.Apparatus;
import items.equipment.Helmet;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Leggings;
import items.equipment.MeleeWeapon;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.Distribution;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the collection of equipment that a creature has.
 */
public class Equipment extends Receptacle{
    
    public final List<Screen> screens;
    private final Hero heroOwner;
    
    /**
     * Creates a new instance
     */
    public Equipment(){
        super(7, "ERROR: You shouldn't be reading this.", -1, -1);
        for(int n=0;n<7;n++) items.add(null);
        screens = getScreens(null);
        heroOwner = null;
    }
    
    /**
     * Creates a new instance
     * @param hero The owner.
     */
    public Equipment(Hero hero){
        super(7, "ERROR: You shouldn't be reading this.", -1, -1);
        for(int n=0;n<7;n++) items.add(null);
        screens = getScreens(hero.getScreenListener());
        heroOwner = hero;
    }
    
    /**
     * Returns the weapon if it exists, null otherwise.
     * @return
     */
    public HeldWeapon getWeapon(){
        return (HeldWeapon) items.get(0);
    }
    
    /**
     * Returns the first amulet if it exists, null otherwise.
     * @return
     */
    public Apparatus getAmulet1(){
        return (Apparatus) items.get(1);
    }
    
    /**
     * Returns the second amulet if it exists, null otherwise.
     * @return
     */
    public Apparatus getAmulet2(){
        return (Apparatus) items.get(2);
    }
    
    /**
     * Returns the helmet if it exists, null otherwise.
     * @return
     */
    public Helmet getHelmet(){
        return (Helmet) items.get(3);
    }
    
    /**
     * Returns the chestplate if it exists, null otherwise.
     * @return
     */
    public Chestplate getChestplate(){
        return (Chestplate) items.get(4);
    }
    
    /**
     * Returns the leggings if it exists, null otherwise.
     * @return
     */
    public Leggings getLeggings(){
        return (Leggings) items.get(5);
    }
    
    /**
     * Returns the boots if it exists, null otherwise.
     * @return
     */
    public Boots getBoots(){
        return (Boots) items.get(6);
    }
    
    public WeaponEnchantment getWeaponEnchantment(){
        return (WeaponEnchantment) ((HeldWeapon) items.get(0)).enchantment;
    }
    
    public int nextHit(int strength){
        try{
            return ((HeldWeapon) items.get(0)).action.nextInt();
        }catch(Exception e){
            return Distribution.r.nextInt(strength);
        }
    }
    
    public double getWeaponAccuracy(){
        return ((MeleeWeapon) items.get(0)).accuracy;
    }
    
    /**
     * Returns the Apparatus of the given type if it exists, null otherwise.
     * @param <T> The type of Apparatus to return.
     * @param ignore Conveys nothing except the type to return.
     * @param index The index that it is found.
     * @return
     */
    public <T extends Apparatus> T get(T ignore, int index){
        return (T) items.get(index);
    }
    
    /**
     * Returns the strength difference between what is given and what is required.
     * @param strength The given strength.
     * @return A positive number IFF strength > req.
     */
    public int strengthDifference(int strength){
        int ret = 0;
        for(int n=3;n<7;n++){
            if(items.get(n)!=null) ret += (strength - ((Apparatus) items.get(n)).strength);
        }
        return ret;
    }
    
    /**
     * Equips a piece of equipment and returns what was displaced.
     * @param main The MainClass to use to display a dialogue in the case of the
     * hero wanting possessing two misc items.
     * @param app The apparatus to equip.
     * @param choiceOfAmulet The choice of amulet to remove should it come to it.
     * @return The apparatus that was displaced, null if nothing.
     */
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

    /**
     * Paints this object onto the given graphics.
     * @param g The graphics to draw on.
     * @param beginWidth The width to begin drawing at.
     * @param beginHeight The height to begin drawing at.
     * @param sqwidth The width of item squares.
     * @param sqheight The height of item squares.
     * @param padding The length of padding.
     * @param owner The owner of this equipment.
     */
    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding){
        if(items.get(0)!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, items.get(0), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, ConstantFields.weaponOutline);
        if(items.get(3)!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(3), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.helmetOutline);
        if(items.get(4)!=null) ImageUtils.paintItemSquare(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(4), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.chestplateOutline);
        if(items.get(5)!=null) ImageUtils.paintItemSquare(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(5), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.leggingsOutline);
        if(items.get(6)!=null) ImageUtils.paintItemSquare(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, items.get(6), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, ConstantFields.bootsOutline);
        
        if(items.get(1)!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, items.get(1), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
        if(items.get(2)!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, items.get(2), heroOwner);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
    }
    
    private List<Screen> getScreens(ScreenListener sl){
        if(screens!=null&&heroOwner.getScreenListener().toString().equals(screens.get(0).getListener().toString())) return screens;
        List<Screen> ret = new LinkedList<>();
        int padding = 4;
        int beginWidth = MainClass.WIDTH/9;
        int beginHeight = MainClass.HEIGHT/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        ret.add(new Screen("Weapon", beginWidth+padding, beginHeight+padding, sqwidth, sqheight, sl));
        ret.add(new Screen("Helmet", beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, sl));
        ret.add(new Screen("Chestplate", beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, sl));
        ret.add(new Screen("Leggings", beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, sl));
        ret.add(new Screen("Boots", beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, sl));

        ret.add(new Screen("Amulet1", beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, sl));
        ret.add(new Screen("Amulet2", beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, sl));
        return ret;
    }
    
    public void changeScreenListener(ScreenListener sl){
        screens.stream().forEach((sc) -> {
            sc.changeScreenListener(sl);
        });
    }
    
}

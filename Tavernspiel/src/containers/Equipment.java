
package containers;

import creatures.Hero;
import dialogues.Dialogue;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import items.Apparatus;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Helmet;
import items.equipment.Leggings;
import items.equipment.MeleeWeapon;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import logic.ConstantFields;
import static logic.ConstantFields.beginHeight;
import static logic.ConstantFields.beginWidth;
import static logic.ConstantFields.padding;
import static logic.ConstantFields.sqheight;
import static logic.ConstantFields.sqwidth;
import static logic.ConstantFields.truthPredicate;
import logic.Distribution;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the collection of equipment that a creature has.
 */
public class Equipment implements Serializable{
    
    private final static long serialVersionUID = 11121437;
    
    public final List<Screen> screens;
    public final Hero heroOwner;
    public HeldWeapon weapon;
    public Apparatus amulet1, amulet2;
    public Helmet helmet;
    public Chestplate chestplate;
    public Leggings leggings;
    public Boots boots;
    
    
    /**
     * Creates a new instance
     * @param hero The owner.
     */
    public Equipment(Hero hero){
        screens = getScreens((HeroInventory)hero.inventory);
        heroOwner = hero;
    }
    
    /**
     * Returns the raw damage number of the current Weapon or a strength
     * substitute if no Weapon is equipped.
     * @param strength The strength of the Hero. 
     * @return
     */
    public double nextHit(double strength){
        try{
            return weapon.damageDistrib.nextInt();
        }catch(Exception e){
            return Distribution.r.nextInt((int)strength);
        }
    }
    
    /**
     * Returns the accuracy of the current Weapon or 1.0 if no Weapon is
     * equipped.
     * @return
     */
    public double getWeaponAccuracy(){
        try{
            return ((MeleeWeapon) weapon).accuracy;
        }catch(NullPointerException e){
            return 1.0;
        }
    }
    
    /**
     * Returns the strength difference between what is given and what is required.
     * @param strength The given strength.
     * @return A positive number IFF strength > req.
     */
    public int strengthDifference(double strength){
        int ret = 0;
        if(weapon!=null) ret += strength - weapon.strength;
        if(helmet!=null) ret += strength - helmet.strength;
        if(chestplate!=null) ret += strength - chestplate.strength;
        if(leggings!=null) ret += strength - leggings.strength;
        if(boots!=null) ret += strength - boots.strength;
        return ret;
    }
    
    /**
     * Equips a piece of equipment and returns what was displaced.
     * @param app The apparatus to equip.
     * @param choice The choice of amulet to remove should it come to it.
     * @return The apparatus that was displaced, null if nothing.
     */
    public Apparatus equip(Apparatus app, int... choice){
        if(app.durability<=0){
            Main.addMessage(ConstantFields.interestColor, "You cannot equip a "
                    + "broken item.");
            return null;
        }
        Apparatus ret;
        app.setToEquipped(this);
        if(app instanceof HeldWeapon){
            ret = weapon;
            if(ret!=null) ret.setToUnequipped();
            weapon = (HeldWeapon) app;
            return ret;
        }else if(app instanceof Helmet){
            ret = helmet;
            if(ret!=null) ret.setToUnequipped();
            helmet = (Helmet) app;
            return ret;
        }else if(app instanceof Chestplate){
            ret = chestplate;
            if(ret!=null) ret.setToUnequipped();
            chestplate = (Chestplate) app;
            return ret;
        }else if(app instanceof Leggings){
            ret = leggings;
            if(ret!=null) ret.setToUnequipped();
            leggings = (Leggings) app;
            return ret;
        }else if(app instanceof Boots){
            ret = boots;
            if(ret!=null) ret.setToUnequipped();
            boots = (Boots) app;
            return ret;
        }else if(amulet1==null){
            amulet1 = app;
            return null;
        }else if(amulet2==null){
            amulet2 = app;
            return null;
        }
        if(choice[0]==0){
            ret = amulet1;
            amulet1 = app;
        }else{
            ret = amulet2;
            amulet2 = app;
        }
        return ret;
    }
    
    /**
     * Unequips the given Item.
     * @param app The Apparatus to unequip.
     * @return
     */
    public Apparatus unequip(Apparatus app){
        Apparatus i = null;
        if(app.equals(weapon)){ i = weapon; weapon = null;}
        else if(app.equals(amulet1)){ i = amulet1; amulet1 = null;}
        else if(app.equals(amulet2)){ i = amulet2; amulet2 = null;}
        else if(app.equals(helmet)){ i = helmet; helmet = null;}
        else if(app.equals(chestplate)){ i = chestplate; chestplate = null;}
        else if(app.equals(leggings)){ i = leggings; leggings = null;}
        else if(app.equals(boots)){ i = boots; boots = null;}
        if(i!=null) i.setToUnequipped();
        return i;
    }

    /**
     * Paints this object onto the given graphics.
     * @param g The graphics to draw on.
     * @param beginWidth The width to begin drawing at.
     * @param beginHeight The height to begin drawing at.
     * @param sqwidth The width of item squares.
     * @param sqheight The height of item squares.
     * @param padding The length of padding.
     */
    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding){
        if(weapon!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, weapon, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+padding, sqwidth, sqheight, ConstantFields.weaponOutline);
        if(helmet!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, helmet, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledHelmetOutline);
        if(chestplate!=null) ImageUtils.paintItemSquare(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, chestplate, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledChestplateOutline);
        if(leggings!=null) ImageUtils.paintItemSquare(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, leggings, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledLeggingsOutline);
        if(boots!=null) ImageUtils.paintItemSquare(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, boots, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, ImageUtils.scaledBootsOutline);
        
        if(amulet1!=null) ImageUtils.paintItemSquare(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, amulet1, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
        if(amulet2!=null) ImageUtils.paintItemSquare(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amulet2, heroOwner, truthPredicate);
        else ImageUtils.paintOutline(g, beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, ConstantFields.amuletOutline);
    }
    
    private List<Screen> getScreens(HeroInventory inv){
        if(screens!=null) return screens;
        List<Screen> ret = new LinkedList<>();
        ret.add(new Screen("e0", beginWidth+padding, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e3", beginWidth+2*padding+sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e4", beginWidth+3*padding+2*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e5", beginWidth+4*padding+3*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e6", beginWidth+5*padding+4*sqwidth, beginHeight+padding, sqwidth, sqheight, inv.manager));

        ret.add(new Screen("e1", beginWidth+padding, beginHeight+2*padding+sqheight, sqwidth, sqheight, inv.manager));
        ret.add(new Screen("e2", beginWidth+2*padding+sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, inv.manager));
        return ret;
    }
    
    /**
     * Returns a random type of armour based on their respective rarities.
     * @param armourDist
     * @return
     */
    public static String getArmourType(Distribution armourDist){
        switch((int)armourDist.next()){
            case 0: return "cloth";
            case 1: return "leather";
            case 2: return "mail";
            case 3: return "scale";
            default: return "plate";
        }
    }
    
    /**
     * Removes the Apparatus and plops it on the ground.
     * @param app
     */
    public void plop(Apparatus app){
        unequip(app);
        heroOwner.area.plop(app, heroOwner.x, heroOwner.y);
    }
    
    public class EquipApparatusDialogue extends Dialogue{

        private final String opA;
        public Apparatus reject, app;

        /**
         * Creates a new instance.
         * @param a
         */
        public EquipApparatusDialogue(Apparatus a){
            super("You can only wear two misc. items at a time, which do you want to"
                    + "unequip?", (String) null, ((Apparatus) amulet1).toString(4), 
                    ((Apparatus) amulet2).toString(4));
            opA = ((Apparatus) amulet1).toString(4);
            app = a;
        }

        @Override
        public void next(){
            if(app.durability<=0){
                Main.addMessage(ConstantFields.interestColor, "You cannot equip a "
                        + "broken item.");
            }else{
                app.setToEquipped(Equipment.this);
                if(app instanceof HeldWeapon){
                    reject = weapon;
                    if(reject!=null) reject.setToUnequipped();
                    weapon = (HeldWeapon) app;
                }else if(app instanceof Helmet){
                    reject = helmet;
                    if(reject!=null) reject.setToUnequipped();
                    helmet = (Helmet) app;
                }else if(app instanceof Chestplate){
                    reject = chestplate;
                    if(reject!=null) reject.setToUnequipped();
                    chestplate = (Chestplate) app;
                }else if(app instanceof Leggings){
                    reject = leggings;
                    if(reject!=null) reject.setToUnequipped();
                    leggings = (Leggings) app;
                }else if(app instanceof Boots){
                    reject = boots;
                    if(reject!=null) reject.setToUnequipped();
                    boots = (Boots) app;
                }else if(amulet1==null){
                    amulet1 = app;
                }else if(amulet2==null){
                    amulet2 = app;
                }else{
                    super.next();
                }
            }
        }

        @Override
        public void screenClicked(Screen.ScreenEvent sc){
            checkDeactivate(sc);
            if(sc.getName()==null||sc.getName().equals("offCase")) app.setToUnequipped();
            else if(sc.getName().equals(opA)){
                reject = amulet1;
                amulet1 = app;
            }else{
                reject = amulet2;
                amulet2 = app;
            }
        }

}
    
}

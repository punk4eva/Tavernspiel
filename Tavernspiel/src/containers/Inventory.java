
package containers;

import ai.ItemActionInterpreter;
import creatureLogic.QuickSlot;
import creatures.Hero;
import dialogues.ItemDialogue;
import dialogues.MoneyDialogue;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.Window;
import items.misc.Gold;
import items.Item;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import listeners.ScreenListener;
import logic.ConstantFields;
import static logic.ConstantFields.beginHeight;
import static logic.ConstantFields.beginWidth;
import static logic.ConstantFields.padding;
import static logic.ConstantFields.sqheight;
import static logic.ConstantFields.sqwidth;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a creature's inventory.
 */
public class Inventory extends Receptacle{

    public int amountOfMoney = 0;
    public final List<Screen> screens;
    public final Hero heroOwner;
    public final QuickSlot quickslot;
    public final InventoryManager manager = new InventoryManager();
    
    public Inventory(){
        super(null, 18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
        heroOwner = null;
        quickslot = null;
    }
    
    public Inventory(Hero hero){
        super(null, 18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
        heroOwner = hero;
        quickslot = new QuickSlot(hero, this);
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }

    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding, Predicate<Item>... pred){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(beginWidth, beginHeight, Main.WIDTH*7/9, Main.HEIGHT*7/9, false);
        ImageUtils.paintGold(g, beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amountOfMoney);
        beginWidth += padding;
        beginHeight += 3*padding + 2*sqheight;
        int n=0, y=0, x;
        for(;y<3&&n<size();y++){
            for(x=0;x<6&&n<size();x++){
                ImageUtils.paintItemSquare(g, beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, get(n), heroOwner, pred);
                n++;
            }
        }
        for(y=0;n>=6;n-=6) y++;
        x = n;
        g.setColor(ConstantFields.backColor);
        for(;y<3;y++){
            for(x=(x==6?0:x);x<6;x++){
                g.fill3DRect(beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, true);
            }
        }
    }
    
    @Override
    public boolean add(Item i){
        if(i instanceof Gold) amountOfMoney += i.quantity;
        else return super.add(i);
        return true;
    }
    
    private List<Screen> getScreens(){
        LinkedList<Screen> ret = new LinkedList<>();
        int sqBeginWidth = padding+beginWidth;
        int sqBeginHeight = 3*padding + 2*sqheight + beginHeight;
        for(int y=0,n=0;y<3;y++){
            for(int x=0;x<6;x++){
                ret.add(new Screen(""+n, sqBeginWidth+x*(padding+sqwidth), 
                        sqBeginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, manager));
                n++;
            }
        }
        ret.add(new Screen("Money", sqBeginWidth+2*padding+2*sqwidth, sqBeginHeight-padding-sqheight, sqwidth, sqheight, manager));
        ret.add(new Screen("invspace", Main.WIDTH/9, Main.WIDTH/9, Main.WIDTH*7/9, Main.HEIGHT*7/9, manager));
        ret.add(new Screen("background", 0, 0, Main.WIDTH, Main.HEIGHT, manager));
        return ret;
    }
    
    public class InventoryManager implements ScreenListener{

        public ScreenListener hijacker;
        public boolean exitable = true;
        
        @Override
        public void screenClicked(ScreenEvent sc){
            if(hijacker!=null) hijacker.screenClicked(sc);
            else{
                String slot = sc.getName();
                System.out.println(slot);
                switch(slot){
                    case "background": if(exitable) Window.main.setInventoryActive(false);
                    case "invspace": return;
                }
                if(slot.startsWith("Money"))
                    new MoneyDialogue(amountOfMoney).next();
                else if(slot.startsWith("e")){
                    Item i = null;
                    switch(slot){
                        case "e0": i = heroOwner.equipment.weapon; break;
                        case "e1": i = heroOwner.equipment.amulet1; break;
                        case "e2": i = heroOwner.equipment.amulet2; break;
                        case "e3": i = heroOwner.equipment.helmet; break;
                        case "e4": i = heroOwner.equipment.chestplate; break;
                        case "e5": i = heroOwner.equipment.leggings; break;
                        case "e6": i = heroOwner.equipment.boots; break;
                    }
                    if(i!=null){
                        Item it = i; //effective finalization.
                        Window.main.addEvent(() -> 
                            ItemActionInterpreter.act(new ItemDialogue(it, heroOwner.expertise).next(), heroOwner, -1));
                    }
                    
                }else{
                    int s = Integer.parseInt(slot);
                    if(s<size())
                        Window.main.addEvent(() -> 
                                ItemActionInterpreter.act(new ItemDialogue(get(s), heroOwner.expertise).next(), heroOwner, s));
                }
            }
        }
    
    }
    
}

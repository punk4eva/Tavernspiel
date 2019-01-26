
package containers;

import creatureLogic.QuickSlot;
import creatures.Hero;
import dialogues.ItemDialogue;
import dialogues.MoneyDialogue;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
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
 * This class represents an Inventory possessed by the Hero.
 */
public class HeroInventory extends Inventory{
    
    private final static long serialVersionUID = 20432178497L;
    
    public final List<Screen> screens;
    public final QuickSlot quickslot;
    public InventoryManager manager = new InventoryManager();
    
    /**
     * Creates a new instance.
     * @param hero The owner.
     */
    public HeroInventory(Hero hero){
        super(hero, new HeroEquipment(hero)); 
        quickslot = new QuickSlot(hero, this);
        screens = getScreens();
        ((HeroEquipment) equipment).screens = ((HeroEquipment) equipment).getScreens(this);
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
    
    /**
     * Paints the Inventory on to the given Graphics.
     * @param g The Graphics.
     * @param beginWidth The x coordinate to begin painting on.
     * @param beginHeight The y coordinate to begin painting on.
     * @param sqwidth The width of each item square.
     * @param sqheight The height of each item square.
     * @param padding The padding between each item square.
     * @param pred The selection of Items to be blurred out.
     */
    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding, Predicate<Item> pred){
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
                        sqwidth, sqheight, get(n), (Hero) owner, pred);
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
    
    /**
     * The manager of the Inventory
     */
    public class InventoryManager implements ScreenListener{

        public transient ScreenListener hijacker;
        public Predicate<Item> predicate = ConstantFields.truthPredicate;
        
        @Override
        public void screenClicked(Screen.ScreenEvent sc){
            if(hijacker!=null) hijacker.screenClicked(sc);
            else{
                String slot = sc.getName();
                System.out.println(slot);
                switch(slot){
                    case "background": Window.main.setInventoryActive(false);
                    case "invspace": return;
                }
                if(slot.startsWith("Money"))
                    new MoneyDialogue(amountOfMoney).next();
                else{
                    Item i = get(slot);
                    int sl;
                    if(slot.startsWith("e")) sl = -10;
                    else sl = Integer.parseInt(slot);
                    if(i!=null&&predicate.test(i)) new ItemDialogue(i, (Hero)owner, sl).next();
                }
            }
        }
    
    }
    
}

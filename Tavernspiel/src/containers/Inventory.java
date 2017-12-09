
package containers;

import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import gui.Screen.ScreenEvent;
import gui.Window;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
import logic.ConstantFields;
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
    public final InventoryManager manager = new InventoryManager();
    
    public Inventory(){
        super(null, 18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
        heroOwner = null;
    }
    
    public Inventory(Hero hero){
        super(null, 18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
        heroOwner = hero;
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }

    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(beginWidth, beginHeight, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, false);
        ImageUtils.paintGold(g, beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amountOfMoney);
        beginWidth += padding;
        beginHeight += 3*padding + 2*sqheight;
        int n=0, y=0, x;
        for(;y<3&&n<items.size();y++){
            for(x=0;x<6&&n<items.size();x++){
                ImageUtils.paintItemSquare(g, beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, items.get(n), heroOwner);
                n++;
            }
        }
        for(y=0;n>=6;n-=6) y++;
        x = n;
        g.setColor(ConstantFields.backColor);
        for(;y<3;y++){
            for(x=(x==6?x=0:x);x<6;x++){
                g.fill3DRect(beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, true);
            }
        }
    }
    
    private List<Screen> getScreens(){
        LinkedList<Screen> ret = new LinkedList<>();
        int padding = Hero.padding;
        int beginWidth = padding+Hero.beginWidth;
        int sqwidth = Hero.sqwidth;
        int sqheight = Hero.sqheight;
        int beginHeight = 3*padding + 2*sqheight + Hero.beginHeight;
        for(int y=0,n=0;y<3;y++){
            for(int x=0;x<6;x++){
                ret.add(new Screen(""+n, beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, manager));
                n++;
            }
        }
        ret.add(new Screen("Money", beginWidth+2*padding+2*sqwidth, beginHeight-padding-sqheight, sqwidth, sqheight, manager));
        ret.add(new Screen("invspace", MainClass.WIDTH/9, MainClass.WIDTH/9, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, manager));
        ret.add(new Screen("background", 0, 0, MainClass.WIDTH, MainClass.HEIGHT, manager));
        return ret;
    }
    
    public class InventoryManager implements ScreenListener{

        public ScreenListener hijacker;
        
        @Override
        public void screenClicked(ScreenEvent sc){
            if(hijacker!=null) hijacker.screenClicked(sc);
            else{
                System.out.println(sc.getName());
                switch(sc.getName()){
                    case "background": Window.main.removeTopViewable();
                        return;
                }
            }
        }
    
    }
    
}

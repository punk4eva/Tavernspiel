
package containers;

import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import gui.Screen.ScreenEvent;
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
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
        heroOwner = null;
    }
    
    public Inventory(Hero hero){
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
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
            x = 0;
            for(;x<6&&n<items.size();x++){
                ImageUtils.paintItemSquare(g, beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, items.get(n), heroOwner);
                n++;
            }
        }
        for(;y<3;y++){
            x = 0;
            for(;x<6;x++){
                g.fill3DRect(beginWidth+x*(padding+sqwidth), 
                        beginHeight+y*(padding+sqheight),
                        sqwidth, sqheight, true);
            }
        }
    }
    
    private List<Screen> getScreens(){
        LinkedList<Screen> ret = new LinkedList<>();
        int padding = 4;
        int beginWidth = padding+MainClass.WIDTH/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        int beginHeight = 3*padding + 2*sqheight + MainClass.HEIGHT/9;
        ret.add(new Screen("background", 0, 0, MainClass.WIDTH, MainClass.HEIGHT, manager));
        ret.add(new Screen("invspace", MainClass.WIDTH/9, MainClass.WIDTH/9, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, manager));
        for(int n=0;n<capacity;n++){
            ret.add(new Screen(""+n, beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight, manager));
        }
        ret.add(new Screen("Money", beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, manager));
        return ret;
    }
    
    public class InventoryManager implements ScreenListener{

        public ScreenListener hijacker;
        
        @Override
        public void screenClicked(ScreenEvent sc){
            if(hijacker!=null) hijacker.screenClicked(sc);
            else{
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    
    }
    
}

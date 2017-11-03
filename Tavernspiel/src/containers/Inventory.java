
package containers;

import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import listeners.ScreenListener;
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
    
    public Inventory(){
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens(null);
        heroOwner = null;
    }
    
    public Inventory(Hero hero){
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens(hero.getScreenListener());
        heroOwner = hero;
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }

    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding){
        g.setColor(Color.gray);
        g.fill3DRect(beginWidth, beginHeight, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, false);
        beginWidth += padding;
        beginHeight += 3*padding + 2*sqheight;
        int n=0;
        for(;n<items.size();n++){
            ImageUtils.paintItemSquare(g, beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight, items.get(n), heroOwner);
        }
        for(;n<capacity;n++){
            g.fill3DRect(beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight), sqwidth, sqheight, true);
        }
        ImageUtils.paintGold(g, beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amountOfMoney);
    }
    
    private List<Screen> getScreens(ScreenListener sl){
        if(screens!=null&&heroOwner.getScreenListener().toString().equals(screens.get(0).getListener().toString())) return screens;
        LinkedList<Screen> ret = new LinkedList<>();
        int padding = 4;
        int beginWidth = padding+MainClass.WIDTH/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        int beginHeight = 3*padding + 2*sqheight + MainClass.HEIGHT/9;
        ret.add(new Screen("background", 0, 0, MainClass.WIDTH, MainClass.HEIGHT, sl));
        ret.add(new Screen("invspace", MainClass.WIDTH/9, MainClass.WIDTH/9, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, sl));
        for(int n=0;n<capacity;n++){
            ret.add(new Screen(""+n, beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight, sl));
        }
        ret.add(new Screen("Money", beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, sl));
        return ret;
    }
    
    public void changeScreenListener(ScreenListener sl){
        screens.stream().forEach((sc) -> {
            sc.changeScreenListener(sl);
        });
    }
    
}


package containers;

import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import items.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a creature's inventory.
 */
public class Inventory extends Receptacle{

    public int amountOfMoney = 0;
    public final ArrayList<Screen> screens;
    
    public Inventory(){
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
    }
    
    public Inventory(ArrayList<Item> i){
        super(i, 18, "ERROR: You shouldn't be reading this.", -1, -1);
        screens = getScreens();
    }

    public static Inventory getFromFileString(String filestring){
        String[] profile = filestring.substring(1, filestring.length()-1).split("|");
        ArrayList<Item> is = new ArrayList<>();
        for(String s : profile[1].split(",")) is.add(Item.getFromFileString(s));
        return new Inventory(is);
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }
    
    @Override
    public String toFileString(){
        String ret = "{" + description + "|";
        return items.stream().map((item) -> item.toFileString()).reduce(ret, String::concat) + "}";
    }

    public void paint(Graphics g, int beginWidth, int beginHeight, int sqwidth, int sqheight, int padding, Hero owner){
        g.setColor(Color.gray);
        g.fill3DRect(beginWidth, beginHeight, MainClass.WIDTH*7/9, MainClass.HEIGHT*7/9, false);
        beginWidth += padding;
        beginHeight += 3*padding + 2*sqheight;
        int n=0;
        for(;n<items.size();n++){
            ImageUtils.paintItemSquare(g, beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight, items.get(n), owner);
        }
        for(;n<capacity;n++){
            g.fill3DRect(beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight), sqwidth, sqheight, true);
        }
        ImageUtils.paintGold(g, beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amountOfMoney);
    }
    
    private ArrayList<Screen> getScreens(){
        ArrayList<Screen> ret = new ArrayList<>();
        int padding = 4;
        int beginWidth = padding+MainClass.WIDTH/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        int beginHeight = 3*padding + 2*sqheight + MainClass.HEIGHT/9;
        for(int n=0;n<capacity;n++){
            ret.add(new Screen(""+n, beginWidth + padding + n*(padding+sqwidth),
                    beginHeight + padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight));
        }
        return ret;
    }
    
}

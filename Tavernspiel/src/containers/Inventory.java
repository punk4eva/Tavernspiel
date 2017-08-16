
package containers;

import creatures.Hero;
import gui.MainClass;
import items.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public class Inventory extends Receptacle{

    public int amountOfMoney = 0;
    
    public Inventory(){
        super(18, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public Inventory(ArrayList<Item> i){
        super(i, 18, "ERROR: You shouldn't be reading this.", -1, -1);
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
            ImageUtils.paintItemSquare(g, padding + n*(padding+sqwidth),
                    padding+(n+2)*(padding+sqheight),
                    sqwidth, sqheight, items.get(n), owner);
        }
        for(;n<capacity;n++){
            g.fill3DRect(padding + n*(padding+sqwidth),
                    padding+(n+2)*(padding+sqheight), sqwidth, sqheight, true);
        }
        ImageUtils.paintGold(g, beginWidth+3*padding+2*sqwidth, beginHeight+2*padding+sqheight, sqwidth, sqheight, amountOfMoney);
    }
    
}

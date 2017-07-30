
package containers;

import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Inventory extends Receptacle{

    public int amountOfMoney = 0;
    
    public Inventory(){
        super(30, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public Inventory(ArrayList<Item> i){
        super(i, 30, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public Inventory(ArrayList<Item> i, int id){
        super(30, i, "ERROR: You shouldn't be reading this.", id, -1, -1);
    }

    public static Inventory getFromFileString(String filestring){
        String[] profile = filestring.substring(1, filestring.length()-1).split("|");
        ArrayList<Item> is = new ArrayList<>();
        for(String s : profile[1].split(",")) is.add(Item.getFromFileString(s));
        return new Inventory(is, Integer.parseInt(profile[0].split(",")[0]));
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }
    
    @Override
    public String toFileString(){
        String ret = "{" + ID + "," + description + "|";
        return items.stream().map((item) -> item.toFileString()).reduce(ret, String::concat) + "}";
    }
    
}


package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import items.Apparatus;
import java.util.ArrayList;
import items.Item;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles storage of items.
 * (name Container is taken)
 * 
 * @TOPLEVEL
 */
public abstract class Receptacle implements Fileable{
    
    public ArrayList<Item> items = new ArrayList<>();
    public int capacity = 1000;
    public int ID;
    public String description;
    public final int x, y; 
    
    
    public interface Sort{
        public boolean select(Item i);
    }
    
    
    public Receptacle(String desc, int xc, int yc){
        description = desc;
        ID = MainClass.idhandler.genID();
        x = xc;
        y = yc;
    }
    
    public Receptacle(int cap, String desc, int xc, int yc){
        description = desc;
        capacity = cap;
        ID = MainClass.idhandler.genID();
        x = xc;
        y = yc;
    }
    
    public Receptacle(ArrayList<Item> i, int cap, String desc, int xc, int yc){
        description = desc;
        items = i;
        capacity = cap;
        ID = MainClass.idhandler.genID();
        x = xc;
        y = yc;
    }
    
    public Receptacle(int cap, ArrayList<Apparatus> i, String desc, int xc, int yc){
        description = desc;
        capacity = cap;
        items.addAll(i);
        ID = MainClass.idhandler.genID();
        x = xc;
        y = yc;
    }
    
    public Receptacle(int cap, ArrayList<Item> i, String desc, int id, int xc, int yc){
        description = desc;
        capacity = cap;
        items = i;
        ID = id;
        x = xc;
        y = yc;
    }
    
    public Item get(int slot) throws ReceptacleIndexOutOfBoundsException{
        if(slot<0||slot-1>capacity){
            throw new ReceptacleIndexOutOfBoundsException("Slot " + slot + " is"
                    + " out of bounds for a receptacle with size " + capacity);
        }
        return items.get(slot);
    }
    
    private void clear(){
        items = new ArrayList<>();
    }
    
    public void trimToCapacity(){
        ArrayList<Item> ret = new ArrayList<>();
        for(int n=0;n<capacity;n++) ret.add(items.get(n));
        items = ret;
    }
    
    public Item peek() throws ReceptacleIndexOutOfBoundsException{
        if(items.isEmpty()) throw new ReceptacleIndexOutOfBoundsException(
                "Receptacle is empty.");
        return items.get(items.size()-1);
    }
    
    public Item pop() throws ReceptacleIndexOutOfBoundsException{
        if(items.isEmpty()) throw new ReceptacleIndexOutOfBoundsException(
                "Receptacle is empty.");
        Item ret = items.get(items.size()-1);
        items.remove(ret);
        return ret;
    }
    
    public void push(Item item) throws ReceptacleOverflowException{
        if(capacity==items.size()) throw new ReceptacleOverflowException("This"
                + " Receptacle is full.");
        items.add(item);
    }
    
    public void pushAll(Receptacle r) throws ReceptacleOverflowException{
        if(capacity<items.size()+r.items.size()) throw new 
                ReceptacleOverflowException("This Receptacle is full.");
        items.addAll(r.items);
    }
    
    public boolean isEmpty(){
        return items.isEmpty();
    }
    
    public boolean contains(Item i){
        return items.stream().anyMatch(item -> (item.name.endsWith(i.name)));
    }
    
    public boolean contains(String i){
        return items.stream().anyMatch(item -> (item.name.endsWith(i)));
    }
    
    public void keep(Sort sort){
        items.stream().filter(item -> sort.select(item));
    }
    
    @Override
    public String toFileString(){
        String ret = "{" + ID + "," + description + "|";
        return items.stream().map((item) -> item.toFileString()).reduce(ret, String::concat) + "}";
    }
    
}

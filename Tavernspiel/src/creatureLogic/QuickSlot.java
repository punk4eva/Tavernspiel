
package creatureLogic;

public class QuickSlot implements Serializable, ScreenListener{

    private int[] pointers;
    private final Inventory inventory;
    private final Hero hero;
    
    public void paint(Graphics g){
        
    }
    
    public QuickSlot(Hero h){
        inventory = h.inventory;
        hero = h;
    }
    
    @Override
    public void screenClicked(ScreenEvent sc){
        return ((Usable)inventory.get(pointers[Integer.parseInt(sc.getName())])).defaultUse();
    }
    
}

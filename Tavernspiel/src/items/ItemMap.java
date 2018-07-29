
package items;

import items.misc.Gold;
import java.util.LinkedList;
import java.util.List;
import level.Location;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Item generation probabilities.
 */
public class ItemMap{
    
    private final Distribution distrib;
    private final Item[] items;
    private final Distribution[] stackSizes;
    public double nextChance, diminisher;
    
    /**
     * Creates a new Instance.
     * @param chances The generation chances
     * @param it The Items that could be generated
     * @param c The chance for one more Item to be generated.
     * @param d The coefficient with which the chance for another Item is
     * diminished.
     */
    public ItemMap(int[] chances, Item[] it, double c, double d){
        distrib = chances==null ? null : new Distribution(chances);
        items = it;
        stackSizes = null;
        nextChance = c;
        diminisher = d;
    }
    
    /**
     * Creates a new Instance.
     * @param chances The generation chances
     * @param it The Items that could be generated
     * @param stk The stack size chance of each Item.
     * @param c The chance for one more Item to be generated.
     * @param d The coefficient with which the chance for another Item is
     * diminished.
     */
    public ItemMap(int[] chances, Item[] it, Distribution[] stk, double c, double d){
        distrib = chances==null ? null : new Distribution(chances);
        items = it;
        stackSizes = stk;
        nextChance = c;
        diminisher = d;
    }
    
    /**
     * Creates a new instance for Itemless rooms.
     */
    public ItemMap(){
        distrib = new Distribution(new int[]{});
        items = new Item[]{};
        stackSizes = null;
        nextChance = 0;
    }
    
    /**
     * Generates a list of Items.
     * @return
     */
    @Unfinished("No new Items are created, May be redundant")
    public List<Item> genList(){
        List<Item> ret = new LinkedList<>();
        if(stackSizes==null) while(hasNext()){
            ret.add(items[(int)distrib.next()]);
        }else while(hasNext()){
            int index = (int)distrib.next();
            Item i = items[index];
            if(i.stackable) i.quantity = (int) stackSizes[index].next();
            ret.add(i);
        }
        throw new UnsupportedOperationException("GenList used");
        //return ret;
    }
    
    private boolean hasNext(){
        boolean ret = nextChance>Distribution.r.nextDouble();
        nextChance *= diminisher;
        return ret;
    }
    
    /**
     * Returns the standard ItemMap.
     * @param depth The depth
     * @param loc The Location
     * @return
     */
    @Unfinished
    public static ItemMap getStandardItemMap(int depth, Location loc){
        return new ItemMap(null, null, -1, -1){
            
            Distribution type = new Distribution(new int[]{12, 3, 3, 9, 9, 1, 1});
            
            @Override
            public List<Item> genList(){
                List<Item> ret = new LinkedList<>();
                while(Distribution.chance(1, 3)){
                    switch((int)type.next()){
                        case 0: //gold
                            ret.add(new Gold(Distribution.getRandomInt(depth*(5-loc.difficulty), depth*(40-2*loc.difficulty))));
                            break;
                        case 1: //armour
                            ret.add(Apparatus.getRandomArmour(depth, loc));
                            break;
                        case 2: //weapons
                            ret.add(Apparatus.getRandomMeleeWeapon(depth, loc));
                            break;
                        case 3: //potions
                            break;
                        case 4: //scrolls
                            break;
                        case 5: //rings
                            break;
                        case 6: //wands
                            break;
                    }
                }
                return ret;
            }
        };
    }
    
    /**
     * Returns the standard ItemMap.
     * @param depth The depth
     * @param loc The Location
     * @return
     */
    @Unfinished
    public static ItemMap getStorageItemMap(int depth, Location loc){
        return new ItemMap(null, null, -1, -1){
            
            Distribution type = new Distribution(new int[]{12, 3, 3, 9, 9, 1, 1});
            
            @Override
            public List<Item> genList(){
                List<Item> ret = new LinkedList<>();
                while(Distribution.chance(1, 3)){
                    switch((int)type.next()){
                        case 0: //gold
                            ret.add(new Gold(Distribution.getRandomInt(depth*(5-loc.difficulty), depth*(40-2*loc.difficulty))));
                            break;
                        case 1: //armour
                            ret.add(Apparatus.getRandomArmour(depth, loc));
                            break;
                        case 2: //weapons
                            ret.add(Apparatus.getRandomMeleeWeapon(depth, loc));
                            break;
                        case 3: //potions
                            break;
                        case 4: //scrolls
                            break;
                        case 5: //rings
                            break;
                        case 6: //wands
                            break;
                    }
                }
                return ret;
            }
        };
    }
    
    /**
     * Returns the ItemMap for a garden.
     * @param depth
     * @param loc
     * @return
     */
    @Unfinished
    public static ItemMap getGardenItemMap(int depth, Location loc){
        return new ItemMap(null, null, -1, -1){
            
            Distribution type = new Distribution(new int[]{12, 3, 3, 9, 9, 1, 1});
            
            @Override
            public List<Item> genList(){
                List<Item> ret = new LinkedList<>();
                while(Distribution.chance(1, 3)){
                    switch((int)type.next()){
                        case 0: //gold
                            ret.add(new Gold(Distribution.getRandomInt(depth*(5-loc.difficulty), depth*(40-2*loc.difficulty))));
                            break;
                        case 1: //armour
                            ret.add(Apparatus.getRandomArmour(depth, loc));
                            break;
                        case 2: //weapons
                            ret.add(Apparatus.getRandomMeleeWeapon(depth, loc));
                            break;
                        case 3: //potions
                            break;
                        case 4: //scrolls
                            break;
                        case 5: //rings
                            break;
                        case 6: //wands
                            break;
                    }
                }
                return ret;
            }
        };
    }
    
}

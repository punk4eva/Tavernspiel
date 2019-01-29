
package items;

import items.misc.Gold;
import java.util.LinkedList;
import java.util.List;
import static level.Dungeon.potionBuilder;
import static level.Dungeon.ringBuilder;
import static level.Dungeon.scrollBuilder;
import level.Location;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Item generation probabilities.
 */
@Unfinished("ItemMaps non-reusable due to diminisher so rework needed.")
public final class ItemMap{
    
    private final static Distribution STANDARD_TYPEDIST = new Distribution(new int[]{12,3,3,9,9}), //@Unfinished Add wand/ring spawn chances.
            STANDARD_POTIONDIST = new Distribution(new int[]{}), 
            STANDARD_SCROLLDIST = new Distribution(new int[]{3,20,1,2,20,17,11,15,13,12,8,4,4,6,3,1,2}), 
            STANDARD_VILEDIST = new Distribution(new int[]{1,1,1,1,1,1,1,1}), //@Unfinished
            STANDARD_ARMORDIST = new Distribution(new int[]{12,20,6,3,1}),
            STANDARD_RINGDIST = new Distribution(new int[]{1,1,1,1}), //@Unfinished
            STANDARD_RINGTYPEDIST = new Distribution(new int[]{5,4,3,1});
    private final static double STANDARD_NEXTCHANCE = 1d/3d, 
            STANDARD_DIMINISHER = 0.8;
    
    private final Distribution typeDist;
    public double nextChance, diminisher;
    public final Distribution potionDist, scrollDist, vileDist, armorDist, 
            ringDist, ringTypeDist;
    
    /**
     * Creates a new Instance.
     * @param chances The generation chances.
     * @param c The chance for one more Item to be generated.
     * @param d The coefficient with which the chance for another Item is
     * diminished.
     * @param potDist The potion generation chances.
     * @param scrDist The scroll generation chances.
     * @param armDist The armor distribution.
     * @param vDist The vile generation chances.
     * @param rDist
     * @param rTDist The ring type distribution.
     */
    public ItemMap(Distribution chances, double c, double d, Distribution potDist, 
            Distribution scrDist, Distribution vDist, Distribution armDist, 
            Distribution rDist, Distribution rTDist){
        typeDist = chances;
        potionDist = potDist;
        scrollDist = scrDist;
        vileDist = vDist;
        nextChance = c;
        diminisher = d;
        armorDist = armDist;
        ringTypeDist = rTDist;
        ringDist = rDist;
    }
    
    /**
     * Creates a new instance for Itemless rooms.
     */
    public ItemMap(){
        typeDist = null;
        scrollDist = null;
        armorDist = null;
        potionDist = null;
        vileDist = null;
        ringDist = null;
        nextChance = 0;
        ringTypeDist = null;
    }
    
    /**
     * Generates a list of Items that are to be plopped in the world.
     * @param loc
     * @return
     */
    public List<Item> genList(Location loc){
        List<Item> ret = new LinkedList<>();
        while(hasNext()){
            switch((int)typeDist.next()){
                case 0: //gold
                    ret.add(new Gold(Distribution.getRandomInt(loc.depth*(5-loc.feeling.difficulty), loc.depth*(40-2*loc.feeling.difficulty))));
                    break;
                case 1: //armour
                    ret.add(Apparatus.getRandomArmour(armorDist));
                    break;
                case 2: //weapons
                    ret.add(Apparatus.getRandomMeleeWeapon(loc.depth, loc));
                    break;
                case 3: //potions
                    System.out.println("Potion spawned");
                    ret.add(potionBuilder().getRandomPotion(potionDist, this));
                    break;
                case 4: //scrolls
                    System.out.println("Scroll spawned");
                    ret.add(scrollBuilder().getRandomScroll(scrollDist));
                    break;
                case 5: //rings
                    System.out.println("Ring spawned");
                    ret.add(ringBuilder().getRandomRing(ringDist, ringTypeDist));
                    break;
                case 6: //wands
                    System.out.println("Wand spawned");
                    break;
            }
        }
        return ret;
    }
    
    private boolean hasNext(){
        boolean ret = nextChance>Distribution.r.nextDouble();
        nextChance *= diminisher;
        return ret;
    }
    
    
    
    @Unfinished
    public final static ItemMap standardItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap storageItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap gardenItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap laboratoryItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap libraryItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap secretLibraryItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public final static ItemMap kitchenItemMap = new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST);
    
    @Unfinished
    public static ItemMap[] lotteryItemMaps = new ItemMap[]{new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST), new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST), new ItemMap(STANDARD_TYPEDIST,
            STANDARD_NEXTCHANCE, STANDARD_DIMINISHER, STANDARD_POTIONDIST, 
            STANDARD_SCROLLDIST, STANDARD_VILEDIST, STANDARD_ARMORDIST, 
            STANDARD_RINGDIST, STANDARD_RINGTYPEDIST)};
    
}

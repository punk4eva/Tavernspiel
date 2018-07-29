
package tiles;

import animation.WaterAnimation;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.function.Function;
import javax.swing.ImageIcon;
import level.Location;
import level.RoomBuilder;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Tile implements Comparable<Tile>{
    
    protected ImageIcon image;
    public String name;
    public boolean treadable;
    public boolean flammable;
    public boolean transparent;
    
    public static final Distribution trapChance = new Distribution(1, 35);
    
    public Tile(String n, ImageIcon ic){
        image = ic;
        name = n;
        treadable = true;
        flammable = false;
        transparent = true;
    }
    
    public Tile(String n, ImageIcon ic, boolean t, boolean f, boolean tr){
        image = ic;
        name = n;
        treadable = t;
        flammable = f;
        transparent = tr;
    }
    
    public Tile(String tile, Location loc, boolean t, boolean f, boolean tr){
        image = loc.getImage(tile);
        name = tile;
        treadable = t;
        flammable = f;
        transparent = tr;
    }
    
    /**
     * Checks whether the Tile is the same type as another with the given name.
     * @param str The name of the comparing Tile.
     * @return
     */
    public boolean equals(String str){
        return str.compareToIgnoreCase(name)==0;
    }

    @Override
    public int compareTo(Tile t){
        return name.compareToIgnoreCase(t.name);
    }
    
    public static Tile wall(Location loc){
        if(Distribution.chance(1, 22)) return new Tile("specialwall", loc, false, false, false);
        return new Tile("wall", loc, false, false, false);
    }
    
    public static Tile floor(Location loc){
        if(trapChance.chance()) return RoomBuilder.getRandomTrap(loc);
        if(Distribution.chance(1, 22)) return new Tile("decofloor", loc, true, false, true);
        return new Tile("floor", loc, true, false, true);
    }
    
    public void paint(Graphics g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
    public boolean isFloor(){
        return name.equals("floor")||name.equals("decofloor");
    }
    
    public static int getID(Tile tile){
        if(tile==null) return -1;
        if(tile instanceof Door){
            if(((Door) tile).hidden) return 6;
            if(((Door) tile).isOpen) return 7;
        }
        try{
            return IDmap.get(tile.name);
        }catch(NullPointerException e){
            return ((WaterAnimation)((AnimatedTile)tile).animation).x+39;
        }
    }
    
    public static Tile getTile(int id, Location loc){
        if(id==-1) return null;
        if(id==6) return new Door(loc, false, true);
        if(id==7){
            Door d = new Door(loc);
            d.open();
            return d;
        }
        return tileMap.get(id).apply(loc);
    }
    
    private static final HashMap<String, Integer> IDmap = new HashMap<>();
    private static final HashMap<Integer, Function<Location, Tile>> tileMap = new HashMap<>();
    static{
        IDmap.put("void", 0);
        IDmap.put("floor", 1);
        IDmap.put("lowgrass", 2);
        IDmap.put("emptywell", 3);
        IDmap.put("wall", 4);
        IDmap.put("door", 5);
        IDmap.put("depthentrance", 8);
        IDmap.put("depthexit", 9);
        IDmap.put("embers", 10);
        IDmap.put("lockeddoor", 11);
        IDmap.put("pedestal", 12);
        IDmap.put("specialwall", 13);
        IDmap.put("barricade", 14);
        IDmap.put("specialfloor", 15);
        IDmap.put("highgrass", 16);
        IDmap.put("greentrap", 17);
        IDmap.put("orangetrap", 18);
        IDmap.put("yellowtrap", 19);
        IDmap.put("decofloor", 21);
        IDmap.put("lockeddepthexit", 22);
        IDmap.put("unlockeddepthexit", 23);
        IDmap.put("purpletrap", 24);
        IDmap.put("sign", 25);
        IDmap.put("redtrap", 26);
        IDmap.put("bluetrap", 27);
        IDmap.put("well", 28);
        IDmap.put("statue", 29);
        IDmap.put("specialstatue", 30);
        IDmap.put("beartrap", 31);
        IDmap.put("silvertrap", 32);
        IDmap.put("bookshelf", 33);
        IDmap.put("alchemypot", 34);
        IDmap.put("floorcutoff", 35);
        IDmap.put("specialfloorcutoff", 36);
        IDmap.put("wallcutoff", 37);
        IDmap.put("brokencutoff", 38);
        tileMap.put(0, loc -> new Chasm("void", loc));
        tileMap.put(1, loc -> new Tile("floor", loc, true, false, true));
        tileMap.put(2, loc -> new Grass(loc, false));
        tileMap.put(3, loc -> new Tile("emptywell", loc, true, false, true));
        tileMap.put(4, loc -> new Tile("wall", loc, false, false, false));
        tileMap.put(5, loc -> new Door(loc));
        tileMap.put(8, loc -> new DepthEntrance(loc));
        tileMap.put(9, loc -> new DepthExit(loc));
        tileMap.put(10, loc -> new Tile("embers", loc, true, false, true));
        tileMap.put(11, loc -> new Door(loc, true));
        tileMap.put(12, loc -> new Tile("pedestal", loc, true, false, true));
        tileMap.put(13, loc -> new Tile("specialwall", loc, false, false, false));
        tileMap.put(14, loc -> new Barricade("barricade", loc));
        tileMap.put(15, loc -> new Tile("specialfloor", loc, true, false, true));
        tileMap.put(16, loc -> new Grass(loc, true));
        tileMap.put(17, loc -> TrapBuilder.getTrap("greentrap", loc));
        tileMap.put(18, loc -> TrapBuilder.getTrap("orangetrap", loc));
        tileMap.put(19, loc -> TrapBuilder.getTrap("yellowtrap", loc));
        tileMap.put(21, loc -> new Tile("decofloor", loc, true, false, true));
        //@Unfinished LockedDepthExit
        //tileMap.put(22, );
        //tileMap.put(23, );
        tileMap.put(24, loc -> TrapBuilder.getTrap("purpletrap", loc));
        //@Unfinished sign
        //tileMap.put(25, );
        tileMap.put(26, loc -> TrapBuilder.getTrap("redtrap", loc));
        tileMap.put(27, loc -> TrapBuilder.getTrap("bluetrap", loc));
        //@Unfinished well
        //tileMap.put(28, );
        tileMap.put(29, loc -> new Tile("statue", loc, false, false, true));
        tileMap.put(30, loc -> new Tile("specialstatue", loc, false, false, true));
        tileMap.put(31, loc -> TrapBuilder.getTrap("beartrap", loc));
        tileMap.put(32, loc -> TrapBuilder.getTrap("silvertrap", loc));
        tileMap.put(33, loc -> new Barricade("bookshelf", loc));
        //@Unfinished alchemy pot
        //tileMap.put(34, );
        tileMap.put(35, loc -> new Chasm("floor", loc));
        tileMap.put(36, loc -> new Chasm("specialfloor", loc));
        tileMap.put(37, loc -> new Chasm("wall", loc));
        tileMap.put(38, loc -> new Chasm("broken", loc));
        tileMap.put(39, loc -> new AnimatedTile(loc, 0));
        tileMap.put(40, loc -> new AnimatedTile(loc, 1));
    }
    
}

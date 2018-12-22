
package tiles;

import tiles.assets.*;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Function;
import javax.swing.ImageIcon;
import level.Location;
import level.RoomBuilder;
import listeners.Interactable;

/**
 *
 * @author Adam Whittaker
 */
public class Tile{
    
    public transient ImageIcon image;
    public String name;
    public boolean treadable;
    public boolean flammable;
    public boolean transparent;
    public Interactable interactable;
    
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
    
    public static Tile wall(Location loc, int x, int y){
        if(loc.feeling.wallChance.chance()) return new DecoratedWall(loc, x, y);
        return new Tile("wall", loc, false, false, false);
    }
    
    public static Tile floor(Location loc){
        if(loc.feeling.trapChance.chance()) return RoomBuilder.getRandomTrap(loc);
        if(loc.feeling.floorChance.chance()) return new Tile("decofloor", loc, true, false, true);
        return new Tile("floor", loc, true, false, true);
    }
    
    public void paint(Graphics2D g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
    public boolean isFloor(){
        return name.equals("floor")||name.equals("decofloor");
    }
    
    public static int getID(Tile tile){
        if(tile==null) return -1;
        if(tile instanceof Serializable) return -99;
        try{
            return IDmap.get(tile.name);
        }catch(NullPointerException e){
            return ((Water)tile).x-3;
        }
    }
    
    public static Tile getTile(int id, Location loc){
        if(id==-1) return null;
        return tileMap.get(id).apply(loc);
    }
    
    public static HashMap<String, Integer> IDmap = new HashMap<>();
    public static HashMap<Integer, Function<Location, Tile>> tileMap = new HashMap<>();
    static{
        IDmap.put("void", 0);
        IDmap.put("floor", 1);
        IDmap.put("lowgrass", 2);
        IDmap.put("emptywell", 3);
        IDmap.put("wall", 4);
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
        tileMap.put(0, (Serializable & Function<Location, Tile>)loc -> new Chasm("void", loc));
        tileMap.put(1, (Serializable & Function<Location, Tile>)loc -> new Tile("floor", loc, true, false, true));
        tileMap.put(2, (Serializable & Function<Location, Tile>)loc -> new Grass(loc, false));
        tileMap.put(3, (Serializable & Function<Location, Tile>)loc -> new Tile("emptywell", loc, true, false, true));
        tileMap.put(4, (Serializable & Function<Location, Tile>)loc -> new Tile("wall", loc, false, false, false));
        tileMap.put(5, (Serializable & Function<Location, Tile>)loc -> new Door(loc));
        tileMap.put(8, (Serializable & Function<Location, Tile>)loc -> new DepthEntrance(loc));
        tileMap.put(9, (Serializable & Function<Location, Tile>)loc -> new DepthExit(loc));
        tileMap.put(10, (Serializable & Function<Location, Tile>)loc -> new Tile("embers", loc, true, false, true));
        tileMap.put(12, (Serializable & Function<Location, Tile>)loc -> new Tile("pedestal", loc, true, false, true));
        tileMap.put(14, (Serializable & Function<Location, Tile>)loc -> new Barricade("barricade", loc));
        tileMap.put(15, (Serializable & Function<Location, Tile>)loc -> new Tile("specialfloor", loc, true, false, true));
        tileMap.put(16, (Serializable & Function<Location, Tile>)loc -> new Grass(loc, true));
        tileMap.put(17, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("green", loc));
        tileMap.put(18, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("orange", loc));
        tileMap.put(19, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("yellow", loc));
        tileMap.put(21, (Serializable & Function<Location, Tile>)loc -> new Tile("decofloor", loc, true, false, true));
        //@Unfinished LockedDepthExit
        //tileMap.put(22, );
        //tileMap.put(23, );
        tileMap.put(24, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("purple", loc));
        //@Unfinished sign
        //tileMap.put(25, );
        tileMap.put(26, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("red", loc));
        tileMap.put(27, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("blue", loc));
        //@Unfinished well
        //tileMap.put(28, );
        tileMap.put(29, (Serializable & Function<Location, Tile>)loc -> new Tile("statue", loc, false, false, true));
        tileMap.put(30, (Serializable & Function<Location, Tile>)loc -> new Tile("specialstatue", loc, false, false, true));
        tileMap.put(31, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("bear", loc));
        tileMap.put(32, (Serializable & Function<Location, Tile>)loc -> TrapBuilder.getTrap("silver", loc));
        tileMap.put(33, (Serializable & Function<Location, Tile>)loc -> new Barricade("bookshelf", loc));
        tileMap.put(34, (Serializable & Function<Location, Tile>)loc -> new AlchemyPot(loc));
        tileMap.put(35, (Serializable & Function<Location, Tile>)loc -> new Chasm("floor", loc));
        tileMap.put(36, (Serializable & Function<Location, Tile>)loc -> new Chasm("specialfloor", loc));
        tileMap.put(37, (Serializable & Function<Location, Tile>)loc -> new Chasm("wall", loc));
        tileMap.put(38, (Serializable & Function<Location, Tile>)loc -> new Chasm("broken", loc));
        tileMap.put(-3, (Serializable & Function<Location, Tile>)loc -> new Water(loc, 0));
        tileMap.put(-2, (Serializable & Function<Location, Tile>)loc -> new Water(loc, 1));
    }
    
}

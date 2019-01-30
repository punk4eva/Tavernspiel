
package logic.mementoes;

import java.io.Serializable;
import java.util.LinkedList;
import level.Location;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the stored form of the Non-serializable parts of the
 * Area.
 */
public class AreaMemento implements Serializable{
    
    private static final long serialVersionUID = 732194622;
    
    private final String locName;
    private final int[][] tileMap;
    private final LinkedList<TileMemento> states = new LinkedList<>();
    
    /**
     * Creates an instance.
     * @param loc The Location.
     * @param map The map of Tiles.
     */
    public AreaMemento(Location loc, Tile[][] map){
        locName = loc.name;
        tileMap = new int[map.length][map[0].length];
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                tileMap[y][x] = Tile.getID(map[y][x]);
                if(tileMap[y][x]==-99) states.add(new TileMemento(x, y, (Serializable) map[y][x]));
            }
        }
    }
    
    /**
     * Returns the Location from this state store.
     * @return
     */
    public Location getLocation(){
        return Location.LOCATION_MAP.get(locName);
    }
    
    /**
     * Returns the Tile map from this state store.
     * @return
     */
    public Tile[][] getMap(){
        Location loc = Location.LOCATION_MAP.get(locName);
        Tile[][] map = new Tile[tileMap.length][tileMap[0].length];
        states.forEach((tm) -> {
            map[tm.y][tm.x] = (Tile)tm.tc;
        });
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x]==null) map[y][x] = Tile.getTile(tileMap[y][x], loc);
            }
        }
        return map;
    }
    
    private static class TileMemento implements Serializable{
        
        private static final long serialVersionUID = 732194632137189L;
        
        int x, y;
        Serializable tc;
        
        protected TileMemento(int x_, int y_, Serializable t){
            x = x_;
            y = y_;
            t = tc;
        }
        
    }
    
}

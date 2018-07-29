
package logic.mementoes;

import java.io.Serializable;
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
            }
        }
    }
    
    /**
     * Returns the Location from this state store.
     * @return
     */
    public Location getLocation(){
        return Location.locationMap.get(locName);
    }
    
    /**
     * Returns the Tile map from this state store.
     * @return
     */
    public Tile[][] getMap(){
        Location loc = Location.locationMap.get(locName);
        Tile[][] map = new Tile[tileMap.length][tileMap[0].length];
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                map[y][x] = Tile.getTile(tileMap[y][x], loc);
            }
        }
        return map;
    }
    
}

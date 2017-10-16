
package designer;

import java.io.Serializable;
import level.Location;
import logic.Distribution;
import tiles.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class TileSelection implements Serializable{
    
    private static final long serialVersionUID = -1384476066;
    
    final Distribution distrib;
    final String[] tiles;
    boolean boundary = false;
    
    public TileSelection(int[] chances, String[] t){
        distrib = new Distribution(chances);
        tiles = t;
    }
    
    public Tile getTile(Location loc){
        return new Tile(tiles[(int)distrib.next()], loc);
    }
    
    
    final static TileSelection wall = new TileSelection(new int[]{1, 9}, new String[]{"specialwall", "wall"});
    final static TileSelection floor = new TileSelection(new int[]{1, 9}, new String[]{"decofloor", "floor"});
    final static TileSelection door = new TileSelection(null, null){
        @Override
        public Door getTile(Location loc){
            boolean b = Distribution.chance(1, 4);
            return new Door(loc, false, b);
        }
    };
    
}

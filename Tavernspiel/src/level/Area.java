
package level;

import exceptions.AreaCoordsOutOfBoundsException;
import java.awt.Dimension;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Area{
    
    public Tile[][] map;
    public Dimension dimension;
    public Location location;
    
    
    public Area(Dimension dim, Location loc){
        dimension = dim;
        location = loc;
        map = createBlank();
    }
    
    private Tile[][] createBlank(){
        Tile[][] ret = new Tile[dimension.height][dimension.width];
        Loopable loop = new Loopable(this, () -> {return new Tile("void");});
        return loop.map();
    }
    
    public void blit(Area area, int x1, int y1) throws AreaCoordsOutOfBoundsException{
        if(!withinBounds(x1, y1)||
                !withinBounds(x1+area.dimension.width, y1+area.dimension.height))
            throw new AreaCoordsOutOfBoundsException("Coords out of bounds.");
        for(int y=y1;y<y1+area.dimension.width;y++){
            for(int x=x1;x<x1+area.dimension.height;x++){
                map[y][x] = area.map[y-y1][x-x1];
            }
        }
    }
    
    public boolean withinBounds(int x, int y){
        return x>=0&&y>=0&&x<dimension.width&&y<dimension.height;
    }
    
}


package designer;

import fileLogic.FileHandler;
import java.awt.Dimension;
import java.io.Serializable;
import level.Area;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class AreaTemplate implements Serializable{
    
    private static final long serialVersionUID = -2047903673;
    
    TileSelection[][] map;
    Location location;
    
    public AreaTemplate(int width, int height, Location loc){
        map = new TileSelection[height][width];
    }
    
    public void serialize(String filepath){
        FileHandler.serialize(this, filepath);
    }
    
    public AreaTemplate deserialize(String filepath){
        return (AreaTemplate) FileHandler.deserialize(filepath);
    }
    
    public Area toArea(){
        Area area = new Area(new Dimension(map[0].length, map.length), location);
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                area.map[y][x] = map[y][x].getTile(location);
                if(map[y][x].boundary) area.map[y][x].treadable = false;
            }
        }
        return area;
    }
    
    public void insert(int x, int y, TileSelection tl){
        map[y][x] = tl;
    }
    
}

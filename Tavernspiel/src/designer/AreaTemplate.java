
package designer;

import logic.FileHandler;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    transient Location location;
    final Dimension dimension;
    
    /**
     * Creates a new Instance.
     * @param dim The dimension of the Area.
     * @param loc The Location.
     */
    public AreaTemplate(Dimension dim, Location loc){
        map = new TileSelection[dim.height][dim.width];
        location = loc;
        dimension = dim;
    }
    
    /**
     * Wraps a TileSelection map.
     * @param tS The 2D array.
     * @param loc The Location.
     */
    protected AreaTemplate(TileSelection[][] tS, Location loc){
        map = tS;
        location = loc;
        dimension = new Dimension(map[0].length, map.length);
    }
    
    /**
     * Serializes this Object.
     * @param filepath The destination filepath.
     */
    public void serialize(String filepath){
        FileHandler.serialize(this, filepath);
    }
    
    /**
     * Deserializes the Object from the given filepath.
     * @param filepath
     * @return
     */
    public static AreaTemplate deserialize(String filepath){
        return (AreaTemplate) FileHandler.deserialize(filepath);
    }
    
    /**
     * Converts this AreaTemplate into an Area.
     * @return
     */
    public Area toArea(){
        Area area = new Area(new Dimension(map[0].length, map.length), location);
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x]!=null){
                    area.map[y][x] = map[y][x].getTile(location);
                    if(map[y][x].boundary) area.map[y][x].treadable = false;
                }
            }
        }
        return area;
    }
    
    /**
     * Inserts the given TileSelection into the given coordinates of the map.
     * @param x
     * @param y
     * @param tl
     */
    public void insert(int x, int y, TileSelection tl){
        map[y][x] = tl;
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        location = Location.locationMap.get((String) in.readObject());
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(location.name);
        System.out.println("LName: " + location.name);
    }
    
}

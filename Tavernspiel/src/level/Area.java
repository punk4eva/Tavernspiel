
package level;

import containers.Floor;
import exceptions.AreaCoordsOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import java.awt.Dimension;
import listeners.AreaEvent;
import listeners.ZipHandler;
import listeners.AreaListener;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Area implements AreaListener{
    
    public Tile[][] map;
    public Dimension dimension;
    public Location location;
    public final int zipcode = ZipHandler.next();
    
    
    public Area(Dimension dim, Location loc){
        dimension = dim;
        location = loc;
        location.addAreaListener(this);
        map = createBlank();
    }
    
    private Tile[][] createBlank(){
        Tile[][] ret = new Tile[dimension.height][dimension.width];
        Loopable loop = new Loopable(this, () -> {return new Tile("void", location);});
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

    @Override
    public void areaActedUpon(AreaEvent ae){
        if(ae.getCode()==zipcode){
            switch(ae.getAction()){
                case "FELLINTOCHASM":
                    //if(nextLevel==null){
                    //    nextLevel.fullLoad();
                    //    hero.changeLevel(nextLevel.zipcode);
                    //    hero.addBuff(BuffBuilder.bleeding(level*2));
                    //}else{
                    //    @unfinished
                    //}
                    break;
                case "BURN": burn(ae.getX(), ae.getY());
                    break;
            }
        }
    }
    
    protected void burn(int x, int y){
        Floor floor = null;
        try{
            floor = new Floor(map[y][x].receptacle);
        }catch(ReceptacleOverflowException ignore){}
        map[y][x] = new Tile("embers", location);
        floor.keep(item -> !item.flammable);
        map[y][x].receptacle = floor;
    }
    
}

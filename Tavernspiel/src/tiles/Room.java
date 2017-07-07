
package tiles;

import java.awt.Dimension;
import level.Area;
import level.Location;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Room extends Area{
    
    public Room(Dimension dim, Location loc){
        super(dim, loc);
    }
    
    public static Room genBlank(Location loc){
        return new Room(new Dimension(Distribution.getRandomInclusiveInt(4, 16),
        Distribution.getRandomInclusiveInt(4, 16)), loc);
    }
    
    protected void paintAndPave(){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(y==0||x==0||y==dimension.height-1||x==dimension.width-1){
                    if(Distribution.chance(1, 10)) map[y][x] = new Tile("specialwall");
                    else map[y][x] = new Tile("wall");
                }else map[y][x] = new Tile("floor");
            }
        }
    }
    
    protected void water(Distribution d){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(d.chance()) map[y][x] = new AnimatedTile("water");
            }
        }
        
        boolean spreads = true;
        Tile water = new AnimatedTile("water");
        for(int n=3;spreads;n++){
            spreads = false;
            Distribution ch = new Distribution(1, n);
            for(int y=0;y<dimension.height;y++){
                for(int x=0;x<dimension.width;x++){
                    if(map[y][x].equals("water")){
                        if(ch.chance()){
                            spreads = true;
                            spread(water, x, y);
                        }
                    }
                }
            }
        }
    }
    
    protected void grass(Distribution d){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(d.chance()) map[y][x] = new Tile("lowgrass");
            }
        }
        
        boolean spreads = true;
        Tile lowgrass = new Tile("lowgrass");
        Tile highgrass = new Tile("highgrass");
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=0;y<dimension.height;y++){
                for(int x=0;x<dimension.width;x++){
                    if(map[y][x].equals("lowgrass")&&Distribution.chance(1, n)){
                        spreads = true;
                        spread(lowgrass, x, y);
                    }
                }
            }
        }
        
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(map[y][x].equals("lowgrass")&&Distribution.chance(1, 2)){
                    map[y][x] = highgrass;
                }
            }
        }
    }
    
    protected void spread(Tile t, int x, int y){
        if(withinBounds(x+1, y)) map[y][x+1] = t;
        if(withinBounds(x-1, y)) map[y][x-1] = t;
        if(withinBounds(x, y+1)) map[y+1][x] = t;
        if(withinBounds(x, y-1)) map[y-1][x] = t;
    }
    
}

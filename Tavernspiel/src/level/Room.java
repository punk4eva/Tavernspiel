
package level;

import java.awt.Dimension;
import logic.Distribution;
import tiles.AnimatedTile;
import tiles.Tile;

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
    
    public static Room genStandard(Location loc){
        Room room = genBlank(loc);
        room.paintAndPave();
        if(loc.waterBeforeGrass){
            room.water();
            room.grass();
        }else{
            room.grass();
            room.water();
        }
        room.addShaders();
        return room;
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
    
    protected void water(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("floor")&&location.waterGenChance.chance()) 
                    map[y][x] = new AnimatedTile("water", x%2);
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            Distribution ch = new Distribution(1, n);
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x].equals("water")){
                        if(ch.chance()){
                            spreads = true;
                            spreadAnimated("water", x, y);
                        }
                    }
                }
            }
        }
    }
    
    protected void grass(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("floor")&&location.grassGenChance.chance()) 
                    map[y][x] = new Tile("lowgrass");
            }
        }
        
        boolean spreads = true;
        Tile lowgrass = new Tile("lowgrass");
        Tile highgrass = new Tile("highgrass");
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x].equals("lowgrass")&&Distribution.chance(1, n)){
                        spreads = true;
                        spread(lowgrass, x, y);
                    }
                }
            }
        }
        
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
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
    
    protected void spreadAnimated(String t, int x, int y){
        if(withinBounds(x+1, y)) map[y][x+1] = new AnimatedTile(t, x%2);
        if(withinBounds(x-1, y)) map[y][x-1] = new AnimatedTile(t, x%2);;
        if(withinBounds(x, y+1)) map[y+1][x] = new AnimatedTile(t, x%2);;
        if(withinBounds(x, y-1)) map[y-1][x] = new AnimatedTile(t, x%2);;
    }
    
    protected void addShaders(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("water")){
                    AnimatedTile tile = (AnimatedTile) map[y][x];
                    tile.addShaders(genShaderString(x, y));
                    map[y][x] = tile;
                }
            }
        }
    }
    
    private String genShaderString(int x, int y){
        String ret = "";
        if(map[y-1][x].equals("water")) ret += "n";
        if(map[y][x+1].equals("water")) ret += "e";
        if(map[y+1][x].equals("water")) ret += "s";
        if(map[y][x-1].equals("water")) ret += "w";
        return ret;
    }
    
}

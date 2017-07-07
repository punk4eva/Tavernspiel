
package level;

import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Loopable implements Runnable{

    private Area area;
    private Function function;
    private int yLower = 0;
    private int yUpper;
    private int xLower = 0;
    private int xUpper;
    
    @Override
    public void run(){
        for(int y=yLower;y<yUpper;y++){
            for(int x=xLower;x<xUpper;x++){
                area.map[y][x] = function.act();
            }
        }
    }
    
    /**
    public void run(int x, int y){
        for(int y1=yLower;y1<yUpper;y1++){
            for(int x1=xLower;x1<xUpper;x1++){
                area.map[y1][x1] = function.act(x, y);
            }
        }
    }
    
    public void run(Tile t){
        for(int y=yLower;y<yUpper;y++){
            for(int x=xLower;x<xUpper;x++){
                area.map[y][x] = function.act(t);
            }
        }
    }**/
    
    public interface Function{
        public Tile act();
        /**
        public Tile act(int x, int y);
        public Tile act(Tile t);**/
    } 
    
    public Loopable(Area a, Function f){
        area = a;
        yUpper = a.dimension.height;
        xUpper = a.dimension.width;
    }
    
    public Loopable(Area a, int yl, int yu, int xl, int xu, Function f){
        yLower = yl;
        yUpper = yu;
        xLower = xl;
        xUpper = xu;
        area = a;
    }
    
    public Tile[][] map(){
        return area.map;
    }
    
}

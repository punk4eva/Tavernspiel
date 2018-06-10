
package creatureLogic;

import gui.Shaders;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import level.Area;
import logic.ConstantFields;
import testUtilities.TestUtil;

/**
 *
 * @author Adam Whittaker
 */
public class VisibilityOverlay extends FieldOfView{
    
    //0: unexplored
    //1: explored
    //2: visible
    public final int[][] map;
    public static final Shaders unexploredFog = new Shaders();
    public static final Shaders exploredFog = new Shaders(ConstantFields.exploredColor, 
            255.0/(double)ConstantFields.exploredColor.getAlpha());

    public VisibilityOverlay(int x, int y, int range, Area area){
        super(x, y, range);
        map = new int[area.dimension.height][area.dimension.width];
    }
    
    @Override
    public void update(int x_, int y_, Area area){
        x = x_;
        y = y_;
        clearVisible(area);
        followGradient(x_, y_, area);
    }
    
    @Override
    public void followGradient(int x_, int y_, Area area){
        map[y_][x_] = 2;
        double inc = Math.atan(0.08D);
        List<ViewRunner> runners = new LinkedList<>();
        for(double theta=0;theta<2d*Math.PI;theta+=inc) runners.add(new ViewRunner(x_, y_, range, theta));
        while(!runners.isEmpty()){
            runners.removeIf(r -> r.fuel==0);
            runners.stream().forEach(r -> {
                Integer[] c = r.run(area);
                map[c[1]][c[0]] = 2;
            });
        }
    }
    
    private void clearVisible(Area area){
        for(int ny=0;ny<area.dimension.height;ny++)
            for(int nx=0;nx<area.dimension.width;nx++)
                if(map[ny][nx]==2) map[ny][nx] = 1;
    }
    
    @Override
    public boolean isVisible(int x_, int y_){
        return map[y_][x_]==2;
    }
    
    public boolean isUnexplored(int x_, int y_){
        return map[y_][x_]==0;
    }
    
    public boolean isExplored(int x_, int y_){
        return map[y_][x_]==1;
    }
    
}

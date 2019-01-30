/*
 * Copyright 2018 Adam Whittaker.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pathfinding.generation;

import java.util.LinkedList;
import level.Area;
import logic.Distribution;
import static logic.Distribution.r;
import pathfinding.Graph;
import pathfinding.Point;
import pathfinding.Point.Direction;
import tiles.Tile;
import tiles.assets.Barricade;
import tiles.assets.Door;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds a maze using a modified drunken walk algorithm.
 */
public class DrunkenCorridorBuilder{
    
    private final Area area;
    private final Graph graph;
    
    private final int stepSize;
    private int iterNum;
    private final double gaussianQuotient, splitChance;
    private final int[] coords;
    
    public DrunkenCorridorBuilder(Area _area, int sD, int it, double gQ, double sC, int... c){
        area = _area;
        if(area.graph==null) area.graph = graph = new Graph(area, null);
        else graph = area.graph;
        stepSize = sD;
        iterNum = it;
        gaussianQuotient = gQ;
        splitChance = sC;
        coords = c;
    }
    
    /**
     * Generates a Labyrinth from scratch.
     * @return
     */
    public Area build(){
        Point z, p;
        if(coords==null) z = graph.map[r.nextInt(area.dimension.height/2)+area.dimension.height/4][r.nextInt(area.dimension.width/2)+area.dimension.width/4];
        else z = graph.map[coords[1]][coords[0]];
        z.isCorridor = true;
        LinkedList<Point> branches = new LinkedList<>();
        branches.add(z);
        while(!branches.isEmpty()){
            p = branches.remove(Distribution.r.nextInt(branches.size()));
            LinkedList<Point> possible = new LinkedList<>();
            for(Direction dir : Point.Direction.values()){ try{
                Point np = graph.map[stepSize*dir.y+p.y][stepSize*dir.x+p.x];
                if(!np.isCorridor&&np.x>0&&np.x<area.dimension.width-1
                        &&np.y>0&&np.y<area.dimension.height-1) possible.add(np);
            }catch(ArrayIndexOutOfBoundsException e){}}
            if(possible.isEmpty()){
                if(p.cameFrom!=null) branches.add(p.cameFrom);
            }else{
                Point np = decidePoint(possible);
                np.cameFrom = p;
                np.isCorridor = true;
                branches.add(np);
                iterNum--;
                if(iterNum<0) break;
                if(Distribution.r.nextDouble()<splitChance) branches.add(p);
            }
        }
        buildCorridors();
        return area;
    }
    
    private void buildCorridors(){
        Point p;
        for(int y=0;y<area.dimension.height;y++){
            for(int x=0;x<area.dimension.width;x++){
                p = graph.map[y][x];
                if(p.cameFrom!=null) extendCorridor(p, p.cameFrom);
            }
        }
        //fillDeadEnds();
    }
    
    private void extendCorridor(Point a, Point b){
        if(a.x!=b.x){
            for(int x=Math.min(a.x, b.x);x<=Math.max(a.x, b.x);x++){
                area.map[a.y][x] = Tile.floor(area.location);
                if(area.map[a.y-1][x]==null) area.map[a.y-1][x] = Tile.wall(area.location, x, a.y-1);
                if(area.map[a.y+1][x]==null) area.map[a.y+1][x] = Tile.wall(area.location, x, a.y+1);
            }
        }else{
            for(int y=Math.min(a.y, b.y);y<=Math.max(a.y, b.y);y++){
                area.map[y][a.x] = Tile.floor(area.location);
                if(area.map[y][a.x-1]==null) area.map[y][a.x-1] = Tile.wall(area.location, a.x-1, y);
                if(area.map[y][a.x+1]==null) area.map[y][a.x+1] = Tile.wall(area.location, a.x+1, y);
            }
        }
        fillCorners(a);
        fillCorners(b);
    }
    
    private void fillCorners(Point p){
        area.map[p.y-1][p.x-1] = Tile.wall(area.location, p.y-1, p.x-1);
        area.map[p.y-1][p.x+1] = Tile.wall(area.location, p.y-1, p.x+1);
        area.map[p.y+1][p.x-1] = Tile.wall(area.location, p.y+1, p.x-1);
        area.map[p.y+1][p.x+1] = Tile.wall(area.location, p.y+1, p.x+1);
        if(area.map[p.y-1][p.x]==null) area.map[p.y-1][p.x] = Tile.wall(area.location, p.y-1, p.x);
        if(area.map[p.y+1][p.x]==null) area.map[p.y+1][p.x] = Tile.wall(area.location, p.y+1, p.x);
        if(area.map[p.y][p.x-1]==null) area.map[p.y][p.x-1] = Tile.wall(area.location, p.y, p.x-1);
        if(area.map[p.y][p.x+1]==null) area.map[p.y][p.x+1] = Tile.wall(area.location, p.y, p.x+1);
    }
    
    private Point decidePoint(LinkedList<Point> points){
        double[] scores = new double[points.size()];
        scores[0] = score(points.get(0));
        for(int n=1;n<scores.length;n++)
            scores[n] = scores[n-1] + score(points.get(n));
        double rand = r.nextDouble()*scores[scores.length-1];
        for(int n=0;n<scores.length;n++)
            if(rand<=scores[n]) return points.get(n);
        throw new IllegalStateException("No more space to build corridors.");
    }
    
    private double score(Point p){
        return Math.pow(Math.E, -(Math.pow(p.x-area.dimension.width/2, 2)+Math.pow(p.y-area.dimension.height/2, 2))/gaussianQuotient);
    }
    
    /**
     * Fills an existing Area with a labyrinth.
     */
    public void fillExistingArea(){
        Point p, z = graph.map[coords[1]][coords[0]];
        z.isCorridor = true;
        LinkedList<Point> branches = new LinkedList<>();
        branches.add(z);
        while(!branches.isEmpty()){
            p = branches.remove(Distribution.r.nextInt(branches.size()));
            LinkedList<Point> possible = new LinkedList<>();
            for(Direction dir : Point.Direction.values()){ try{
                Point np = graph.map[stepSize*dir.y+p.y][stepSize*dir.x+p.x];
                np = getStructuralPoint(np, p); 
                if(np!=null) possible.add(np);
            }catch(ArrayIndexOutOfBoundsException e){}}
            if(possible.isEmpty()){
                if(p.cameFrom!=null) branches.add(p.cameFrom);
            }else{
                Point np = decidePoint(possible);
                np.cameFrom = p;
                np.isCorridor = true;
                branches.add(np);
                iterNum--;
                if(iterNum<0) break;
                if(Distribution.r.nextDouble()<splitChance) branches.add(p);
            }
        }
        buildCorridors();
    }
    
    private Point getStructuralPoint(Point p, Point came){
        if(((p.x <= 0 || p.x >= area.dimension.width-1) || p.y <= 0)||p.y>=area.dimension.height-1) return null;
        if(!p.isCorridor) return p;
        return getDoorIntercept(p, came);
    }
    
    private Point getDoorIntercept(Point a, Point b){
        if(a.x!=b.x) for(int x=Math.min(a.x, b.x);x<=Math.max(a.x, b.x);x++)
            if(area.map[a.y][x] instanceof Door || area.map[a.y][x] instanceof Barricade){
                if(b.x>a.x) return graph.map[a.y][x+1];
                return graph.map[a.y][x-1];
            }
        else for(int y=Math.min(a.y, b.y);y<=Math.max(a.y, b.y);y++)
            if(area.map[y][a.x] instanceof Door || area.map[y][a.x] instanceof Barricade){
                if(b.y>a.y) return graph.map[y+1][a.x];
                return graph.map[y-1][a.x];
            }
        return null;
    }
    
}

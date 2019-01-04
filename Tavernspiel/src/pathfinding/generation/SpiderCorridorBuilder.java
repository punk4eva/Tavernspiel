/*
 * Copyright 2019 Adam Whittaker.
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

import level.Area;
import logic.Distribution;
import pathfinding.Graph;
import pathfinding.Point;
import pathfinding.PriorityQueue;
import pathfinding.Searcher;
import static pathfinding.Searcher.DIRECTIONS;
import pathfinding.Waypoint;
import tiles.Tile;
import tiles.assets.Barricade;
import tiles.assets.Door;

/**
 *
 * @author Adam Whittaker
 */
public class SpiderCorridorBuilder{
    
    private final Area area;
    private final boolean[][] corridors;
    private final int windyness;
    private final boolean decayActive;
    
    private final class SpiderCorridorAlgorithm extends Searcher{
        
        SpiderCorridorAlgorithm(int windyness){
            super(area.graph, area);
            addCheck = (from, to) -> /*to.currentCost > from.currentCost + to.movementCost &&*/ to.checked==null || !to.checked;
            frontier = new PriorityQueue<>(p -> Distribution.r.nextInt(windyness) +  p.currentCost);
        }
        
        @Override
        public void checkedFloodfill(Point start){
            graph.reset();
            frontier.clear();
            start.currentCost = 0;
            frontier.add(start);
            start.checked = true;
            int nx, ny;
            while(!frontier.isEmpty()){
                Point p = frontier.poll();
                for(Point.Direction dir : DIRECTIONS){
                    nx = p.x+dir.x;
                    ny = p.y+dir.y;
                    if(area.withinBounds(nx-1, ny-1)&&area.withinBounds(nx+1, ny+1)){
                        if((area.map[ny][nx]==null&&addCheck.check(p, graph.map[ny][nx]))||area.map[ny][nx] instanceof Door||area.map[ny][nx] instanceof Barricade){
                            graph.map[ny][nx].checked = true;
                            graph.map[ny][nx].cameFrom = p;
                            graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                            frontier.add(graph.map[ny][nx]);
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param w The windyness factor of the corridors.
     * @param decay Whether to decay walls into cavities.
     */
    public SpiderCorridorBuilder(Area a, int w, boolean decay){
        area = a;
        corridors = new boolean[a.dimension.height][a.dimension.width];
        if(area.graph==null) area.graph = new Graph(area, null);
        windyness = w;
        decayActive = decay;
    }
    
    private void extend(Point p, Point a, Point b){
        area.map[b.y][b.x] = Tile.floor(area.location);
        if(a.x!=b.x){
            if(area.map[b.y-1][b.x]==null) area.map[b.y-1][b.x] = Tile.wall(area.location, b.x, b.y-1);
            if(area.map[b.y+1][b.x]==null) area.map[b.y+1][b.x] = Tile.wall(area.location, b.x, b.y+1);
            if(area.map[a.y-1][a.x]==null) area.map[a.y-1][a.x] = Tile.wall(area.location, a.x, a.y-1);
            if(area.map[a.y+1][a.x]==null) area.map[a.y+1][a.x] = Tile.wall(area.location, a.x, a.y+1);
            corridors[b.y-1][b.x] = true;
            corridors[b.y+1][b.x] = true;
            corridors[a.y-1][a.x] = true;
            corridors[a.y+1][a.x] = true;
            if(p==null||a.x==p.x){
                if(area.map[a.y-1][a.x-1]==null) area.map[a.y-1][a.x-1] = Tile.wall(area.location, a.x-1, a.y-1);
                if(area.map[a.y-1][a.x+1]==null) area.map[a.y-1][a.x+1] = Tile.wall(area.location, a.x+1, a.y-1);
                if(area.map[a.y+1][a.x-1]==null) area.map[a.y+1][a.x-1] = Tile.wall(area.location, a.x-1, a.y+1);
                if(area.map[a.y+1][a.x+1]==null) area.map[a.y+1][a.x+1] = Tile.wall(area.location, a.x+1, a.y+1);
                corridors[a.y-1][a.x-1] = true;
                corridors[a.y-1][a.x+1] = true;
                corridors[a.y+1][a.x-1] = true;
                corridors[a.y+1][a.x+1] = true;
            }
        }else{
            if(area.map[b.y][b.x-1]==null) area.map[b.y][b.x-1] = Tile.wall(area.location, b.x-1, b.y);
            if(area.map[b.y][b.x+1]==null) area.map[b.y][b.x+1] = Tile.wall(area.location, b.x+1, b.y);
            if(area.map[a.y][a.x-1]==null) area.map[a.y][a.x-1] = Tile.wall(area.location, a.x-1, a.y);
            if(area.map[a.y][a.x+1]==null) area.map[a.y][a.x+1] = Tile.wall(area.location, a.x+1, a.y);
            corridors[b.y][b.x-1] = true;
            corridors[b.y][b.x+1] = true;
            corridors[a.y][a.x-1] = true;
            corridors[a.y][a.x+1] = true;
            if(p==null||a.x!=p.x){
                if(area.map[a.y-1][a.x-1]==null) area.map[a.y-1][a.x-1] = Tile.wall(area.location, a.x-1, a.y-1);
                if(area.map[a.y-1][a.x+1]==null) area.map[a.y-1][a.x+1] = Tile.wall(area.location, a.x+1, a.y-1);
                if(area.map[a.y+1][a.x-1]==null) area.map[a.y+1][a.x-1] = Tile.wall(area.location, a.x-1, a.y+1);
                if(area.map[a.y+1][a.x+1]==null) area.map[a.y+1][a.x+1] = Tile.wall(area.location, a.x+1, a.y+1);
                corridors[a.y-1][a.x-1] = true;
                corridors[a.y-1][a.x+1] = true;
                corridors[a.y+1][a.x-1] = true;
                corridors[a.y+1][a.x+1] = true;
            }
        }
    }
    
    private void growCavities(){
        for(int x=0;x<area.dimension.width;x++) for(int y=0;y<area.dimension.height;y++)
            if(canDecayWall(x, y)) area.map[y][x] = Tile.floor(area.location);
    }
    
    private boolean canDecayWall(int x, int y){
        if(area.map[y][x] == null || !area.map[y][x].name.endsWith("wall") || area.graph.map[y][x].checked == null||!area.graph.map[y][x].checked) return false;
        int floors = 0;
        if(area.map[y-1][x-1]==null) return false;
        if(area.map[y-1][x-1].treadable) floors++;
        if(area.map[y-1][x+1]==null) return false;
        if(area.map[y-1][x+1].treadable) floors++;
        if(area.map[y+1][x-1]==null) return false;
        if(area.map[y+1][x-1].treadable) floors++;
        if(area.map[y+1][x+1]==null) return false;
        if(area.map[y+1][x+1].treadable) floors++;
        if(area.map[y-1][x]==null) return false;
        if(area.map[y-1][x].treadable) floors++;
        if(area.map[y+1][x]==null) return false;
        if(area.map[y+1][x].treadable) floors++;
        if(area.map[y][x-1]==null) return false;
        if(area.map[y][x-1].treadable) floors++;
        if(area.map[y][x+1]==null) return false;
        if(area.map[y][x+1].treadable) floors++;
        return floors>2;
    }
    
    /**
     * Generates and builds all corridors in the Area.
     * @return A map of corridors.
     */
    public boolean[][] build(){
        SpiderCorridorAlgorithm sca = new SpiderCorridorAlgorithm(windyness);
        Point p = getFreePoint();
        sca.checkedFloodfill(p);
        if(area.map[p.y-1][p.x-1]==null) area.map[p.y-1][p.x-1] = Tile.wall(area.location, p.x-1, p.y-1);
        if(area.map[p.y-1][p.x+1]==null) area.map[p.y-1][p.x+1] = Tile.wall(area.location, p.x+1, p.y-1);
        if(area.map[p.y+1][p.x-1]==null) area.map[p.y+1][p.x-1] = Tile.wall(area.location, p.x-1, p.y+1);
        if(area.map[p.y+1][p.x+1]==null) area.map[p.y+1][p.x+1] = Tile.wall(area.location, p.x+1, p.y+1);
        for(Waypoint w : area.graph.waypoints) buildCorridor(w);
        if(decayActive) growCavities();
        return corridors;
    }
    
    /**
     * Builds a corridor out of a singular Path.
     * @param path
     */
    private void buildCorridor(Point p){
        Point np, pp = null;
        while(p.cameFrom!=null){
            np = p.cameFrom;
            extend(pp, p, np);
            pp = p;
            p = np;
        }
    }
    
    private Point getFreePoint(){
        int x, y;
        for(Waypoint p : area.graph.waypoints){
            for(Point.Direction dir : DIRECTIONS){
                x = p.x+dir.x;
                y = p.y+dir.y;
                if(area.map[y][x]==null) return area.graph.map[y][x];
            }
        }
        throw new IllegalStateException("No point found!");
    }
    
}

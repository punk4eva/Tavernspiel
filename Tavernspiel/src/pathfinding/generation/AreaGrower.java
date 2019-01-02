/*
 * Copyright 2018 punk4eva.
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

import java.awt.Dimension;
import level.Area;
import level.Location;
import logic.Distribution;
import pathfinding.Graph;
import pathfinding.Point;
import tiles.Tile;

/**
 *
 * @author punk4eva
 */
public class AreaGrower{
    
    private final Area area;
    private final Graph graph;
    
    private final double startChance;
    private final int minLimit, maxLimit, birthMax, birthMin;
    private int iterNum;
    private final boolean aliveEdges;
    
    public AreaGrower(Dimension dim, Location loc, double sC, int miL, int maL, int bMi, int bMa, int it, boolean aE){
        area = new Area(dim, loc);
        area.graph = graph = new Graph(area, null);
        startChance = sC;
        minLimit = miL;
        iterNum = it;
        maxLimit = maL;
        birthMax = bMa;
        birthMin = bMi;
        aliveEdges = aE;
    }
    
    private void initialize(){
        for(int x=0;x<graph.map[0].length;x++) for(Point[] row : graph.map){
            if(Distribution.r.nextDouble()<startChance)
                row[x].isCorridor = true;
        }
    }
    
    private int getNeighborNum(int x, int y){
        int n = 0;
        try{
            if(graph.map[y-1][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){
            if(aliveEdges) n++;
        }
        try{
        if(graph.map[y-1][x].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y-1][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y+1][x-1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y+1][x].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        try{
        if(graph.map[y+1][x+1].isCorridor) n++;
        }catch(ArrayIndexOutOfBoundsException e){if(aliveEdges) n++;}
        return n;
    }
    
    private void iterate(){
        int n;
        for(int x=0;x<graph.map[0].length;x++){
            for(int y=0;y<graph.map.length;y++){
                n = getNeighborNum(x, y);
                if(n<=minLimit||n>=maxLimit) graph.map[y][x].checked = false;
                else if(n>=birthMin&&n<=birthMax) graph.map[y][x].checked = true;
                else graph.map[y][x].checked = graph.map[y][x].isCorridor;
            }
        }
        for(int x=0;x<graph.map[0].length;x++) for(Point[] row : graph.map){
            row[x].isCorridor = row[x].checked;
    }
    
}
    
    public Area simulate(){
        initialize();
        for(;iterNum>0;iterNum--) iterate();
        build();
        return area;
    }
    
    private void build(){
        for(int x=0;x<graph.map[0].length;x++) for(int y=0;y<graph.map.length;y++)
            if(graph.map[y][x].isCorridor) area.map[y][x] = Tile.wall(area.location, x, y);
            else area.map[y][x] = Tile.floor(area.location);
    }
    
}

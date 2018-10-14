/*
 * Copyright 2018 steelr.
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
package pathfinding;

import creatureLogic.VisibilityOverlay;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import level.Area;
import pathfinding.Point.Direction;
import tiles.assets.Door;

/**
 *
 * @author steelr
 */
public class NavigationMesh implements Serializable{
    
    private static final long serialVersionUID = 462317L;
    
    private HashMap<Integer, Point> pointInRoom = new HashMap<>();
    private HashMap<Integer, LinkedList<Waypoint>> roomPoints = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Path>> mesh = new HashMap<>();
    private final boolean[][] found;
    
    /**
     *  Creates a new instance.
     * @param graph Requires Searcher, Waypoints and map.
     * @param area
     */
    public NavigationMesh(Graph graph, Area area){
        int roomNum = 0;
        for(int y = 0; y<graph.map.length; y++)
            for(int x = 0; x<graph.map[0].length; x++)
                if(area.map[y][x]!=null && graph.map[y][x].checked != null
                        && graph.map[y][x].roomNum == -1 
                        && !graph.map[y][x].isCorridor 
                        && !(graph.map[y][x] instanceof Waypoint)){
            roomFloodFill(area, graph, graph.map[y][x], roomNum);
            roomNum++;
        }
        for(Integer i=0;i<roomNum;i++){
            HashMap<Integer, Path> paths = new HashMap<>(); 
            for(Integer j=0;j<roomNum;j++){
                if(Objects.equals(i, j)) continue;
                paths.put(j, buildPath(graph, i ,j));
            }
            mesh.put(i, paths);
        }
        found = new boolean[roomNum][];
        for(int y=0;y<roomNum;y++) found[y] = new boolean[roomNum-y];
    }
    
    private Path buildPath(Graph graph, int r1, int r2){
        LinkedList<Waypoint> points = roomPoints.get(r1);
        Point end = pointInRoom.get(r2);
        graph.searcher.initializeEndpoint(end);
        LinkedList<Path> paths = new LinkedList<>();
        points.stream().forEach(point -> {
            graph.searcher.extendedFloodfill(point);
            Path p = graph.followTrail(end.x, end.y);
            p.cutToWaypoint();
            paths.add(p);
        });
        System.out.println(r1==r2);
        System.out.println("paths: " + paths.size());
        return paths.stream().reduce((p1, p2) -> p1.size()>p2.size() ? p2 : p1).get();
    }
    
    private void roomFloodFill(Area area, Graph graph, Point start, int rN){
        graph.reset();
        LinkedList<Point> frontier = new LinkedList<>();
        LinkedList<Waypoint> wayP = new LinkedList<>();
        frontier.add(start);
        pointInRoom.put(rN, start);
        start.roomNum = rN;
        while(!frontier.isEmpty()){
            Point p = frontier.pop();
            for(Direction d : Searcher.directions){
                int nx = p.x+d.x, ny = p.y+d.y;
                if(area.map[ny][nx].treadable && graph.map[ny][nx].roomNum==-1&&graph.map[ny][nx].checked!=null){
                    graph.map[ny][nx].roomNum = rN;
                    if(graph.map[ny][nx] instanceof Waypoint)
                        wayP.add((Waypoint) graph.map[ny][nx]);
                    else frontier.add(graph.map[ny][nx]);
                }
            }
        }
        roomPoints.put(rN, wayP);
    }
    
    /**
     * Returns The Path between two Waypoints and null if the Path doesn't exist.
     * @param a
     * @param b
     * @return
     */
    public Path retrievePath(Integer a, Integer b){
        return mesh.get(a).get(b);
    }
    
    /**
     * Returns The Path between two Waypoints and null if the Path doesn't exist
     * or hasn't been found yet.
     * @param a
     * @param b
     * @param fov
     * @return
     */
    public Path retrievePath(Integer a, Integer b, VisibilityOverlay fov){
        if(found[a][b]) return mesh.get(a).get(b);
        else{
            Path p = mesh.get(a).get(b);
            if(p.isDiscovered(fov)){
                found[a][b] = true;
                return p;
            }else return null;
        }
    }
    
    public void debugPaint(Graphics g, int focusX, int focusY){
    
    }
    
}

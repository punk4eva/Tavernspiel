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

import java.awt.Graphics;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import pathfinding.Point.Direction;

/**
 *
 * @author steelr
 */
public class NavigationMesh implements Serializable{
    
    private static final long serialVersionUID = 462317L;
    
    private HashMap<Integer, Point> pointInRoom = new HashMap<>();
    private HashMap<Integer, LinkedList<Waypoint>> roomPoints = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, Path>> mesh = new HashMap<>();
    
    /**
     *  Creates a new instance.
     * @param graph Requires Searcher, waypoints and map.
     */
    public NavigationMesh(Graph graph){
        int roomNum = 0;
        for(Point[] row : graph.map)
            for(int x = 0; x<graph.map[0].length; x++)
                if(row[x].checked != null && row[x].roomNum == -1 && !row[x].isCorridor && !(row[x] instanceof Waypoint)){
            roomFloodFill(graph, row[x], roomNum);
            roomNum++;
        }
        for(Integer i=0;i<roomNum;i++){
            HashMap<Integer, Path> paths = new HashMap<>(); 
            for(Integer j=0;j<roomNum;j++){
                if(i.equals(j)) continue;
                paths.put(j, buildPath(graph, i ,j));
            }
            mesh.put(i, paths);
        }
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
        return paths.stream().reduce((p1, p2) -> p1.size()>p2.size() ? p2 : p1).get();
    }
    
    private void roomFloodFill(Graph graph, Point start, int rN){
        LinkedList<Point> frontier = new LinkedList<>();
        LinkedList<Waypoint> wayP = new LinkedList<>();
        frontier.add(start);
        pointInRoom.put(rN, start);
        while(!frontier.isEmpty()){
            Point p = frontier.pop();
            p.roomNum = rN;
            for(Direction d : Searcher.directions){
                int x = p.x+d.x, y = p.y+d.y;
                if(graph.map[y][x].roomNum==-1&&graph.map[y][x].checked!=null){
                    if(graph.map[y][x] instanceof Waypoint){
                        graph.map[y][x].roomNum = rN;
                        wayP.add((Waypoint) graph.map[y][x]);
                    }
                    else frontier.add(graph.map[y][x]);
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
    
    public void debugPaint(Graphics g, int focusX, int focusY){
    
    }
    
}

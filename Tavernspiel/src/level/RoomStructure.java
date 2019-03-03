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
package level;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import level.RoomBuilder.PreDoored;
import logic.Distribution;
import pathfinding.Graph;
import pathfinding.Point;
import pathfinding.Searcher;
import pathfinding.generation.AreaGrower;
import pathfinding.generation.DrunkenCorridorBuilder;
import pathfinding.generation.SpiderCorridorBuilder;
import tiles.Tile;
import tiles.assets.Door;
import tiles.assets.SpecialFloor;

/**
 *
 * @author Adam Whittaker
 * 
 * This class generates patterns in Rooms.
 */
public abstract class RoomStructure extends Area{
    
    protected List<Room> rooms;
    
    public RoomStructure(Dimension dim, Location loc, List<Room> list){
        super(dim, loc);
        rooms = list;
    }
    
    /**
     * Generates the room.
     */
    public abstract void generate();
    
    void shaveBufferMarks(int _x, int _y, int w, int h){
            for(int x=_x-1;x<_x+w+1;x++){
                graph.map[_y-1][x].isCorridor = false;
                graph.map[_y+h][x].isCorridor = false;
            }
            for(int y=_y;y<_y+h;y++){
                graph.map[y][_x-1].isCorridor = false;
                graph.map[y][_x+w].isCorridor = false;
            }
        }
    
    /**
     * A Hallway sub-structure. Cannot function independently.
     * Max 6 Rooms.
     */
    public static class Hallway extends RoomStructure{
        
        private final List<Room> rooms1 = new LinkedList<>();
        private final int width;

        public Hallway(Location loc, int w, List<Room> list){
            super(new Dimension(1, 1), loc, list);
            width = w;
        }

        @Override
        public void generate(){
            int[] c = partition();
            int length = getHallLength(), x = 0;
            dimension = new Dimension(length+1, c[0]+c[1]+width+2);
            map = new Tile[dimension.height][dimension.width];
            constructCorridor(c[0], length);
            for(Room room : rooms){
                blitDirty(room, x, c[0] - room.dimension.height);
                if(!room.oriented) map[c[0]-1][x+room.dimension.width/2] = new Door(location);
                x += room.dimension.width-1;
            }
            if(x<length) for(;x<=length;x++) map[c[0]-1][x] = Tile.wall(location, x, c[0]-1);
            x = 0;
            for(Room room : rooms1){
                if(room.oriented) room.orientation = 2;
                blitDirty(room, x, c[0]+width);
                if(!room.oriented) map[c[0]+width][x+room.dimension.width/2] = new Door(location);
                x += room.dimension.width-1;
            }
            if(x<length) for(;x<=length;x++) map[c[0]+width][x] = Tile.wall(location, x, c[0]+width);
        }
        
        private int[] partition(){
            rooms.sort((r1, r2) -> -compare(r1, r2));
            int n=0;
            Room ro;
            for(Iterator<Room> i=rooms.iterator();i.hasNext();n++){
                ro = i.next();
                if(n%2==1){
                    i.remove();
                    rooms1.add(ro);
                }
            }
            int[] c = new int[]{rooms.get(0).dimension.height, rooms1.get(0).dimension.height};
            Collections.shuffle(rooms);
            Collections.shuffle(rooms1);
            return c;
        }

        private int compare(Room t, Room t1){
            return new Integer(t.dimension.height).compareTo(t1.dimension.height);
        }
        
        private int getHallLength(){
            return Math.max(rooms.stream().map((r) -> r.dimension.width-1).reduce(1, Integer::sum), 
                    rooms1.stream().map((r) -> r.dimension.width-1).reduce(1, Integer::sum))-1;
        }
        
        private void constructCorridor(int h, int length){
            for(int y=h;y<h+width;y++){
                map[y][0] = Tile.wall(location, 0, y);
                map[y][length] = Tile.wall(location, length, y);
            }
            map[h+width/2][length] = new Door(location);
            for(int y=h;y<h+width;y++)
                for(int x=1;x<length;x++)
                    map[y][x] = new SpecialFloor(location);
            
        }
        
    }
    
    /**
     * An algorithm that randomly places rooms.
     */
    private abstract static class RoomPlacer extends RoomStructure{

        public RoomPlacer(Dimension dim, Location loc, List<Room> list){
            super(dim, loc, list);
            graph = new Graph(this, null);
        }

        @Override
        public void generate(){
            //sorts rooms based on area
            rooms.sort((r, r1) -> new Integer(r1.dimension.width*r1.dimension.height).compareTo(r.dimension.width*r.dimension.height));
            //Chooses a random orientation for each room.
            rooms.stream().forEach(r -> {
                r.orientation = Distribution.r.nextInt(4);
            });
            Dimension d; //dimension of the room
            Integer n = 0; //the index of the room
            int i; //incrementing variable
            Integer[][] coords = new Integer[rooms.size()][2];
            while(n<rooms.size()){
                d = getDimension(rooms.get(n));
                for(i=0;i<15;i++){
                    Integer[] point = generatePoint(d);
                    if(spaceFree(point, d)){
                        mark(point, d, n, coords);
                        n++;
                        break;
                    }
                }
                if(i==15){
                    n--;
                    unmark(coords[n], d, n, coords);
                }
            }
            Room r;
            for(i=0;i<rooms.size();i++){
                r = rooms.get(i);
                if(!(r.oriented||r instanceof PreDoored)) r.addDoors();
                blitDirty(r, coords[i][0], coords[i][1]);
            }
        }
        
        protected Dimension getDimension(Room r){
            if(r.orientation%2==0) return r.dimension;
            else return new Dimension(r.dimension.height, r.dimension.width);
        }
        
        protected Integer[] generatePoint(Dimension d){
            return new Integer[]{3+Distribution.r.nextInt(dimension.width-12-d.width),
                3+Distribution.r.nextInt(dimension.height-12-d.height)};
        }
        
        protected boolean spaceFree(Integer[] c, Dimension d){
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                if(graph.map[c[1]-1][x].isCorridor||graph.map[c[1]+d.height+1][x].isCorridor) return false;
            for(int y=c[1];y<c[1]+d.height;y++)
                if(graph.map[y][c[0]-1].isCorridor||graph.map[y][c[0]+d.width+1].isCorridor) return false;
            return true;
        }
        
        protected void mark(Integer[] c, Dimension d, Integer n, Integer[][] coords){
            coords[n] = c;
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                for(int y=c[1]-1;y<c[1]+d.height+1;y++)
                    graph.map[y][x].isCorridor = true;
        }
        
        protected void unmark(Integer[] c, Dimension d, Integer n, Integer[][] coords){
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                for(int y=c[1]-1;y<c[1]+d.height+1;y++)
                    graph.map[y][x].isCorridor = false;
            coords[n] = null;
        }
        
    }
    
    /**
     * A Labyrinth of Rooms.
     */
    public static class Labyrinth extends RoomPlacer{
        
        private final transient DrunkenCorridorBuilder dcb;

        public Labyrinth(Location loc, List<Room> list){
            super(new Dimension(60,60), loc, list);
            dcb = new DrunkenCorridorBuilder(this, 4, 500, 10.0, 0.25, 1,1);
        }

        @Override
        public void generate(){
            Room r;
            Dimension d;
            Integer[][] coords = planRooms();
            for(int i=0;i<rooms.size();i++){
                r = rooms.get(i);
                if(!(r.oriented || r instanceof PreDoored)) r.addLabyrinthDoors();
                blitDirty(r, coords[i][0], coords[i][1]);
                d = getDimension(r);
                shaveBufferMarks(coords[i][0], coords[i][1], d.width, d.height);
            }
            dcb.fillExistingArea();
        }
        
        private Integer[][] planRooms(){
            rooms.sort((r, r1) -> new Integer(r1.dimension.width*r1.dimension.height).compareTo(r.dimension.width*r.dimension.height));
            rooms.stream().forEach(r -> {
                r.orientation = Distribution.r.nextInt(4);
            });
            Dimension d;
            Integer n = 0;
            int i;
            Integer[][] coords = new Integer[rooms.size()][2];
            while(n<rooms.size()){
                d = getDimension(rooms.get(n));
                for(i=0;i<10;i++){
                    Integer[] point = generatePoint(d, rooms.get(n));
                    if(spaceFree(point, d)){
                        mark(point, d, n, coords);
                        n++;
                        break;
                    }
                }
                if(i>9){
                    n--;
                    unmark(coords[n], d, n, coords);
                }
            }
            return coords;
        }

        protected Integer[] generatePoint(Dimension d, Room r){
            if(r.oriented||r instanceof PreDoored) return generateSpecialPoint(d, r);
            else return new Integer[]{2+Distribution.r.nextInt((dimension.width-d.width-3)/2)*2,
                    2+Distribution.r.nextInt((dimension.height-d.height-3)/2)*2};
        }
        
        protected Integer[] generateSpecialPoint(Dimension d, Room r){
            Integer[] door = r.findDoor();
            if(r.orientation%2==0){
                return new Integer[]{3+Distribution.r.nextInt((dimension.width-12-d.width)/2)*2+door[0]%2,
                    3+Distribution.r.nextInt((dimension.height-12-d.height)/2)*2+d.height%2};
            }else{
                return new Integer[]{3+Distribution.r.nextInt((dimension.width-12-d.width)/2)*2+d.width%2,
                    3+Distribution.r.nextInt((dimension.height-12-d.height)/2)*2+door[1]%2}; 
            }
        }
    
    }
    
    /**
     * A cellular-automata generated Cavern.
     */
    public static class Cavern extends RoomPlacer{
        
        private final transient AreaGrower ag;
        private final transient boolean paths;

        public Cavern(Location loc, List<Room> list, boolean p){
            super(new Dimension(60,60), loc, list);
            ag = new AreaGrower(dimension, loc, 0.375,  3,9,  4,9,  4, true);
            paths = p;
        }

        @Override
        public void generate(){
            map = ag.simulate().map;
            rooms.sort((r, r1) -> new Integer(r1.dimension.width*r1.dimension.height).compareTo(r.dimension.width*r.dimension.height));
            rooms.stream().forEach(r -> {
                r.orientation = Distribution.r.nextInt(4);
            });
            Dimension d;
            Integer n = 0;
            int i;
            Integer[][] coords = new Integer[rooms.size()][2];
            while(n<rooms.size()){
                d = getDimension(rooms.get(n));
                for(i=0;i<10;i++){
                    Integer[] point = generatePoint(d);
                    if(spaceFree(point, d)){
                        mark(point, d, n, coords);
                        n++;
                        break;
                    }
                }
                if(i>9){
                    n--;
                    unmark(coords[n], d, n, coords);
                }
            }
            Room r;
            for(i=0;i<rooms.size();i++){
                r = rooms.get(i);
                if(!(r.oriented||r instanceof PreDoored)) r.addDoors();
                blitDirty(r, coords[i][0], coords[i][1]);
                d = getDimension(r);
                shaveBufferMarks(coords[i][0], coords[i][1], d.width, d.height);
            }
            Point centre = getFreePoint();
            graph.recalculateWaypoints(this);
            Searcher searcher = new Searcher(graph, this);
            searcher.structuredFloodfill(centre);
            buildPaths();
        }
        
        private Point getFreePoint(){
            while(true){
                Integer[] c = new Integer[]{3+Distribution.r.nextInt(dimension.width-12),
                        3+Distribution.r.nextInt(dimension.height-12)};
                if(!graph.map[c[1]][c[0]].isCorridor) return graph.map[c[1]][c[0]];
            }
        }
        
        private void buildPaths(){
            for(Point p : graph.waypoints){
                while(p.cameFrom!=null){
                    p = p.cameFrom;
                    if(paths) map[p.y][p.x] = new SpecialFloor(location);
                    else map[p.y][p.x] = Tile.floor(location);
                }
            }
        }
    
    }
    
    /**
     * A Spider web-like corridor generation algorithm.
     */
    public static class SpiderCorridor extends RoomPlacer{
        
        private final transient SpiderCorridorBuilder scb;
        public transient boolean[][] corridors;

        public SpiderCorridor(Dimension dim, Location loc, List<Room> list, int windyness, boolean decayActive){
            super(dim, loc, list);
            scb = new SpiderCorridorBuilder(this, windyness, decayActive);
        }

        @Override
        public void generate(){
            super.generate();
            System.out.println("Ran super()");
            graph.recalculateWaypoints(this);
            System.out.println("Recalculated Waypoints");
            corridors = scb.build();
            System.out.println("Built corridors");
        }
    
    }
    
    /**
     * A compact box-like assortment of Rooms.
     */
    public static class Complex extends RoomStructure{

        public Complex(Dimension dim, Location loc, List<Room> list){
            super(dim, loc, list);
        }

        @Override
        public void generate(){
            
        }
        
    }
    
}

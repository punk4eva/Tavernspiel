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
import tiles.Tile;
import tiles.assets.Door;

/**
 *
 * @author Adam Whittaker
 */
public abstract class RoomStructure extends Area{
    
    protected List<Room> rooms = new LinkedList<>();
    
    public RoomStructure(Dimension dim, Location loc, List<Room> list){
        super(dim, loc);
        rooms = list;
    }
    
    public abstract void generate();
    
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
                    map[y][x] = new Tile("specialfloor", location, true, false, true);
            
        }
        
    }
    
    public static class Cave extends RoomStructure{

        public Cave(Location loc, List<Room> list){
            super(new Dimension(list.stream().map((r) -> Math.max(r.dimension.width, r.dimension.height)+1).reduce(1, Integer::sum)+3, 
                    list.stream().map((r) -> Math.max(r.dimension.width, r.dimension.height)+1).reduce(1, Integer::sum)+3), loc, list);
            graph = new Graph(this, null);
        }

        @Override
        public void generate(){
            rooms.sort((r, r1) -> new Integer(r1.dimension.width*r1.dimension.height).compareTo(r.dimension.width*r.dimension.height));
            Dimension d;
            Integer n = 0;
            int i;
            Integer[][] coords = new Integer[rooms.size()][2];
            while(n<rooms.size()){
                d = rooms.get(n).dimension;
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
                r.orientation = Distribution.r.nextInt(4);
                if(!(r.oriented||r instanceof PreDoored)) r.addDoors();
                blitDirty(r, coords[i][0], coords[i][1]);
                if(i==0) startCoords = new Integer[]{coords[i][0]+1, coords[i][0]+1};
            }
        }
        
        private Integer[] generatePoint(Dimension d){
            return new Integer[]{3+Distribution.r.nextInt(dimension.width-12-d.width),
                3+Distribution.r.nextInt(dimension.height-12-d.height)};
        }
        
        private boolean spaceFree(Integer[] c, Dimension d){
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                if(graph.map[c[1]-1][x].isCorridor||graph.map[c[1]+d.height+1][x].isCorridor) return false;
            for(int y=c[1];y<c[1]+d.height;y++)
                if(graph.map[y][c[0]-1].isCorridor||graph.map[y][c[0]+d.width+1].isCorridor) return false;
            return true;
        }
        
        private void mark(Integer[] c, Dimension d, Integer n, Integer[][] coords){
            coords[n] = c;
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                for(int y=c[1]-1;y<c[1]+d.height+1;y++)
                    graph.map[y][x].isCorridor = true;
        }
        
        private void unmark(Integer[] c, Dimension d, Integer n, Integer[][] coords){
            for(int x=c[0]-1;x<c[0]+d.width+1;x++)
                for(int y=c[1]-1;y<c[1]+d.height+1;y++)
                    graph.map[y][x].isCorridor = false;
            coords[n] = null;
        }
        
    }
    
    public static class Complex extends RoomStructure{

        public Complex(Dimension dim, Location loc, List<Room> list){
            super(dim, loc, list);
        }

        @Override
        public void generate(){
        }
        
    }
    
}

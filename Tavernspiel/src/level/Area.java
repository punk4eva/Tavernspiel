
package level;

import containers.Floor;
import containers.Receptacle;
import creatures.Hero;
import exceptions.AreaCoordsOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import listeners.AreaEvent;
import listeners.DeathEvent;
import logic.GameObject;
import blob.Blob;
import creatureLogic.VisibilityOverlay;
import designer.AreaTemplate;
import static gui.MainClass.HEIGHT;
import static gui.MainClass.WIDTH;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import logic.Utils.Unfinished;
import pathfinding.Graph;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an Area.
 */
public class Area implements Serializable{
    
    private final static long serialVersionUID = 2049197;
    
    public final Tile[][] map;
    public final Dimension dimension;
    public final Location location;
    public Integer[] startCoords;
    public volatile LinkedList<GameObject> objects = new LinkedList<>();
    public volatile LinkedList<Receptacle> receptacles = new LinkedList<>();
    public Graph graph = null;
    private volatile Hero hero;
    public final VisibilityOverlay overlay;
    public final Semaphore semaphore = new Semaphore(0);
    
    /**
     * Creates a new instance.
     * @param dim The dimension.
     * @param loc The location.
     */
    public Area(Dimension dim, Location loc){
        dimension = dim;
        location = loc;
        map = new Tile[dimension.height][dimension.width];
        overlay = new VisibilityOverlay(0,0,6,this);
    }
    
    /**
     * Prints the given area onto this area.
     * @param area The area to print.
     * @param x1 The top left x.
     * @param y1 The top left y.
     * @throws AreaCoordsOutOfBoundsException If it can't be fit in with the
     * given coordinates.
     */
    public void blit(Area area, int x1, int y1) throws AreaCoordsOutOfBoundsException{
        if(!withinBounds(x1, y1)||
                !withinBounds(x1+area.dimension.width, y1+area.dimension.height))
            throw new AreaCoordsOutOfBoundsException("Coords out of bounds.");
        for(int y=y1;y<y1+area.dimension.height;y++){
            for(int x=x1;x<x1+area.dimension.width;x++){
                map[y][x] = area.map[y-y1][x-x1];
            }
        }
        objects.addAll(area.objects.stream().map(ob -> {
            ob.x += x1;
            ob.y += y1;
            return ob;
        }).collect(Collectors.toList()));
        receptacles.addAll(area.receptacles.stream().map(rec -> {
            rec.x += x1;
            rec.y += y1;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null) startCoords = new Integer[]{area.startCoords[0]+x1, area.startCoords[1]+y1};
    }
    
    /**
     * Prints the given area onto this area (not replacing preexisting Tiles).
     * @param area The area to print.
     * @param x1 The top left x.
     * @param y1 The top left y.
     * @throws AreaCoordsOutOfBoundsException If it can't be fit in with the
     * given coordinates.
     */
    public void blitSafely(Area area, int x1, int y1) throws AreaCoordsOutOfBoundsException{
        if(!withinBounds(x1, y1)||
                !withinBounds(x1+area.dimension.width, y1+area.dimension.height))
            throw new AreaCoordsOutOfBoundsException("Coords out of bounds.");
        for(int y=y1;y<y1+area.dimension.height;y++){
            for(int x=x1;x<x1+area.dimension.width;x++){
                if(map[y][x]==null) map[y][x] = area.map[y-y1][x-x1];
            }
        }
        objects.addAll(area.objects.stream().map(ob -> {
            ob.x += x1;
            ob.y += y1;
            return ob;
        }).collect(Collectors.toList()));
        receptacles.addAll(area.receptacles.stream().map(rec -> {
            rec.x += x1;
            rec.y += y1;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null) startCoords = new Integer[]{startCoords[0]+x1, startCoords[1]+y1};
    }
    
    /**
     * Checks the given coordinates are within bounds.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if they are, false if not.
     */
    public boolean withinBounds(int x, int y){
        return x>=0&&y>=0&&x<dimension.width&&y<dimension.height;
    }
    
    /**
     * Clicks on the given tile with the given coordinates.
     * @param x The x of the Tile.
     * @param y The y of the Tile.
     * @param clickMode The click mode.
     */
    public void click(int x, int y, String clickMode){
        switch(clickMode){
            case "normal":
                hero.attributes.ai.setDestination(x, y);
        }
    }
    
    /**
     * Burns the given Tile.
     * @param x The x of the Tile.
     * @param y The y of the Tile.
     */
    public void burn(int x, int y){
        map[y][x] = new Tile("embers", location, true, false, true);
        Receptacle r = getReceptacle(x, y);
        if(r != null) r.keep(item -> !item.flammable);
    }
    
    /**
     * Checks whether the Tile is treadable.
     * @param x The x of the Tile.
     * @param y The y of the Tile.
     * @return Whether this tile is treadable.
     */
    protected boolean isTreadable(int x, int y){
        return map[y][x].treadable;
    }
    
    /**
     * Returns the receptacle at the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The receptacle if it exists, null if not.
     */
    public Receptacle getReceptacle(int x, int y){
        for(int n=0;n<receptacles.size();n++){
            Receptacle temp = receptacles.get(n);
            if(temp.x==x&&temp.y==y) return temp;
        }
        return null;
    }   
    
    
    /**
     * Responds to a Creature's death.
     * @param de The DeathEvent.
     */
    public void lifeTaken(DeathEvent de){
        //@unfinished
        try{
            getReceptacle(de.getX(), de.getY()).pushAll(de.getCreature().inventory);
            getReceptacle(de.getX(), de.getY()).pushAll(de.getCreature().equipment);
        }catch(ReceptacleOverflowException ignore){
        }catch(NullPointerException e){
            Floor floor = new Floor(de.getX(), de.getY());
            try{
                floor.pushAll(de.getCreature().inventory);
                floor.pushAll(de.getCreature().equipment);
            }catch(ReceptacleOverflowException ignore){}
            receptacles.add(floor);
        }
        objects.remove(de.getCreature());
        de.getCreature().animator.switchTo("death"); //May want to move this.
    }
    
    /**
     * Responds to an AreaEvent.
     * @param ae The AreaEvent.
     */
    public void areaActedUpon(AreaEvent ae){
        switch(ae.getAction()){
            case "FELLINTOCHASM":
                //if(nextLevel==null){          @unfinished
                //    nextLevel.fullLoad();
                //    hero.changeLevel(nextLevel.zipcode);
                //    hero.addBuff(BuffBuilder.bleeding(level*2));
                //}else{
                //    @unfinished
                //}
                break;
            case "BURN": burn(ae.getX(), ae.getY());
                break;
            case "FIND": 
                break;
        }
    }

    /**
     * Replaces the receptacle that is on the given coordinates. If no 
     * receptacle is there, nothing will happen.
     * @param x The x of the Receptacle.
     * @param y The x of the Receptacle.
     * @param receptacle The new Receptacle.
     */
    public void replaceHeap(int x, int y, Receptacle receptacle){
        List<Receptacle> tempList = (List<Receptacle>) receptacles.clone();
        for(int n=0;n<tempList.size();n++){
            Receptacle temp = tempList.get(n);
            if(temp.x==x&&temp.y==y){
                receptacles.remove(n);
                receptacles.add(receptacle);
                break;
            }
        }
    }
    
    /**
     * Adds a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void addObject(GameObject object){
        object.setArea(this);
        object.start();
        objects.add(object);
    }
    
    /**
     * Removes a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void removeObject(GameObject object){
        objects.remove(object);
        object.stop();
    }

    /**
     * Checks if there is a gas on the given Tile.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if there is, false if not.
     */
    public boolean gasPresent(int x, int y){
        return objects.stream().filter(ob -> ob instanceof Blob && ob.y==y && ob.x==x).count()>0;
    }
    
    /**
     * Tests if a Creature can step on a given coordinate.
     * @param x
     * @param y
     * @return true if it is, false if not.
     */
    public boolean tileFree(int x, int y){
        boolean stood = true;
        for(GameObject ob : objects) if(!(ob instanceof Blob) && ob.x==x && ob.y==y){
            stood = false;
            break;
        }
        return stood&&map[y][x].treadable;
    }
    
    public static Area getPreloadedArea(String filepath){
        return AreaTemplate.deserialize(filepath).toArea();
    }
    
    @Unfinished("Remove bookmarks.")
    public void renderObjects(Graphics g, int focusX, int focusY){
        objects.stream().forEach(ob -> {
            ob.render(g, focusX, focusY);
        });
        //graph.paint(g, focusX, focusY);
    }
    
    public synchronized void turn(double turnNum){
        for(;turnNum>=1;turnNum--){
            objects.stream().forEach(ob -> {
                ob.threadTurn(1.0);
                try{
                    semaphore.acquire();
                }catch(InterruptedException e){}
            });
        }
        if(turnNum!=0.0){
            double d = turnNum; //effective finalization.
            objects.stream().forEach(ob -> {
                ob.threadTurn(d);
                try{
                    semaphore.acquire();
                }catch(InterruptedException e){}
            });
        }
    }
    
    public synchronized void addHero(Hero h){
        h.setArea(this);
        try{
        if(!(objects.get(0) instanceof Hero)){
            h.start();
            objects.add(0, h);
        }
        }catch(IndexOutOfBoundsException e){
            h.start();
            objects.add(h);
        }
        hero = h;
    }

    public synchronized void shade(Graphics g, int focusX, int focusY, double zoom){
        for(int y=focusY, maxY=focusY+dimension.height*16;y<maxY;y+=16){
            for(int x=focusX, maxX=focusX+dimension.width*16;x<maxX;x+=16){
                if(x<0||y<0||x*zoom>WIDTH||y*zoom>HEIGHT) continue;
                try{
                    int tx = (x-focusX)/16, ty = (y-focusY)/16;
                    if(overlay.isExplored(tx, ty)) g.drawImage(VisibilityOverlay.exploredFog.getShadow(overlay.map, x, y, 1), x, y, null);
                    else if(overlay.isUnexplored(tx, ty)) g.drawImage(VisibilityOverlay.unexploredFog.getShadow(overlay.map, x, y, 0), x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*skip frame*/}
            }
        }
    }
    
}

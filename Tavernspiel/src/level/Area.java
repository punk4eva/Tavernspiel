
package level;

import blob.Blob;
import containers.Floor;
import containers.Receptacle;
import creatureLogic.VisibilityOverlay;
import creatures.Hero;
import designer.AreaTemplate;
import exceptions.AreaCoordsOutOfBoundsException;
import items.Item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import listeners.AreaEvent;
import listeners.DeathEvent;
import logic.GameObject;
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
    public Integer[] startCoords, endCoords;
    private final List<GameObject> objects = (List<GameObject>)(Object)Collections.synchronizedList(new LinkedList<>());
    protected final List<Receptacle> receptacles = (List<Receptacle>)(Object)Collections.synchronizedList(new LinkedList<>());
    public Graph graph = null;
    protected volatile Hero hero;
    public final VisibilityOverlay overlay;
    public final ReentrantLock objectLock = new ReentrantLock();
    
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
        else if(area.endCoords!=null) endCoords = new Integer[]{area.endCoords[0]+x1, area.endCoords[1]+y1};
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
        if(area.startCoords!=null) startCoords = new Integer[]{area.startCoords[0]+x1, area.startCoords[1]+y1};
        else if(area.endCoords!=null) endCoords = new Integer[]{area.endCoords[0]+x1, area.endCoords[1]+y1};
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
        if(r != null) r.removeIf(item -> item.flammable);
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
            getReceptacle(de.getX(), de.getY()).addAll(de.getCreature().inventory);
            getReceptacle(de.getX(), de.getY()).addAll(de.getCreature().equipment);
        }catch(NullPointerException e){
            Floor floor = new Floor(de.getX(), de.getY());
            floor.addAll(de.getCreature().inventory);
            floor.addAll(de.getCreature().equipment);
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
        receptacles.remove(getReceptacle(x, y));
        receptacles.add(receptacle);
    }
    
    /**
     * Adds a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void addObject(GameObject object){
        objectLock.lock();
        try{
            object.setArea(this, true);
            objects.add(object);
        }finally{
            objectLock.unlock();
        }
    }
    
    /**
     * Removes a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void removeObject(GameObject object){
        objectLock.lock();
        try{
            objects.remove(object);
        }finally{
            objectLock.unlock();
        }
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
        /*boolean stood = true;
        for(GameObject ob : objects) if(!(ob instanceof Blob) && ob.x==x && ob.y==y){
        stood = false;
        break;
        }
        return stood&&map[y][x].treadable;*/
        return graph.tileFree(x, y);
    }
    
    /**
     * Removes and returns an Item from the given coordinates.
     * @param x
     * @param y
     * @return The item at the top of the Receptacle.
     * @throws NullPointerException if there is no Receptacle.
     */
    public Item pickUp(int x, int y){
        Receptacle r = getReceptacle(x, y);
        Item ret = r.pop();
        if(r.isEmpty()) receptacles.remove(r);
        return ret;
    }
    
    public static Area getPreloadedArea(String filepath){
        return AreaTemplate.deserialize(filepath).toArea();
    }
    
    public void renderObjects(Graphics g, int focusX, int focusY){
        synchronized(receptacles){
            receptacles.stream().forEach(r -> {
                if(overlay.isVisible(r.x, r.y)){
                    if(r.icon!=null) g.drawImage(r.icon.getImage(), r.x*16+focusX, r.y*16+focusY, null);
                    else if(!r.isEmpty()) r.peek().animation.animate(g, r.x*16+focusX, r.y*16+focusY);
                }
            });
        }
        objectLock.lock();
        try{
            for(final ListIterator<GameObject> iter=objects.listIterator();iter.hasNext();){
                GameObject ob = iter.next();
                if(overlay.isVisible(ob.x, ob.y)) ob.render(g, focusX, focusY);
            }
            //graph.paint(g, focusX, focusY, this);
        }finally{
            objectLock.unlock();
        }
    }
    
    public void turn(double turnNum){
        objectLock.lock();
        try{
            for(;turnNum>=1;turnNum--){
                for(final ListIterator<GameObject> iter = objects.listIterator();iter.hasNext();) 
                    iter.next().turn(1.0);
            }
            if(turnNum!=0.0){
                for(final ListIterator<GameObject> iter = objects.listIterator();iter.hasNext();) 
                    iter.next().turn(turnNum);
            }
        }finally{
            objectLock.unlock();
        }
    }
    
    public void addHero(Hero h, boolean start){
        hero = h;
        h.setArea(this, start);
        objectLock.lock();
        try{
            try{
                if(!(objects.get(0) instanceof Hero)){
                    objects.add(0, h);
                }
            }catch(IndexOutOfBoundsException e){
                objects.add(h);
            }
        }finally{
                objectLock.unlock();
        }
    }

    public void plop(Item i, int x, int y){
        receptacles.add(new Floor(i, x, y));
    }
    
}

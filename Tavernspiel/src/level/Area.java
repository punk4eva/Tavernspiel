
package level;

import blob.Blob;
import containers.Floor;
import containers.Receptacle;
import creatureLogic.VisibilityOverlay;
import creatures.Hero;
import designer.AreaTemplate;
import exceptions.AreaCoordsOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import listeners.AreaEvent;
import listeners.DeathEvent;
import logic.GameObject;
import logic.Utils.Unfinished;
import pathfinding.Graph;
import tiles.AnimatedTile;
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
    private volatile LinkedList<GameObject> objects = new LinkedList<>();
    public volatile LinkedList<Receptacle> receptacles = new LinkedList<>();
    public Graph graph = null;
    private volatile Hero hero;
    public final VisibilityOverlay overlay;
    
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
        if(area.startCoords!=null) startCoords = new Integer[]{startCoords[0]+x1, startCoords[1]+y1};
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
        object.setArea(this, true);
        objects.add(object);
    }
    
    /**
     * Removes a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void removeObject(GameObject object){
        objects.remove(object);
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
    
    public static Area getPreloadedArea(String filepath){
        return AreaTemplate.deserialize(filepath).toArea();
    }
    
    @Unfinished("Remove bookmarks.")
    public synchronized void renderObjects(Graphics g, int focusX, int focusY){
        receptacles.stream().forEach(r -> {
            if(overlay.isVisible(r.x, r.y)){
                if(r.icon!=null) g.drawImage(r.icon.getImage(), r.x*16+focusX, r.y*16+focusY, null);
                else if(!r.isEmpty()) r.peek().animation.animate(g, r.x*16+focusX, r.y*16+focusY);
            }
        });
        objects.stream().forEach(ob -> {
            if(overlay.isVisible(ob.x, ob.y)) ob.render(g, focusX, focusY);
        });
        //graph.paint(g, focusX, focusY);
    }
    
    public synchronized void turn(double turnNum){
        for(;turnNum>=1;turnNum--){
            Iterator<GameObject> iter = objects.iterator();
            while(iter.hasNext()) iter.next().turn(1.0);
        }
        if(turnNum!=0.0){
            Iterator<GameObject> iter = objects.iterator();
            while(iter.hasNext()) iter.next().turn(turnNum);
        }
    }
    
    public synchronized void addHero(Hero h, boolean start){
        h.setArea(this, start);
        try{
            if(!(objects.get(0) instanceof Hero)){
                objects.add(0, h);
            }
        }catch(IndexOutOfBoundsException e){
            objects.add(h);
        }
        hero = h;
    }
    
    public void startAllAnimations(){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(map[y][x] instanceof AnimatedTile) ((AnimatedTile) map[y][x]).animation.start();
            }
        }
    }
    
    public void stopAllAnimations(){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(map[y][x] instanceof AnimatedTile) ((AnimatedTile) map[y][x]).animation.stop();
            }
        }
    }

    @Unfinished("Only for experimental purposes.")
    public void plop(Item i, int x, int y){
        receptacles.add(new Floor(i, x, y));
    }
    
}

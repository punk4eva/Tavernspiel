
package level;

import containers.Floor;
import containers.Receptacle;
import creatures.Hero;
import exceptions.AreaCoordsOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import listeners.AreaEvent;
import listeners.DeathEvent;
import logic.Distribution;
import logic.GameObject;
import blob.Gas;
import pathfinding.Graph;
import tiles.Tile;
import tiles.TrapBuilder;

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
    public volatile LinkedList<GameObject> objects = new LinkedList<>();
    public volatile LinkedList<Receptacle> receptacles = new LinkedList<>();
    public Graph graph = null;
    
    /**
     * Creates a new instance.
     * @param dim The dimension.
     * @param loc The location.
     */
    public Area(Dimension dim, Location loc){
        dimension = dim;
        location = loc;
        map = createBlank();
    }
    
    private Tile[][] createBlank(){
        Tile[][] ret = new Tile[dimension.height][dimension.width];
        return ret;
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
        objects.addAll(area.objects);
        receptacles.addAll(area.receptacles);
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
        objects.addAll(area.objects);
        receptacles.addAll(area.receptacles);
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
     * @param hero The Hero.
     */
    public void click(int x, int y, String clickMode, Hero hero){
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
    protected void burn(int x, int y){
        map[y][x] = new Tile("embers", location);
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
     * Randomly places Items on the ground.
     * @param items The list of items.
     */
    protected void randomlyPlop(List<Item> items){
        items.stream().forEach(item -> {
            int x, y;
            do{
                x = Distribution.getRandomInclusiveInt(0, dimension.width-1);
                y = Distribution.getRandomInclusiveInt(0, dimension.height-1);
            }while(!isTreadable(x, y));
            try{
                if(getReceptacle(x, y)!=null) getReceptacle(x, y).push(item);
                else{
                    receptacles.add(TrapBuilder.getRandomReceptacle(item, x, y));
                }
            }catch(ReceptacleOverflowException ignore){}
        });
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
        return objects.stream().filter(ob -> ob instanceof Gas && ob.y==y && ob.x==x).count()>0;
    }
    
    /**
     * Tests if a Creature can step on a given coordinate.
     * @param x
     * @param y
     * @return true if it is, false if not.
     */
    public boolean tileFree(int x, int y){
        boolean stood = true;
        for(GameObject ob : objects) if(!(ob instanceof Gas) && ob.x==x && ob.y==y){
            stood = false;
            break;
        }
        return stood&&map[y][x].treadable;
    }
    
    public void renderObjects(Graphics g){
        objects.stream().forEach(ob -> {
            ob.render(g);
        });
    }
    
    public void turn(double turnNum){
        objects.stream().forEach(ob -> {
            ob.turn(turnNum);
        });
    }
    
}

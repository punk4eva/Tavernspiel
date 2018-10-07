
package level;

import logic.mementoes.AreaMemento;
import ai.PlayerAI;
import blob.Blob;
import containers.Floor;
import containers.LockedChest;
import containers.PhysicalReceptacle;
import creatureLogic.VisibilityOverlay;
import creatures.Creature;
import creatures.Hero;
import designer.AreaTemplate;
import exceptions.AreaCoordsOutOfBoundsException;
import gui.Window;
import items.Item;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import listeners.Interactable;
import logic.Distribution;
import logic.GameObject;
import logic.Utils.Unfinished;
import pathfinding.Graph;
import tiles.assets.Grass;
import tiles.Tile;
import tiles.assets.DepthEntrance;
import tiles.assets.DepthExit;
import tiles.assets.Water;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an Area.
 */
public class Area implements Serializable{
    
    private final static long serialVersionUID = 2049197;
    
    public transient Tile[][] map;
    public final Dimension dimension;
    public transient Location location;
    public Integer[] startCoords, endCoords;
    public final int depth;
    //@Delete
    //private final List<GameObject> objects = (List<GameObject>)(Object)Collections.synchronizedList(new LinkedList<>());
    //protected final List<Receptacle> receptacles = (List<Receptacle>)(Object)Collections.synchronizedList(new LinkedList<>());
    private final List<GameObject> objects = new LinkedList<>();
    protected final List<PhysicalReceptacle> receptacles = new LinkedList<>();

    public Graph graph = null;
    public volatile Hero hero;
    public final VisibilityOverlay overlay;
    public transient ReentrantLock objectLock = new ReentrantLock();
    
    @Unfinished("Remove debug")
    public boolean debugMode = false;
    
    /**
     * Creates a new instance.
     * @param dim The dimension.
     * @param loc The location.
     */
    public Area(Dimension dim, Location loc){
        dimension = dim;
        depth = loc.depth;
        location = loc;
        map = new Tile[dimension.height][dimension.width];
        overlay = new VisibilityOverlay(0,0,loc.feeling.visibility,this);
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
            ob.setArea(this, true);
            return ob;
        }).collect(Collectors.toList()));
        receptacles.addAll(area.receptacles.stream().map(rec -> {
            rec.x += x1;
            rec.y += y1;
            if(rec instanceof Interactable) 
                map[rec.y][rec.x].interactable = (Interactable) rec;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null){
            startCoords = new Integer[]{area.startCoords[0]+x1, area.startCoords[1]+y1};
            ((DepthEntrance) map[startCoords[1]][startCoords[0]]).currentArea = this;
        }else if(area.endCoords!=null){
            endCoords = new Integer[]{area.endCoords[0]+x1, area.endCoords[1]+y1};
            ((DepthExit) map[endCoords[1]][endCoords[0]]).setArea(this);
        }
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
            ob.setArea(this, true);
            return ob;
        }).collect(Collectors.toList()));
        receptacles.addAll(area.receptacles.stream().map(rec -> {
            rec.x += x1;
            rec.y += y1;
            if(rec instanceof Interactable) 
                map[rec.y][rec.x].interactable = (Interactable) rec;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null){
            startCoords = new Integer[]{area.startCoords[0]+x1, area.startCoords[1]+y1};
            ((DepthEntrance) map[startCoords[1]][startCoords[0]]).currentArea = this;
        }else if(area.endCoords!=null){
            endCoords = new Integer[]{area.endCoords[0]+x1, area.endCoords[1]+y1};
            ((DepthExit) map[endCoords[1]][endCoords[0]]).setArea(this);
        }
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
     * Burns the given Tile.
     * @param x The x of the Tile.
     * @param y The y of the Tile.
     */
    public void burn(int x, int y){
        map[y][x] = new Tile("embers", location, true, false, true);
        PhysicalReceptacle r = getReceptacle(x, y);
        if(r != null){
            r.removeIf(item -> item.flammable);
            removeReceptacle(r);
        }
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
    public PhysicalReceptacle getReceptacle(int x, int y){
        for(int n=0;n<receptacles.size();n++){
            PhysicalReceptacle temp = receptacles.get(n);
            if(temp.x==x&&temp.y==y){
                System.out.println("get: " + temp);
                return temp;
            }
        }
        System.out.println("get: null");
        return null;
    }
    
    /**
     * Adds a Receptacle to this Area.
     * @param rec
     */
    public void addReceptacle(PhysicalReceptacle rec){
        if(rec instanceof Interactable) 
            map[rec.y][rec.x].interactable = (Interactable) rec;
        receptacles.add(rec);
    }
    
    /**
     * Removes the Receptacle at the given coordinates.
     * @param x
     * @param y
     * @throws IllegalStateException if there is no Receptacle at the given
     * coords.
     */
    public void removeReceptacle(int x, int y){
        PhysicalReceptacle r;
        Iterator<PhysicalReceptacle> iter = receptacles.iterator();
        while(iter.hasNext()){
            r = iter.next();
            if(r.x==x&&r.y==y){
                if(r instanceof Interactable) map[y][x].interactable = null;
                iter.remove();
                return;
            }
        }
        throw new IllegalStateException("No receptacle at coords: " + x + ", " + y);
    }
    
    /**
     * Removes the given Receptacle.
     * @param r
     */
    public void removeReceptacle(PhysicalReceptacle r){
        receptacles.remove(r);
        map[r.y][r.x].interactable = null;
    }
    
    
    /**
     * Responds to a Creature's death.
     * @param c The Creature.
     */
    public void lifeTaken(Creature c){
        //@unfinished
        try{
            getReceptacle(c.x, c.y).addAll(c.inventory);
            getReceptacle(c.x, c.y).addAll(c.equipment);
        }catch(NullPointerException e){
            Floor floor = new Floor(c.x, c.y);
            floor.addAll(c.inventory);
            floor.addAll(c.equipment);
            addReceptacle(floor);
        }
        objects.remove(c);
    }

    /**
     * Replaces the receptacle that is on the given coordinates. If no 
     * receptacle is there, nothing will happen.
     * @param x The x of the Receptacle.
     * @param y The x of the Receptacle.
     * @param rec The new Receptacle.
     */
    public void replaceHeap(int x, int y, PhysicalReceptacle rec){
        receptacles.remove(getReceptacle(x, y));
        if(rec instanceof Interactable) 
                map[y][x].interactable = (Interactable) rec;
        receptacles.add(rec);
    }
    
    /**
     * Adds a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void addObject(GameObject object){
        //objectLock.lock();
        //try{
            object.setArea(this, true);
            objects.add(object);
        //}finally{
        //    objectLock.unlock();
        //}
    }
    
    /**
     * Removes a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void removeObject(GameObject object){
        //objectLock.lock();
        //try{
            objects.remove(object);
        //}finally{
        //    objectLock.unlock();
        //}
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
     * Checks if there is a Creature on the given Tile.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if there is, false if not.
     */
    public boolean creaturePresent(int x, int y){
        return objects.stream().anyMatch(c -> c instanceof Creature && c.x==x && c.y==y);
    }
    
    /**
     * Deserializes an AreaTemplate and generates an Area from it.
     * @param filepath The filepath of the AreaTemplate.
     * @return
     */
    public static Area getPreloadedArea(String filepath){
        return AreaTemplate.deserialize(filepath).toArea();
    }
    
    /**
     * Links this Area's entrance with another Area.
     * @param link The Area to link with.
     */
    public void linkEntrance(Area link){
        ((DepthEntrance)map[startCoords[1]][startCoords[0]]).previousArea = link;
    }
    
    /**
     * Renders all GameObjects in this Area.
     * @param g The Graphics
     * @param focusX
     * @param focusY
     */
    public void renderObjects(Graphics g, int focusX, int focusY){
        synchronized(receptacles){
            receptacles.stream().forEach(r -> {
                if(overlay.isVisible(r.x, r.y)){
                    if(r.icon!=null) g.drawImage(r.icon.getImage(), r.x*16+focusX, r.y*16+focusY, null);
                    else if(!r.isEmpty()) r.peek().animation.animate(g, r.x*16+focusX, r.y*16+focusY);
                }
            });
        }
        //System.out.println("Checkpoint 1");
        //objectLock.lock();
        //System.out.println("Checkpoint 2");
        //try{
            for(final ListIterator<GameObject> iter=objects.listIterator();iter.hasNext();){
                GameObject ob = iter.next();
                if(overlay.isVisible(ob.x, ob.y)) ob.render(g, focusX, focusY);
            }
            if(debugMode) graph.paint(g, focusX, focusY, this);
        //}finally{
        //    objectLock.unlock();
        //}
    }
    
    /**
     * Tells every Creature that an amount of turns has passed.
     * @param turnNum The amount of turns passed.
     */
    public void turn(double turnNum){
        objectLock.lock();
        try{
            for(final ListIterator<GameObject> iter = objects.listIterator();iter.hasNext();) 
                iter.next().turn(turnNum);
        }finally{
            objectLock.unlock();
        }
    }
    
    /**
     * Adds a Hero to this Area.
     * @param h The Hero
     * @param start Whether to place the Hero at the start or end coordinates.
     */
    public void addHero(Hero h, boolean start){
        hero = h;
        h.setArea(this, start);
        //objectLock.lock();
        //try{
            try{
                if(!(objects.get(0) instanceof Hero)){
                    objects.add(0, h);
                }
            }catch(IndexOutOfBoundsException e){
                objects.add(h);
            }
        //}finally{
        //    objectLock.unlock();
        //}
    }

    /**
     * Adds an Item to the Area on the given coordinates.
     * @param i The Item
     * @param x
     * @param y
     */
    public void plop(Item i, int x, int y){
        PhysicalReceptacle r = getReceptacle(x, y);
        if(r==null) addReceptacle(new Floor(i, x, y));
        else if(r instanceof LockedChest) LockedChest.replop(x, y, this, i);
        else r.add(i);
    }
    
    /**
     * Returns a list of all Items in this Area.
     * @return
     */
    public List<Item> items(){
        return receptacles.stream().collect(LinkedList<Item>::new, 
                (lst, receptacle) -> lst.addAll(receptacle),
                (lst, lst2) -> lst.addAll(lst2));
    }
    
    /**
     * Clicks on the given tile coordinates.
     * @param x
     * @param y
     * @deprecated DANGEROUS AWT-only
     */
    public void click(int x, int y){
        if(overlay.isUnexplored(x, y)) return;
        if(hero.x!=x||hero.y!=y){
            ((PlayerAI)hero.attributes.ai).unfinished = true;
            hero.attributes.ai.setDestination(x, y);
            Window.main.setTurnsPassed(hero.attributes.speed);
        }else hero.attributes.ai.BASEACTIONS
                    .interact(hero, hero.area, hero.x, hero.y);
    }
    
        
    
    /**
     * Generates water.
     */
    private void water(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("floor")&&location.feeling.waterGenChance.chance()){
                    map[y][x] = new Water(location, x%2);
                }
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            Distribution ch = new Distribution(1, n);
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x]!=null&&map[y][x].equals("water")){
                        if(ch.chance()){
                            spreads = true;
                            spreadWater(x, y);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Generates grass.
     */
    private void grass(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("floor")&&location.feeling.grassGenChance.chance()) 
                    map[y][x] = new Grass(location, false);
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x]!=null&&map[y][x].equals("lowgrass")&&Distribution.chance(1, n)){
                        spreads = true;
                        spreadGrass(x, y);
                    }
                }
            }
        }
        
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("lowgrass")&&location.feeling.grassUpgradeChance.chance()){
                    if(location.feeling.equals(LevelFeeling.BURNED))
                        map[y][x] = new Tile("embers", location, true, false, true);
                    else map[y][x] = new Grass(location, true);
                }
            }
        }
    }
    
    /**
     * Spreads grass orthogonally.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    private void spreadGrass(int x, int y){
        if(map[y][x+1].isFloor()) map[y][x+1] = new Grass(location, false);
        if(map[y][x-1].isFloor()) map[y][x-1] = new Grass(location, false);
        if(map[y+1][x].isFloor()) map[y+1][x] = new Grass(location, false);
        if(map[y-1][x].isFloor()) map[y-1][x] = new Grass(location, false);
    }
    
    /**
     * Spreads water orthogonally.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    private void spreadWater(int x, int y){
        if(map[y][x+1].isFloor()) map[y][x+1] = new Water(location, x%2);
        if(map[y][x-1].isFloor()) map[y][x-1] = new Water(location, x%2);
        if(map[y+1][x].isFloor()) map[y+1][x] = new Water(location, x%2);
        if(map[y-1][x].isFloor()) map[y-1][x] = new Water(location, x%2);
    }
    
    /**
     * Adds water and grass to this Area and shades it.
     */
    public void addDeco(){
        if(location.feeling.waterBeforeGrass){
            water();
            grass();
        }else{
            grass();
            water();
        }
        addWaterShaders();
    }
    
    private void addWaterShaders(){
        for(int y=1;y<dimension.height-1;y++)
            for(int x=1;x<dimension.width-1;x++)
                if(map[y][x]!=null&&map[y][x].equals("water"))
                    ((Water)map[y][x]).addShaders(genShaderString(x, y), location);
    }
    
    private String genShaderString(int x, int y){
        String ret = "";
        if(!map[y-1][x].name.contains("wa")) ret += "n";
        if(!map[y][x+1].name.contains("wa")) ret += "e";
        if(!map[y+1][x].name.contains("wa")) ret += "s";
        if(!map[y][x-1].name.contains("wa")) ret += "w";
        return ret;
    }
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        AreaMemento memento = (AreaMemento) in.readObject();
        location = memento.getLocation();
        map = memento.getMap();
        objectLock = new ReentrantLock();
        addWaterShaders();
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(new AreaMemento(location, map));
    }
    
}

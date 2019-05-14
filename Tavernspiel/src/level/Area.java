
package level;

import ai.PlayerAI;
import blob.Blob;
import containers.FloorCrate;
import containers.LockedChest;
import containers.PhysicalCrate;
import creatureLogic.VisibilityOverlay;
import creatures.Creature;
import creatures.Hero;
import designer.AreaTemplate;
import gui.Window;
import static gui.mainToolbox.Main.HEIGHT;
import static gui.mainToolbox.Main.WIDTH;
import static gui.mainToolbox.MouseInterpreter.xOrient;
import static gui.mainToolbox.MouseInterpreter.yOrient;
import static gui.mainToolbox.MouseInterpreter.zoom;
import items.Item;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import listeners.Interactable;
import listeners.RotatableTile;
import logic.ConstantFields;
import logic.Distribution;
import logic.GameObject;
import logic.Utils.Unfinished;
import logic.mementoes.AreaMemento;
import pathfinding.Graph;
import tiles.Tile;
import tiles.assets.Barricade;
import tiles.assets.DepthEntrance;
import tiles.assets.DepthExit;
import tiles.assets.Door;
import tiles.assets.Embers;
import tiles.assets.Grass;
import tiles.assets.Water;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an Area where the game can be in.
 */
public class Area implements Serializable{
    
    private final static long serialVersionUID = 2049197;
    
    public transient Tile[][] map;
    public Dimension dimension;
    public transient Location location;
    public Integer[] startCoords, endCoords;
    public final int depth;
    private final List<GameObject> objects = new ArrayList<>();
    protected final List<PhysicalCrate> crates = new ArrayList<>();
    private ListIterator<GameObject> iter;

    public Graph graph = null;
    public volatile Hero hero;
    public final VisibilityOverlay overlay;
    public transient ReentrantLock objectLock = new ReentrantLock();
    public int orientation = 0;
    
    @Unfinished("Remove debug")
    public boolean debugMode = true;
    
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
     * Prints the given area onto this area and replaces only non-treadable tiles.
     * @param area The area to print.
     * @param x1 The top left x.
     * @param y1 The top left y.
     */
    public void blit(Area area, int x1, int y1){
        int o = getApparentOrientation(area), w = area.dimension.width, h = area.dimension.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++){
            if(map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1]==null) map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = area.map[y][x];
            else if(!map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1].treadable&&area.map[y][x].treadable){
                if(!map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1].getClass().isAssignableFrom(Tile.class)){
                    map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = area.map[y][x];
                }
            }else if(area.map[y][x].getClass().isAssignableFrom(Tile.class)
                    && !(area.map[y][x] instanceof Door) && !(area.map[y][x] instanceof Barricade)){
                map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = area.map[y][x];
            }
        }
        objects.addAll(area.objects.stream().map(ob -> {
            int temp = y1 + yOrient(o,ob.x,ob.y,w,h);
            ob.x = x1 + xOrient(o,ob.x,ob.y,w,h);
            ob.y = temp;
            ob.setArea(this, true);
            return ob;
        }).collect(Collectors.toList()));
        crates.addAll(area.crates.stream().map(rec -> {
            int temp = y1 + yOrient(o,rec.x,rec.y,w,h);
            rec.x = x1 + xOrient(o,rec.x,rec.y,w,h);
            rec.y = temp;
            if(rec instanceof Interactable) 
                map[rec.y][rec.x].interactable = rec;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null){
            startCoords = new Integer[]{xOrient(o,area.startCoords[0],area.startCoords[1],w,h)+x1, yOrient(o,area.startCoords[0],area.startCoords[1],w,h)+y1};
            ((DepthEntrance) map[startCoords[1]][startCoords[0]]).currentArea = this;
        }else if(area.endCoords!=null){
            endCoords = new Integer[]{xOrient(o,area.endCoords[0],area.endCoords[1],w,h)+x1, yOrient(o,area.endCoords[0],area.endCoords[1],w,h)+y1};
            ((DepthExit) map[endCoords[1]][endCoords[0]]).setArea(this);
        }
    }
    
    /**
     * Prints the given area onto this area (not replacing preexisting Tiles).
     * @param area The area to print.
     * @param x1 The top left x.
     * @param y1 The top left y.
     */
    public void blitSafely(Area area, int x1, int y1){
        int o = getApparentOrientation(area), w = area.dimension.width, h = area.dimension.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++){
            if(map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1]==null) map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = area.map[y][x];
            else throw new IllegalStateException("Safe blit failed.");
        }
        objects.addAll(area.objects.stream().map(ob -> {
            int temp = y1 + yOrient(o,ob.x,ob.y,w,h);
            ob.x = x1 + xOrient(o,ob.x,ob.y,w,h);
            ob.y = temp;
            ob.setArea(this, true);
            return ob;
        }).collect(Collectors.toList()));
        crates.addAll(area.crates.stream().map(rec -> {
            int temp = y1 + yOrient(o,rec.x,rec.y,w,h);
            rec.x = x1 + xOrient(o,rec.x,rec.y,w,h);
            rec.y = temp;
            if(rec instanceof Interactable) 
                map[rec.y][rec.x].interactable = rec;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null){
            startCoords = new Integer[]{xOrient(o,area.startCoords[0],area.startCoords[1],w,h)+x1, yOrient(o,area.startCoords[0],area.startCoords[1],w,h)+y1};
            ((DepthEntrance) map[startCoords[1]][startCoords[0]]).currentArea = this;
        }else if(area.endCoords!=null){
            endCoords = new Integer[]{xOrient(o,area.endCoords[0],area.endCoords[1],w,h)+x1, yOrient(o,area.endCoords[0],area.endCoords[1],w,h)+y1};
            ((DepthExit) map[endCoords[1]][endCoords[0]]).setArea(this);
        }
    }
    
    /**
     * Raw copies the given area onto this one.
     * @param area
     * @param x1
     * @param y1
     */
    protected void blitDirty(Area area, int x1, int y1){
        int o = getApparentOrientation(area), w = area.dimension.width, h = area.dimension.height;
        for(int y=0;y<h;y++) for(int x=0;x<w;x++){
            map[yOrient(o,x,y,w,h)+y1][xOrient(o,x,y,w,h)+x1] = area.map[y][x];
            if(area.map[y][x] instanceof RotatableTile) ((RotatableTile) area.map[y][x]).rotateImage(o);
        }
        objects.addAll(area.objects.stream().map(ob -> {
            int temp = y1 + yOrient(o,ob.x,ob.y,w,h);
            ob.x = x1 + xOrient(o,ob.x,ob.y,w,h);
            ob.y = temp;
            ob.setArea(this, true);
            return ob;
        }).collect(Collectors.toList()));
        crates.addAll(area.crates.stream().map(rec -> {
            int temp = y1 + yOrient(o,rec.x,rec.y,w,h);
            rec.x = x1 + xOrient(o,rec.x,rec.y,w,h);
            rec.y = temp;
            if(rec instanceof Interactable) 
                map[rec.y][rec.x].interactable = rec;
            return rec;
        }).collect(Collectors.toList()));
        if(area.startCoords!=null){
            startCoords = new Integer[]{xOrient(o,area.startCoords[0],area.startCoords[1],w,h)+x1, yOrient(o,area.startCoords[0],area.startCoords[1],w,h)+y1};
            ((DepthEntrance) map[startCoords[1]][startCoords[0]]).currentArea = this;
        }else if(area.endCoords!=null){
            endCoords = new Integer[]{xOrient(o,area.endCoords[0],area.endCoords[1],w,h)+x1, yOrient(o,area.endCoords[0],area.endCoords[1],w,h)+y1};
            ((DepthExit) map[endCoords[1]][endCoords[0]]).setArea(this);
        }
    }
    
    private int getApparentOrientation(Area area){
        int o = area.orientation - orientation;
        if(o<0) return o+4;
        return o;
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
        map[y][x] = new Embers(location);
        PhysicalCrate r = getReceptacle(x, y);
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
    public PhysicalCrate getReceptacle(int x, int y){
        for(int n=0;n<crates.size();n++){
            PhysicalCrate temp = crates.get(n);
            if(temp.x==x&&temp.y==y){
                return temp;
            }
        }
        return null;
    }
    
    /**
     * Adds a Receptacle to this Area.
     * @param rec
     */
    public void addReceptacle(PhysicalCrate rec){
        if(rec instanceof Interactable) 
            map[rec.y][rec.x].interactable = rec;
        crates.add(rec);
    }
    
    /**
     * Removes the Receptacle at the given coordinates.
     * @param x
     * @param y
     * @throws IllegalStateException if there is no Receptacle at the given
     * coords.
     */
    public void removeReceptacle(int x, int y){
        PhysicalCrate r;
        Iterator<PhysicalCrate> it = crates.iterator();
        while(it.hasNext()){
            r = it.next();
            if(r.x==x&&r.y==y){
                if(r instanceof Interactable) map[y][x].interactable = null;
                it.remove();
                return;
            }
        }
        throw new IllegalStateException("No receptacle at coords: " + x + ", " + y);
    }
    
    /**
     * Removes the given Receptacle.
     * @param r
     */
    public void removeReceptacle(PhysicalCrate r){
        crates.remove(r);
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
            getReceptacle(c.x, c.y).addAll(c.inventory.equipment);
        }catch(NullPointerException e){
            FloorCrate floor = new FloorCrate(c.x, c.y);
            floor.addAll(c.inventory);
            floor.addAll(c.inventory.equipment);
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
    public void replaceHeap(int x, int y, PhysicalCrate rec){
        crates.remove(getReceptacle(x, y));
        if(rec instanceof Interactable) 
                map[y][x].interactable = rec;
        crates.add(rec);
    }
    
    /**
     * Adds a GameObject to this Area.
     * @param object The new GameObject.
     */
    public void addObject(GameObject object){
        //objectLock.lock();
        //try{
            object.setArea(this, true);
            if(objectLock.isLocked()) iter.add(object);
            else objects.add(object);
        //}finally{
        //    objectLock.unlock();
        //}
    }

    /**
     * Gets all gases on the given Tile.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return A list of all gases on the given Tile, empty if there are no gases.
     */
    public List<Blob> getGases(int x, int y){
        return objects.stream().filter(ob -> ob instanceof Blob && ob.y==y && ob.x==x).map(ob -> (Blob) ob).collect(Collectors.toList());
    }
    
    /**
     * Checks if there is a gas on the given Tile.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param name The name of the GameObject to search for.
     * @return True if there is, false if not.
     */
    public boolean gameObjectPresent(int x, int y, String name){
        return objects.stream().filter(ob -> ob.name.equals(name) && ob.y==y && ob.x==x).count()>0;
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
        return graph.tileFree(x, y)&&map[y][x].treadable;
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
     * @param loc Specifies a Location to be used, otherwise the Location the
     *      template was serialized with will be used.
     * @return
     */
    public static Area getPreloadedArea(String filepath, Location... loc){
        Area area = AreaTemplate.deserialize(filepath).toArea(loc);
        for(int y=0;y<area.dimension.height;y++){
            for(int x=0;x<area.dimension.width;x++){
                if(area.map[y][x] instanceof DepthExit) area.endCoords = new Integer[]{x, y};
                else if(area.map[y][x] instanceof DepthEntrance) area.startCoords = new Integer[]{x, y};
            }
        }
        area.graph = new Graph(area, null);
        return area;
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
    public void renderObjects(Graphics2D g, int focusX, int focusY){
        synchronized(crates){
            crates.stream().forEach(r -> {
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
        new ArrayList<>(objects).stream().filter((ob) -> (overlay.isVisible(ob.x, ob.y))).forEachOrdered((ob) -> {
            if(!ob.dead) ob.render(g, focusX, focusY);
        }); //if(debugMode) graph.debugPaint(g, focusX, focusY, this);
        /*}finally{
        objectLock.unlock();
        }*/
    }
    
    /**
     * Tells every Creature that an amount of turns has passed.
     * @param turnNum The amount of turns passed.
     */
    public void turn(double turnNum){
        objectLock.lock();
        try{
            GameObject current;
            for(iter = objects.listIterator();iter.hasNext();){
                current = iter.next();
                if(current.dead) iter.remove();
                else current.turn(turnNum);
            }
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
        PhysicalCrate r = getReceptacle(x, y);
        if(r==null) addReceptacle(new FloorCrate(i, x, y));
        else if(r instanceof LockedChest) LockedChest.replop(x, y, this, i);
        else r.add(i);
    }
    
    /**
     * Returns a list of all Items in this Area.
     * @return
     */
    public List<Item> items(){
        return crates.stream().collect(LinkedList<Item>::new, 
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
            Window.main.setTurnsPassed(hero.attributes.health.walkSpeed);
        }else hero.attributes.ai.BASEACTIONS
                    .interact(hero, hero.area, hero.x, hero.y);
    }
    
    
    /**
     * Paints the given area on the given graphics.
     * @thread render
     * @param fx The focusX
     * @param fy The focusY
     * @param g The graphics to paint on.
     */
    public void paint(Graphics2D g, int fx, int fy){
        g.setColor(ConstantFields.exploredColor);
        for(int y=fy, maxY=fy+dimension.height*16;y<maxY;y+=16){
            for(int x=fx, maxX=fx+dimension.width*16;x<maxX;x+=16){
                int tx = (x-fx)/16, ty = (y-fy)/16;
                try{
                    if(x<0||y<0||x*zoom>WIDTH||y*zoom>HEIGHT) continue;
                    if(debugMode){
                        if(map[ty][tx]!=null) map[ty][tx].paint(g, x, y);
                    }else{                  
                        Image shade, exShade;
                        if(overlay.isUnexplored(tx, ty)) continue;
                        else shade = VisibilityOverlay.unexploredFog.getShadow(overlay.map, tx, ty, 0, true);
                        if(map[ty][tx]!=null) map[ty][tx].paint(g, x, y);
                        
                        if(!overlay.isExplored(tx, ty))
                            exShade = VisibilityOverlay.exploredFog.getShadow(overlay.map, tx, ty, 1, false);
                        else exShade = VisibilityOverlay.exploredFog.getFullShader();
                        if(exShade!=null) g.drawImage(exShade, x, y, null);
                        if(shade!=null) g.drawImage(shade, x, y, null);
                    }
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
        if(debugMode) graph.debugPaint(g, fx, fy, this);
    }
        
    
    /**
     * Generates water.
     */
    private void generateWater(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("floor")&&location.feeling.waterGenChance.chance()){
                    map[y][x] = new Water(location, x%2);
                    graph.map[y][x].number = 3;
                }
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x]!=null && graph.map[y][x].number == n && map[y][x].equals("water") && Distribution.chance(1, n)){
                        spreads = true;
                        spreadWater(x, y, n+1);
                    }
                }
            }
        }
    }
    
    /**
     * Generates grass.
     */
    private void generateGrass(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("floor")&&location.feeling.grassGenChance.chance()){
                    map[y][x] = new Grass(location, false);
                    graph.map[y][x].number = 3;
                }
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x]!=null && graph.map[y][x].number == n && map[y][x].equals("lowgrass") && Distribution.chance(1, n)){
                        spreads = true;
                        spreadGrass(x, y, n+1);
                    }
                }
            }
        }
        
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x]!=null&&map[y][x].equals("lowgrass")&&location.feeling.grassUpgradeChance.chance()){
                    if(location.feeling.equals(LevelFeeling.BURNED))
                        map[y][x] = new Embers(location);
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
    private void spreadGrass(int x, int y, int generation){
        if(map[y][x+1].isFloor()){
            map[y][x+1] = new Grass(location, false);
            graph.map[y][x+1].number = generation;
        }
        if(map[y][x-1].isFloor()){
            map[y][x-1] = new Grass(location, false);
            graph.map[y][x-1].number = generation;
        }
        if(map[y+1][x].isFloor()){
            map[y+1][x] = new Grass(location, false);
            graph.map[y+1][x].number = generation;
        }
        if(map[y-1][x].isFloor()){
            map[y-1][x] = new Grass(location, false);
            graph.map[y-1][x].number = generation;
        }
    }
    
    /**
     * Spreads water orthogonally.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    private void spreadWater(int x, int y, int generation){
        if(map[y][x+1].isFloor()){
            map[y][x+1] = new Water(location, x%2);
            graph.map[y][x+1].number = generation;
        }
        if(map[y][x-1].isFloor()){
            map[y][x-1] = new Water(location, x%2);
            graph.map[y][x-1].number = generation;
        }
        if(map[y+1][x].isFloor()){
            map[y+1][x] = new Water(location, x%2);
            graph.map[y+1][x].number = generation;
        }
        if(map[y-1][x].isFloor()){
            map[y-1][x] = new Water(location, x%2);
            graph.map[y-1][x].number = generation;
        }
    }
    
    /**
     * Adds water and grass to this Area and shades it.
     */
    public void addDeco(){
        if(location.feeling.waterBeforeGrass){
            generateWater();
            generateGrass();
        }else{
            generateGrass();
            generateWater();
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

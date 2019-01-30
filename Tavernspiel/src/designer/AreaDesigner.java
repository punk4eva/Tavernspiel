
package designer;

import gui.Window;
import gui.mainToolbox.Main;
import static gui.mainToolbox.Main.HEIGHT;
import static gui.mainToolbox.Main.WIDTH;
import gui.mainToolbox.MouseInterpreter;
import static gui.mainToolbox.MouseInterpreter.focusX;
import static gui.mainToolbox.MouseInterpreter.focusY;
import gui.mainToolbox.Pacemaker;
import gui.mainToolbox.PageFlipper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayDeque;
import java.util.Scanner;
import level.Location;
import logic.Utils.Unfinished;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 */
public class AreaDesigner extends Main{
    
    AreaTemplate area = new AreaTemplate(new Dimension(1, 1), null);
    CommandLibrary command = new CommandLibrary();
    boolean boundary = false, fillMode = false;
    TileSelection brush = TileSelection.floor;
    
    /**
     * Creates a new instance.
     */
    public AreaDesigner(){
        mouse = new MouseEmulator();
        window = new Window(WIDTH, HEIGHT, "Area Designer", this);
        Dimension dim = getDimension();
        if(dim==null){
            System.out.println("Filepath...");
            area = AreaTemplate.deserialize(new Scanner(System.in).nextLine());
        }else area = new AreaTemplate(
            dim, pollLocation());
        start();
    }
    
    static Dimension getDimension(){
        System.out.println("Define the area bounds...");
        String next = new Scanner(System.in).nextLine();
        if(next.startsWith("/load")) return null;
        String[] p = next.split(", ");
        return new Dimension(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]));
    }
    
    @Override
    @Unfinished("Fix")
    public final synchronized void start(){
        pageFlipper = new PageFlipper(this);
        pageFlipper.setPage("main");
        pacemaker = new Pacemaker(this);
        runThread = new Thread(this, "Run Thread");
        runThread.setDaemon(true);
        runThread.start();
        running = true;
    }
    
    class CommandLibrary{
        
        String commands[] = new String[]{"/tile * ", "/tile ", "/boundary",
            "/custom ", "/save ", "/exit", "/erasor", "/fill"};
        Scanner scan = new Scanner(System.in);
        
        public void activate(){
            String command = scan.nextLine();
            for(String str : commands) if(command.startsWith(str)){
                switch(str){
                    case "/tile * ": tileStar(command.substring(str.length()));
                        return;
                    case "/tile ": tile(command.substring(str.length()));
                        return;
                    case "/fill": fill();
                        return;
                    case "/boundary": boundary();
                        return;
                    case "/erasor": brush = null;
                        return;
                    case "/custom ": custom(command.substring(str.length()));
                        return;
                    case "/save ": save(command.substring(str.length()));
                        return;
                    case "/exit": stop();
                        System.exit(0);
                        return;
                }
            }
            System.err.println("Invalid command \"" + command + "\"");
        }
        
    }
    
    /**
     * @param str name of TileSelection (e.g: "wall").
     * Sets the TileSelection directly.
     */
    void tileStar(String str){
        brush = TileSelection.select(str);
    }
    
    /**
     * @param str The name and color of the Tile (e.g: "wall, emerald").
     * Sets the TileSelection to be a single static tile with the given color.
     */
    void tile(String str){
        String[] p = str.split(", ");
        brush = new TileSelection(p[0], p[1], p[2]);
    }
    
    /**
     * Toggles the boundary status of the brush.
     */
    void boundary(){
        boundary = !boundary;
    }
    
    /**
     * @param str The tile names and probabilities (e.g: 
     *          "wall, 22|specialwall, 1").
     * Sets the brush to a brand new TileSelection.
     */
    void custom(String str){
        brush = TileSelection.parse(str);
    }
    
    void save(String filepath){
        area.serialize(filepath);
    }
    
    void fill(){
        fillMode = !fillMode;
    }
    
    void fill(int x, int y){
        ArrayDeque<Integer[]> queue = new ArrayDeque<>();
        try{
            queue.add(new Integer[]{x, y});
            area.map[y][x] = brush.clone();
        }catch(Exception e){return;}
        while(!queue.isEmpty()){
            Integer[] current = queue.pop();
            for(Point.Direction dir : Point.Direction.values()){
                Integer[] next = new Integer[]{dir.x+current[0], dir.y+current[1]};
                System.out.println(current[0] + ", " + current[1]);
                try{
                    if(area.map[next[1]][next[0]]==null){
                        queue.add(next);
                        area.map[next[1]][next[0]] = brush.clone();
                    }
                }catch(Exception e){}
            }
        }
    }
    
    /**
     * Renders the designer.
     */
    @Override
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(4);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        paintAreaTemplate(area, g);
        if(gui.dialogue!=null) gui.dialogue.paint(g);
        g.dispose();
        bs.show();
    }
    
    /**
     * Paints the given area on the given graphics.
     * @param area The area to paint.
     * @param g The graphics to paint on.
     */
    public void paintAreaTemplate(AreaTemplate area, Graphics g){
        for(int y=focusY;y<focusY+area.dimension.height*16;y+=16){
            for(int x=focusX;x<focusX+area.dimension.width*16;x+=16){                
                TileSelection tile = area.map[(y-focusY)/16][(x-focusX)/16];
                if(tile!=null) tile.paint(g, x, y);
            }
        }
    }
    
    public static void main(String... args){
        AreaDesigner designer = new AreaDesigner();
        while(true) designer.command.activate();
    }
    
    private class MouseEmulator extends MouseInterpreter{

        @Override
        public void mouseDragged(MouseEvent me){
            if(System.currentTimeMillis()%25!=0) return;
            Integer[] p = pixelToTile(me.getX(), me.getY());
            try{
                if(boundary) area.map[p[1]][p[0]].boundary = !area.map[p[1]][p[0]].boundary;
                else if(brush==null) area.map[p[1]][p[0]] = null;
                else area.map[p[1]][p[0]] = brush.clone();
            }catch(Exception e){}
        }

        @Override
        public void mouseClicked(MouseEvent me){
            Integer[] p = pixelToTile(me.getX(), me.getY());
            try{
                if(fillMode) fill(p[0], p[1]);
                else if(boundary) area.map[p[1]][p[0]].boundary = !area.map[p[1]][p[0]].boundary;
                else if(brush==null) area.map[p[1]][p[0]] = null;
                else area.map[p[1]][p[0]] = brush.clone();
            }catch(Exception e){}
        }

        @Override
        public void mouseReleased(MouseEvent me){}

        @Override
        public void mouseWheelMoved(MouseWheelEvent me){}

    }
    
    public static Location pollLocation(){
        System.out.println("What is the name of the location?");
        String name = new Scanner(System.in).nextLine();
        Location loc = Location.LOCATION_MAP.get(name);
        if(loc==null) throw new IllegalStateException("Invalid name: " + name);
        return loc;
    }
    
}

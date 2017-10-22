
package designer;

import gui.MainClass;
import static gui.MainClass.HEIGHT;
import static gui.MainClass.WIDTH;
import static gui.MainClass.frameDivisor;
import static gui.MainClass.frameNumber;
import gui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

/**
 *
 * @author Adam Whittaker
 */
public class AreaDesigner extends MainClass{
    
    AreaTemplate area = new AreaTemplate(new Dimension(1, 1), null);
    CommandLibrary command = new CommandLibrary();
    boolean boundary = false;
    TileSelection brush = TileSelection.floor;
    
    
    public AreaDesigner(){
        window = new Window(WIDTH, HEIGHT, "Area Designer", this);
        Dimension dim = getDimension();
        if(dim==null){
            System.out.println("Filepath...");
            area = AreaTemplate.deserialize(new Scanner(System.in).nextLine());
        }else area = new AreaTemplate(
            dim, new LocationFactory().produce());
    }
    
    static Dimension getDimension(){
        System.out.println("Define the area bounds...");
        String next = new Scanner(System.in).nextLine();
        if(next.startsWith("/load")) return null;
        String[] p = next.split(", ");
        return new Dimension(Integer.parseInt(p[0]), 
                Integer.parseInt(p[1]));
    }
    
    class CommandLibrary{
        
        String commands[] = new String[]{"/tile * ", "/tile ", "/boundary",
            "/custom ", "/save ", "/exit", "/erasor"};
        Scanner scan = new Scanner(System.in);
        
        public void activate(){
            String command = scan.nextLine();
            for(String str : commands) if(command.startsWith(str)){
                switch(str){
                    case "/tile * ": tileStar(command.substring(str.length()));
                        return;
                    case "/tile ": tile(command.substring(str.length()));
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
    
    void tileStar(String str){
        brush = TileSelection.select(str);
    }
    
    void tile(String str){
        String[] p = str.split(" ");
        brush = new TileSelection(p[0], p[1]);
    }
    
    void boundary(){
        boundary = !boundary;
    }
    
    void custom(String str){
        brush = TileSelection.parse(str);
    }
    
    void save(String filepath){
        area.serialize(filepath);
    }
    
    /**
     * Renders the game with the given framerate.
     * @param frameInSec The framerate.
     */
    @Override
    public void render(int frameInSec){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(4);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if(frameInSec%16==0){
            frameNumber = (frameNumber+1) % frameDivisor;
        }
        paintAreaTemplate(area, g);
        if(currentDialogue!=null) currentDialogue.paint(g);
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
    
    @Override
    public void mouseDragged(MouseEvent me){
        if(System.currentTimeMillis()%25!=0) return;
        Integer[] p = translateMouseCoords(me.getX(), me.getY());
        try{
            if(boundary) area.map[p[1]][p[0]].boundary = !area.map[p[1]][p[0]].boundary;
            else if(brush==null) area.map[p[1]][p[0]] = null;
            else area.map[p[1]][p[0]] = brush.clone();
        }catch(Exception e){}
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        Integer[] p = translateMouseCoords(me.getX(), me.getY());
        try{
            if(boundary) area.map[p[1]][p[0]].boundary = !area.map[p[1]][p[0]].boundary;
            else if(brush==null) area.map[p[1]][p[0]] = null;
            else area.map[p[1]][p[0]] = brush.clone();
        }catch(Exception e){}
    }
    
    @Override
    public void mouseReleased(MouseEvent me){
    
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent me){

    }
    
}

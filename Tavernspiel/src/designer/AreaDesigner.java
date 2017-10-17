
package designer;

import dialogues.Dialogue;
import gui.MainClass;
import static gui.MainClass.HEIGHT;
import static gui.MainClass.WIDTH;
import static gui.MainClass.frameDivisor;
import static gui.MainClass.frameNumber;
import gui.Window;
import guiUtils.CSlider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

/**
 *
 * @author Adam Whittaker
 */
public class AreaDesigner extends MainClass{
    
    AreaTemplate area = new AreaTemplate(new Dimension(1, 1), null);
    CommandLibrary command = new CommandLibrary();
    
    
    public AreaDesigner(){
        window = new Window(WIDTH, HEIGHT, "Area Designer", this);
        area = new AreaTemplate(
            new BoundsDialogue().getDimension(this),
            new LocationFactory().produce());
    }
    
    static class BoundsDialogue extends Dialogue{
        
        public BoundsDialogue(){
            super("define the area bounds", "offCase", false, 
                    new CSlider[]{new CSlider("width", 0, 0, 0, 120, 1),
                        new CSlider("height", 0, 0, 0, 120, 1)});
        }
        
        public Dimension getDimension(MainClass main){
            Object[] data = action(main).getData();
            return new Dimension((int)data[0], (int)data[1]);
        }
        
    }
    
    class CommandLibrary{
        
        String commands[] = new String[]{"/tile * ", "/tile ", "/boundary",
            "/custom", "/save "};
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
                    case "/custom": custom();
                        return;
                    case "/save ": save(command.substring(str.length()));
                        return;
                }
            }
            System.err.println("Invalid command \"" + command + "\"");
        }
        
    }
    
    void tileStar(String str){
    
    }
    
    void tile(String str){
    
    }
    
    void boundary(){
    
    }
    
    void custom(){
    
    }
    
    void save(String filepath){
    
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
        new AreaDesigner();
    }
    
}

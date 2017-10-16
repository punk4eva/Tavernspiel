
package designer;

import dialogues.Dialogue;
import gui.MainClass;
import guiUtils.CComponent;
import guiUtils.CSlider;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Scanner;

/**
 *
 * @author Adam Whittaker
 */
public class AreaDesigner extends MainClass{
    
    AreaTemplate area;
    
    static class BoundsDialogue extends Dialogue{
        
        public BoundsDialogue(){
            super("define the area bounds", "offCase", false, 
                    new CSlider[]{new CSlider("width", 0, 0, 0, 120, 1),
                        new CSlider("height", 0, 0, 0, 120, 1)});
        }
        
        public Dimension getDimension(AreaDesigner des, MainClass main){
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
    
}

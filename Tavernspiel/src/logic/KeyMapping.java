
package logic;

import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Adam Whittaker
 */
public class KeyMapping{
    
    private KeyMapping(){}
    
    //Key Typed
    public static char GO_UP = 'w';
    public static char GO_DOWN = 's';
    public static char GO_LEFT = 'a';
    public static char GO_RIGHT = 'd';
    public static char INTERACT = 'f';
    public static char TOGGLE_INVENTORY = 'e';
    
    //Key Pressed
    public static int ESCAPE = KeyEvent.VK_ESCAPE;
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int LEFT = KeyEvent.VK_A;
    public static int RIGHT = KeyEvent.VK_D;
    
    public static void saveState(){
        try(FileWriter out = new FileWriter("saves/keyMapping.state")){
            out.write(GO_UP);
            out.write(GO_DOWN);
            out.write(GO_LEFT);
            out.write(GO_RIGHT);
            out.write(INTERACT);
            out.write(TOGGLE_INVENTORY);
            out.write(ESCAPE);
            out.write(UP);
            out.write(DOWN);
            out.write(LEFT);
            out.write(RIGHT);
        }catch(IOException e){}
    }
    
    public static void retrieveState(){
        try(FileReader in = new FileReader("saves/keyMapping.state")){
            GO_UP = (char) in.read();
            GO_DOWN = (char) in.read();
            GO_LEFT = (char) in.read();
            GO_RIGHT = (char) in.read();
            INTERACT = (char) in.read();
            TOGGLE_INVENTORY = (char) in.read();
            ESCAPE = in.read();
            UP = in.read();
            DOWN = in.read();
            LEFT = in.read();
            RIGHT = in.read();
        }catch(IOException e){}
    }
    
}


package dialogues.assets;

import dialogues.ButtonDialogue;
import gui.Game;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * A dialogue to catch exceptions at run-time.
 */
public class ExceptionDialogue extends ButtonDialogue{
    
    private final Exception exception;
    
    public ExceptionDialogue(Exception ex){
        super(false, null, "ERROR DETECTED", ex.toString().toUpperCase() + ": " + ex.getMessage(), new String[]{"abort", "restart", "fail"});
        exception = ex;
    }
    
    /**
     * Decides how to proceed at the given exception.
     */
    @Override
    public synchronized void next(){
        super.next();
        exception.printStackTrace(Main.exceptionStream);
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
        Game game = (Game) Window.main;
        switch(sc.getName()){
            case "abort":
                game.save();
                System.exit(-1);
            case "restart":
                game.save();
                game.stop();
                game.start();
                break;
            case "fail":
                System.exit(-1);
        }
    }
    
}

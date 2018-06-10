
package dialogues;

import gui.Game;
import gui.mainToolbox.Main;
import gui.Window;

/**
 *
 * @author Adam Whittaker
 * 
 * A dialogue to catch exceptions at runtime.
 */
public class ExceptionDialogue extends Dialogue{
    
    private final Exception exception;
    
    public ExceptionDialogue(Exception ex){
        super(ex.toString().toUpperCase() + ": " + ex.getMessage(), null, false, "abort", "retry", "fail");
        exception = ex;
    }
    
    /**
     * Decides how to proceed at the given exception.
     */
    public synchronized void next(){
        exception.printStackTrace(Main.exceptionStream);
        Game game = (Game) Window.main;
        switch(super.action(game).getName()){
            case "abort":
                game.save();
                System.exit(-1);
            case "retry":
                game.save();
                game.stop();
                game.start();
                break;
            case "fail":
                System.exit(-1);
        }
    }
    
}

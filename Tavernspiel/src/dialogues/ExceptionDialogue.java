
package dialogues;

import gui.Game;

/**
 *
 * @author Adam Whittaker
 */
public class ExceptionDialogue extends Dialogue{
    
    private final Exception exception;
    
    public ExceptionDialogue(Exception ex){
        super(ex.toString().toUpperCase() + ": " + ex.getMessage(), "abort", "abort", "retry", "fail");
        exception = ex;
    }
    
    public synchronized void next(Game game){
        exception.printStackTrace(game.exceptionStream);
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

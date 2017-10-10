
package dialogues;

import gui.Game;

/**
 *
 * @author Charlie Hands
 * @author Adam Whittaker
 */
public class PauseMenu extends Dialogue{
    
    public PauseMenu(){
        super("PAUSE", "offCase", false, "Resume", "How to play", "Options", "Exit to menu");
    }
    
    /**
     * Pauses the given Game.
     * @param game The Game to pause.
     */
    public void next(Game game){
        String ret = super.action(game).getName();
        switch(ret){
            case "Options":
                new OptionsMenu().next(game);
                break;
            case "How to play":
                new InstructionsMenu().next(game);
                break;
            case "Exit to menu":
                game.save();
                game.endGame();
                break;
        }
    }
}

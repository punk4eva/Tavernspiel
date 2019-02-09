
package dialogues;

import gui.Game;
import gui.Window;
import gui.mainToolbox.Screen;

/**
 *
 * @author Charlie Hands
 * @author Adam Whittaker
 */
public class PauseMenu extends Dialogue{
    
    public PauseMenu(){
        super("PAUSE", false, new String[]{"Resume", "How to play", "Options", "Exit to menu"});
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
        Game game = (Game) Window.main;
        switch(name.getName()){
            case "Options":
                new SettingsMenu().next(game);
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

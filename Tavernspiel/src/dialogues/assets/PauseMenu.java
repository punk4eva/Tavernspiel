
package dialogues.assets;

import dialogues.ButtonDialogue;
import dialogues.assets.SettingsMenu;
import gui.Game;
import gui.Window;
import gui.mainToolbox.Screen;

/**
 *
 * @author Charlie Hands and Adam Whittaker
 */
public class PauseMenu extends ButtonDialogue{
    
    public PauseMenu(){
        super(false, null, "PAUSE", "", new String[]{"Resume", "How to play", "Options", "Exit to menu"});
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

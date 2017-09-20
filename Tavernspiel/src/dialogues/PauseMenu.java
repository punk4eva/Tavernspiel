
package dialogues;

import gui.Game;

/**
 *
 * @author Charlie Hands
 * @author Adam Whittaker
 */
public class PauseMenu extends Dialogue{
    
    public PauseMenu(){
        super("PAUSE", "offCase", false, "Resume", "Options", "Exit to menu");
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
            case "Exit to menu":
                game.save();
                game.endGame();
                break;
        }
    }

    /*public List<Screen> getScreens(){
    LinkedList<Screen> ret = new LinkedList<>();
    ret.add(new Screen("Background", 0,0,Window.main.getWidth(),Window.main.getHeight(),this));
    ret.add(new Screen("BackButton",100,100,20,20,this));
    
    return ret;
    }

    public List<Screen> getScreenList(){
       return screens;
    }*/

    /*public void paint(Graphics g){
    Font titlefnt = new Font("Arial", 10, 50);
    Font btnfont = new Font("Arial", 10, 20);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, Window.main.getWidth(),Window.main.getHeight());
    g.setColor(Color.WHITE);
    g.setFont(titlefnt);
    g.drawString("Paused", 20,20);
    g.drawRect(100,100,20,20);
    g.setFont(btnfont);
    g.drawString("Back", 100,100);
    }*/

    /*public void screenClicked(Screen.ScreenEvent e){
    
    }*/
    
}

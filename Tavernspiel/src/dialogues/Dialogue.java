
package dialogues;

import gui.MainClass;
import gui.Screen;
import java.awt.Color;
import java.awt.Graphics;
import listeners.ScreenListener;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class Dialogue implements ScreenListener{
    
    final String question;
    final String[] options;
    private String clickedScreen;
    private final Screen[] screenArray;
    private final String offCase;
    private final int height;
    private final int padding = 8;
    
    public Dialogue(String quest, String off, String... opt){
        options = opt;
        offCase = off;
        screenArray = getScreenArray();
        question = Utils.lineFormat(quest, 20);
        height = 2*padding + 12*Utils.lineCount(question) + (36+padding)*options.length;
    }
    
    public void paint(Graphics g, final int WIDTH, final int HEIGHT){
        int beginHeight = (HEIGHT-height)/2;
        int beginWidth = WIDTH/3;
        g.setColor(Color.gray);
        g.fill3DRect(beginWidth, beginHeight, beginWidth, height, false);
        g.setColor(Color.yellow);
        g.drawString(question, 2*padding+beginWidth, beginHeight+2*padding);
    }
    
    private void activate(MainClass main){
        main.changeCurrentDialogue(this);
        for(Screen sc : screenArray) main.addScreen(sc);
    }
    
    private void deactivate(MainClass main){
        main.changeCurrentDialogue(null);
        main.removeScreens(screenArray);
    }
    
    public synchronized String next(MainClass main){
        activate(main);
        try{
            wait();
        }catch(InterruptedException ex){
            System.err.println("Dialogue interrupted...");
        }
        deactivate(main);
        return clickedScreen;
    }

    @Override
    public synchronized void screenClicked(String screenName){
        clickedScreen = screenName;
        notify();
    }
    
    public synchronized void clickedOff(){
        clickedScreen = offCase;
        notify();
    }

    private Screen[] getScreenArray(){
        Screen[] ary = new Screen[options.length];
        for(int n=0;n<ary.length;n++){
            ary[n] = new Screen(options[n], 2*padding+MainClass.WIDTH/3, (2*padding+36)*n+2*padding+(MainClass.HEIGHT-height)/2, MainClass.WIDTH*2/3-2*padding, (2*padding+36)*(n+1)+(MainClass.HEIGHT-height)/2);
            ary[n].changeScreenListener(this);
        }
        return ary;
    }
    
}

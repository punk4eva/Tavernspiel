
package dialogues;

import gui.MainClass;
import gui.Screen;
import gui.Viewable;
import java.awt.Color;
import java.awt.Graphics;
import listeners.ScreenListener;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class Dialogue implements ScreenListener, Viewable{
    
    final String question;
    final String[] options;
    private String clickedScreen;
    private final Screen[] screenArray;
    private final String offCase;
    private final int height;
    private final int padding = 8;
    private final int heightOfQuestion;
    
    public Dialogue(String quest, String off, String... opt){
        options = opt;
        offCase = off;
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*options.length;
        screenArray = getScreenArray();
    }
    
    @Override
    public void paint(Graphics g){
        int beginHeight = (MainClass.HEIGHT-height)/2;
        int beginWidth = MainClass.WIDTH/3;
        g.setColor(Color.gray);
        g.fill3DRect(beginWidth, beginHeight, beginWidth, height, false);
        g.setColor(Color.magenta);
        for(int n=0;n<options.length;n++){
            g.fill3DRect(padding+beginWidth, 
                    beginHeight+heightOfQuestion+2*padding+(36+padding)*n, 
                    beginWidth-2*padding, 
                    36, true);
        }
        g.setColor(Color.yellow);
        g.drawString(question, 2*padding+beginWidth, beginHeight+2*padding);
        for(int n=0;n<options.length;n++){
            g.drawString(options[n], 
                    2*padding+beginWidth, 
                    beginHeight+heightOfQuestion+5*padding+(36+padding)*n);
        }
    }
    
    private void activate(MainClass main){
        main.changeCurrentDialogue(this);
        for(Screen sc : screenArray) main.addScreen(sc);
    }
    
    private void deactivate(MainClass main){
        main.changeCurrentDialogue(null);
        main.removeScreens(screenArray);
    }
    
    public synchronized String action(MainClass main){
        activate(main);
        try{
            wait();
        }catch(InterruptedException ex){
            ex.printStackTrace(MainClass.exceptionStream);
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
        Screen[] ary = new Screen[options.length+1];
        int beginWidth = MainClass.WIDTH/3;
        int beginHeight = (MainClass.HEIGHT-height)/2;
        for(int n=0;n<ary.length-1;n++){
            System.out.println(n + ": " + options[n]);
            ary[n] = new Screen(options[n], 
                    padding+beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight, 
                    beginWidth-2*padding, 
                    36);
            ary[n].changeScreenListener(this);
        }
        ary[ary.length-1] = new Screen("blank click", beginWidth, beginHeight, beginWidth, 36);
        return ary;
    }
    
}

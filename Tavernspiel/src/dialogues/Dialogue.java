
package dialogues;

import gui.MainClass;
import gui.Screen;
import gui.Screen.ScreenEvent;
import java.awt.Graphics;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * An Option Dialogue.
 */
public class Dialogue implements ScreenListener{
    
    final String question;
    final String[] options;
    private ScreenEvent clickedScreen;
    private final Screen[] screenArray;
    private final ScreenEvent offCase;
    private final int height;
    private final int padding = 8;
    private final int heightOfQuestion;
    private boolean clickOffable = true;
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param opt The options.
     */
    public Dialogue(String quest, ScreenEvent off, String... opt){
        options = opt;
        offCase = off;
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*options.length;
        screenArray = getScreens();
    }
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param opt The options.
     */
    public Dialogue(String quest, String off, String... opt){
        options = opt;
        offCase = new ScreenEvent(off);
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*options.length;
        screenArray = getScreens();
    }
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param click Sets whether the user can click away.
     * @param opt The options.
     */
    public Dialogue(String quest, String off, boolean click, String... opt){
        options = opt;
        clickOffable = click;
        offCase = new ScreenEvent(off);
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*options.length;
        screenArray = getScreens();
    }
    
    /**
     * Paints this Dialogue onto the given Graphics.
     * @param g The Graphics.
     */
    public void paint(Graphics g){
        int beginHeight = (MainClass.HEIGHT-height)/2;
        int beginWidth = MainClass.WIDTH/3;
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(beginWidth, beginHeight, beginWidth, height, false);
        g.setColor(ConstantFields.frontColor);
        for(int n=0;n<options.length;n++){
            g.fill3DRect(padding+beginWidth, 
                    beginHeight+heightOfQuestion+2*padding+(36+padding)*n, 
                    beginWidth-2*padding, 
                    36, true);
        }
        g.setColor(ConstantFields.textColor);
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
    
    /**
     * Activates this Dialogue.
     * @param main The MainClass to draw on.
     * @return The ScreenEvent that happened.
     */
    public synchronized ScreenEvent action(MainClass main){
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
    public synchronized void screenClicked(ScreenEvent sce){
        clickedScreen = sce;
        notify();
    }
    
    /**
     * Notifies this Dialogue that the user clicked away.
     */
    public synchronized void clickedOff(){
        clickedScreen = offCase;
        if(clickOffable) notify();
    }

    /**
     * Gets the screens associated with this Dialogue.
     * @return The Screens.
     */
    public final Screen[] getScreens(){
        Screen[] ary = new Screen[options.length+1];
        int beginWidth = MainClass.WIDTH/3;
        int beginHeight = (MainClass.HEIGHT-height)/2;
        for(int n=0;n<ary.length-1;n++){
            System.out.println(n + ": " + options[n]);
            ary[n] = new Screen(options[n], 
                    padding+beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight, 
                    beginWidth-2*padding, 
                    36, this);
        }
        ary[ary.length-1] = new Screen("blank click", beginWidth, beginHeight, beginWidth, 36, this);
        return ary;
    }
    
}

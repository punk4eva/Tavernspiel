
package dialogues;

import gui.MainClass;
import gui.Screen;
import gui.Screen.ScreenEvent;
import guiUtils.CButton;
import guiUtils.CComponent;
import guiUtils.CSlider;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 * 
 * An Option Dialogue.
 */
public class Dialogue implements ScreenListener, KeyListener{
    
    final String question;
    final CComponent[] options;
    private ScreenEvent clickedScreen;
    private final Screen[] screenArray;
    private final ScreenEvent offCase;
    private final int height;
    private final int padding = 8;
    private final int heightOfQuestion;
    private boolean clickOffable = true;
    private boolean customComponents = false;
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param opt The options.
     */
    public Dialogue(String quest, ScreenEvent off, String... opt){
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = getButtons(opt);
        offCase = off;
        screenArray = convertCComponents(options);
    }
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param opt The options.
     */
    public Dialogue(String quest, String off, String... opt){
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = getButtons(opt);
        offCase = new ScreenEvent(off);
        screenArray = convertCComponents(options);
    }
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param click Sets whether the user can click away.
     * @param opt The options.
     */
    public Dialogue(String quest, String off, boolean click, String... opt){
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = getButtons(opt);
        clickOffable = click;
        offCase = new ScreenEvent(off);
        screenArray = convertCComponents(options);
    }
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param click Sets whether the user can click away.
     * @param opt The interactables.
     */
    public Dialogue(String quest, String off, boolean click, CComponent... opt){
        question = Utils.lineFormat(quest, 20);
        heightOfQuestion = 12*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = opt;
        dressCComponents();
        clickOffable = click;
        offCase = new ScreenEvent(off);
        screenArray = Utils.getScreens(opt);
        customComponents = true;
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
        for(CComponent cc : options) cc.paint(g);
        g.setColor(ConstantFields.textColor);
        g.drawString(question, 2*padding+beginWidth, beginHeight+2*padding);
    }
    
    private void activate(MainClass main){
        if(customComponents) main.addKeyListener(this);
        main.changeCurrentDialogue(this);
        for(Screen sc : screenArray) if(sc instanceof CSlider.CSliderHandle) main.addDraggable(sc);
        else main.addScreen(sc);
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

    private CButton[] getButtons(String[] strs){
        CButton[] ary = new CButton[strs.length];
        int beginWidth = MainClass.WIDTH/3;
        int beginHeight = (MainClass.HEIGHT-height)/2;
        for(int n=0;n<ary.length;n++){
            ary[n] = new CButton(strs[n], 
                    padding+beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight, 
                    padding, this);
        }
        return ary;
    }
    
    /**
     * Gets the screens associated with this Dialogue.
     * @return The Screens.
     */
    public final Screen[] getScreenArray(){
        return screenArray;
    }
    
    private Screen[] convertCComponents(CComponent[] ary){
        LinkedList<Screen> lst = new LinkedList<>();
        for(CComponent cc : ary) lst.add((CButton)cc);
        lst.add(new Screen("blank click", MainClass.WIDTH/3, (MainClass.HEIGHT-height)/2, MainClass.WIDTH/3, 36, this));
        return lst.toArray(new Screen[lst.size()]);
    }
    
    private void dressCComponents(){
        int beginWidth = MainClass.WIDTH/3;
        int beginHeight = (MainClass.HEIGHT-height)/2;
        for(int n=0;n<options.length;n++) options[n].setTopLeft(padding+beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight);
    }

    @Override
    public void keyTyped(KeyEvent ke){}
    @Override
    public void keyPressed(KeyEvent ke){}
    @Override
    public synchronized void keyReleased(KeyEvent ke){
        if(ke.getKeyCode()==KeyEvent.VK_ENTER&&customComponents){
            clickedScreen = new ScreenEvent(options);
            notify();
        }
    }
    
}

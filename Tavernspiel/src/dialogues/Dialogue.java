
package dialogues;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.mainToolbox.Screen.ScreenEvent;
import gui.utils.CButton;
import gui.utils.CComponent;
import gui.utils.CSlider;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import listeners.ScreenListener;
import logic.ConstantFields;
import logic.ImageUtils;
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
    private int height = -1;
    private final int padding = 8;
    private final int heightOfQuestion;
    private boolean clickOffable = true;
    private boolean customComponents = false;
    private String[] cnames;
    private final Semaphore semaphore = new Semaphore(0);
    
    /**
     * Creates a new Dialogue with the given options.
     * @param quest The question.
     * @param off What happens if the user clicks away.
     * @param opt The options.
     */
    public Dialogue(String quest, ScreenEvent off, String... opt){
        question = Utils.lineFormat(quest, 31);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
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
        question = Utils.lineFormat(quest, 31);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
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
        question = Utils.lineFormat(quest, 31);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
        options = getButtons(opt);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
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
     * @param n The names of the options.
     */
    public Dialogue(String quest, String off, boolean click, CComponent[] opt, String... n){
        question = Utils.lineFormat(quest, 31);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = opt;
        dressCComponents();
        clickOffable = click;
        offCase = new ScreenEvent(off);
        screenArray = Utils.getScreens(opt);
        cnames = n;
        customComponents = true;
    }
    
    /**
     * Paints this Dialogue onto the given Graphics.
     * @param g The Graphics.
     */
    public void paint(Graphics g){
        int beginHeight = (Main.HEIGHT-height)/2;
        int beginWidth = Main.WIDTH/3;
        g.setFont(ConstantFields.textFont);
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(beginWidth, beginHeight, beginWidth, height, false);
        g.setColor(ConstantFields.frontColor);
        for(CComponent option : options) option.paint(g);
        g.setColor(ConstantFields.textColor);
        ImageUtils.drawString(g, question, 2*padding+beginWidth, beginHeight+2*padding);
    }
    
    private void activate(Main main){
        if(customComponents) main.addKeyListener(this);
        main.changeDialogue(this);
        for(Screen sc : screenArray) if(sc instanceof CSlider.CSliderHandle) main.addDraggable(sc);
        else main.addScreenFirst(sc);
    }
    
    private void deactivate(Main main){
        main.changeDialogue(null);
        main.removeScreens(screenArray);
    }
    
    /**
     * Activates this Dialogue.
     * @param main The MainClass to draw on.
     * @return The ScreenEvent that happened.
     */
    public synchronized ScreenEvent action(Main main){
        activate(main);
        //main.addEvent(() -> {
            try{
                semaphore.acquire();
            }catch(InterruptedException ex){
                ex.printStackTrace(Main.exceptionStream);
            }
            deactivate(main);
        //});
        return clickedScreen;
    }

    @Override
    public void screenClicked(ScreenEvent sce){
        clickedScreen = sce;
        semaphore.release();
    }
    
    /**
     * Notifies this Dialogue that the user clicked away.
     */
    public void clickedOff(){
        clickedScreen = offCase;
        if(clickOffable) semaphore.release();
    }

    private CButton[] getButtons(String[] strs){
        CButton[] ary = new CButton[strs.length];
        int beginWidth = Main.WIDTH/3;
        int beginHeight = (Main.HEIGHT-height)/2;
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
        lst.add(new Screen("/exit", 0, 0, Main.WIDTH, Main.HEIGHT, this));
        lst.add(new Screen("blank click", Main.WIDTH/3, (Main.HEIGHT-height)/2, Main.WIDTH/3, height, this));
        for(CComponent cc : ary) lst.add((CButton)cc);
        return lst.toArray(new Screen[lst.size()]);
    }
    
    private void dressCComponents(){
        int beginWidth = Main.WIDTH/3;
        int beginHeight = (Main.HEIGHT-height)/2;
        for(int n=0;n<options.length;n++){
            options[n].setTopLeft(padding+beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight);
            if(options[n] instanceof Screen) ((Screen) options[n]).changeScreenListener(this);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke){}
    @Override
    public void keyPressed(KeyEvent ke){}
    @Override
    public synchronized void keyReleased(KeyEvent ke){
        if(ke.getKeyCode()==KeyEvent.VK_ENTER&&customComponents){
            clickedScreen = new ScreenEvent(options);
            semaphore.release();
        }
    }
    
}

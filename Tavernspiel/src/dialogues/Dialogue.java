
package dialogues;

import gui.Window;
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
import java.util.List;
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
public abstract class Dialogue implements ScreenListener, KeyListener{
    
    final String question;
    final CComponent[] options;
    private final List<Screen> screens;
    private final ScreenEvent offCase;
    private int height = -1;
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
        question = Utils.lineFormat(quest, 31);
        heightOfQuestion = ImageUtils.getStringHeight()*Utils.lineCount(question);
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = getButtons(opt);
        offCase = off;
        screens = convertCComponents(options);
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
        screens = convertCComponents(options);
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
        height = 2*padding + heightOfQuestion + (36+padding)*opt.length;
        options = getButtons(opt);
        clickOffable = click;
        offCase = new ScreenEvent(off);
        screens = convertCComponents(options);
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
    
    private void deactivate(){
        Window.main.setDialogue(null);
    }
    
    /**
     * Activates this Dialogue.
     */
    public synchronized void next(){
        Window.main.setDialogue(this);
        screens.forEach((sc) -> {
            if(sc instanceof CSlider.CSliderHandle) Window.main.addDraggable(sc);
        });
    }

    /**
     * Checks to see if the given ScreenEvent would cause the Dialogue to
     * deactivate and deactivates it if necessary.
     * @param sce
     */
    public void checkDeactivate(ScreenEvent sce){
        if(!sce.equals(offCase)||clickOffable) deactivate();
    }
    
    /**
     * Notifies this Dialogue that the user clicked away.
     */
    public void clickedOff(){
        screenClicked(offCase);
        //if(clickOffable) semaphore.release();
    }

    private CButton[] getButtons(String[] strs){
        CButton[] ary = new CButton[strs.length];
        int beginWidth = Main.WIDTH/3 + padding;
        int beginHeight = (Main.HEIGHT-height)/2;
        for(int n=0;n<ary.length;n++){
            ary[n] = new CButton(strs[n], beginWidth, 
                    2*padding+heightOfQuestion+(36+padding)*n+beginHeight, 
                    padding, this);
        }
        return ary;
    }
    
    /**
     * Gets the screens associated with this Dialogue.
     * @return The Screens.
     */
    public final List<Screen> getScreens(){
        return screens;
    }
    
    private List<Screen> convertCComponents(CComponent[] ary){
        LinkedList<Screen> lst = new LinkedList<>();
        for(CComponent cc : ary) lst.add((Screen)cc);
        lst.add(new Screen("blank click", Main.WIDTH/3, (Main.HEIGHT-height)/2, Main.WIDTH/3, height, this));
        lst.add(new Screen("/exit", 0, 0, Main.WIDTH, Main.HEIGHT, this));
        return lst;
    }

    @Override
    public void keyTyped(KeyEvent ke){}
    @Override
    public void keyPressed(KeyEvent ke){}
    @Override
    public void keyReleased(KeyEvent ke){}
    
}

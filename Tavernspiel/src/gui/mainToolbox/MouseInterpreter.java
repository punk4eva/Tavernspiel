
package gui.mainToolbox;

import gui.Window;
import static gui.mainToolbox.Main.performanceStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import static gui.mainToolbox.Main.HEIGHT;
import static gui.mainToolbox.Main.WIDTH;
import static gui.mainToolbox.Main.gui;
import level.Area;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class MouseInterpreter extends MouseAdapter{
    
    public volatile static int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1, maxFX, maxFY, minFX, minFY;
    protected static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0, MIN_ZOOM = 0.512;
    public static final int MOVE_RESOLUTION = 4;
    
    @Override
    @Unfinished("Remove debug prints")
    public void mouseClicked(MouseEvent me){
        boolean notClicked = true;
        int x = me.getX(), y = me.getY();
        for(Screen sc : gui.screens){ //Used for-each instead of stream because of "break".
            if(sc.withinBounds(x, y)){
                if(sc.name.equals("blank click")){
                    System.err.println("blank click");
                    return;
                }
                if(!sc.name.equals("/exit")){
                    System.err.println(sc.name);
                    sc.wasClicked(me);
                    notClicked = false;
                }
                break;
            }
        }
        if(notClicked){if(gui.hudClear()){
            Integer[] p = pixelToTile(x, y);
            if(Window.main.currentArea.tileFree(p[0], p[1])) Window.main.currentArea.click(p[0], p[1]);
        }else{
            gui.dialogue.clickedOff();
        }}
    }
    @Override
    public void mouseReleased(MouseEvent me){
        if(!gui.draggables.isEmpty()){
            int x = me.getX(), y = me.getY();
            if(gui.lastDragged.withinBounds(x,y)) gui.lastDragged.wasClicked(me);
            else for(Screen sc : gui.draggables){
                if(sc.withinBounds(x, y)){
                    gui.lastDragged = sc;
                    sc.wasClicked(me);
                    break;
                }
            }
        }else if(xOfDrag!=-1){
            xOfDrag = -1;
        }
    }
    
    /**
     * Translates on-screen pixel click coordinates to tile coordinates.
     * @param mx The x coordinate of the click.
     * @param my The y coordinate of the click.
     * @return An int array representing the tile coordinates.
     */
    public static Integer[] pixelToTile(double mx, double my){
        return new Integer[]{Math.floorDiv((int)(mx/zoom)-focusX, 16), Math.floorDiv((int)(my/zoom)-focusY, 16)};
    }
    
    /**
     * Translates tile coordinates into on-screen pixel coordinates.
     * @param tx The x coordinate of the click.
     * @param ty The y coordinate of the click.
     * @return An int array representing the on-screen coordinates.
     */
    public static Integer[] tileToPixel(double tx, double ty){
        return new Integer[]{(int)((16D*tx+8D+focusX)*zoom), (int)((16D*ty+8D+focusY)*zoom)};
    }
    
    /**
     * Sets the focus based on the tile coordinates.
     * @param tilex
     * @param tiley
     */
    public void setTileFocus(int tilex, int tiley){
        focusX = (int)(WIDTH/zoom)/2 - tilex * 16;
        focusY = (int)(HEIGHT/zoom)/2 - tiley * 16;
    }
    
    /**
     * Sets the focus based on the pixel coordinates.
     * @param x
     * @param y
     */
    public void setPixelFocus(int x, int y){
        focusX = (int)((double)WIDTH/zoom/2d) - x;
        focusY = (int)((double)HEIGHT/zoom/2d) - y;
    }
    
    /**
     * Sets the focus directly (top-left rather than center).
     * @param x The x pixel
     * @param y The y pixel
     */
    public void setDirectFocus(int x, int y){
        focusX = x;
        focusY = y;
    }
    
    /**
     * Sets the focus bounds for a given Area.
     * @param area The Area.
     */
    public void setFocusBounds(Area area){
        int temp = Main.WIDTH - 32 - (int)((area.dimension.width*16)*zoom);
        if(temp > 16){
            maxFX = temp;
            minFX = 16;
        }else{
            maxFX = 16;
            minFX = temp;
        }
        temp = Main.HEIGHT - 32 - (int)((area.dimension.height*16)*zoom);
        if(temp > 16){
            maxFY = temp;
            minFY = 16;
        }else{
            maxFY = 16;
            minFY = temp;
        }
    }
    
    /**
     * Returns the center coordinates of the screen.
     * @return
     */
    public static int[] getCenter(){
        return new int[]{(int)((double)WIDTH/zoom/2d), (int)((double)HEIGHT/zoom/2d)};
    }

    @Override
    public void mouseDragged(MouseEvent me){
        if(!gui.draggables.isEmpty()){
            int x = me.getX(), y = me.getY();
            if(gui.lastDragged.withinBounds(x,y)) gui.lastDragged.wasClicked(me);
            else for(Screen sc : gui.draggables){
                if(sc.withinBounds(x, y)){
                    gui.lastDragged = sc;
                    sc.wasClicked(me);
                    break;
                }
            }
        }else if(gui.hudClear()){
            int tempx, tempy;
            if(xOfDrag == -1){
                xOfDrag = me.getX() - focusX;
                yOfDrag = me.getY() - focusY;        
            }
            tempx = me.getX() - xOfDrag;
            tempy = me.getY() - yOfDrag;
            focusX = tempx>minFX ? (tempx<maxFX ? tempx : maxFX) : minFX;
            focusY = tempy>minFY ? (tempy<maxFY ? tempy : maxFY) : minFY;
        }
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent me){
        //zoomCenterX = me.getX();
        //zoomCenterY = me.getY();
        switch(me.getWheelRotation()){
            case -1: if(zoom<MAX_ZOOM){
                zoom *= 1.25;
                performanceStream.println("ZOOM: " + zoom);
            }
                break;
            default: if(zoom>MIN_ZOOM){
                zoom /= 1.25;
                performanceStream.println("ZOOM: " + zoom);
            }
        }
        setFocusBounds(Window.main.currentArea);
    }
    
}

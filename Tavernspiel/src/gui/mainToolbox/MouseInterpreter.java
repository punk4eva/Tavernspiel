
package gui.mainToolbox;

import gui.Window;
import static gui.mainToolbox.Main.HEIGHT;
import static gui.mainToolbox.Main.WIDTH;
import static gui.mainToolbox.Main.gui;
import static gui.mainToolbox.Main.performanceStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import level.Area;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles Mouse input.
 */
@Unfinished("Could combine with KeyProcessor")
public class MouseInterpreter extends MouseAdapter{
    
    public volatile static int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1, maxFX, maxFY, minFX, minFY;
    public static double zoom = 1.0;
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
        if(notClicked){
            if(gui.hudClear()){
                Integer[] p = pixelToTile(x, y);
                if(Window.main.currentArea.tileFree(p[0], p[1])) Window.main.currentArea.click(p[0], p[1]);
            }else{
                gui.dialogue.clickedOff();
            }
        }
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
    public static Integer[] tileToPixel(int tx, int ty){
        return new Integer[]{16*tx+8+focusX, 16*ty+8+focusY};
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
        focusX = (int)(WIDTH/zoom/2d) - x;
        focusY = (int)(HEIGHT/zoom/2d) - y;
    }
    
    /**
     * Sets the focus directly (top-left rather than centre).
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
    @Unfinished("Might remove")
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
     * Returns the centre coordinates of the screen.
     * @return
     */
    public static int[] getCenter(){
        return new int[]{(int)(WIDTH/zoom/2d), (int)(HEIGHT/zoom/2d)};
    }
    
    /**
     * Applies the re-centered quadrant rotate matrix to the given x,y coordinates.
     * @param o The number of quadrants rotated.
     * @param x
     * @param y
     * @param w The width
     * @param h The height
     * @return The x coordinate of the image.
     */
    public static int xOrient(int o, int x, int y, int w, int h){
        switch(o){
            case 0: return x;
            case 1: return y;
            case 2: return w-x-1;
            default: return h-y-1;
        }
    }
    
    /**
     * Applies the re-centered quadrant rotate matrix to the given x,y coordinates.
     * @param o The number of quadrants rotated.
     * @param x
     * @param y
     * @param w The width
     * @param h The height
     * @return The y coordinate of the image.
     */
    public static int yOrient(int o, int x, int y, int w, int h){
        switch(o){
            case 0: return y;
            case 1: return w-x-1;
            case 2: return h-y-1;
            default: return x;
        }
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
            if(xOfDrag == -1){
                xOfDrag = (int)(me.getX()/zoom) - focusX;
                yOfDrag = (int)(me.getY()/zoom) - focusY;        
            }
            /*int tempx, tempy;
            tempx = me.getX() - xOfDrag;
            tempy = me.getY() - yOfDrag;
            focusX = tempx>minFX ? (tempx<maxFX ? tempx : maxFX) : minFX;
            focusY = tempy>minFY ? (tempy<maxFY ? tempy : maxFY) : minFY;*/
            focusX = (int)(me.getX()/zoom) - xOfDrag;
            focusY = (int)(me.getY()/zoom) - yOfDrag;
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

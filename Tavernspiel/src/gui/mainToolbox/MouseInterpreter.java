
package gui.mainToolbox;

import gui.Window;
import static gui.mainToolbox.Main.performanceStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import static gui.mainToolbox.Main.HEIGHT;
import static gui.mainToolbox.Main.WIDTH;
import static gui.mainToolbox.Main.gui;

/**
 *
 * @author Adam Whittaker
 */
public class MouseInterpreter extends MouseAdapter{
    
    public volatile static int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1;
    protected static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0, MIN_ZOOM = 0.512;
    
    @Override
    public void mouseClicked(MouseEvent me){
        boolean notClicked = true;
        int x = me.getX(), y = me.getY();
        for(Screen sc : gui.screens){ //Used for-each instead of stream because of "break".
            if(sc.withinBounds(x, y)){
                if(sc.name.equals("blank click")) return;
                if(!sc.name.equals("/exit")){
                    sc.wasClicked(me);
                    notClicked = false;
                }
                break;
            }
        }
        if(notClicked){if(gui.viewablesSize()==1){
            Integer[] p = pixelToTile(x, y);
            if(Window.main.currentArea.tileFree(p[0], p[1])) Window.main.turnThread.click(p[0], p[1]);
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
        return new Integer[]{(int)Math.floor((mx-focusX)/(16D*zoom)), (int)Math.floor((my-focusY)/(16D*zoom))};
    }
    
    /**
     * Translates tile coordinates into on-screen pixel coordinates.
     * @param tx The x coordinate of the click.
     * @param ty The y coordinate of the click.
     * @return An int array representing the on-screen coordinates.
     */
    public static Integer[] tileToPixel(double tx, double ty){
        return new Integer[]{(int)((16D*tx+8D)*zoom + focusX), (int)((16D*ty+8D)*zoom + focusY)};
    }
    
    /**
     * Sets the focus based on the tile coordinates.
     * @param tilex
     * @param tiley
     */
    public void setFocus(int tilex, int tiley){
        focusX = (int)(WIDTH/zoom)/2 - tilex * 16;
        focusY = (int)(HEIGHT/zoom)/2 - tiley * 16;
    }
    
    /**
     * Sets the focus based on the pixel coordinates.
     * @param x
     * @param y
     */
    public void setPixelFocus(int x, int y){
        focusX = (int)(WIDTH/zoom)/2 - x;
        focusY = (int)(HEIGHT/zoom)/2 - y;
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
        }else if(gui.dialogue == null && gui.viewables.size() <= 1){
            if(xOfDrag == -1){
                xOfDrag = me.getX() - focusX;
                yOfDrag = me.getY() - focusY;        
            }
            focusX = me.getX() - xOfDrag;
            focusY = me.getY() - yOfDrag;
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
    }
    
}

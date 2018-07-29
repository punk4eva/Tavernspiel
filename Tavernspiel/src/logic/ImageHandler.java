
package logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class ImageHandler{
    
    private static final HashMap<String, Dimension> map = new HashMap<>();
    static{
        map.put("void", new Dimension(0, 0));
        map.put("floor", new Dimension(16, 0));
        map.put("lowgrass", new Dimension(32, 0));
        map.put("emptywell", new Dimension(48, 0));
        map.put("wall", new Dimension(64, 0));
        map.put("closeddoor", new Dimension(80, 0));
        map.put("door", new Dimension(80, 0));
        map.put("opendoor", new Dimension(96, 0));
        map.put("depthentrance", new Dimension(112, 0));
        map.put("depthexit", new Dimension(128, 0));
        map.put("embers", new Dimension(144, 0));
        map.put("lockeddoor", new Dimension(160, 0));
        map.put("pedestal", new Dimension(176, 0));
        map.put("specialwall", new Dimension(192, 0));
        map.put("barricade", new Dimension(208, 0));
        map.put("specialfloor", new Dimension(224, 0));
        map.put("highgrass", new Dimension(240, 0));
        map.put("greentrap", new Dimension(16, 16));
        map.put("orangetrap", new Dimension(48, 16));
        map.put("yellowtrap", new Dimension(80, 16));
        map.put("offtrap", new Dimension(112, 16));
        map.put("decofloor", new Dimension(128, 16));
        map.put("lockeddepthexit", new Dimension(144, 16));
        map.put("unlockeddepthexit", new Dimension(160, 16));
        map.put("purpletrap", new Dimension(176, 16));
        map.put("sign", new Dimension(208, 16));
        map.put("redtrap", new Dimension(224, 16));
        map.put("bluetrap", new Dimension(0, 32));
        map.put("well", new Dimension(32, 32));
        map.put("statue", new Dimension(48, 32));
        map.put("specialstatue", new Dimension(64, 32));
        map.put("beartrap", new Dimension(80, 32));
        map.put("silvertrap", new Dimension(112, 32));
        map.put("bookshelf", new Dimension(144, 32));
        map.put("alchemypot", new Dimension(160, 32));
        map.put("floorcutoff", new Dimension(176, 32));
        map.put("specialfloorcutoff", new Dimension(192, 32));
        map.put("wallcutoff", new Dimension(208, 32));
        map.put("brokencutoff", new Dimension(224, 32));
        map.put("shadernesw", new Dimension(0, 48));
        map.put("shaderesw", new Dimension(16, 48));
        map.put("shadernsw", new Dimension(32, 48));
        map.put("shadersw", new Dimension(48, 48));
        map.put("shadernew", new Dimension(64, 48));
        map.put("shaderew", new Dimension(80, 48));
        map.put("shadernw", new Dimension(96, 48));
        map.put("shaderw", new Dimension(112, 48));
        map.put("shadernes", new Dimension(128, 48));
        map.put("shaderes", new Dimension(144, 48));
        map.put("shaderns", new Dimension(160, 48));
        map.put("shaders", new Dimension(176, 48));
        map.put("shaderne", new Dimension(192, 48));
        map.put("shadere", new Dimension(208, 48));
        map.put("shadern", new Dimension(224, 48));
        map.put("shader", new Dimension(240, 48));
    }
    
    public final static void initializeIcons(Location loc){
        map.entrySet().forEach((entry) -> {
            loc.tilemap.put(entry.getKey(), getImage(entry.getValue(), loc));
        });
    }
    
    /**
     * Retrieves the Image from the given coordinates in the tileset.
     * @param dim The Dimension representing the coordinates.
     * @param loc The Location
     * @return
     */
    private static ImageIcon getImage(Dimension dim, Location loc){
        BufferedImage bi = new BufferedImage(
                loc.tileset.getIconWidth(),
                loc.tileset.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(loc.tileset.getImage(), 0, 0, null);
        g.dispose();
        return new ImageIcon(bi.getSubimage(dim.width, dim.height, 16, 16));
    }
    
    /**
     * Gets the frames for the water animation.
     * @param loc The Location
     * @param x The starting x coordinate.
     * @return
     */
    public static ImageIcon[] getWaterFrames(Location loc, int x){
        BufferedImage bi = new BufferedImage(
                loc.waterImage.getIconWidth(),
                loc.waterImage.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(loc.waterImage.getImage(), 0, 0, null);
        g.dispose();
        ImageIcon[] ret = new ImageIcon[loc.waterImage.getIconHeight()-15];
        x*=16;
        for(int y=ret.length-1;y>=0;y--){
            ret[ret.length-y-1] = new ImageIcon(bi.getSubimage(x, y, 16, 16));
        }
        return ret;
    }
    
    /**
     * Combines two ImageIcons together.
     * @param ic1
     * @param ic2
     * @return
     */
    public static ImageIcon combineIcons(ImageIcon ic1, ImageIcon ic2){
        BufferedImage bi1 = new BufferedImage(
                ic1.getIconWidth(),
                ic1.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi1.createGraphics();
        ic1.paintIcon(null, g, 0, 0);
        ic2.paintIcon(null, g, 0, 0);
        g.dispose();
        return new ImageIcon(bi1);
    }
    
    /**
     * Combines two Images together.
     * @param i1
     * @param i2
     * @return
     */
    public static Image combineIcons(Image i1, Image i2){
        BufferedImage bi1 = new BufferedImage(
                i1.getWidth(null),
                i1.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi1.createGraphics();
        g.drawImage(i1, 0, 0, null);
        g.drawImage(i2, 0, 0, null);
        g.dispose();
        return bi1;
    }
    
}

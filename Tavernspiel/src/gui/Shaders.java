
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class Shaders{

    public final BufferedImage shadows;
    {
        ImageIcon ic = new ImageIcon("graphics/shadows.png");
        shadows = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = shadows.getGraphics();
        g.drawImage(ic.getImage(), 0, 0, null);
        g.dispose();
    }
    
    /**
     * Creates a new instance.
     * @param col The color to shade with.
     * @param alphaDivisor The shade coefficient.
     */
    public Shaders(Color col, double alphaDivisor){
        WritableRaster raster = shadows.getRaster();
        int R= col.getRed(), G=col.getGreen(), B=col.getBlue();
        for(int y=0;y<shadows.getHeight();y++){
            for(int x=0;x<shadows.getWidth();x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                pixel[0] = R;
                pixel[1] = G;
                pixel[2] = B;
                pixel[3] = (int)(pixel[3]/alphaDivisor);
                raster.setPixel(x, y, pixel);
            }
        }
    }
    
    /**
     * Creates a new instance of a black shader.
     */
    public Shaders(){}
    
    /**
     * Gets the shadow for the given tile with the given visibility map.
     * @param map An int array representing the visibility of each tile in the Area.
     * @param x
     * @param y
     * @param n The int representing the value of a shaded tile.
     * @param black Whether the tile is being skipped from rendering.
     * @return
     */
    public BufferedImage getShadow(int[][] map, int x, int y, int n, boolean black){
        boolean s0=true,s1=true,s2=true,s3=true;
        try{
            if(map[y-1][x]!=n) s0 = false;
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(map[y][x-1]!=n) s1 = false;
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(map[y][x+1]!=n) s2 = false;
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(map[y+1][x]!=n) s3 = false;
        }catch(ArrayIndexOutOfBoundsException e){}
        if(s0){
            if(s1){
                if(s2){
                    if(s3){
                        return black ? null : shadows.getSubimage(0, 0, 16, 16);
                    }else{
                        return shadows.getSubimage(0, 32, 16, 16);
                    }
                }else{
                    if(s3){
                        return shadows.getSubimage(48, 32, 16, 16);
                    }else{
                        return shadows.getSubimage(32, 16, 16, 16);
                    }
                }
            }else{
                if(s2){
                    if(s3){
                        return shadows.getSubimage(16, 32, 16, 16);
                    }else{
                        return shadows.getSubimage(48, 16, 16, 16);
                    }
                }else{
                    if(s3){
                        return shadows.getSubimage(32, 0, 16, 16);
                    }else{
                        return shadows.getSubimage(0, 48, 16, 16);
                    }
                }
            }
        }else{
            if(s1){
                if(s2){
                    if(s3){
                        return shadows.getSubimage(32, 32, 16, 16);
                    }else{
                        return shadows.getSubimage(48, 0, 16, 16);
                    }
                }else{
                    if(s3){
                        return shadows.getSubimage(16, 16, 16, 16);
                    }else{
                        return shadows.getSubimage(48, 48, 16, 16);
                    }
                }
            }else{
                if(s2){
                    if(s3){
                        return shadows.getSubimage(0, 16, 16, 16);
                    }else{
                        return shadows.getSubimage(16, 48, 16, 16);
                    }
                }else{
                    if(s3){
                        return shadows.getSubimage(32, 48, 16, 16);
                    }else{
                        return shadows.getSubimage(16, 0, 16, 16);
                    }
                }
            }
        }
    }
    
}


package logic;

import creatures.Hero;
import items.Apparatus;
import items.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Adam Whittaker
 */
public class ImageUtils{
    
    public static void paintItemSquare(Graphics g, int x, int y, int sqwidth, int sqheight, Item i, Hero h){
        g.fill3DRect(x, y, sqwidth, sqheight, true);
        boolean cursed = i.hasKnownCurse();
        boolean ided = i.isIdentified(h);
        if(cursed){
            g.setColor(ConstantFields.cursedColour);
            g.fillRect(x+2, y+2, sqwidth-4, sqheight-4);
        }else if(!ided){
            g.setColor(ConstantFields.unidentifiedColour);
            g.fillRect(x+2, y+2, sqwidth-4, sqheight-4);
        }
        if(i instanceof Apparatus){
            ((Apparatus) i).draw(g, x+(sqwidth-16)/2,
                    y+(sqheight-16)/2);
        }else{
            g.drawImage(i.icon, x+(sqwidth-16)/2,
                    y+(sqheight-16)/2, null);
        }
        g.setColor(Color.white);
        if(i.quantity!=1) g.drawString(""+i.quantity, x+4, y+4);
    }
    
    public static void addImageOverlay(String filepath) throws IOException{
        BufferedImage image = ImageIO.read(new File(filepath));
        BufferedImage ret = new BufferedImage(32, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics grap = ret.getGraphics();
        grap.drawImage(image, 0, 0, null);
        grap.dispose();
        
        WritableRaster raster = ret.getRaster();
        for(int y=0;y<16;y++){
            for(int x=16;x<32;x++){
                int[] pixel = raster.getPixel(x-16, y, (int[]) null);
                if(pixel[3]!=0){
                    setTransparentAround(raster, x, y);
                    raster.setPixel(x, y, new int[]{11, 18, 1, 13}); //Mark the pixel wth a unique colour.
                }
            }
        }
        
        ImageIO.write(ret, "png", new File("graphics/image.png"));
    }
    
    public static void setRasterPixel(WritableRaster raster, int x, int y, int R, int G, int B, int A){
        raster.setPixel(x, y, new int[]{R,G,B,A});
    }
    
    private static void setTransparentAround(WritableRaster raster, int nx, int ny){
        for(int y=ny-2;y<=ny+2;y++){
            for(int x=nx-2;x<=nx+2;x++){
                try{
                    int[] pixel = raster.getPixel(x-16, y, (int[]) null);
                    if(pixel[3]==0){
                        raster.setPixel(x, y, new int[]{13, 1, 18, 11}); //Mark the pixel with unique colour. 
                    }
                }catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }

    public static void paintGold(Graphics g, int x, int y, int sqwidth, int sqheight, int amountOfMoney){
        g.fill3DRect(x, y, sqwidth, sqheight, true);
        if(amountOfMoney==0){
            g.drawImage(ConstantFields.goldOutline, x+(sqwidth-16)/2,
                    y+(sqheight-16)/2, null);
        }else{
            g.drawImage(ConstantFields.gold, x+(sqwidth-16)/2,
                    y+(sqheight-16)/2, null);
        }
    }
    
    public static void paintOutline(Graphics g, int x, int y, int sqwidth, int sqheight, Image img){
        g.fill3DRect(x, y, sqwidth, sqheight, true);
        g.drawImage(img, x+(sqwidth-16)/2,
                y+(sqheight-16)/2, null);
    }

    public static Image fade(BufferedImage img, int newAlpha){
        WritableRaster raster = img.getRaster();
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                pixel[3] = newAlpha;
                raster.setPixel(x, y, pixel);
            }
        }
        return img;
    }
    
    public static BufferedImage addImageBuffer(Image image){
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bi;
    }
    
}


package logic;

import creatures.Hero;
import items.Item;
import items.builders.ItemBuilder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class ImageUtils{
    
    private ImageUtils(){}
    
    private static final BufferedImage scaledGold = scale(convertToBuffered(ItemBuilder.getIcon(64, 160)), 3),
            scaledGoldOutline = scale(convertToBuffered(new ImageIcon("graphics/outlines/goldOutline.png")), 3);
    public static final BufferedImage scaledHelmetOutline = scale(convertToBuffered(new ImageIcon("graphics/outlines/helmetOutline.png")), 3),
            scaledChestplateOutline = scale(convertToBuffered(new ImageIcon("graphics/outlines/chestplateOutline.png")), 3),
            scaledLeggingsOutline = scale(convertToBuffered(new ImageIcon("graphics/outlines/leggingsOutline.png")), 3),
            scaledBootsOutline = scale(convertToBuffered(new ImageIcon("graphics/outlines/bootsOutline.png")), 3);
    
    
    public static void paintItemSquare(Graphics g, int x, int y, int sqwidth, int sqheight, Item i, Hero h, Predicate<Item> pred){
        g.setColor(ConstantFields.backColor);
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
        //if(i==null) return;
        BufferedImage buffer = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics bg = buffer.getGraphics();
        i.animation.animate(bg, 0, 0);
        if(!pred.test(i)) alpha(buffer);
        g.drawImage(scale(buffer, 3), x+(sqwidth-48)/2, y+(sqheight-48)/2, null);
        g.setColor(Color.white);
        if(i.quantity!=1) g.drawString(""+i.quantity, x+4, y+10);
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
    
    public static void createImageOutline(BufferedImage img, String filepath) throws IOException{
        BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        
        WritableRaster raster = ret.getRaster(), imgRaster = img.getRaster();
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                int alpha = imgRaster.getPixel(x, y, (int[])null)[3];
                if(alpha<255&&alpha>0)
                    raster.setPixel(x, y, new int[]{0,0,0,160});
            }
        }
        
        ImageIO.write(ret, "png", new File(filepath));
    }
    
    public static BufferedImage buildOverlay(BufferedImage img){
        BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        WritableRaster retRaster = ret.getRaster(), imgRaster = img.getRaster();
        for(int y=0;y<16;y++){
            for(int x=16;x<12;x++){
                int[] pixel = imgRaster.getPixel(x-16, y, (int[]) null);
                if(pixel[3]==255){
                    setTransparentAround(retRaster, x, y);
                    imgRaster.setPixel(x, y, new int[]{11, 18, 1, 13}); //Mark the pixel wth a unique colour.
                }
            }
        }
        return ret;
    }
    
    public static void setRasterPixel(WritableRaster raster, int x, int y, int R, int G, int B, int A){
        raster.setPixel(x, y, new int[]{R,G,B,A});
    }
    
    private static void setTransparentAround(WritableRaster raster, int nx, int ny){
        for(int y=ny-2;y<=ny+2;y++){
            for(int x=nx-2;x<=nx+2;x++){
                try{
                    int[] pixel = raster.getPixel(x-16, y, (int[]) null);
                    if(pixel[3]!=255){
                        raster.setPixel(x, y, new int[]{13, 1, 18, 11}); //Mark the pixel with unique colour. 
                    }
                }catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }

    public static void paintGold(Graphics g, int x, int y, int sqwidth, int sqheight, int amountOfMoney){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, sqwidth, sqheight, true);
        if(amountOfMoney==0){
            g.drawImage(scaledGoldOutline, x+(sqwidth-48)/2,
                    y+(sqheight-48)/2, null);
        }else{
            g.drawImage(scaledGold, 
                    x+(sqwidth-48)/2, y+(sqheight-48)/2, null);
            g.setColor(Color.white);
            g.drawString(""+amountOfMoney, x+4, y+10);
        }
    }
    
    public static void paintOutline(Graphics g, int x, int y, int sqwidth, int sqheight, Image img){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(x, y, sqwidth, sqheight, true);
        g.drawImage(img, x+(sqwidth-48)/2,
                y+(sqheight-48)/2, null);
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
    
    public static BufferedImage convertToBuffered(ImageIcon image){
        Image img = image.getImage();
        if(img instanceof BufferedImage) return (BufferedImage) img;
        BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bi;
    }
    
    public static ImageIcon mirror(ImageIcon icon){
        int w = icon.getIconWidth(), h = icon.getIconHeight();
        BufferedImage ret = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = ret.getRaster(), iconRaster = convertToBuffered(icon).getRaster();
        for(int x=0;x<w;x++)
            for(int y=0;y<h;y++)
                raster.setPixel(x, y, iconRaster.getPixel(w-1-x, y, (int[]) null));
        return new ImageIcon(ret);
    }
    
    public static void drawString(Graphics g, String str, int x, int y){
        y -= g.getFontMetrics().getHeight();
        for(String line : str.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    
    public static int getStringHeight(){
        return (int)Math.ceil(ConstantFields.textFont.getLineMetrics("Example", new FontRenderContext(new AffineTransform(), true, true)).getHeight());
    }
    
    public static void alpha(BufferedImage bi){
        WritableRaster raster = bi.getRaster();
        for(int y=0;y<bi.getHeight();y++){
            for(int x=0;x<bi.getWidth();x++){
                int[] pixel = raster.getPixel(x, y, (int[]) null);
                if(pixel[3]>10) pixel[3]-=10;
                raster.setPixel(x, y, pixel);
            }
        }
    }
    
    public static BufferedImage scale(BufferedImage b, double factor){
        BufferedImage ret = new BufferedImage((int)(factor*b.getWidth()), (int)(factor*b.getHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(factor, factor);
        g.drawRenderedImage(b, at);
        g.dispose();
        return ret;
    }
    
}

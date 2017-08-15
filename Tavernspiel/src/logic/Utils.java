
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
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author Adam Whittaker
 * 
 * Holds miscellaneous static-access utility methods.
 */
public class Utils{
    
    public static <T> boolean contains(T[] ary, T object){
        return Arrays.asList(ary).contains(object);
    }
    
    public static boolean contains(int[] ary, int key) {
        for(int i : ary) if(i == key) return true;
        return false;
    }

    public static String toFileString(int[] ary){
        String ret = "{";
        for(int i : ary) ret += i + "*";
        return ret.substring(0, ret.length()-1) + "}";
    }
    
    public static int[] getIntArrayFromFileString(String filestring){
        String[] profile = filestring.substring(1, filestring.length()-1).split("*");
        int[] ret = new int[profile.length];
        for(int n=0;n<profile.length;n++) ret[n] = Integer.parseInt(profile[n]);
        return ret;
    }
    
    public static int lineCount(String str){
        int count = 1;
        for(int n=0;n<str.length()-1;n++){
            if(str.substring(n,n+2).equals("\n")) count++;
        }
        return count;
    }
    
    public static String lineFormat(String str, int lineLength){
        String[] words = str.split(" ");
        int counter = 0;
        String ret = "";
        for(String word : words){
            if(word.length()+counter>=lineLength){
                counter = word.length();
                ret += "\n"+ word;
            }else{
                counter += 1 + word.length();
                ret += " " + word;
            }
        }
        return ret.substring(1);
    }
    
    public static long euclideanAlgorithm(long large, long small){
        long x = large % small;
        if(x==0) return small;
        return euclideanAlgorithm(small, x); 
    }
    
    public static long frameUpdate(long large, long small){
        return (large/euclideanAlgorithm(large, small))*small;
    }
    
    public @interface optimisable{
        String value() default ""; //notes
    }
    
    public static void main(String... args) throws IOException{
        //debugging
        BufferedImage bi = ImageIO.read(new File("C:\\Users\\Adam\\Documents\\NetBeansProjects\\pixel-dungeon\\assets\\items.png"));
        bi = bi.getSubimage(0, 0, 16, 16);
        ImageIO.write(bi, "png", new File("graphics/image.png"));
        ImageUtils.addImageOverlay("graphics/image.png");
    }
    
}

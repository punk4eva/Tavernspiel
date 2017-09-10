
package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import static logic.Distribution.r;

/**
 *
 * @author Adam Whittaker
 * 
 * Holds miscellaneous static-access utility methods.
 */
public final class Utils{

    public static int[] shuffle(int[] ary){
        for(int i = ary.length - 1; i > 0; i--){
            int index = r.nextInt(i + 1);
            int a = ary[index];
            ary[index] = ary[i];
            ary[i] = a;
        }
        return ary;
    }
    
    private Utils(){}
    
    public static int[] rangeArray(int from, int to){
        int[] ret = new int[to-from];
        for(int n=0;n<ret.length;n++) ret[n] = from + n;
        return ret;
    }
    
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
    
    /**
     * Checks if an rgb pixel equals another rgb pixel.
     * @param p The first pixel.
     * @param q The second pixel.
     * @return True if the 1st 3 numbers equal, false if not.
     */
    public static boolean pixelColourEquals(int[] p, int[] q){
        for(int n=0;n<3;n++) if(p[n]!=q[n]) return false;
        return true;
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
    
    public @interface Optimisable{
        String value() default ""; //notes
    }
    
    public @interface Unfinished{
        String value() default ""; //notes
    }
    
    public @interface Catch{
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

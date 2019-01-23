
package logic;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.utils.CComponent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Inherited;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    
    public static <T> T[] shuffle(T[] ary){
        for(int i = ary.length - 1; i > 0; i--){
            int index = r.nextInt(i + 1);
            T a = ary[index];
            ary[index] = ary[i];
            ary[i] = a;
        }
        return ary;
    }

    public static List<Screen> getScreens(CComponent[] comp){
        List<Screen> screens = new LinkedList<>();
        for(CComponent cc : comp) screens.addAll(Arrays.asList(cc.getScreens()));
        return screens;
    }
    
    public static int roundToClosest(float d, int increment, double intersection){
        return aboveAssist(d/(float)increment, intersection)*increment;
    }
    
    private static int aboveAssist(double d, double inter){
        if((d-Math.floor(d))<inter) return (int)Math.floor(d);
        return (int)Math.ceil(d);
    }

    public static ImageIcon getBGI(String bgiPath){
        ImageIcon img = new ImageIcon(bgiPath);
        BufferedImage bi = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB); //note no alpha
        Graphics g = bi.createGraphics();
        g.drawImage(img.getImage(), 0, 0, Main.WIDTH, Main.HEIGHT, null);
        return new ImageIcon(bi);
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
    
    public static int floor(double d){
        if(d>=0) return (int) Math.floor(d);
        else return (int)(Math.floor(d)+1);
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
    
    /**
     * Checks if an rgba pixel equals another rgb pixel.
     * @param p The first pixel.
     * @param q The second pixel.
     * @return True if the 1st 3 numbers equal, false if not.
     */
    public static boolean alphaColourEquals(int[] p, int[] q){
        for(int n=0;n<4;n++) if(p[n]!=q[n]) return false;
        return true;
    }
    
    public static int lineCount(String str){
        int count = 1;
        for(char c : str.toCharArray()) if(c=='\n') count++;
        return count;
    }
    
    public static String lineFormat(String string, int lineLength){
        String ret = "";
        for(String str : string.split("\n")){
            int counter = -1;
            for(String word : str.split(" ")){
                if(word.length()+counter>=lineLength){
                    counter = word.length();
                    ret += "\n"+ word;
                }else{
                    if(counter==-1){
                        ret += word;
                        counter += word.length();
                    }else{
                        ret += " " + word;
                        counter += 1 + word.length();
                    }
                }
            }
            ret += '\n';
        }
        return ret;
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
    
    @Inherited
    public @interface Unfinished{
        String value() default ""; //notes
    }

    public @interface Catch{
        String value() default ""; //notes
    }
    
    public static void printHashCodes(String... strs){
        for(String str : strs) System.out.println(str.hashCode());
    }
    
    //@Delete after debugging
    public static void main(String... args){
        //debugging
        System.out.println(Math.floor(-4.5));
    }
    
}

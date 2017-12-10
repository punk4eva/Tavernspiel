
package logic;

import gui.Screen;
import guiUtils.CComponent;
import java.io.IOException;
import java.lang.annotation.Inherited;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

    public static Screen[] getScreens(CComponent[] comp){
        List<Screen> screens = new LinkedList<>();
        for(CComponent cc : comp) screens.addAll(Arrays.asList(cc.getScreens()));
        return screens.toArray(new Screen[screens.size()]);
    }
    
    public static int roundToClosest(float d, int increment, double intersection){
        return aboveAssist(d/(float)increment, intersection)*increment;
    }
    
    private static int aboveAssist(double d, double inter){
        if((d-Math.floor(d))<inter) return (int)Math.floor(d);
        return (int)Math.ceil(d);
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
    
    public static Integer[] testPolarConversion(int r, double theta){
        return new Integer[]{(int)Math.round(r*Math.cos(theta)), (int)Math.round(r*Math.sin(theta))};
    }
    
    protected static boolean testBlocked(double theta, List<Double[]> block){
        return block.stream().anyMatch(c -> theta>=c[0]&&theta<=c[1]);
    }
    
    public static void main(String... args) throws IOException{
        //debugging
        //printHashCodes("TileSelection");
        /*double increment = Math.PI/60;
        for(double theta=0,max=2*Math.PI;theta<max;theta+=increment){
        Integer[] c1 = testPolarConversion(1, theta),
        c2 = testPolarConversion(2, theta),
        c3 = testPolarConversion(3, theta),
        c4 = testPolarConversion(4, theta),
        c5 = testPolarConversion(5, theta),
        c6 = testPolarConversion(6, theta);
        System.out.println("1, "+theta+" -> "+c1[0]+", "+c1[1]+"\t  "+
        "2, "+theta+" -> "+c2[0]+", "+c2[1]+"\t  "+
        "3, "+theta+" -> "+c3[0]+", "+c3[1]+"\t  "+
        "4, "+theta+" -> "+c4[0]+", "+c4[1]+"\t  "+
        "5, "+theta+" -> "+c5[0]+", "+c5[1]+"\t  "+
        "6, "+theta+" -> "+c6[0]+", "+c6[1]);
        }*/
        /*List<Double[]> blocking = new LinkedList<>();
        //blocking.add(new Double[]{0D, 1D});
        blocking.add(new Double[]{-Math.PI/4, Math.PI/4});
        //blocking.add(new Double[]{0D, 3D});
        //blocking.add(new Double[]{0D, 3D});
        double increment = Math.PI/60;
        for(double theta=0,max=2*Math.PI;theta<max;theta+=increment){
        System.out.println(theta + " -> " + testBlocked(theta, blocking));
        }*/
        //printHashCodes("english");
    }
    
}

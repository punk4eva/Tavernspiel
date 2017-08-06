
package logic;

import java.util.Arrays;

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
    
    public @interface optimisable{
        String value() default ""; //notes
    }
    
}

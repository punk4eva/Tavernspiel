
package listeners;

/**
 *
 * @author Adam Whittaker
 */
public class ZipHandler{
    
    private static int currentCode = 0;
    
    public static int next(){
        return currentCode++;
    }
    
}

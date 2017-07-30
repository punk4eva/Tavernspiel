
package logic;

/**
 *
 * @author Adam Whittaker
 * 
 * Allows Objects to become File strings.
 */
public interface Fileable{
    public String toFileString();
    public static Object getFromFileString(String filestring){
        throw new UnsupportedOperationException("Fileable.getFromFileString() not overridden.");
    }
}

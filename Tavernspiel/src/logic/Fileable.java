
package logic;

/**
 *
 * @author Adam Whittaker
 * 
 * Allows Objects to become File strings.
 */
public interface Fileable{
    public String toFileString();
    public <T> T getFromFileString(String filestring);
}

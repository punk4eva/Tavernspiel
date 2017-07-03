
package exceptions;

/**
 *
 * @author Adam Whittaker
 */
public class ReceptacleIndexOutOfBoundsException extends Exception{

    /**
     * Creates a new instance of
     * ReceptacleIndexOutOfBoundsException without detail message.
     */
    public ReceptacleIndexOutOfBoundsException(){
    }

    /**
     * Constructs an instance of
     * ReceptacleIndexOutOfBoundsException with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ReceptacleIndexOutOfBoundsException(String msg){
        super(msg);
    }
}


package exceptions;

/**
 *
 * @author Adam Whittaker
 */
public class AreaCoordsOutOfBoundsException extends Exception{

    /**
     * Creates a new instance of AreaCoordsOutOfBoundsException
     * without detail message.
     */
    public AreaCoordsOutOfBoundsException(){
    }

    /**
     * Constructs an instance of AreaCoordsOutOfBoundsException
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AreaCoordsOutOfBoundsException(String msg){
        super(msg);
    }
}

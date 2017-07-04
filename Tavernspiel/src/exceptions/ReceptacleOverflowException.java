
package exceptions;

/**
 *
 * @author Adam Whittaker
 */
public class ReceptacleOverflowException extends Exception{

    /**
     * Creates a new instance of ReceptacleOverflowException
     * without detail message.
     */
    public ReceptacleOverflowException(){
    }

    /**
     * Constructs an instance of ReceptacleOverflowException with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ReceptacleOverflowException(String msg){
        super(msg);
    }
}

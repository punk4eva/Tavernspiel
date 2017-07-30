
package exceptions;

/**
 *
 * @author Adam Whittaker
 * 
 * To be thrown if something reaches intelligence level 42.
 */
public class IntelligenceFortyTwoException extends Exception{

    /**
     * Creates a new instance of IntelligenceFortyTwoException without detail message.
     */
    public IntelligenceFortyTwoException(){}

    /**
     * Constructs an instance of IntelligenceFortyTwoException with the specified detail message.
     * @param msg the detail message.
     */
    public IntelligenceFortyTwoException(String msg){
        super(msg);
    }
}

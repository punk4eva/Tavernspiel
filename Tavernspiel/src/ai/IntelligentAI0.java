
package ai;

/**
 *
 * @author Adam Whittaker
 */
public class IntelligentAI0 extends AITemplate{
    
    public IntelligentAI0(){
        intelligence = 0;
    }

    public static IntelligentAI0 getFromFileString(String filestring){
        return new IntelligentAI0();
    }
    
}

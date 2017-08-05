
package ai;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI with intelligence 0. Capable of shambling around randomly.
 */
public class IntelligentAI0 extends AITemplate{
    
    public IntelligentAI0(){
        intelligence = 0;
    }

    public static IntelligentAI0 getFromFileString(String filestring){
        return new IntelligentAI0();
    }
    
}

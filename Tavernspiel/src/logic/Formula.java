
package logic;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 * 
 * Stores formulas for leveling up, etc.
 */
public class Formula implements Serializable{
    
    private final static long serialVersionUID = 987228486;
    
    public double multiply = 1;
    public double add = 0;
    private LinkedList<Formula> nested = new LinkedList<>();
    
    /**
    * Creates a new instance for doubles.
    * @param mult The coefficient
    * @param a The additive
    */
    public Formula(double mult, double a){
        multiply = mult;
        add = a;
    }
    
    /**
    * Creates a new wrapper around an existing Formula.
    * @param mult The coefficient
    * @param a The additive
    * @param f The formula
    */
    public Formula(double mult, double a, Formula f){
        multiply = mult;
        add = a;
        nested = f.nested;
        nested.add(this);
    }
    
    /**
     * Evaluates the result of this Formula.
     * @param x The input
     * @return The output
     */
    public double get(double x){
        for(Formula f : nested) x = x*f.multiply + f.add;
        return x;
    }
    
}

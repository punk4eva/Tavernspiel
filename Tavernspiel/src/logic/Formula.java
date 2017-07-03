
package logic;

/**
 *
 * @author Adam Whittaker
 * 
 * Stores formulas for leveling up, etc.
 */
public class Formula{
    
    public double multiply;
    public double add;
    public int intMultiply;
    public int intAdd;
    
    public Formula(double mult, double a){
        multiply = mult;
        add = a;
    }
    
    public Formula(int multi, int ad){
        intMultiply = multi;
        intAdd = ad;
    }
    
    public double getDouble(double x){
        return (multiply * x) + add;
    }
    
    public int getInt(int x){
        return (intMultiply * x) + intAdd;
    }
    
}

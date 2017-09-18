
package logic;

import java.io.Serializable;

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
    public int intMultiply = 1;
    public int intAdd = 0;
    private final boolean divideMode;
    
    public Formula(double mult, double a){
        multiply = mult;
        add = a;
        divideMode = false;
    }
    
    public Formula(int multi, int ad){
        intMultiply = multi;
        intAdd = ad;
        divideMode = false;
    }
    
    public Formula(double mult, double a, boolean div){
        multiply = mult;
        add = a;
        divideMode = div;
    }
    
    public Formula(int multi, int ad, boolean div){
        intMultiply = multi;
        divideMode = div;
        intAdd = ad;
    }
    
    public Formula(double mult, double a, int multi, int ad){
        multiply = mult;
        intMultiply = multi;
        add = a;
        intAdd = ad;
        divideMode = false;
    }
    
    public Formula(double mult, double a, int multi, int ad, boolean div){
        multiply = mult;
        intMultiply = multi;
        add = a;
        divideMode = div;
        intAdd = ad;
    }
    
    public double getDouble(double x){
        return divideMode ? (multiply / x) + add : (multiply * x) + add;
    }
    
    public int getInt(int x){
        return divideMode ? (intMultiply / x) + intAdd : (intMultiply * x) + intAdd;
    }
    
}


package logic;

/**
 *
 * @author Adam Whittaker
 * 
 * Stores formulas for leveling up, etc.
 */
public class Formula implements Fileable{
    
    public double multiply = 1;
    public double add = 0;
    public int intMultiply = 1;
    public int intAdd = 0;
    
    public Formula(double mult, double a){
        multiply = mult;
        add = a;
    }
    
    public Formula(int multi, int ad){
        intMultiply = multi;
        intAdd = ad;
    }
    
    public Formula(double mult, double a, int multi, int ad){
        multiply = mult;
        intMultiply = multi;
        add = a;
        intAdd = ad;
    }
    
    public Formula(){}
    
    public double getDouble(double x){
        return (multiply * x) + add;
    }
    
    public int getInt(int x){
        return (intMultiply * x) + intAdd;
    }
    
    @Override
    public String toFileString(){
        return "*" + multiply + "+" + add;
    }

    public static Formula getFromFileString(String str){
        double mult = Double.parseDouble(str.substring(1, str.indexOf("+")));
        double add = Double.parseDouble(str.substring(str.indexOf("+")+1));
        return new Formula(mult, add, (int) mult, (int) add);
    }
    
}

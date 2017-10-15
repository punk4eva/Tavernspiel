
package logic;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles probability.
 */
public class Distribution implements Serializable{
    
    private final static long serialVersionUID = -1416387932;
    
    protected double[] outputs;
    protected int[] chances;
    public transient static final Random r = new Random();
    
    public Distribution(double[] out, int[] cha){
        outputs = out;
        chances = convert(cha);
    }
    
    public Distribution(double l, double u){
        outputs = new double[]{l, u};
    }
    
    public Distribution(double[] out){
        outputs = out;
        chances = new int[out.length];
        for(int n=1;n<out.length+1;n++){
            chances[n-1] = n;
        }
    }
    
    public Distribution(int[] cha){
        chances = convert(cha);
        outputs = new double[cha.length];
        for(int n=0;n<cha.length;n++) chances[n] = n;
    }
    
    /**
     * Gives a random output from this Distribution's output array based on its
     * chances.
     * @return An output from the array.
     */
    public double next(){
        return outputs[chanceToInt(r.nextInt(chances[chances.length-1])+1)];
    }
    
    /**
     * Gives a random integer based on this Distribution's output array.
     * @return A random integer between output[0] (inclusive) and output[1] (exclusive)
     * of this object.
     */
    public int nextInt(){
        return r.nextInt((int)(outputs[1]-outputs[0])) + (int)outputs[0];
    }
    
    /**
     * Gets the index of output which the given chance value obtains.
     * @param i The chance value.
     * @return The index of the output.
     */
    protected int chanceToInt(int i){
        for(int n=0;n<chances.length;n++) if(i<=chances[n]) return n;
        return -1;
    }
    
    /**
     * Converts an array of normal chances to an array of cumulative chances. 
     * @param ary The array.
     * @return The modified array.
     */
    public static int[] convert(int[] ary){
        int cumulative = 0;
        for(int n=0;n<ary.length;n++){
            cumulative += ary[n];
            ary[n] = cumulative;
        }
        return ary;
    }
    
    /**
     * Returns a random integer between the given bounds (inclusive).
     * @param lo The lower bound.
     * @param up The upper bound.
     * @return A random integer.
     */
    public static int getRandomInt(int lo, int up){
        return r.nextInt(1+up-lo)+lo;
    }
    
    /**
     * Returns a random double between the given bounds (inclusive).
     * @param lo The lower bound.
     * @param up The upper bound.
     * @return A random double.
     */
    public static double getRandomInclusiveDouble(double lo, double up){
        return r.nextDouble() * (up-lo) + lo;
    }
    
    /**
     * Returns a Distribution with an equal liklihood t select the given numbers.
     * @param length The length of the distribution.
     * @return A Distribution of given length with outputs ranging from 0 - length-1.
     */
    public static Distribution getUniformDistribution(int length){
        double[] output = new double[length]; 
        int[] chances = new int[length];
        for(int n=0;n<length;n++){
            output[n] = n;
            chances[n] = 1;
        }
        return new Distribution(output, chances);
    }
    
    public static Distribution getUniformDistribution(int[] ary){
        int length = ary.length;
        double[] output = new double[length]; 
        int[] chances = new int[length];
        for(int n=0;n<length;n++){
            output[n] = n;
            chances[n] = 1;
        }
        return new Distribution(output, chances);
    }
    
    /**
     * Returns a randomly generated boolean.
     * @param chance The numerator.
     * @param in The denominator.
     * @return true if a randomly generated integer between 0 and in is less than
     * chance.
     */
    public static boolean chance(double chance, double in){
        return r.nextDouble()*in<chance;
    }
    
    /**
     * Returns a randomly generated boolean.
     * @return true if a randomly generated integer between 0 and output[1] is less than
     * output[0].
     */
    public boolean chance(){
        return r.nextDouble()*outputs[1]<outputs[0];
    }
    
    public void updateFromFormula(int x, Formula... formulas){
        for(int n=0;n<outputs.length;n++){
            outputs[n] = formulas[n].getInt(x);
        }
    }
    
    public static double randomDouble(double low, double up){
        return r.nextDouble() * (up-low) + low;
    }
    
    public static int getRandomInt(int from, int to, int... not){
        try{
            return getRandomIntHelper(from, to, 0, not);
        }catch(StackOverflowError e){
            int ary[] = Utils.shuffle(Utils.rangeArray(from, to));
            System.out.println("ACTIVATED");
            for(int i : ary){
                boolean notPresent = true;
                for(int j : not) if(j==i){
                    notPresent = false;
                    break;
                }
                if(notPresent) return i;
            }
        }
        return Integer.MIN_VALUE;
    }
    
    private static int getRandomIntHelper(int from, int to, int count, int... not){
        if(count>=80) throw new StackOverflowError();
        int n = r.nextInt(to-from) + from;
        for(int i : not) if(i==n) return getRandomIntHelper(from, to, count+1, not);
        return n;
    }
    
}

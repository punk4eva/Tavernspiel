
package logic;

import java.io.Serializable;
import java.util.LinkedList;
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
    public transient static final Random R = new Random();
    
    /**
     * Creates a new instance.
     * @param out The possible outputs.
     * @param cha The relative chances of those outputs.
     */
    public Distribution(double[] out, int[] cha){
        outputs = out;
        chances = convert(cha);
    }
    
    /**
     * Creates a new instance meant for generating random booleans.
     * @param l The numerator of the chance to return true.
     * @param u The denominator of the chance to return true.
     */
    public Distribution(double l, double u){
        outputs = new double[]{l, u};
    }
    
    /**
     * Creates a new instance with equally likely outputs.
     * @param out The outputs.
     */
    public Distribution(double[] out){
        outputs = out;
        chances = new int[out.length];
        for(int n=1;n<out.length+1;n++){
            chances[n-1] = n;
        }
    }
    
    /**
     * Creates a new instance returning any value from 0 to n-1 (inclusive)
     * where n is the length of the chances array.
     * @param cha The relative chances of each value being returned.
     */
    public Distribution(int[] cha){
        chances = convert(cha);
        outputs = new double[cha.length];
        for(int n=0;n<cha.length;n++) outputs[n] = n;
    }
    
    /**
     * Gives a random output from this Distribution's output array based on its
     * chances.
     * @return An output from the array.
     */
    public double next(){
        return outputs[chanceToInt(R.nextInt(chances[chances.length-1])+1)];
    }
    
    /**
     * Gives a random integer based on this Distribution's output array.
     * @return A random integer between output[0] (inclusive) and output[1] (exclusive)
     * of this object.
     */
    public int nextInt(){
        return R.nextInt((int)(outputs[1]-outputs[0])) + (int)outputs[0];
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
        return R.nextInt(1+up-lo)+lo;
    }
    
    /**
     * Generates a normally distributed double with the given mean and standard
     * deviation.
     * @param mean
     * @param sDev
     * @return
     */
    public static double getGaussian(double mean, double sDev){
        return mean + sDev*R.nextGaussian();
    }
    
    /**
     * Returns a normally distributed double above 0.
     * @param mean
     * @param sDev
     * @return
     */
    public static double getGaussianA(double mean, double sDev){
        double ret;
        do ret = getGaussian(mean, sDev);
        while(ret<0);
        return ret;
    }
    
    /**
     * Returns a random double between the given bounds (inclusive).
     * @param lo The lower bound.
     * @param up The upper bound.
     * @return A random double.
     */
    public static double getRandomInclusiveDouble(double lo, double up){
        return R.nextDouble() * (up-lo) + lo;
    }
    
    /**
     * Returns a Distribution with an equal likelihood t select the given numbers.
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
    
    /**
     * Returns a value from a subsection of this Distribution, with the same 
     * relative chances.
     * @param s The start of the subsection (inclusive). 
     * @param t The end of the subsection (exclusive).
     * @return
     */
    public double nextFromRange(int s, int t){
        int start;
        if(s==0) start=0;
        else start = chances[s-1];
        int[] c = new int[t-s];
        double[] o = new double[t-s];
        for(int n=s;n<t;n++){
            o[n] = outputs[n-s];
            c[n] = chances[n-s]-start;
        }
        return new Distribution(o, c).next();
    }
    
    /**
     *
     * @param ary
     * @return
     */
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
        return R.nextDouble()*in<chance;
    }
    
    /**
     * Returns a randomly generated boolean.
     * @return true if a randomly generated integer between 0 and output[1] is less than
     * output[0].
     */
    public boolean chance(){
        return R.nextDouble()*outputs[1]<outputs[0];
    }
    
    /**
     * Ups all the values in the output array by 'a'.
     * @param a The additive.
     */
    public void add(double a){
        for(int n=0;n<outputs.length;n++) outputs[n] += a;
    }
    
    /**
     * Ups the first value by 'a' and the second by 'b'.
     * @param a The additive.
     * @param b The additive.
     */
    public void add(double a, double b){
        outputs[0] += a;
        outputs[1] += b;
    }
    
    /**
     *
     * @param low
     * @param up
     * @return
     */
    public static double randomDouble(double low, double up){
        return R.nextDouble() * (up-low) + low;
    }
    
    /**
     * Generates a random int whose value cannot be one of those specified.
     * @param from The lower bound. (inclusive)
     * @param to The upper bound. (non-inclusive)
     * @param not The range of values not possible to generate.
     * @return A random int.
     */
    public static int getRandomInt(int from, int to, int... not){
        LinkedList<Integer> lst = new LinkedList<>();
        for(int n=from;n<to;n++){
            boolean notpresent = true;
            for(int i : not) if(i==n){
                notpresent = false;
                break;
            }
            if(notpresent) lst.add(n);
        }
        if(lst.isEmpty()) return Integer.MIN_VALUE;
        return lst.get(R.nextInt(lst.size()));
    }
    
    /**
     * This class captures a normal distribution with an adjustable mean and
     * standard deviation.
     */
    public static class NormalProb implements Serializable{
        
        private static final long serialVersionUID = 56734892217432L;
        
        public double mean, sDev;
        
        /**
         * Constructs a normal distribution with the given mean and standard
         * deviation.
         * @param m
         * @param s
         */
        public NormalProb(double m, double s){
            mean = m;
            sDev = s;
        }
        
        /**
         * Returns a normally distributed double above 0.
         * @return
         */
        public double next(){
            return Distribution.getGaussianA(mean, sDev);
        }
        
        /**
         * Returns a normally distributed double above 0 with mean and sDev 
         * modifiers.
         * @param m The addition to the mean.
         * @param s The multiplication of the sDev.
         * @return
         */
        public double next(double m, double s){
            return getGaussianA(mean+m, sDev*s);
        }
        
        /**
         * Returns true if the Gaussian was above 0.
         * @return
         */
        public boolean check(){
            return getGaussian(mean, sDev)>0;
        }
        
    }
    
}

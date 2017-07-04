
package logic;

import java.util.Random;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles probability.
 */
public class Distribution{
    
    protected double[] outputs;
    protected int[] chances;
    private static final Random r = new Random();
    
    public Distribution(double[] out, int[] cha){
        outputs = out;
        chances = convert(cha);
    }
    
    public Distribution(int l, int u){
        outputs = new double[] {l, u};
    }
    
    public double next(){
        return outputs[chanceToInt(r.nextInt(chances[chances.length-1])+1)];
    }
    
    public int nextInt(){
        return r.nextInt((int)(outputs[1]-outputs[0])) + (int)outputs[0];
    }
    
    protected int chanceToInt(int i){
        for(int n=0;n<chances.length;n++) if(i<=chances[n]) return n;
        return -1;
    }
    
    private int[] convert(int[] ary){
        int cumulative = 0;
        for(int i : ary){
            cumulative += i;
            i = cumulative;
        }
        return ary;
    }
    
    public static int getRandomInclusiveInt(int lo, int up){
        return r.nextInt(1+up-lo)+lo;
    }
    
    public static double getRandomInclusiveDouble(double lo, double up){
        return r.nextDouble() * (up-lo) + lo;
    }
    
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
    
}

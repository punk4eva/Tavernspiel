
package logic;

import java.util.Random;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles probability.
 */
public class Distribution{
    
    private double[] outputs;
    private int[] chances;
    private final Random r = new Random();
    
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
    
    private int chanceToInt(int i){
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
    
}

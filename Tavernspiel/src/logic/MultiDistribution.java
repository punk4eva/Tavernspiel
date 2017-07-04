
package logic;

import java.util.Random;

/**
 *
 * @author Adam Whittaker
 */
public class MultiDistribution{
    
    private Distribution[] distributions = null;
    private int incrementor = 0;
    private static final Random r = new Random();
    
    public MultiDistribution(Distribution[] distribs){
        distributions = distribs;
    }
    
    public void increment(){
        incrementor++;
    }
    
    public void decrement(){
        incrementor--;
    }
    
    public Distribution get(){
        return distributions[incrementor];
    }
    
    public double next(){
        return distributions[incrementor].outputs[distributions[incrementor]
                .chanceToInt(r.nextInt(distributions[incrementor]
                        .chances[distributions[incrementor]
                                .chances.length-1])+1)];
    }
    
    public int nextInt(){
        return r.nextInt((int)(distributions[incrementor].outputs[1]-
                distributions[incrementor].outputs[0])) + 
                (int)distributions[incrementor].outputs[0];
    }
    
    public static MultiDistribution getAllUniform(int amount, int length){
        Distribution[] distribs = new Distribution[amount];
        for(int a=0;a<amount;a++){
            distribs[a] = Distribution.getUniformDistribution(length);
        }
        return new MultiDistribution(distribs);
    } 
    
}

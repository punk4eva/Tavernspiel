
package pathfinding;

/**
 *
 * @author Adam Whittaker
 */
public class Weight{
    
    public double array[];
    
    public Weight(long... ary){
        long avg = average(ary);
        array = new double[ary.length];
        for(int n=0;n<ary.length;n++){
            array[n] = ((double)ary[n])/avg;
        }
    }
    
    public Weight(long[] ary, double... weights){
        long avg = average(ary);
        array = new double[ary.length];
        for(int n=0;n<ary.length;n++){
            array[n] = weights[n] * ((double)ary[n])/avg;
        }
    }
    
    public static double average(double... ary){
        double sum = ary[0];
        for(int n=1;n<ary.length;n++) sum += ary[n];
        return sum / ary.length;
    }
    
    public static long average(long... ary){
        long sum = ary[0];
        for(int n=1;n<ary.length;n++) sum += ary[n];
        return sum / ary.length;
    }
    
}

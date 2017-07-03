
package items;

/**
 *
 * @author Adam Whittaker
 */
public class Helmet extends Apparatus{
    
    public Helmet(String s){
        super(s, false);
    }
    
    public Helmet(Item i){
        super(i.name, false);
    }
    
}

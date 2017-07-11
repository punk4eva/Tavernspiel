
package items;

/**
 *
 * @author Adam Whittaker
 */
public class ItemAction{
    
    private final String action;
    private String data[];
    
    public ItemAction(String str){
        action = str;
    }
    
    public ItemAction(String str, String str2){
        action = str;
        data = new String[]{str2};
    }
    
    public ItemAction(String str, String[] d){
        action = str;
        data = d;
    }
    
    public String getName(){
        return action;
    }
    
    public String[] getData(){
        return data;
    }
    
    protected static ItemAction[] getDefaultActions(){
        return new ItemAction[]{new ItemAction("THROW"), new ItemAction("DROP")};
    }
    
    protected static ItemAction[] getArray(int length){
        ItemAction[] ret = new ItemAction[length];
        ret[0] = new ItemAction("THROW");
        ret[1] = new ItemAction("DROP");
        return ret;
    }
    
}

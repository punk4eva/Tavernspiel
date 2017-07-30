
package items;

import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 */
public class ItemAction implements Fileable{
    
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

    @Override
    public String toFileString(){
        String ret = "<" + action + "<itactd>";
        for(String d : data) ret += d + ",";
        return (data.length==0 ? ret : ret.substring(ret.length()-1)) + ">";
    }

    @Override
    public ItemAction getFromFileString(String filestring){
        String[] profile = filestring.substring(1, filestring.length()-1).split("<itactd>");
        if(profile[1].isEmpty()) return new ItemAction(profile[0]);
        return new ItemAction(profile[0], profile[1].split(","));
    }
    
}

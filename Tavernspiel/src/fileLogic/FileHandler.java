
package fileLogic;

import java.io.File;

/**
 *
 * @author Adam Whittaker
 */
public class FileHandler{
    
    public static ReadWrite readwrite;
    
    public static String getExtension(File file){
        String filepath = file.getPath();
        return filepath.substring(filepath.lastIndexOf(".")+1);
    }
    
    public static boolean isType(File file, String extension){
        return getExtension(file).equals(extension);
    }
    
    public static void initReadWrite(String filepath){
        readwrite = new ReadWrite(filepath);
    }
    
}

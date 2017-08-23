
package fileLogic;

import gui.Handler;
import gui.MainClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public class FileHandler{
    
    public static ObjectOutputStream output;
    
    public static String getExtension(File file){
        String filepath = file.getPath();
        return filepath.substring(filepath.lastIndexOf(".")+1);
    }
    
    public static boolean isType(File file, String extension){
        return getExtension(file).equals(extension);
    }
    
    public static void initOutputDestination(String filepath){
        try{
            output = new ObjectOutputStream(new FileOutputStream(filepath));
        }catch(IOException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
    }
    
    public static void toFile(Serializable object, String destinationFilepath){
        initOutputDestination(destinationFilepath);
        try{
            output.writeObject(object);
            output.close();
        }catch(IOException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
    }
    
    public static Object getFromFile(String filepath){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath));
            return in.readObject();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        return "EXCEPTION";
    }
    
}

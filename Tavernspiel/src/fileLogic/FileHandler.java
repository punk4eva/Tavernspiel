
package fileLogic;

import dialogues.ExceptionDialogue;
import gui.Game;
import gui.MainClass;
import gui.Window;
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
 * 
 * Handles IO
 */
public final class FileHandler{
    
    /**
     * Gets the extension for a given File.
     * @param file The File.
     * @return the extension as a String.
     */
    public static String getExtension(File file){
        String filepath = file.getPath();
        return filepath.substring(filepath.lastIndexOf(".")+1);
    }
    
    /**
     * Checks if the given File has the given extension.
     * @param file The File.
     * @param extension The extension.
     * @return True if the File has the given extension, false if not.
     */
    public static boolean isType(File file, String extension){
        return getExtension(file).equals(extension);
    }
    
    /**
     * Serializes a given object.
     * @param object The object.
     * @param filepath The destination file path.
     */
    public static void toFile(Serializable object, String filepath){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filepath))){
            output.writeObject(object);
        }catch(IOException e){
            new ExceptionDialogue(e).next((Game)Window.main);
        }
    }
    
    /**
     * Deserializes something from a file path.
     * @param filepath The file path.
     * @return The Object obtained.
     */
    public static Object getFromFile(String filepath){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            return in.readObject();
        }catch(IOException | ClassNotFoundException e){
            new ExceptionDialogue(e).next((Game)Window.main);
        }
        return "EXCEPTION";
    }
    
}

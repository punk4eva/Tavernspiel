
package logic;

import dialogues.ExceptionDialogue;
import gui.Game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import level.Dungeon;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles File IO
 */
public final class FileHandler{
    
    private FileHandler(){}
    
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
    public static void serialize(Serializable object, String filepath){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filepath))){
            output.writeObject(object);
        }catch(IOException e){
            e.printStackTrace();
            new ExceptionDialogue(e).next();
        }
    }
    
    /**
     * Deserializes something from a file path.
     * @param filepath The file path.
     * @return The Object obtained.
     */
    public static Object deserialize(String filepath){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            return in.readObject();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            new ExceptionDialogue(e).next();
        }
        return "EXCEPTION";
    }
    
    /**
     * Serializes the Game
     * @param game
     */
    public static void serializeGame(Game game){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(game.savePath))){
            output.writeObject(game.dungeon);
            KeyMapping.saveState();
        }catch(IOException e){
            e.printStackTrace();
            new ExceptionDialogue(e).next();
        }
    }
    
    /**
     * Loads a Game object from the given filepath.
     * @param filepath
     * @return
     */
    public static Game deserializeGame(String filepath){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))){
            Dungeon d = (Dungeon) in.readObject();
            KeyMapping.retrieveState();
            return new Game(d);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            new ExceptionDialogue(e).next();
        }
        return null;
    }
    
}


package fileLogic;

import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.PlayData;
import creatures.Hero;
import gui.Handler;
import java.io.File;
import java.io.IOException;
import level.Dungeon;
import logic.Fileable;

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
    
    public static void toFile(Fileable object, String destinationFilepath){
        initReadWrite(destinationFilepath);
        readwrite.write(object.toFileString());
    }
    
    public static Object getFromFile(File file, Handler handler) throws IOException{
        String filestring = new ReadWrite(file).read();
        String extension = getExtension(file).toLowerCase();
        switch(extension){
            case "atrib": return Attributes.getFromFileString(filestring);
            case "hero": return Hero.getFromFileString(filestring, handler);
            case "plydta": return PlayData.getFromFileString(filestring);
            case "lvl": return Dungeon.getFromFileString(filestring);
            case "deathdta": return DeathData.getFromFileString(filestring);
        }
        throw new IOException("Extension \"" + extension + "\" not recognised!");
    }
    
}

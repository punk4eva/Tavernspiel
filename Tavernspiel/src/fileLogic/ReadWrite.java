
package fileLogic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Adam Whittaker
 */
public class ReadWrite{
    
    
    //Variable declaration
    private static FileReader read;
    private static FileWriter write;
    private final File file;
    
    
    //Constructors
    
    /**
     * @param f The name of the text file to read/write to.
     */
    public ReadWrite(String f){
        file = new File(f);
    }
    
    /**
     * @param f The file to read/write to.
     */
    public ReadWrite(File f){
        file = f;
    }
    
    
    //Methods
    
    /**
     * Creates the file that is stored in the instance of ReadWrite.
     */
    protected void create(){
        try{
	    if(file.createNewFile()) System.out.println("File has been created!");
            else System.err.println("File already exists.");
    	}catch(IOException e){
            System.err.println("Error creating file.");
	}
    }
    
    /**
     * Reads the file.
     * @return The text file as a String.
     */
    public String read(){
        String store = "";
        try{
            read = new FileReader(file);
            while(read.ready()) store += (char) read.read();
            read.close();
        }catch(IOException ex){
            System.err.println("Error in ReadWrite.read()");
        }
        return store;
    }
    
    /**
     * Writes the given String to the file.
     * @param str The String to write.
     */
    public void write(String str){
        try{
            write = new FileWriter(file, true);
            write.write(str);
            write.close();
        }catch(IOException e){
            System.err.println("Error in ReadWite.write(String str)");
        }
    }
    
    /**
     * Clears the file.
     */
    protected void clear(){
        try{
            write = new FileWriter(file);
            write.close();
        }catch(IOException e){
            System.err.println("Error in ReadWrite.clear()");
        }
    }
    
}

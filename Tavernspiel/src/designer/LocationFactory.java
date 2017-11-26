
package designer;

import static java.lang.System.out;
import java.util.Scanner;
import level.Location;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class LocationFactory{
    
    Scanner scan = new Scanner(System.in);
    Location defaultLocation = new Location("Default", "shkoderTileset",
            "water", "sound/songs/Cyanoshrooms.wav", 2);
    
    public Location produce(){
        out.println("What is the name?");
        String name = scan.nextLine();
        if(name.equals("/pass")) return defaultLocation;
        out.println("What is the path to the tileset?");
        String tiles = scan.nextLine();
        out.println("What is the path to the water image?");
        String water = scan.nextLine();
        out.println("What is the path to the background music?");
        String bmp = scan.nextLine();
        out.println("Should water be generated before grass?");
        Boolean wbg = Boolean.parseBoolean(scan.nextLine());
        System.out.println("What's the country code?");
        int i = Integer.parseInt(scan.nextLine());
        return new Location(name, tiles, water, getWaterDistrib(), getGrassDistrib(), wbg, bmp, i);
    }
    
    private Distribution getWaterDistrib(){
        out.println("What is the water generation chance?");
        String[] p = scan.nextLine().split(" in ");
        try{
            return new Distribution(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
        }catch(Exception e){
            return new Distribution(1, 20);
        }
    }
    
    private Distribution getGrassDistrib(){
        out.println("What is the grass generation chance?");
        String[] p = scan.nextLine().split(" in ");
        try{
            return new Distribution(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
        }catch(Exception e){
            return new Distribution(1, 20);
        }
    }
    
}

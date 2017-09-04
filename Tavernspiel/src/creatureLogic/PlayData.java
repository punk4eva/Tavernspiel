
package creatureLogic;

import fileLogic.FileHandler;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 * 
 * A class that interacts with data from all games played.
 */
public class PlayData implements Serializable{
    
    public int gamesPlayed;
    public HashSet<Badge> badgesEarned;
    public int gamesWon;
    
    /**
     * Creates a new instance.
     * @param gP
     * @param gW
     * @param bE
     */
    public PlayData(int gP, int gW, List<Badge> bE){
        gamesPlayed = gP;
        badgesEarned = new HashSet(bE);
        gamesWon = gW;
    }
    
    /**
     * Retrieves PlayData from its file.
     */
    public PlayData(){
        PlayData pd = (PlayData) FileHandler.getFromFile("saves/gamedata.plydta");
        gamesPlayed = pd.gamesPlayed;
        badgesEarned = pd.badgesEarned;
        gamesWon = pd.gamesWon;
    }
    
    /**
     * Saves this Object.
     */
    public void save(){
        FileHandler.toFile(this, "saves/gamedata.plydta");
    };
    
    /**
     * Returns the percentage of games won as a String.
     * @return A String representation.
     */
    public String successRate(){
        return (((double) gamesWon)/((double) gamesPlayed) * 100) + "%";
    }
    
    /**
     * Removes badges that have been overridden.
     */
    public void updateOverridingBadges(){
        HashSet<Badge> temp = (HashSet<Badge>) badgesEarned.clone();
        temp.stream().forEach((b) -> {
            if(b.isOverriden(temp)) badgesEarned.remove(b);
        });   
    }
    
}


package creatureLogic;

import fileLogic.FileHandler;
import fileLogic.ReadWrite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

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
    
    public PlayData(int gP, int gW, ArrayList<Badge> bE){
        gamesPlayed = gP;
        badgesEarned = new HashSet(bE);
        gamesWon = gW;
    }
    
    public PlayData(){
        PlayData pd = (PlayData) FileHandler.getFromFile("saves/gamedata.plydta");
        gamesPlayed = pd.gamesPlayed;
        badgesEarned = pd.badgesEarned;
        gamesWon = pd.gamesWon;
    }
    
    public void saveToFile(){
        FileHandler.toFile(this, "saves/gamedata.plydta");
    };
    
    
    public String successRate(){
        return (((double) gamesWon)/((double) gamesPlayed) * 100) + "%";
    }
    
    public void updateOverridingBadges(){
        badgesEarned.stream().forEach((b) -> {
            if(b.isOverriden(badgesEarned)) badgesEarned.remove(b);
        });
    }
    
}

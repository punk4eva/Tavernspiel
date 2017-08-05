
package creatureLogic;

import fileLogic.ReadWrite;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * A class that interacts with data from all games played.
 */
public class PlayData implements Fileable{
    
    public int gamesPlayed;
    public HashSet<Badge> badgesEarned;
    public int gamesWon;
    private final ReadWrite rw = new ReadWrite("saves/gamedata.plydta");
    
    public PlayData(int gP, int gW, ArrayList<Badge> bE){
        gamesPlayed = gP;
        badgesEarned = new HashSet(bE);
        gamesWon = gW;
    }
    
    public PlayData(){
        PlayData pd = getFromFileString(rw.read());
        gamesPlayed = pd.gamesPlayed;
        badgesEarned = pd.badgesEarned;
        gamesWon = pd.gamesWon;
    }

    @Override
    public String toFileString(){
        String ret = gamesPlayed + "|" + gamesWon + "|";
        ret = badgesEarned.stream().map((b) -> (b instanceof DeathBadge
               ? ((DeathBadge) b).toFileString() + ","
               : b.toFileString() + ","
            )).reduce(ret, String::concat);
        return ret.substring(0, ret.length()-1);
    }
    
    public static PlayData getFromFileString(String filestring){
        String[] profile = filestring.split("|");
        ArrayList<Badge> badges = new ArrayList<>();
        for(String str : profile[2].split(",")){
            badges.add(Badge.getFromFileString(str));
        }
        return new PlayData(Integer.parseInt(profile[0]), Integer.parseInt(profile[1]), badges);
    }
    
    public void saveToFile(){
        rw.overwrite(toFileString());
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


package logic;

import java.io.File;
import java.util.ArrayDeque;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Adam Whittaker
 */
public class SoundHandler{
    
    private static Thread backgroundMusicLoop;
    private static boolean flowMode;
    private static ArrayDeque<File> playlist = new ArrayDeque<>();

    public static void playSFX(String path, float volChange){
        File file = new File("sound/SFX/" + path);
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            FloatControl gainControl = 
            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(volChange);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/1000);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static synchronized void playAbruptLoop(String path, float volChange){
        File file = new File("sound/songs/" + path);
        if(backgroundMusicLoop!=null) backgroundMusicLoop.interrupt();
        backgroundMusicLoop = new Thread(
                () -> {
                    Clip clip = null;
                    try{
                    clip = AudioSystem.getClip();
                    }catch(LineUnavailableException ex){}
                    try{
                        while(!flowMode){
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                file);
                            clip.open(inputStream);
                            FloatControl gainControl =
                                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(volChange);
                            clip.start();
                            Thread.sleep(clip.getMicrosecondLength()/1000);
                            clip.close();
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                        clip.close();
                    }
        });
        backgroundMusicLoop.start();
    }

    public static synchronized void playFlowingLoop(String path, float volChange){
        flowMode = true;
        File file = new File("sound/songs/" + path);
        new Thread(() -> {
            try{
                if(backgroundMusicLoop!=null) backgroundMusicLoop.join();
                backgroundMusicLoop = new Thread(() -> {
                    Clip clip = null;
                    try{
                        clip = AudioSystem.getClip();
                    }catch(LineUnavailableException ex){}
                    try{
                        flowMode = false;
                        while(!flowMode){
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                file);
                            clip.open(inputStream);
                            FloatControl gainControl =
                                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(volChange);
                            clip.start();
                            Thread.sleep(clip.getMicrosecondLength()/1000);
                            clip.close();
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                        clip.close();
                    }
                });
                backgroundMusicLoop.start();
            }catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }).start();
    }
    
    public static synchronized void playAbruptQueue(float volChange){
        if(backgroundMusicLoop!=null) backgroundMusicLoop.interrupt();
        backgroundMusicLoop = new Thread(
                () -> {
                    Clip clip = null;
                    try{
                    clip = AudioSystem.getClip();
                    }catch(LineUnavailableException ex){}
                    try{
                        while(!flowMode&&!playlist.isEmpty()){
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                playlist.poll());
                            clip.open(inputStream);
                            FloatControl gainControl =
                                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(volChange);
                            clip.start();
                            Thread.sleep(clip.getMicrosecondLength()/1000);
                            clip.close();
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                        clip.close();
                    }
        });
        backgroundMusicLoop.start();
    }
    
    public static synchronized void playFlowingQueue(float volChange){
        flowMode = true;
        new Thread(() -> {
            try{
                if(backgroundMusicLoop!=null) backgroundMusicLoop.join();
                backgroundMusicLoop = new Thread(() -> {
                    Clip clip = null;
                    try{
                        clip = AudioSystem.getClip();
                    }catch(LineUnavailableException ex){}
                    try{
                        flowMode = false;
                        while(!flowMode&&!playlist.isEmpty()){
                            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                playlist.poll());
                            clip.open(inputStream);
                            FloatControl gainControl =
                                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(volChange);
                            clip.start();
                            Thread.sleep(clip.getMicrosecondLength()/1000);
                            clip.close();
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                        clip.close();
                    }
                });
                backgroundMusicLoop.start();
            }catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }).start();
    }
    
    public static synchronized void addSong(String path){
        playlist.add(new File("sound/songs/"+path));
    }
    
    public static synchronized void stopBackground(){
        backgroundMusicLoop.interrupt();
        backgroundMusicLoop = null;
    }
    
}

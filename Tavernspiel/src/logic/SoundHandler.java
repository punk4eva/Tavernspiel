
package logic;

import gui.Window;
import gui.mainToolbox.Main;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Adam Whittaker
 */
public class SoundHandler{
    
    private MusicThread backgroundMusicLoop0, backgroundMusicLoop1;
    private boolean currentLoop0 = false;
    private Thread SFXThread;

    public void playSFX(String path){
        File file = new File("sound/" + path);
        if(SFXThread!=null) SFXThread.interrupt();
        SFXThread = new Thread(() -> {
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(file));
                FloatControl gainControl = 
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(Window.sfxVolume);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength()/1000);
            }catch(Exception e){
                //System.err.println(e.getMessage());
            }
        });
        SFXThread.start();
    }
    
    /**
     * A static version of the playSFX method.
     * @param path
     */
    public static void SFX(String path){
        Main.soundSystem.playSFX(path);
    }
    
    /**
     * Plays a music loop abruptly.
     * @param path The path of the music.
     */
    public synchronized void playAbruptLoop(String path){
        if(currentLoop0){
            if(backgroundMusicLoop0!=null) backgroundMusicLoop0.stopClip();
            currentLoop0 = false;
            backgroundMusicLoop1 = new MusicThread(path, currentLoop0);
            backgroundMusicLoop1.start();
        }else{
            if(backgroundMusicLoop1!=null) backgroundMusicLoop1.stopClip();
            currentLoop0 = true;
            backgroundMusicLoop0 = new MusicThread(path, currentLoop0);
            backgroundMusicLoop0.start();
        }
    }

    /**
     * Plays a music loop after the current song is over.
     * @param path The path of the music.
     */
    public synchronized void playSmoothLoop(String path){
        if(currentLoop0){
            if(backgroundMusicLoop0!=null) backgroundMusicLoop0.fadeOut();
            currentLoop0 = false;
            if(backgroundMusicLoop1==null){
                backgroundMusicLoop1 = new MusicThread(path, currentLoop0);
                backgroundMusicLoop1.startOnFadeIn();
            }else backgroundMusicLoop1.fadeIn();
        }else{
            if(backgroundMusicLoop1!=null) backgroundMusicLoop1.fadeOut();
            currentLoop0 = true;
            if(backgroundMusicLoop0==null){
                backgroundMusicLoop0 = new MusicThread(path, currentLoop0);
                backgroundMusicLoop0.startOnFadeIn();
            }else backgroundMusicLoop0.fadeIn();
        }
    }
    
    /**
     * Stops the background music abruptly.
     */
    public synchronized void stopBackgroundAbruptly(){
        if(currentLoop0) backgroundMusicLoop0.stopClip();
        else backgroundMusicLoop1.stopClip();
    }
    
    /**
     * Stops the background music via a gradual fade-out.
     */
    public synchronized void stopBackgroundSmoothly(){
        if(currentLoop0) backgroundMusicLoop0.fadeOut();
        else backgroundMusicLoop1.fadeOut();
    }
    
    /**
     * Pauses the background music.
     */
    public synchronized void pause(){
        if(currentLoop0) backgroundMusicLoop0.pause();
        else backgroundMusicLoop1.pause();
    }
    
    /**
     * Resumes the background music.
     */
    public synchronized void resume(){
        if(currentLoop0) backgroundMusicLoop0.unpause();
        else backgroundMusicLoop1.unpause();
    }
    
    /**
     * Updates the volume of the background music from the volume in Window.
     */
    public void updateVolume(){
        if(currentLoop0) backgroundMusicLoop0.gainControl.setValue(Window.musicVolume);
        else backgroundMusicLoop1.gainControl.setValue(Window.musicVolume);
    }
    
    public static class MusicThread extends Thread implements LineListener{
        
        private final String filepath;
        private final Clip clip;
        private final FloatControl gainControl;
        private final boolean loop0;
        private volatile boolean paused = false, fadingOut = false, 
                fadingIn = false, playing = true;
        
        private MusicThread(String path, boolean l0){
            filepath = path;
            try{
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("sound/"+filepath)));
            }catch(LineUnavailableException | IOException | UnsupportedAudioFileException ex){
                throw new IllegalStateException(
                        "Creation: " + filepath, ex);
            }
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(Window.musicVolume);
            loop0 = l0;
        }
        
        public void setVolume(float vol){
            gainControl.setValue(vol);
        }
        
        public synchronized void pause(){
            if(!paused){
                paused = true;
                clip.stop();
            }
        }
        
        public synchronized void unpause(){
            if(paused){
                paused = false;
                clip.start();
            }
        }
        
        public void fadeOut(){
            fadingOut = true;
            playing = false;
            synchronized(clip){
                clip.notify();
            }
        }
        
        public void fadeIn(){
            fadingOut = false;
            fadingIn = true;
            playing = true;
        }
        
        public void startOnFadeIn(){
            gainControl.setValue(gainControl.getMinimum()+40);
            fadingIn = true;
            start();
        }
        
        public void stopClip(){
            clip.stop();
            playing = false;
            synchronized(clip){
                clip.notify();
            }
        }
        
        private void close(){
            playing = false;
            clip.close();
            if(loop0) Main.soundSystem.backgroundMusicLoop0 = null;
            else Main.soundSystem.backgroundMusicLoop1 = null;
        }
        
        @Override
        public void run(){
            try{
                clip.start();
                if(fadingIn){
                    while(gainControl.getValue()<Window.musicVolume){
                        gainControl.setValue(gainControl.getValue()+0.5f);
                        Thread.sleep(200);
                    }
                    fadingIn = false;
                }
                while(true){
                    synchronized(clip){
                        while(playing) clip.wait();
                    }
                    if(fadingOut){
                        System.out.println("Fading out..." + fadingIn);
                        try{
                            while(!fadingIn){
                                gainControl.setValue(gainControl.getValue()-0.5f);
                                Thread.sleep(200);
                            }
                        }catch(IllegalArgumentException e){
                            fadingIn = false;
                        }
                        if(fadingIn){
                            while(gainControl.getValue()<Window.musicVolume){
                                gainControl.setValue(gainControl.getValue()+0.5f);
                                Thread.sleep(200);
                            }
                            fadingIn = false;
                        }else close();
                    }else close();
                }
            }catch(InterruptedException e){
                System.err.println(e.getMessage());
                close();
            }
        }

        @Override
        public void update(LineEvent le){
            if(le.getType().equals(LineEvent.Type.STOP)) synchronized(clip){
                fadingOut = false;
                fadingIn = false;
                clip.notify();
            }
        }
        
    }
    
}

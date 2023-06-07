package ZombieApocalypse.Utility;

import javax.sound.sampled.*;

public class PlayWav {
    private static Clip clip = null;
    private static FloatControl music;

    private static Clip clipSound = null;
    private static FloatControl sound;

    private static PlayWav play = null;

    private PlayWav(){}
    public static PlayWav getInstance(){
        if(play == null)
            play = new PlayWav();
        return play;
    }

    //Musica
    public void play(String path){
        clip = ResourcesLoader.getInstance().getAudioClip(path);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        music = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }
    public void stop(){ clip.stop(); }
    public boolean isPlay(){
        if(clip != null)
            return clip.isRunning();
        return false;
    }
    public void setVolume(int value){
        music.setValue(value);
    }

    //Suoni
    public void loadSound(String path){
        clipSound = ResourcesLoader.getInstance().getAudioClip(path);
        sound = (FloatControl) clipSound.getControl(FloatControl.Type.MASTER_GAIN);
    }
    public void playSound(){
        clipSound.start();
    }
    public void setVolumeSound(int value){
        sound.setValue(value);
    }

}

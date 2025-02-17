package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

    private float volumeLevel;
    private Clip currentSoundTrack;
    
    public SoundPlayer(float volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    private void loadSoundTrack(String filePath) {
        try {
            // load audio file into audioinputstream
            AudioInputStream soundFile = AudioSystem.getAudioInputStream(getClass().getResource("/assets/" + filePath + ".wav"));
            currentSoundTrack = AudioSystem.getClip();

            // open sound file
            currentSoundTrack.open(soundFile);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playMusic(String filePath) {
        // stop previous music
        if(currentSoundTrack != null) currentSoundTrack.stop();

        // get audio file
        loadSoundTrack(filePath);
        setSound();

        // loop infinitely
        currentSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playSoundEffect(String filePath) {
        if(currentSoundTrack != null) currentSoundTrack.stop();
        
        loadSoundTrack(filePath);
        setSound();

        // sfx only plays once
        currentSoundTrack.start();
    }

    private void setSound() {
        if(currentSoundTrack == null) return;

        FloatControl floatControl = (FloatControl) currentSoundTrack.getControl(FloatControl.Type.MASTER_GAIN);
        float range = floatControl.getMaximum() - floatControl.getMinimum();
        float gain = (range * volumeLevel) + floatControl.getMinimum(); // current volume is a proportion of the total range
        floatControl.setValue(gain); // apply changes
    }

    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel;
        setSound();
    }
}
    
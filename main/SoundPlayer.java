package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

    private Clip currentSoundTrack;
    
    public SoundPlayer() {

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

        // loop infinitely
        currentSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playSoundEffect(String filePath) {
        currentSoundTrack.stop();
        
        loadSoundTrack(filePath);

        // sfx only plays once
        currentSoundTrack.start();
    }
}
    
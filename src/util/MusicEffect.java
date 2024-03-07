package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicEffect {
    private static Clip clip;
    private String fname;
    public MusicEffect(String fname)
    {
        this.fname=fname;
        File bgMusicFile = new File(fname);
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(bgMusicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playonce(boolean flag)
    {
        if(flag) {
            clip.start();
        }
        else
        {
            clip.stop();
            clip.setFramePosition(0);
        }

    }
    // 2.²¥·Å
    public  void play(){
        //Ñ­»·²¥·Å

        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }
}
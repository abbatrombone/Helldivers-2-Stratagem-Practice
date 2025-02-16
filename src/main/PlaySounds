package me.abbatrombone.traz;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Arrays;

public class PlaySound {

    public static void playRight(){}

    public static void playWrong(){
        File audiofile = new File("/home/trazodone/Music/WRONG.wav");
        new Thread(new Runnable() {
            public void run() {
                try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(String.valueOf(audiofile)).getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();

                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println("error: "+e.getMessage());
                    System.out.println(Arrays.toString(AudioSystem.getAudioFileTypes()));
                }
            }
        }).start();

    }

}

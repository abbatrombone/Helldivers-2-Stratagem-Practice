package me.abbatrombone.traz;

import org.apache.commons.lang3.SystemUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import static jdk.xml.internal.SecuritySupport.getClassLoader;

public class PlaySound {


    public static void playRight(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream is = getClass().getClassLoader().getResourceAsStream("Correct.wav");
                    assert is != null;

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(is);
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

    public static void playWrong(){
        //File audiofile = new File("/home/trazodone/Music/WRONG.wav");
        new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream is = getClass().getClassLoader().getResourceAsStream("WRONG.wav");
                    assert is != null;

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(is);
                    Clip clip = AudioSystem.getClip();
                    clip.open(inputStream);
                    clip.start();
                      /*
                    old way of coding this
                    use method found here: https://mkyong.com/java/java-read-a-file-from-resources-folder/
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(String.valueOf(audiofile)).getAbsoluteFile());
                    clip...
                    */
                } catch (Exception e) {
                    System.err.println("error: "+e.getMessage());
                    System.out.println(Arrays.toString(AudioSystem.getAudioFileTypes()));
                }
            }
        }).start();
    }
}

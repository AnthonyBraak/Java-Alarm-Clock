import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class AlarmClock implements Runnable{

    // External Variables
    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner scanner;

    // Constructor
    AlarmClock(LocalTime alarmTime, String filePath, Scanner scanner) {
        this.alarmTime = alarmTime;
        this.filePath = filePath;
        this.scanner = scanner;
    }

    // Start the timer after user has input the time
    @Override
    public void run(){
        while(LocalTime.now().isBefore(alarmTime)){
            try {
                // try this every second again
                Thread.sleep(1000);

                LocalTime now = LocalTime.now();

                // print out the time on the same line always in double numbers
                System.out.printf("\r%02d:%02d:%02d",
                        now.getHour(),
                        now.getMinute(),
                        now.getSecond());
            } catch (InterruptedException e){
                System.out.println("Thread was interrupted");
            }
        }
        System.out.println("TIME IS UP");
        playSound(filePath);
    }

    // play a sound designates in the main file until input is given
    private void playSound(String filePath){
        File audioFile = new File(filePath);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.println("Press Enter to stop the alarm: ");
            scanner.nextLine();
            clip.stop();
            scanner.close();
        } catch (UnsupportedAudioFileException e){
            System.out.println("Audiofile format not supported");
        } catch (LineUnavailableException e){
            System.out.println("Audio unavailable");
        } catch (IOException e){
            System.out.println("Error reading audio file");
        }
    }
}

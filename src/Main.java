import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime alarmTime = null;
        // set the path to the alarm sound
        String filePath = "music.wav";

        //wait until a valid time was put in
        while (alarmTime == null){
            try {
                System.out.print("HH:mm:ss  ");
                String inputTime = scanner.nextLine();

                alarmTime = LocalTime.parse(inputTime, formatter);
                System.out.println("Alarm set for " + alarmTime);
            } catch (DateTimeParseException e) {
                System.out.println("Wrong Format");;
            }
        }
        //start the second thread that runs the alarm clock
        Thread alarmThread = new Thread(new AlarmClock(alarmTime, filePath, scanner));
        alarmThread.start();
    }
}

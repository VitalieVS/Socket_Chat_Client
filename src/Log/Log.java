package Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;

public class Log {

    BufferedWriter out = null;

    public void openLog() {
        try {
            FileWriter fstream = new FileWriter("log.txt", true); //true tells to append data.
            out = new BufferedWriter(fstream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLog(String user, String message) {
        DateFormat date = DateFormat.getDateTimeInstance();
        try {
            out.write("[" + date.getCalendar().getTime() + "] " + user + message + "\n");
        } catch (Exception e) {
            System.out.println("unknown");
        }

    }

    public void closeLog(){
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

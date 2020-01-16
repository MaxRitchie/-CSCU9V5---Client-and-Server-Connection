import java.net.*;
import java.util.Date;
import java.io.*;

public class Connection extends Thread
{
    String this_port;
    String this_host;
    String next_host;
    int next_port;

    public Connection(Socket s, int next_port, String this_port, String next_host, String this_host)
    {
        this.next_host = next_host;
        this.next_port = next_port;
        this.next_host = next_host;
        this.this_host = this_host;
    }

    public void run()
    {
        // So enter the critical region
        //  ... record snap-shot on text file (our shared resource)
        try {
            System.out.println("Writing to file: record.txt");
            Date timestmp = new Date();
            String timestamp = timestmp.toString();
            // Next create fileWriter -true means writer *appends* 
            FileWriter fw_id = new FileWriter("record.txt", true);
            // Create PrintWriter -true = flush buffer on each println 
            // println means adds a newline (as distinct from print)
            PrintWriter pw_id = new PrintWriter(fw_id, true);
            pw_id.println(
                    "Record from ring node on host " + this_host + ", port number " + this_port + ", is " + timestamp);

            pw_id.close();
            fw_id.close();
        }
        catch (java.io.IOException e) {
            System.out.println("Error writing to file: " + e);
        }

        try {
            sleep(3000);
        }
        catch (java.lang.InterruptedException e) {
            System.out.println("sleep failed: " + e);
        }

        try {// connect to  next    node    in  the ring    - signals   passing the token.  
            Socket s = new Socket(next_host, next_port);

            if (s.isConnected()) //  Did it  connect OK? 
                System.out.println("Socket  to  next    node    (" + next_host + ":" + next_port + ")  connected   OK");
            else
                System.out.println("** Socket  to  next    ring    node    (" + next_host + ":  " + next_port
                        + ")  failed  to  connect");

            try {
                sleep(100);
            } //  a   short   delay   before  closing socket. 
            catch (java.lang.InterruptedException e) {
                System.out.println("sleep  fail:   " + e);
            }

            s.close(); //  token   now passed. 
            try {
                sleep(100);
            } //  another short   delay   
            catch (java.lang.InterruptedException e) {
                System.out.println("sleep  fail:   " + e);
            }

            if (s.isClosed())
                System.out.println(
                        "Socket  to  next    ring    node    (" + next_host + ":  " + next_port + ")  is  now closed");
            else
                System.out.println("** Socket  to  next    ring    node    (" + next_host + ":" + next_port
                        + ")  is  still   open!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //  end of  socket  try
    }
}

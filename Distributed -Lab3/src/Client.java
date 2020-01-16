import java.net.*;
import java.io.*;

public class Client
{
    int counter = 0;

    public Client(int server_port, String server_host)
    {
        do {
            try {

                Socket s = new Socket(server_host, server_port);

                //                InputStream in = s.getInputStream();
                //                
                //                DataInputStream dataInput = new DataInputStream(in); 
                //
                //                //BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                //
                //                //System.out.println(bin.readLine());
                //
                //                System.out.println(dataInput.readDouble());
                //                
                //                counter++;
                //                
                //                System.out.println("Counter: " + counter);
                //                
                //                s.close();
                //                
                //                Thread.sleep(500);

                // clear out file used by ring nodes
                while (true) {
                    System.out.println("Clearing record.txt file");

                    try {
                        // create fileWriter - false = new file so clear contents
                        FileWriter fw_id = new FileWriter("record.txt", false);
                        // that's it - all now cleaned up
                        fw_id.close();
                    }
                    catch (java.io.IOException e) {
                        System.err.println("Exception in clearing file: main: " + e);
                    }

                }
            }
            catch (java.io.IOException e) {
                System.out.println(e);
                System.exit(1);
            }
        }
        while (true);

    }

    public static void main(String args[])
    {
        if ((args.length < 1) || (args.length > 2)) {
            System.out.println("Usage: [this port][next host][next port]");
            System.out.println("Only " + args.length + " parameters entered");
            System.exit(1);
        }

        int server_port = 7000;
        String server_host = args[0];

        if (args.length == 2) {
            server_port = Integer.parseInt(args[1]);
        }

        Client client = new Client(server_port, server_host);
    }

}

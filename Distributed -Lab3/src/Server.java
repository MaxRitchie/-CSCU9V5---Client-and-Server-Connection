import java.net.*;

public class Server
{
    private ServerSocket s;
    private Socket client;
    private Connection c;

    public static void main(String argv[])
    {

        if ((argv.length < 3) || (argv.length > 3)) {
            System.out.println("Usage: [this port][next host][next port]");
            System.out.println("Only " + argv.length + " parameters entered");
            System.exit(1);
        }

        int this_port = Integer.parseInt(argv[0]);
        int next_port = Integer.parseInt(argv[2]);
        
        String next_host = argv[1]; 
        
        Server ring_host = new Server(this_port, next_host, next_port);
        
        //Server timeOfDayServer = new Server();
    } // end main 

    public Server(int this_port, String next_host, int next_port)
    {
        // create the socket the server will listen to 
        try {
            s = new ServerSocket(this_port);
        }
        catch (java.io.IOException e) {
            System.out.println(" " + e);
            System.exit(1);
        }

        System.out.println("Server is listening ....");

        do {
            // OK, now listen for connections and create them	
            try {

                client = s.accept();
                System.out.println("SERVER:  connection accepted\n\n");

                // create a separate thread to service the client
                c = new Connection(client, next_port, next_host, next_host, next_host);
                c.start();

                try {
                    Thread.sleep(500);
                }
                catch (Exception e) {
                    System.out.print(e);
                }
            }
            catch (java.io.IOException e) {
                System.out.println(e);
            }
        }
        while (true);
    }
}
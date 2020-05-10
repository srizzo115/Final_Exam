import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

/*
 * Author: Vallath Nandakumar and the EE 422C instructors.
 * Data: April 20, 2020
 * This starter code assumes that you are using an Observer Design Pattern and the appropriate Java library
 * classes.  Also using Message objects instead of Strings for socket communication.
 * See the starter code for the Chat Program on Canvas.  
 * This code does not compile.
 */
public class Server extends Observable {

    static Server server;
    private ArrayList<OutputStream> outputStreams;
    private ArrayList<String> items;
    private ArrayList<Customer> customers;
    
    public static void main (String [] args) {
        server = new Server();
        try {
			server.populateItems();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			server.SetupNetworking();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void populateItems() throws Exception {
		// TODO Auto-generated method stub
    	items = new ArrayList<String>();
    	File file = new File("Items.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file)); 
    	String st; 
    	while ((st = br.readLine()) != null) {
    		items.add(st);
    	}
    	System.out.println(items);
	}

	private void SetupNetworking() throws Exception{
        outputStreams = new ArrayList<OutputStream>();
		int port = 5000;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("ss port up");
            while (true) {
                Socket clientSocket = ss.accept();
                ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
                Thread t = new Thread(new ClientHandler(clientSocket, writer));
                t.start();
                addObserver(writer);
                System.out.println("got a connection");
            }
        } catch (IOException e) {}
    }

    class ClientHandler implements Runnable {
        private  ObjectInputStream reader;
        private  ClientObserver writer; // See Canvas. Extends ObjectOutputStream, implements Observer
        Socket clientSocket;

        public ClientHandler(Socket clientSocket, ClientObserver writer) {
			//...
        }

        public void run() {
			//...
        }
    } // end of class ClientHandler
}

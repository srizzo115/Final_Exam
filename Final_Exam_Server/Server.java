import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    	br.close();
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
                outputStreams.add(clientSocket.getOutputStream());
                System.out.println("got a connection");
            }
        } catch (IOException e) {}
    }

    class ClientHandler implements Runnable {
        private  BufferedReader reader;
        private  ClientObserver writer; // See Canvas. Extends ObjectOutputStream, implements Observer
        private Socket clientSocket;
        //private String clientName;

        public ClientHandler(Socket clientSocket, ClientObserver writer) {
        	try {
        		this.clientSocket = clientSocket;
            	this.writer = writer;
				reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				System.out.println("created clientHandler");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        public void run() {
			String message;
        	try {
				System.out.println("trying to read");
				while (((message) = reader.readLine()) != null) {
					
					System.out.println("clientHandler got message:" + message);
					if (message.startsWith("NewCustomer")) {
		
						String clientName = message.substring(12);
						Customer newCustomer = new Customer(clientName);
						customers.add(newCustomer);
						System.out.println("added " + newCustomer);
					}
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}
        }
    } // end of class ClientHandler
}

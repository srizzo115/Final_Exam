import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Author: Vallath Nandakumar and EE 422C instructors
 * Date: April 20, 2020
 * This starter code is from the MultiThreadChat example from the lecture, and is on Canvas. 
 * It doesn't compile.
 */

public class Client extends Application { 
	// I/O streams 
	ObjectOutputStream toServer = null; 
	ObjectInputStream fromServer = null;
	PrintWriter writer;
	
	//JavaFx
	private Stage primaryClientStage;
	private Stage loginStage;
	
	private String clientName;
	private ArrayList<String> items;
	

	@Override
	public void start(Stage arg0) throws Exception, IOException { 
		//BorderPane mainPane = new BorderPane(); 
		primaryClientStage = new Stage();
		loginStage = new Stage();
		
		//Create login screen
		GridPane loginGrid = new GridPane();
		
		//login text field
		TextField login = new TextField();
		login.setPrefWidth(150);
		login.setPromptText("Username");
		login.setPrefColumnCount(5);
		
		//login button
		Button loginButton = new Button("Log in");
		
		//guest button
		Button guestButton = new Button("Log in as Guest");
		
		loginGrid.add(login, 0, 0);
		loginGrid.add(loginButton, 1, 0);
		loginGrid.add(guestButton, 0, 1);
		
		Scene loginScene = new Scene(loginGrid);
		loginStage.setScene(loginScene);
		loginStage.show();
		
		
		/* //Create a scene and place it in the stage 
		Scene scene = new Scene(mainPane, 450, 200); 
		primaryStage.setTitle("Client"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 

		//XX.setOnAction(e -> { 
		//});  // etc.
		*/
		try { 
			// Create a socket to connect to the server 
			@SuppressWarnings("resource")
			Socket socket = new Socket("127.0.0.1", 5000); 
			System.out.println("connection made");
						
			// Create an output stream to send data to the server 
			toServer = new ObjectOutputStream(socket.getOutputStream());
			writer = new PrintWriter(socket.getOutputStream());
			
			// Create an input stream to receive data from the server 
			fromServer = new ObjectInputStream(socket.getInputStream()); 

		} 
		catch (IOException ex) { 
			System.out.println("no connection found");
			ex.printStackTrace();
		}
		
		//set up client screen
		GridPane clientGrid = new GridPane();
				
		//items dropdown
		items = new ArrayList<String>();
    	File file = new File("Items.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file)); 
    	String st; 
    	while ((st = br.readLine()) != null) {
    		items.add(st);
    	}
    	ComboBox<String> itemsDropDown = new ComboBox<String>();
		itemsDropDown.setPromptText("Auction Items");
		itemsDropDown.getItems().addAll(items);
		br.close();
		System.out.println(items);
		
		//Highest Bid Label
		Label highestBid = new Label("Highest Bid is ");
		
		//Past Bid
		Label pastBid = new Label("You're highest bid for this item is");
		
		//logout button
		Button logOut = new Button("Log out");
		
		//Enter Bid Text Field
		TextField enterBid = new TextField();
		enterBid.setPrefWidth(150);
		enterBid.setPromptText("Enter Bid");
		
		//Enter Bid button
		Button makeBid = new Button("Bid");
		
		//User label
		Label user = new Label(clientName);
		
		//Warning field
		TextArea warning = new TextArea();
		
		//add nodes to grid
		clientGrid.add(itemsDropDown, 0, 0);
		clientGrid.add(user, 1, 0);
		clientGrid.add(highestBid, 0, 1);
		clientGrid.add(pastBid, 0, 2);
		clientGrid.add(enterBid, 0, 3);
		clientGrid.add(makeBid, 1, 3);
		clientGrid.add(warning, 0, 4);
		clientGrid.add(logOut, 1, 4);
		
		Scene clientScene = new Scene(clientGrid);
		primaryClientStage.setScene(clientScene);
		//primaryClientStage.show();
		writer.println("NewCustomer Sam");
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				clientName = login.getText();
				System.out.println("client knows name: " + clientName);
				String message = "NewCustomer " + clientName;
				writer.println(message);
				loginStage.hide();
				primaryClientStage.show();
			}
			
		});
	}
	
	public static void main(String[] args) {
		Application.launch();
		try {
			new Client().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() {
		// TODO Auto-generated method stub
		
	}
}

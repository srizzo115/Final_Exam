import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

	//JavaFx
	private Stage primaryClientStage;
	private Stage loginStage;
	
	private String clientName;
	

	@Override
	public void start(Stage arg0) { 
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

			// Create an input stream to receive data from the server 
			fromServer = new ObjectInputStream(socket.getInputStream()); 

			// Create an output stream to send data to the server 
			toServer = new ObjectOutputStream(socket.getOutputStream()); 
		} 
		catch (IOException ex) { 
			System.out.println("no connection found");
			ex.printStackTrace();
		}
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

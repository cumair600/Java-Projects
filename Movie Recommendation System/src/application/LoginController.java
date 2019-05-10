package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.sun.org.apache.xml.internal.security.utils.I18n;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable{
	
	JDBC jdbc;
	KsOmk ksomk;

	@FXML AnchorPane rootPane;
	@FXML StackPane stackPane;
	@FXML Label closeButton;
	@FXML Button btnSignUp;
	@FXML Button btnSignIn;
	@FXML TextField edtEmail;
	@FXML TextField edtPassword;
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		ksomk = new KsOmk();
		jdbc = new JDBC();
		try {
			jdbc.connectDataBase();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List<User> users = jdbc.fetchUsers(ksomk);
			ksomk.users = users;
			List<Movie> movies = jdbc.fetchMovies(ksomk);
			ksomk.movies = movies;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void onClickClose(){
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void onClickSignUp(ActionEvent event) {

		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Signup.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
			
			//BrowseController controller = loader.getController();
			//u.ksomk = ksomk;
			//controller.initData((Viewer)u,ksomk);
			
			Stage stage = (Stage) btnSignUp.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClickSignIn(ActionEvent event) {
		
		for(User u : ksomk.users)
		{
			if(u.username.equals(edtEmail.getText().toString()) && u.password.equals(edtPassword.getText().toString()))
			{
				if(u.type.equalsIgnoreCase("Viewer"))
				{
					try {
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("Browse.fxml"));
						Parent parent = loader.load();
						
						Scene parentScene = new Scene(parent);
						
						BrowseController controller = loader.getController();
						u.ksomk = ksomk;
						controller.initData((Viewer)u,ksomk);
						
						Stage stage = (Stage) btnSignUp.getScene().getWindow();
						stage.setScene(parentScene);
						stage.show();
						
						return;
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(u.type.equalsIgnoreCase("Admin"))
				{
						try {
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("Admin.fxml"));
						Parent parent = loader.load();
						
						Scene parentScene = new Scene(parent);
						
						AdminController controller = loader.getController();
						controller.initData((Admin)u,ksomk);
						
						Stage stage = (Stage) btnSignUp.getScene().getWindow();
						stage.setScene(parentScene);
						stage.show();
						
						return;
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		JFXDialogLayout content = new JFXDialogLayout();
		Text t = new Text("Authentication Failed");
		t.setFill(Color.RED);
		content.setHeading(t);
		content.setBody(new Text("Invalid Username or Password! Please Try again."));
		JFXButton btn = new JFXButton("Okay");
		btn.setStyle("-fx-text-fill:RED");
		JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				dialog.close();
			}
		});
		content.setActions(btn);
		dialog.show();
	}
}

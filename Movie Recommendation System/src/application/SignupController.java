package application;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignupController implements Initializable{
	
	JDBC jdbc;
	KsOmk ksomk;
	
	@FXML StackPane stackPane;
	@FXML Label closeButton;
	@FXML JFXButton btnSignIn;
	@FXML JFXButton btnCreate;
	@FXML JFXTextField edtName;
	@FXML JFXTextField edtUsername;
	@FXML JFXTextField edtPassword;
	@FXML JFXTextField edtConfirm;
	@FXML JFXTextField edtAge;
	@FXML Label txtError;
	
	@FXML
	private void onClickClose(){
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void onClickSignIn(ActionEvent event) {

		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Login.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
			
			//BrowseController controller = loader.getController();
			//u.ksomk = ksomk;
			//controller.initData((Viewer)u,ksomk);
			
			Stage stage = (Stage) btnCreate.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClickCreate(ActionEvent event) {
		
		if(edtName.getText().isEmpty() || edtUsername.getText().isEmpty() || edtPassword.getText().isEmpty() || edtConfirm.getText().isEmpty() || edtAge.getText().isEmpty())
		{
			txtError.setText("All Fields must be filled!");
		}
		else if(!edtPassword.getText().toString().equals(edtConfirm.getText().toString()))
		{
			txtError.setText("Passwords does not match!");
		}
		else
		{
			txtError.setText("");
			Viewer v = new Viewer(edtName.getText().toString(),edtUsername.getText().toString(),edtPassword.getText().toString(),ksomk,Integer.parseInt(edtAge.getText().toString()));
			try {
				ksomk.addUser(v);
				
				stackPane.setVisible(true);
				
				JFXDialogLayout content = new JFXDialogLayout();
				Text t = new Text("Success");
				t.setFill(Color.GREEN);
				content.setHeading(t);
				content.setBody(new Text("Congratulations! Account Created Successfully."));
				JFXButton btn = new JFXButton("Okay");
				btn.setStyle("-fx-text-fill:Green");
				JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
				btn.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event)
					{
						dialog.close();
						try {
							Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
							Stage stage = (Stage) btnCreate.getScene().getWindow();
							
							Scene scene =  new Scene(root);
							
							stage.setScene(scene);
							stage.close();
							stage.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				content.setActions(btn);
				dialog.show();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		stackPane.setVisible(false);
		
		ksomk = new KsOmk();
		jdbc = new JDBC();
		
		try {
			jdbc.connectDataBase();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {
	
	KsOmk ksomk;
	JDBC jdbc;
	Admin admin;
	
	@FXML StackPane stackPane;
	
	@FXML TextField txtName;
	@FXML TextField txtYear;
	@FXML TextField txtLength;
	@FXML TextField txtGenre;
	@FXML TextField txtCast;
	@FXML TextField txtImage;
	@FXML TextArea txtDescription;
	
	@FXML Label txtError;
	
	@FXML Label closeButton;
	@FXML JFXButton btnAdd;
	@FXML JFXButton btnLogout;
	
	public void initData(Admin a,KsOmk k)
	{
		stackPane.setVisible(false);
		admin = a;
		ksomk = k;
		jdbc = new JDBC();
		try {
			jdbc.connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClickClose(){
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void onClickAdd()
	{
		if(txtName.getText().isEmpty() || txtYear.getText().isEmpty() || txtLength.getText().isEmpty() || txtGenre.getText().isEmpty() || txtCast.getText().isEmpty() || txtImage.getText().isEmpty() || txtDescription.getText().isEmpty())
		{
			txtError.setText("All fields must be filled!");
		}
		else
		{
			stackPane.setVisible(true);
			txtError.setText("");
			int count = ksomk.countMovies();
			String[] castTokens = txtCast.getText().toString().split(",");
			String[] genreTokens = txtGenre.getText().toString().split(",");
			Movie m = new Movie(count+1,txtName.getText().toString(),parseStrings(castTokens),parseStrings(genreTokens),txtDescription.getText().toString(),txtImage.getText().toString(),txtYear.getText().toString(),txtLength.getText().toString(),ksomk);
			admin.addMovie(m);
			
			JFXDialogLayout content = new JFXDialogLayout();
			Text t = new Text("Success");
			t.setFill(Color.GREEN);
			content.setHeading(t);
			content.setBody(new Text("Congratulations! Movie Added Successfully."));
			JFXButton btn = new JFXButton("Okay");
			btn.setStyle("-fx-text-fill:Green");
			JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
			btn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event)
				{
					dialog.close();
					txtName.setText("");
					txtYear.setText("");
					txtLength.setText("");
					txtGenre.setText("");
					txtCast.setText("");
					txtImage.setText("");
					txtDescription.setText("");
				}
			});
			content.setActions(btn);
			dialog.show();
		}
	}
	
	public List<String> parseStrings(String[] strings)
	{
		List<String> list = new ArrayList<String>();
		for(String s : strings)
		{
			list.add(s);
		}
		return list;
	}
	
	@FXML private void onClickLogout()
	{
		stackPane.setVisible(true);
		JFXDialogLayout content = new JFXDialogLayout();
		Text t = new Text("Logout");
		t.setFill(Color.RED);
		content.setHeading(t);
		content.setBody(new Text("Are you Sure?"));
		JFXButton btn = new JFXButton("Yes");
		btn.setStyle("-fx-text-fill:red");
		JFXButton btn2 = new JFXButton("No");
		btn2.setStyle("-fx-text-fill:red");
		JFXDialog dialog = new JFXDialog(stackPane,content,JFXDialog.DialogTransition.CENTER);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				dialog.close();
				try {
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("Login.fxml"));
					Parent parent = loader.load();
					
					Scene parentScene = new Scene(parent);
					
					Stage stage = (Stage) btnAdd.getScene().getWindow();
					stage.setScene(parentScene);
					stage.show();
					
					return;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				dialog.close();
			}
		});
		content.setActions(btn,btn2);
		dialog.show();
	}

}

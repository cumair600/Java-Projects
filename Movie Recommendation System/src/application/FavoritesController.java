package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FavoritesController {
	
	@FXML StackPane stackPane;
	
	Viewer viewer;
	@FXML JFXListView<String> list;
	@FXML JFXButton btnRemove;
	
	KsOmk ksomk;
	
	@FXML 
	Label btnClose, btnBack;
	
	public void initData(Viewer v,KsOmk k)
	{
		stackPane.setVisible(false);
		viewer = v;
		ksomk = k;
		for(Movie m : viewer.favorites)
		{
			list.getItems().add(m.name);
		}
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	@FXML private void onClickRemove()
	{
		stackPane.setVisible(true);
		JFXDialogLayout content = new JFXDialogLayout();
		Text t = new Text("Remove");
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
				
				String m = list.getSelectionModel().getSelectedItem();
				int id = getMovieId(m);
				list.getItems().remove(m);
				viewer.removeFromFav(id);	
				return;
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
	
	@FXML
	private void onClickClose()
	{
		Stage stage = (Stage) btnClose.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void onClickBack()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Browse.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
			
			BrowseController controller = loader.getController();
			controller.initData(viewer,ksomk);
			
			Stage stage = (Stage) btnRemove.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			return;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private int getMovieId(String n)
	{
		for(Movie m : viewer.favorites)
		{
			if(m.name.equalsIgnoreCase(n))
			{
				return m.id;
			}
		}
		return -1;
	}

}

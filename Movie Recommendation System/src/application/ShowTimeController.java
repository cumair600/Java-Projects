package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class ShowTimeController implements Initializable{
	
	Movie movie;
	Viewer viewer;
	KsOmk ksomk;
	
	MediaPlayer mediaplayer;
	
	@FXML 
	MediaView mediaPlayer;
	
	@FXML 
	Label txtMovieName, txtCast, txtGenre, txtDescription;
	
	@FXML 
	Label btnClose, btnBack;
	
	@FXML 
	private JFXButton btnPlay, btnStop;
	
	
	public void initData(Movie m, Viewer v, KsOmk k)
	{
		movie = m;
		viewer = v;
		ksomk = k;
		txtMovieName.setText(m.name);
		txtCast.setText(m.cast.toString());
		txtGenre.setText(m.genre.toString());
		txtDescription.setText(m.description);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		String path = new File("src/application/movies/got.mp4").getAbsolutePath();
		Media media = new Media(new File(path).toURI().toString());
		mediaplayer = new MediaPlayer(media);
		mediaPlayer.setMediaPlayer(mediaplayer);
	}
	
	@FXML 
	private void onClickPlay()
	{
		mediaplayer.play();
	}
	
	@FXML
	private void onClickStop()
	{
		mediaplayer.stop();
	}
	
	@FXML
	private void onClickClose()
	{
		Stage stage = (Stage) btnClose.getScene().getWindow();
		mediaplayer.stop();
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
			
			Stage stage = (Stage) btnBack.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			return;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

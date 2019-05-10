package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BrowseController implements Initializable{
	
	KsOmk ksomk;
	JDBC jdbc;
	int ids[] = new int[16];
	static int page = 0;
	
	Viewer viewer = new Viewer();
	
	@FXML JFXButton btnNext;
	@FXML JFXButton btnPrev;
	
	@FXML JFXButton btnFav;
	
	@FXML ImageView i1;
	@FXML Label m1;
	@FXML Label y1;
	@FXML JFXCheckBox f1;
	
	@FXML ImageView i2;
	@FXML Label m2;
	@FXML Label y2;
	@FXML JFXCheckBox f2;
	
	@FXML ImageView i3;
	@FXML Label m3;
	@FXML Label y3;
	@FXML JFXCheckBox f3;
	
	@FXML ImageView i4;
	@FXML Label m4;
	@FXML Label y4;
	@FXML JFXCheckBox f4;
	
	@FXML ImageView i5;
	@FXML Label m5;
	@FXML Label y5;
	@FXML JFXCheckBox f5;
	
	@FXML ImageView i6;
	@FXML Label m6;
	@FXML Label y6;
	
	@FXML ImageView i7;
	@FXML Label m7;
	@FXML Label y7;
	
	@FXML ImageView i8;
	@FXML Label m8;
	@FXML Label y8;
	
	@FXML ImageView i9;
	@FXML Label m9;
	@FXML Label y9;
	
	@FXML ImageView i10;
	@FXML Label m10;
	@FXML Label y10;
	
	@FXML StackPane stackPane;
	
	@FXML Label closeButton;
	@FXML JFXToggleButton btnNightMode;
	@FXML JFXButton btnLogOut;
	@FXML SplitPane splitPane;
	@FXML AnchorPane noRecPane;
	@FXML AnchorPane anchorPane1;
	@FXML AnchorPane anchorPane2;
	@FXML AnchorPane newMoviePane1;
	@FXML AnchorPane newMoviePane2;
	@FXML AnchorPane newMoviePane3;
	@FXML AnchorPane newMoviePane4;
	@FXML AnchorPane newMoviePane5;
	@FXML AnchorPane recMoviePane1;
	@FXML AnchorPane recMoviePane2;
	@FXML AnchorPane recMoviePane3;
	@FXML AnchorPane recMoviePane4;
	@FXML AnchorPane recMoviePane5;
	
	@FXML Label txtName;
	
	public void initData(Viewer v,KsOmk k)
	{
		stackPane.setVisible(false);
		viewer = v;
		ksomk = k;
		v.ksomk = ksomk;
		txtName.setText(v.name);
		
		noRecPane.setVisible(false);
		jdbc = new JDBC();
		
		try {
			jdbc.connectDataBase();
			viewer.favorites = jdbc.fetchFavorites(viewer.username, ksomk);
			
			loadData(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClickNext()
	{
		page++;
		if((page*5) < ksomk.movies.size())
			loadData(page*5);
	}
	
	@FXML private void onClickPrev()
	{
		if(page > 0)
		{
			page--;
			loadData(page*5);
		}
	}
	
	@FXML private void onClickFav()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Favorites.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
			
			FavoritesController controller = loader.getController();
			controller.initData(viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
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
	private void onClickLogOut(ActionEvent event) {
		
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
					
					Stage stage = (Stage) btnFav.getScene().getWindow();
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
	
	@FXML 
	private void onClickNightMode()
	{
		newMoviePane1.setStyle("-fx-border-color: red;");
		newMoviePane2.setStyle("-fx-border-color: red;");
		newMoviePane3.setStyle("-fx-border-color: red;");
		newMoviePane4.setStyle("-fx-border-color: red;");
		newMoviePane5.setStyle("-fx-border-color: red;");
		recMoviePane1.setStyle("-fx-border-color: red;");
		recMoviePane2.setStyle("-fx-border-color: red;");
		recMoviePane3.setStyle("-fx-border-color: red;");
		recMoviePane4.setStyle("-fx-border-color: red;");
		recMoviePane5.setStyle("-fx-border-color: red;");
		
		if(btnNightMode.isSelected()) {
			splitPane.setStyle("-fx-background-color: White;");
			anchorPane1.setStyle("-fx-background-color: White;");
			anchorPane1.setStyle("-fx-border-color: red;");
			anchorPane2.setStyle("-fx-background-color: White;");
			anchorPane2.setStyle("-fx-border-color: red;");
			noRecPane.setStyle("-fx-background-color: White;");
		}
		else 
		{
			splitPane.setStyle("-fx-background-color: Black;");
			anchorPane1.setStyle("-fx-background-color: Black;");
			anchorPane1.setStyle("-fx-border-color: red;");
			anchorPane2.setStyle("-fx-background-color: Black;");
			noRecPane.setStyle("-fx-background-color: Black;");
			anchorPane2.setStyle("-fx-border-color: red;");
		}
	}
	
	@FXML private void onClickFav1()
	{
		int id = ids[1];
		if(f1.isSelected())
		{
			viewer.addToFav(id);
		}
		else
		{
			viewer.removeFromFav(id);
		}
	}
	
	@FXML private void onClickFav2()
	{
		int id = ids[2];
		if(f2.isSelected())
		{
			viewer.addToFav(id);
		}
		else
		{
			viewer.removeFromFav(id);
		}
	}
	
	@FXML private void onClickFav3()
	{
		int id = ids[3];
		if(f3.isSelected())
		{
			viewer.addToFav(id);
		}
		else
		{
			viewer.removeFromFav(id);
		}
	}
	
	@FXML private void onClickFav4()
	{
		int id = ids[4];
		if(f4.isSelected())
		{
			viewer.addToFav(id);
		}
		else
		{
			viewer.removeFromFav(id);
		}
	}
	
	@FXML private void onClickFav5()
	{
		int id = ids[5];
		if(f5.isSelected())
		{
			viewer.addToFav(id);
		}
		else
		{
			viewer.removeFromFav(id);
		}
	}
	
	@FXML private void onClicki1()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[1]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki2()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[2]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki3()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[3]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki4()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[4]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki5()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[5]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki6()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[6]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki7()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[7]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki8()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[8]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki9()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[9]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML private void onClicki10()
	{
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ShowTime.fxml"));
			Parent parent = loader.load();
			
			Scene parentScene = new Scene(parent);
				
			ShowTimeController controller = loader.getController();
			Movie m = ksomk.getMovie(ids[10]);
			controller.initData(m,viewer,ksomk);
			
			Stage stage = (Stage) btnLogOut.getScene().getWindow();
			stage.setScene(parentScene);
			stage.show();
			
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	private void loadData(int count)
	{	
		Movie mo1 = ksomk.movies.get(count);
		count++;
		File file1 = new File(mo1.image);
        Image image1 = new Image(file1.toURI().toString());
        i1 = new ImageView(image1);
       // i1.setImage(image1);
        
        m1.setText(mo1.name);
        ids[count] = mo1.id; 
        y1.setText(mo1.year);
        
        Movie mo2 = ksomk.movies.get(count);
        count++;
		File file2 = new File(mo2.image);
        Image image2 = new Image(file2.toURI().toString());
        i2 = new ImageView(image2);
        
        m2.setText(mo2.name);
        ids[count] = mo2.id; 
        y2.setText(mo2.year);
        
        Movie mo3 = ksomk.movies.get(count);
        count++;
		File file3 = new File(mo3.image);
        Image image3 = new Image(file3.toURI().toString());
       // i3.setImage(image3);
        i3 = new ImageView(image3);
        
        m3.setText(mo3.name);
        ids[count] = mo3.id; 
        y3.setText(mo3.year);
        
        Movie mo4 = ksomk.movies.get(count);
        count++;
		File file4 = new File(mo3.image);
        Image image4 = new Image(file4.toURI().toString());
        //i4.setImage(image4);
        i4 = new ImageView(image4);
        
        m4.setText(mo4.name);
        ids[count] = mo4.id; 
        y4.setText(mo4.year);
        
        Movie mo5 = ksomk.movies.get(count);
        count++;
        File file5 = new File(mo5.image);
        Image image5 = new Image(file5.toURI().toString());
        //i5.setImage(image5);
        i5 = new ImageView(image5);
        
        m5.setText(mo5.name);
        ids[count] = mo5.id; 
        y5.setText(mo5.year);
        
        checkFavs();
        
        if(viewer.favorites.size() == 0)
        {
        	noRecPane.setVisible(true);
        }
        else
        {
        	List<Movie> recs = getRecommendations();
        	
        	Movie mo6 = recs.get(0);
    		File file6 = new File(mo6.image);
            Image image6 = new Image(file6.toURI().toString());
            //i6.setImage(image6);
            i6 = new ImageView(image6);
            
            
            m6.setText(mo6.name);
            ids[6] = mo6.id; 
            y6.setText(mo6.year);
            
            Movie mo7 = recs.get(1);
    		File file7 = new File(mo7.image);
            Image image7 = new Image(file7.toURI().toString());
           // i7.setImage(image7);
            i7 = new ImageView(image7);
            
            m7.setText(mo7.name);
            ids[7] = mo7.id; 
            y7.setText(mo7.year);
            
            Movie mo8 = recs.get(2);
    		File file8 = new File(mo8.image);
            Image image8 = new Image(file8.toURI().toString());
            //i8.setImage(image8);
            i8 = new ImageView(image8);
            
            m8.setText(mo8.name);
            ids[8] = mo8.id; 
            y8.setText(mo8.year);
            
            Movie mo9 = recs.get(3);
    		File file9 = new File(mo9.image);
            Image image9 = new Image(file9.toURI().toString());
           // i9.setImage(image9);
            i9 = new ImageView(image9);
            
            m9.setText(mo9.name);
            ids[9] = mo9.id; 
            y9.setText(mo4.year);
            
            Movie mo10 = recs.get(4);
    		File file10 = new File(mo10.image);
            Image image10 = new Image(file10.toURI().toString());
            //i10.setImage(image10);
            i10 = new ImageView(image10);
            
            m10.setText(mo5.name);
            ids[10] = mo10.id; 
            y10.setText(mo10.year);
        }
	}
	
	private void checkFavs()
	{
		int val = page*5;
		
		for(Movie m : viewer.favorites)
		{
			if(ids[1] == m.id)
			{
				f1.setSelected(true);
				break;
			}
			//else f1.setSelected(false);
		}
		
		for(Movie m : viewer.favorites)
		{
			if(ids[2] == m.id)
			{
				f2.setSelected(true);
				break;
			}
			//else f2.setSelected(false);
		}
		
		for(Movie m : viewer.favorites)
		{
			if(ids[3] == m.id)
			{
				f3.setSelected(true);
				break;
			}
			//else f3.setSelected(false);
		}
		
		for(Movie m : viewer.favorites)
		{
			if(ids[4] == m.id)
			{
				f4.setSelected(true);
				break;
			}
			//else f4.setSelected(false);
		}
		
		for(Movie m : viewer.favorites)
		{
			if(ids[5] == m.id)
			{
				f5.setSelected(true);
				break;
			}
			//else f5.setSelected(false);
		}
	}
	
	private List<Movie> getRecommendations()
	{
		List<Movie> recs = new ArrayList<>();
		
		String genre = viewer.favorites.get(page).genre.get(0);
		
		recs = ksomk.getMoviesByGenre(genre);
		
		return recs;
	}

}

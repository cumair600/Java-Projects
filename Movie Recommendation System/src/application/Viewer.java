package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Viewer extends User{

	int age;
	List<Movie> favorites;
	
	public Viewer(String name, String username, String password,String type, KsOmk ksomk,int dob) {
		super(name, username, password,type, ksomk);
		age = dob;
		favorites = new ArrayList<Movie>();
	}
	
	public Viewer(String name, String username, String password, KsOmk ksomk,int dob) {
		super(name, username, password, ksomk);
		age = dob;
		favorites = new ArrayList<Movie>();
	}
	
	public Viewer() {
		super();
		favorites = new ArrayList<Movie>();
	}

	public boolean addToFav(int mid)
	{
		JDBC jdbc = new JDBC();
		try {
			jdbc.addToFavorites(this.username, mid);
			Movie m = ksomk.getMovie(mid);
			favorites.add(m);
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean removeFromFav(int mid)
	{
		JDBC jdbc = new JDBC();
		try {
			jdbc.removeFromFavorites(this.username, mid);
			Movie m = ksomk.getMovie(mid);
			favorites.remove(m);
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
}

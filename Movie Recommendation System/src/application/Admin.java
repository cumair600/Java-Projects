package application;

import java.sql.SQLException;

public class Admin extends User{


	// Functions to be added
	
	public Admin(String name, String username, String password, KsOmk ksomk) {
		super(name, username, password, ksomk);
	}
	
	public Admin(String name, String username, String password, String type, KsOmk ksomk) {
		super(name, username, password,type, ksomk);
	}
	
	public boolean removeUser(String u)
	{
		JDBC jdbc = new JDBC();
		try {
			jdbc.removeUser(u);
			Viewer v = getUserByUsername(u);
			ksomk.users.remove(v);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Viewer getUserByUsername(String username)
	{
		for(User u : ksomk.users)
		{
			if(username == u.username)
				return (Viewer)u;
		}
		return null;
	}
	
	public boolean addMovie(Movie m)
	{
		JDBC jdbc = new JDBC();
		try {
			m.id = ksomk.countMovies() + 1;
			jdbc.addMovie(m);
			ksomk.movies.add(m);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeMovie(int id)
	{
		JDBC jdbc = new JDBC();
		try {
			jdbc.removeMovie(id);
			Movie m = getMovieById(id);
			ksomk.movies.remove(m);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

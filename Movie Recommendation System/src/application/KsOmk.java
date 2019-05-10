package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KsOmk {
	
	List<User> users;
	List<Movie> movies;
	
	public KsOmk()
	{
		users = new ArrayList<>();
		movies = new ArrayList<>();
	}
	
	public KsOmk(List<User> users, List<Movie> movies) {
		super();
		this.users = users;
		this.movies = movies;
	}
	
	public Movie getMovie(int id)
	{
		for(Movie m : movies)
		{
			if(m.id == id)
			{
				return m;
			}
		}
		return null;
	}
	
	public int countMovies()
	{
		return movies.size();
	}
	
	public boolean addUser(Viewer v)
	{
		JDBC jdbc = new JDBC();
		try {
			v.type = "Viewer";
			jdbc.addUser(v);
			users.add(v);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Movie> getMoviesByGenre(String genre)
	{
		List<Movie> list = new ArrayList<>();
		
		for(Movie m : movies)
		{
			if(list.size() == 5)
				return list;
			for(String g : m.genre)
			{
				if(list.size() == 5)
					return list;
				if(g.equalsIgnoreCase(genre))
				{
					list.add(m);
				}
			}
		}
		
		for(int i = list.size() ; i < 5 ; i++)
		{
			Movie m = movies.get(i);
			list.add(m);
		}
		return list;
	}
	
}

package application;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	String name;
	String username;
	String password;
	String type;
	KsOmk ksomk;
	
	public User(String name, String username, String password, KsOmk ksomk) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.ksomk = ksomk;
	}
	
	public User(String name, String username, String password, String type, KsOmk ksomk) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.type = type;
		this.ksomk = ksomk;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public Movie getMovieById(int id)
	{
		for(Movie m : ksomk.movies)
		{
			if(id == m.id)
				return m;
		}
		return null;
	}
	
}

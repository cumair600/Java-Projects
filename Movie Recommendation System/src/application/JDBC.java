package application;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class JDBC {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public void connectDataBase() throws Exception {
		  
		  
		  connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/KsOmk?autoReconnect=true&useSSL=false","root","root");
	}
	
	public List<User> fetchUsers(KsOmk k) throws SQLException
	{
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/KsOmk?autoReconnect=true&useSSL=false","root","root");
		
		List<User> users = new ArrayList<User>();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from Users");
		while(resultSet.next())
		{
			String name = resultSet.getString("_name");
			String username = resultSet.getString("_username");
			String password = resultSet.getString("_password");
			String type = resultSet.getString("_type");
			int age = resultSet.getInt("_age");
			if(type.equalsIgnoreCase("Admin"))
			{
				Admin a = new Admin(name,username,password,type,k);
				users.add(a);
			}
			else if(type.equalsIgnoreCase("Viewer"))
			{
				Viewer v = new Viewer(name,username,password,type,k,age);
				users.add(v);
			}
		}
		return users;
	}
	
	public List<Movie> fetchMovies(KsOmk k) throws SQLException
	{
		List<Movie> movies = new ArrayList<Movie>();
		statement = connect.createStatement();
		resultSet = statement.executeQuery("select * from Movies");
		while(resultSet.next())
		{
			int id = resultSet.getInt("_id");
			String name = resultSet.getString("_name");
			String description = resultSet.getString("_description");
			String year = resultSet.getString("_year");
			String length = resultSet.getString("_length");
			String cast = resultSet.getString("_cast");
			String genre = resultSet.getString("_genre");
			String image = resultSet.getString("_image");
			String[] castTokens = cast.split(",");
			String[] genreTokens = genre.split(",");
			List<String> casts = parseStrings(castTokens);
			List<String> genres = parseStrings(genreTokens);
			Movie m = new Movie(id,name,casts,genres,description,image,year,length,k);
			movies.add(m);
		}
		return movies;
	}
	
	public List<Movie> fetchFavorites(String u, KsOmk k) throws SQLException
	{
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/KsOmk?autoReconnect=true&useSSL=false","root","root");
		
		List<Movie> favs = new ArrayList<Movie>();
		statement = connect.createStatement();
		preparedStatement = connect.prepareStatement("Select * from Favorites where _username = ?");
		preparedStatement.setString(1, u);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next())
		{
			int id = resultSet.getInt("_mid");
			favs.add(k.getMovie(id));
		}
		return favs;
	}
	
	public boolean addUser(Viewer v) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("insert into Users values(?,?,?,?,?)");
		preparedStatement.setString(1, v.name);
		preparedStatement.setString(2, v.username);
		preparedStatement.setString(3, v.password);
		preparedStatement.setString(4, v.type);
		preparedStatement.setInt(5,v.age);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("User Added!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : User Cannot be Added!");
				 return false;
			 }
	}
	
	public boolean removeUser(String u) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("delete from Users where _username = ? ");
		preparedStatement.setString(1,u);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("User Removed!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : User Cannot be Deleted!");
				 return false;
			 }
	}
	
	public boolean addMovie(Movie m) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("insert into Movies values(?,?,?,?,?,?,?,?)");
		preparedStatement.setInt(1, m.id);
		preparedStatement.setString(2, m.name);
		preparedStatement.setString(3, m.description);
		preparedStatement.setString(4, m.year);
		preparedStatement.setString(5, m.length);
		String cast = listToString(m.cast);
		String genre = listToString(m.genre);
		preparedStatement.setString(6, cast);
		preparedStatement.setString(7, genre);
		preparedStatement.setString(8, m.image);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("Movie Added!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : Movie Cannot be Added!");
				 return false;
			 }
	}
	
	public boolean removeMovie(int id) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("delete from Movies where _id = ? ");
		preparedStatement.setInt(1,id);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("Movie Removed!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : Movie Cannot be Deleted!");
				 return false;
			 }
	}
	
	public boolean addToFavorites(String u,int mid) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("insert into Favorites values(?,?)");
		preparedStatement.setString(1, u);
		preparedStatement.setInt(2, mid);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("Added to Favorites!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : Cannot be Added to Fav!");
				 return false;
			 }
	}
	
	public boolean removeFromFavorites(String u, int mid) throws SQLException
	{
		try {
			connectDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		preparedStatement = connect.prepareStatement("delete from Favorites where _username = ? and _mid = ? ");
		preparedStatement.setString(1,u);
		preparedStatement.setInt(2,mid);
		 try {
			 preparedStatement.executeUpdate();
			 System.out.println("Removed from Fav!");
			 return true;
			 }
			 catch(Exception e)
			 {
				 System.out.println("Error : Cannot be Removed from Fav!");
				 return false;
			 }
	}
	
	public ResultSet getResultSet(String query) throws SQLException
	{
		  connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/KsOmk?autoReconnect=true&useSSL=false","root","root");
		  statement = connect.createStatement();
		  resultSet = statement.executeQuery(query);
		  return resultSet;
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
	
	public String listToString(List<String> list)
	{
		String result = "";
		for(String s : list)
		{
			if(!result.equalsIgnoreCase(""))
			{
				result = result + ",";
			}
			result = result + s;
		}
		return result;
	}
}


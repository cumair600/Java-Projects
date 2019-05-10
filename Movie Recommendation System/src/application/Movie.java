package application;

import java.util.List;

public class Movie {
	
	int id;
	String name;
	List<String> cast;
	List<String> genre;
	String description;
	String image;
	String year;
	String length;
	KsOmk ksomk;
	
	public Movie(String name, List<String> cast, List<String> genre, String description, String image, String year,String length, KsOmk ksomk) {
		super();
		this.name = name;
		this.cast = cast;
		this.genre = genre;
		this.description = description;
		this.image = image;
		this.year = year;
		this.length = length;
		this.ksomk = ksomk;
	}
	
	public Movie(int id, String name, List<String> cast, List<String> genre, String description,String image, String year,String length, KsOmk ksomk) {
		super();
		this.id = id;
		this.name = name;
		this.cast = cast;
		this.genre = genre;
		this.description = description;
		this.image = image;
		this.year = year;
		this.length = length;
		this.ksomk = ksomk;
	}
	

}

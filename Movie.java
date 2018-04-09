/**
 * Movie class represents each movie shown in a cinema 
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 */

public class Movie {
	public int cinemaID;
	public String time;
	public String movieName;
	
	/**
	 * Constructor for class Movie
	 * @param cinemaID : : primary key of the cinema
	 * @param time : the time at which the movie is shown
	 * @param movieName : name of the movie
	 */
	public Movie(int cinemaID, String time, String movieName) {
		this.cinemaID = cinemaID;
		this.time = time;
		this.movieName = movieName;
	}
	
	/**
	 * @return cinemaID where the movie is shown
	 * @postcondition returns cinemaID
	 */
	public int getCinemaID() {
		return this.cinemaID;
	}
	
	/**
	 * @return time when the movie is shown
	 * @postcondition returns time
	 */
	public String getTime() {
		return this.time;
	}
	
	/**
	 * @return movie name
	 * @postcondition returns movieName
	 */
	public String getMovie() {
		return this.movieName;
	}
}
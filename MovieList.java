/**
 * MovieList class manages a list of all Movies in all cinemas
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant movieList is unique
 */

import java.util.ArrayList;

public class MovieList {
    private ArrayList<Movie> movieList = new ArrayList<Movie>();

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @param movieName : name of the movie
     * @postcondition movie appended to movieList
     */
    public void addMovie(int cinemaID, String time, String movieName) {
        movieList.add(new Movie(cinemaID, time, movieName));
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @return empty string if no movie shown in cinemaID at the time, else name of the movie
     * @postcondition returns string   
     */
    public String findMovie(int cinemaID, String time) {
        String movie = "";
        for (Movie tmpM : movieList) {
            if (tmpM.getCinemaID() == cinemaID && tmpM.getTime().equals(time)) {
                movie = tmpM.getMovie();
            }
        }
        return movie;
    }
}
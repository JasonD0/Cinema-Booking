import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Cinema class provides a represenation of a cinema
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant timeSlots is unique
 */

public class Cinema {
    public int cinemaID;
    public ArrayList<String> movieList;
    public Map<String, Integer> timeSlots;

    /**
     * Constructor class for Cinema
     * @param id : unique identifier representing a cinema
     */
    public Cinema(int id) {
        this.cinemaID = id;
        this.timeSlots = new HashMap<String, Integer>();
        this.movieList = new ArrayList<String>();
    }

    /**
     * @return cinemaID
     * @postcondition returns cinemaID
     */
    public int getCinemaID() {
        return this.cinemaID;
    }

    /**
     * @param movie : name of the movie 
     * @postcondition adds movie to movieList shown in the cinema 
     */
    public void addMovie(String movie) {
        this.movieList.add(movie);
    }

    /**
     * @precondition maximum number of movies shown at any time in the cinema is 1
     * @param time : time the movie is shown in the cinema
     * @param seatsID : primary key for set of seats for the movie
     * @postcondition links set of seats for the time in the cinema
     */
    public void setTimeSlots(String time, int seatsID) {
        this.timeSlots.put(time, seatsID);
    }

    /**
     * @param time : time the movie is shown in the cinema
     * @return set of seats associated with time of a movie
     * @postcondition returns seatsID
     */
    public int getSeatsID(String time) {
        return this.timeSlots.get(time);
    }
}
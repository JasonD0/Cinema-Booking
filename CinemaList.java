import java.util.ArrayList;

/**
 * CinemaList class manages a list of all cinemas 
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant cinemaList is unique
 */

public class CinemaList {
    private ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
    private ArrayList<Integer> existingCinema = new ArrayList<Integer>();

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @return seatsID if cinema exists, 0 otherwise 
     * @postcondition returns an integer
     */
    public int getSeatID(int cinemaID, String time) {
    	// search for the id for the set of seats in the cinema at time
        for (Cinema tmpC : cinemaList) {
            if (tmpC.getCinemaID() == cinemaID) {
                return tmpC.getSeatsID(time);
            }
        }
        return 0;
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @postcondition checks if cinema is already created
     */
    public void addCinema(int cinemaID) {
    	// add new cinema if it hasn't been created
    	if (!existingCinema.contains(cinemaID)) {
            Cinema tmpCinema = new Cinema(cinemaID);
            cinemaList.add(tmpCinema);
            existingCinema.add(cinemaID);
        }
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param movieName : name of the movie
     * @param seatsID : primary key for set of seats for the movie
     * @param time : time the movie is shown in the cinema
     */
    public void initialiseMovieInCinema(int cinemaID, String movieName, int seatsID, String time) {
    	for (Cinema tmpC : cinemaList) {
            if (tmpC.getCinemaID() == cinemaID) {
                tmpC.addMovie(movieName);
                // links movie with the id for the set of seats in cinemaID at time
                tmpC.setTimeSlots(time, seatsID);
            }
        }
    }
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * MovieSeatList class manages a list of each set of seats for each movie
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant seatsList is unique
 */

public class MovieSeatList {
    private ArrayList<Seats> seatsList = new ArrayList<Seats>();
    private Map<String, String> rowsAndSeats = new HashMap<String, String>();
    
    /**
     * @param seatID : primary key for set of seats for the movie
     * @param bookingID : primary key for a customer booking
     */
    public void findAndDeassignSeats(int seatID, int bookingID) {
        for (Seats tmpS : seatsList) {
            if (seatID == tmpS.getSeatsID()) {
                tmpS.deassignSeats(bookingID);
            }
        }
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @param movieName : name of the movie
     * @param seatsID : primary key for set of seats for the movie
     * @postcondition new seats are initialised and added to seatsList
     */
    public void addSeats(int cinemaID, String time, String movieName, int seatsID) {
        Seats tmpSeats = new Seats(cinemaID, seatsID);
        tmpSeats.initCinemaSeats(rowsAndSeats.get(String.valueOf(cinemaID)));
        seatsList.add(tmpSeats);
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param row : primary key of the row of seats in the cinema
     * @param numSeats : number of seats in the row
     * @postcondition row and its number of seats are saved
     */
    public void saveCinemaRowsAndSeats(int cinemaID, String row, int numSeats) {
        if (rowsAndSeats.containsKey(String.valueOf(cinemaID))) {
        	// concatenate exisiting string of row and its seats for cinemaID with another set of row and it's number of seats
        	rowsAndSeats.put(String.valueOf(cinemaID), rowsAndSeats.get(String.valueOf(cinemaID)) + " " + row + " " + numSeats);
        } else {
        	// add new string of row and its seats for cinemaID
        	rowsAndSeats.put(String.valueOf(cinemaID), row + " " + numSeats);
        }
    }

    /**
     * @param time : time the movie is shown in the cinema
     * @param movie : name of the movie
     * @param seatID : primary key for set of seats for the movie
     */
    public void printMovieSeatBookings(String time, String movie, int seatID) {
    	// find set of seats with seatID and print it's booked seats
    	for (Seats tmpS : seatsList) {
            if (seatID == tmpS.getSeatsID()) {
                System.out.print(movie);
                tmpS.printBookedSeats();
                return;
            }
        }
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param bookingID : primary key for a customer booking
     * @param time : time the movie is shown in the cinema
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made 
     * @param seatsID : primary key for set of seats for the movie
     * @param changeType : increase for increasing tickets booked, decrease for decreasing booked
     * @return true if seats successfully set, false otherwise
     * @postcondition checks if enough seats can be allocated
     */
    public boolean setMovieSeats(int cinemaID, int bookingID, String time, int tickets, String bookingType, int seatID, String changeType) {
        for (Seats tmpS : seatsList) {
        	// try setting seats for the movie at time
            if (seatID == tmpS.getSeatsID()) {
                if (!tmpS.setSeats(bookingID, tickets, bookingType, changeType)) return false;
            }
        }
        return true;
    }

    /**
     * @param bookingID : primary key for a customer booking
     * @param seatID : primary key for set of seats for the movie
     * @return empty string if seatID doesnt exist, row otherwise
     * @postcondition string is returned
     */
    public String getSeatRow(int bookingID, int seatID) {
    	// get row in the set of seats booked by bookingID
        for (Seats tmpS : seatsList) {
            if (seatID == tmpS.getSeatsID()) {
                return tmpS.getRow(bookingID);
            }
        }
        return "";
    }
}
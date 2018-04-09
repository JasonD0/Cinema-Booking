/**
 * Booking class handles instructions given from the input text file 
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 */

public class Booking {
    private TicketList ticketList;
    private MovieList movieList;
    private CinemaList cinemaList;
    private MovieSeatList movieSeatList;

    /**
     * Constructor for Booking class
     */
    public Booking() {
        ticketList = new TicketList();
        movieList = new MovieList();
        cinemaList = new CinemaList();
        movieSeatList = new MovieSeatList();
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param row : primary key of the row of seats in the cinema
     * @param numSeats : number of seats in the row
     * @postcondition new cinema created and added to cinemaList, row and numSeats saved 
     */
    public void initialiseCinema(int cinemaID, String row, int numSeats) {
        cinemaList.addCinema(cinemaID);
        movieSeatList.saveCinemaRowsAndSeats(cinemaID, row, numSeats);
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @param movieName : name of the movie
     * @param seatsID : primary key for set of seats for the movie
     * @postcondition set of seats initialised for movie, movie added to movieList and into the cinema
     */
    public void initialiseSessions(int cinemaID, String time, String movieName, int seatsID) {
        movieSeatList.addSeats(cinemaID, time, movieName, seatsID);
        movieList.addMovie(cinemaID, time, movieName);
        cinemaList.initialiseMovieInCinema(cinemaID, movieName, seatsID, time);
    }

    /**
     * @precondition bookingType is "request"
     * @param cinemaID : primary key of the cinema
     * @param bookingID : primary key for a customer booking
     * @param time : time the movie is shown in the cinema
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made  
     * @param changeType : increase for increasing tickets booked, decrease for decreasing booked
     * @return true if enough adjacent seats available, otherwise false
     * @postcondition checks if enough seats available
     */
    public boolean requestBooking(int cinemaID, int bookingID, String time, int tickets, String bookingType, String changeType) {
        int seatID = 0;
        seatID = cinemaList.getSeatID(cinemaID, time);
        if (!movieSeatList.setMovieSeats(cinemaID, bookingID, time, tickets, bookingType, seatID, changeType)) return false;
        ticketList.addTicket(cinemaID, time, bookingID, tickets, movieSeatList.getSeatRow(bookingID, seatID));
        return true;
    }

    /**
     * @precondition bookingType is "change"
     * @param bookingID : primary key for a customer booking
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made  
     * @postcondition checks if bookingID has a booking
     */
    public void changeBooking(int bookingID, int cinemaID, String time, int tickets, String bookingType) {
        int seatID = 0;
        boolean ticketIncreased = false, ticketDecreased = false;
        if (!ticketList.checkBookingExist(bookingID)) {
        	System.out.print("Change rejected");
        	return;
        }
        String[] cinemaTime = ticketList.getCinemaTime(bookingID);
        if (ticketList.getNumTickets(bookingID) <= tickets && cinemaTime[1].equals(time) && cinemaTime[0].equals(String.valueOf(cinemaID))) {
        	ticketIncreased = requestBooking(cinemaID, bookingID, time, tickets, bookingType, "increase");
        }
        if (ticketList.getNumTickets(bookingID) > tickets && cinemaTime[1].equals(time) && cinemaTime[0].equals(String.valueOf(cinemaID))) {
        	ticketDecreased = requestBooking(cinemaID, bookingID, time, tickets, bookingType, "decrease");
        }
        if (ticketIncreased == false && ticketDecreased == false && !requestBooking(cinemaID, bookingID, time, tickets, bookingType, "")) return;
        // old bookings is closer to start of list
        ticketList.removeTicket(bookingID);
        if (ticketIncreased || ticketDecreased) return; 
        seatID = cinemaList.getSeatID(Integer.parseInt(cinemaTime[0]), cinemaTime[1]);
        movieSeatList.findAndDeassignSeats(seatID, bookingID);
    }

    /**
     * @param bookingID : primary key for a customer booking
     * @postcondition checks if bookingID has a booking
     */
    public void cancelBooking(int bookingID) {
        if (!ticketList.checkBookingExist(bookingID)) {
            System.out.print("Cancel rejected");
            return;
        }
        String[] cinemaTime = ticketList.getCinemaTime(bookingID);
        int seatID = cinemaList.getSeatID(Integer.parseInt(cinemaTime[0]), cinemaTime[1]);
        movieSeatList.findAndDeassignSeats(seatID, bookingID);
        ticketList.removeTicket(bookingID);
        System.out.print("Cancel " + bookingID);
    }

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @postcondition prints all seat bookings in each row and the movie shown in given cinema and time
     */
    public void printBookings(int cinemaID, String time) {
        int seatID = cinemaList.getSeatID(cinemaID, time);
        String movie = movieList.findMovie(cinemaID, time);
        movieSeatList.printMovieSeatBookings(time, movie, seatID);
    }
}
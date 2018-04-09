/**
 * Ticket class represents each booking's deatils
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 */

public class Ticket {
    public int cinemaID;
    public String time;
    public int bookingID;
    public int tickets;
    public String row;

    /**
     * Constructor Class for Ticket
     * @param cinemaID : : primary key of the cinema
     * @param time : time of the movie booked
     * @param bookingID : primary key for a customer booking
     * @param tickets : the number of seats booked
     * @param row : the row in which the seats were allocated
     */
    public Ticket(int cinemaID, String time, int bookingID, int tickets, String row) {
        this.cinemaID = cinemaID;
        this.time = time;
        this.bookingID = bookingID;
        this.tickets = tickets;
        this.row = row;
    }

    /**
     * @return cinemaID for the booking
     */
    public int getCinemaID() {
        return this.cinemaID;
    }

    /**
     * @return time for the booking
     * @postcondition returns time booked
     */
    public String getTime() {
        return this.time;
    }

    /**
     * @return bookingID identifying the customer 
     * @postcondition returns bookingID for the ticket
     */
    public int getBookingID() {
        return this.bookingID;
    }

    /**
     * @return row of the seats booked
     * @postcondition returns row of the seats booked
     */
    public String getRow() {
        return this.row;
    }

    /**
     * @return number of tickets booked
     * @postcondition returns number of tickets booked
     */
    public int getNumTickets() {
        return this.tickets;
    }
}
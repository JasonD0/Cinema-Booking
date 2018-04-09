import java.util.ArrayList;
import java.util.Iterator;

/**
 * TicketList class manages list of all bookings in the cinema booking system
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant ticketList is unique
 */

public class TicketList {
    private ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

    /**
     * @param cinemaID : primary key of the cinema
     * @param time : time the movie is shown in the cinema
     * @param bookingID : primary key for a customer booking
     * @param tickets : number of seats booked
     * @param row : primary key of the row of seats in the cinema
     * @postcondition new ticket added to ticketList
     */
    public void addTicket(int cinemaID, String time, int bookingID, int tickets, String row) {
        ticketList.add(new Ticket(cinemaID, time, bookingID, tickets, row));
    }

    /**
     * @precondition oldest ticket having lower index in list
     * @param bookingID : primary key for a customer booking
     */
    public void removeTicket(int bookingID) {
        Iterator<Ticket> it = ticketList.iterator();
        // find ticket for bookingID and remove it 
        while (it.hasNext()) {
            Ticket tmpT = (Ticket)it.next();
            if (bookingID == tmpT.getBookingID()) {
                it.remove();
                break;
            }
        }
    }

    /**
     * @param bookingID : primary key for a customer booking
     * @return true if booking exists, else false
     * @postcondition checks if booking exists
     */
    public boolean checkBookingExist(int bookingID) {
        for (Ticket tmpT: ticketList) {
            if (bookingID == tmpT.getBookingID()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param bookingID : primary key for a customer booking
     * @return -1 if bookingID hasn't made a booking, else return number of tickets booked
     * @postcondition returns integer
     */
    public int getNumTickets(int bookingID) {
        for (Ticket tmpT: ticketList) {
            if (bookingID == tmpT.getBookingID()) {
                return tmpT.getNumTickets();
            }
        }
        return -1;
    }

    /**
     * @param bookingID : primary key for a customer booking
     * @return array of -1 if booking exists, else the cinema and time booked  
     * @postcondition returns string array
     */
    public String[] getCinemaTime(int bookingID) {
        String[] result = {"-1","-1"};
        // search for ticket with bookingID
        for (Ticket tmpT: ticketList) {
            if (tmpT.getBookingID() == bookingID) {
                result[0] = String.valueOf(tmpT.getCinemaID());
                result[1] = tmpT.getTime();
                break;
            }
        }
        return result;
    }
}
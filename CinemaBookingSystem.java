import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CinemaBookingSystem class reads and filters the input text file as a set of instructions
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 */

// assume session, cinema valid
// rows in cinema in order ie front to 2nd to 3rd to back row
// booking id unique (id)
public class CinemaBookingSystem {

	/**
	 * Main method for the cinema booking system
	 * @param args : 1 text file
	 */
    public static void main(String[] args) {
        File input = new File(args[0]); // reads in text file
        Scanner sc = null;
        Booking bookingProcessor = new Booking();
        try {
            // reads input text file containing cinema booking system instructions 
            sc = new Scanner(input);
            int i = 0, firstPrint = 1;
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                // ignore empty lines or lines starting with "#"
                if (line.length == 0 || line == null || (line[0].trim()).equals("") || (line[0]).contains("#")) continue;
                if (firstPrint != 1) System.out.println();
                switch (line[0]) {
                    case "Cinema":
                        bookingProcessor.initialiseCinema(Integer.parseInt(line[1]), line[2], Integer.parseInt(line[3]));
                        break;
                    case "Session":
                        bookingProcessor.initialiseSessions(Integer.parseInt(line[1]), line[2], line[3], ++i);
                        break;
                    case "Request":
                    	firstPrint = 0;
                        bookingProcessor.requestBooking(Integer.parseInt(line[2]), Integer.parseInt(line[1]), line[3], Integer.parseInt(line[4]), "Booking", "");
                        break;
                    case "Change":
                    	firstPrint = 0;
                        bookingProcessor.changeBooking(Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], Integer.parseInt(line[4]), "Change");
                        break;
                    case "Cancel":
                    	firstPrint = 0;
                        bookingProcessor.cancelBooking(Integer.parseInt(line[1]));
                        break;
                    case "Print":
                    	firstPrint = 0;
                        bookingProcessor.printBookings(Integer.parseInt(line[1]), line[2]);
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) sc.close();
        }
    }
}
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Seats class provides a representation of each set of seats for each movie in a cinema and inherits from Cinema class
 * @author Jason Do z5159932
 * COMP2511
 * Assignment 1 Cinema Booking System
 * @invariant cinemaSeats is unique, bookingID is unique
 */

public class Seats extends Cinema {
    public int seatsID;
    private Map<String, int[]> cinemaSeats = new LinkedHashMap<String, int[]>();

    /**
     * Constructor class for Seats
     * @param id : unqiue identifier for the cinema in which the current set of seats is in 
     * @param seatsID : unique identifier for the set of seats for a particular movie
     */
    public Seats(int id, int seatsID) {
        super(id);
        this.seatsID = seatsID;
    }

    /**
     * @return seatsID 
     * @postcondition returns seatsID 
     */
    public int getSeatsID() {
        return this.seatsID;
    }
    
    /**
     * @precondition rowAndSeats % 2 == 0, row is before seats in string 
     * @param rowsAndSeats : row and its seats  
     * @postcondition seats and rows are initialised, and added to cinemaSeats
     */
    public void initCinemaSeats(String rowsAndSeats) {
        String[] rowAndSeat = rowsAndSeats.split(" ");
        // loops through string and get the row and it's seats
        for (int j = 0; j < rowAndSeat.length; j += 2) {
            String row = rowAndSeat[j];
            int numSeats = Integer.parseInt(rowAndSeat[j + 1]);
            int[] seats = new int[numSeats + 1];
            // pretend first index doesnt exist, sets all seats to zero
            seats[0] = -1;
            for (int i = 1; i <= numSeats; i++) {
                seats[i] = 0;
            }
            this.cinemaSeats.put(row, seats);
        }
    }

    /**
     * @precondition booked seats in seats array are set as the bookingID
     * @param bookingID : primary key for a customer booking
     * @return empty string if bookingID has seats booked, the row booked by bookingID otherwise
     * @postcondition returns String
     */
    public String getRow(int bookingID) {
    	// find row with a value in seats array equal to bookingID 
        for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
            int[] seats = rows.getValue();
            String row = rows.getKey();
            for (int i = 1; i < seats.length; i++) {
                if (seats[i] == bookingID) return row;
            }
        }
        return "";
    }

    /**
     * @precondition booked seats in seats array are set as the bookingID
     * @param bookingID : primary key for a customer booking
     */
    public void deassignSeats(int bookingID) {
        int i = 0, flag = 0;
        // iterates through cinema rows and deassigns all seats in seats array with value bookingID
        for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
            int[] seats = rows.getValue();
            for (i = 1; i < seats.length; i++) {
                if (seats[i] != bookingID && flag > 0) return;
                if (seats[i] == bookingID) {
                	seats[i] = 0;
                	flag++;
                }
            }
        }
    }

    /**
     * @precondition changeType is increase if existingcustomer increased tickets for same cinema and time, 
     *                             decrease if existing customer decreased tickets for same cinema and time
     * @param bookingID : primary key for a customer booking
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made  
     * @param changeType : increase for increasing tickets booked, decrease for decreasing booked
     * @return true if seats successfully set, false otherwise
     * @postcondition checks if booking of seats valid, prints a message for successful seat setting and for unsuccesful seat setting 
     */
    public boolean setSeats(int bookingID, int tickets, String bookingType, String changeType) {
        // iterates through cinema rows and marks seats with bookingID
        for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
            int[] seats = rows.getValue();
            String row = rows.getKey();
            // try set extra adjacent seats for existing customer
            if (changeType.equals("increase")) {
            	increaseSeats(bookingID, tickets, bookingType); 
            	return true;
            // reduce number of seats booked for existing customer
            } else if (changeType.equals("decrease")) {
            	decreaseSeats(bookingID, tickets, bookingType);
            	return true;
            }
            // set new sets of seats
            int seatIndex = findSeats(seats, tickets);
            if (seatIndex == -1) continue;
            initialiseBookedSeats(bookingID, tickets, seatIndex, seats, bookingType, row);
            return true;
        }
        printBookedSeatsDetails(0, 0, 0, "0", 1, bookingType); // prints 'bookingType' rejected
        return false;
    }
    
    /**
     * helper method for setSeats
     * @param seats : array representing seats in a row
     * @param tickets : number of seats to be booked
     * @return -1 if not enough seats available, else start index of the set of empty seats for seats booked   
     * @postcondition checks if enough seats available, returns integer
     */
    private int findSeats(int[] seats, int tickets) {
    	for (int i = 1; i < seats.length; i++) {
    		if (seats[i] != 0) continue;
    		// check if available seats after current free seat
    		for (int j = i, openSeats = 0; j < seats.length; j++) {
    			if (seats[j] != 0) break;
    			openSeats++;
    			if (openSeats == tickets) return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * helper method for setSeats
     * @param firstSeat : index of first booked seat of a bookingID
     * @param lastSeat : index of last booked seat of a bookingID 
     * @param bookingID : primary key for a customer booking
     * @param row : primary key of the row of seats in the cinema
     * @param rejected : equals 1 if seats can be allocated
     * @param bookingType : type of booking made
     * @postcondition print message detailing the booking of seats 
     */
    private void printBookedSeatsDetails(int firstSeat, int lastSeat, int bookingID, String row, int rejected, String bookingType) {
    	if (rejected == 1) System.out.print(bookingType + " rejected"); // prints 'bookingType' rejected
    	else if (firstSeat == lastSeat) System.out.print(bookingType + " " + bookingID + " " + row + firstSeat); //prints row and the single seat booked
    	else System.out.print(bookingType + " " + bookingID + " " + row + firstSeat + "-" + row + lastSeat); //prints row and the multiple seats booked
    }
    
    /**
     * @param bookingID : primary key for a customer booking
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made
     * @return true if succesfully increase number of seats for bookingID, else false
     * @postcondiion return boolean
     */
    public boolean increaseSeats(int bookingID, int tickets, String bookingType) {
    	int i = 0, seatsReserved = 0;
    	// iterates through cinema rows 
    	for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
    		int[] seats = rows.getValue();
    		String row = rows.getKey();
    		for (i = 1, seatsReserved = 0; seatsReserved < tickets && i < seats.length; i++) {
    			// check if empty seats after booked seats
    			if (seats[i] == bookingID || (seatsReserved > 0 && seats[i] == 0)) {
    				seatsReserved++;
    			} else {
    				seatsReserved = 0;
    			}
    			if (seatsReserved == tickets) {
    				initialiseBookedSeats(bookingID, tickets, (i - tickets + 1), seats, bookingType, row); // increase seats for bookingID
    				return true;
    			}
    		}
    	}
    	printBookedSeatsDetails(0, 0, 0, "0", 1, bookingType); // prints 'bookingType' rejected
    	return false;
    }
    
    /**
     * helper method for increaseSeats and setSeats
     * @precondition seats available
     * @param bookingID : primary key for a customer booking
     * @param tickets : number of seats to be booked
     * @param seatIndex : start index to begin setting seats for bookingID
     * @param seats : array representing seats in a row
     * @param bookingType : type of booking made
     * @param row : primary key of the row of seats in the cinema
     * @post prints row and seats booked, set seats for bookingID
     */
    private void initialiseBookedSeats(int bookingID, int tickets, int seatIndex, int[] seats, String bookingType, String row) {
    	int i, seatsReserved;
    	// set seats to bookingID
    	for (i = seatIndex, seatsReserved = 0; seatsReserved < tickets; i++, seatsReserved++) {
            seats[i] = bookingID;    
        }
    	printBookedSeatsDetails((i - tickets), (i - 1), bookingID, row, 0, bookingType); // prints row and seats booked
    }
    
    /**
     * @precondition seats for bookingID were all adjacent
     * @param bookingID : primary key for a customer booking
     * @param tickets : number of seats to be booked
     * @param bookingType : type of booking made
     * @postcondition reduce number of booked seats, prints message 
     */
    public void decreaseSeats(int bookingID, int tickets, String bookingType) {
        int i = 0, seatsReserved = 0, startDecreasing = 0, startIndex = 0, endIndex = 0;
        // iterates through cinema rows 
        for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
            int[] seats = rows.getValue();
            String row = rows.getKey();
            for (i = 1, seatsReserved = 0; i < seats.length; i++) {
            	if (startDecreasing != 0 && seats[i] != bookingID) break; 
            	if (startDecreasing != 0 && seats[i] == bookingID) seats[i] = 0; // remove extra seats for BookingID
            	// set conditions to start removing extra seats for bookingiD and save start and end index for seats array
            	if (seats[i] == bookingID) {
            		seatsReserved++;
            		if (seatsReserved == 1) startIndex = i;
            	}
            	if (seatsReserved == tickets && startDecreasing == 0) {
            		startDecreasing++;
            		endIndex = i;
            	}
            }
            if (startDecreasing != 0) {
            	printBookedSeatsDetails(startIndex, endIndex, bookingID, row, 0, bookingType); // prints row and seats booked
            	return;
            }
        }
    }


    /**
     * prints all booked seats in the cinema
     */
    public void printBookedSeats() {
        // iterates through cinema rows 
    	String bookedSeats = "";
        for (Map.Entry<String, int[]> rows : cinemaSeats.entrySet()) {
            int[] seats = rows.getValue();
            String row = rows.getKey();
            bookedSeats = getBookedSeats(seats); // get booked seats in the row
            System.out.print((bookedSeats != "") ? "\n" + row + ": " + bookedSeats : "");
        }
    }
    
    /**
     * helper method for printBookedSeats
     * @param seats : : array representing seats in a row
     * @return empty string if no seats booked, else the start and end index for all bookedIDs in the set of seats in the row
     * @postcondition returns string of bookedIDs in a row
     */
    private String getBookedSeats(int[] seats) {
        int bookingID = 0, j = 0, i = 0, flag = 0;
        String seatsTaken = "";
        // loops through seats array for a row and prints all booked seats
        for (i = 1; i < seats.length; i++) {
            if (seats[i] == 0) continue;
            if (flag != 0) seatsTaken += ",";
            bookingID = seats[i];
            for (j = i; j < seats.length; j++) {
                if (seats[j] != bookingID) break;
            }
            seatsTaken += (j == i + 1) ? i : i + "-" + (j - 1);
            i = j - 1;
            flag++;
        }
        return seatsTaken;
    }

}
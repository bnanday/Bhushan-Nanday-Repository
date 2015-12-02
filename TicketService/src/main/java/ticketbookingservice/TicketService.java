package ticketbookingservice;

import auditorium.Level;

/**
 *
 * @author bnanday
 */


/*
 * This is the main interface which is being implemented by the Ticket Service.
 * */
public interface TicketService {
    
    int numSeatsAvailable(Level venueLevel);
	
    SeatHold findAndHoldSeats(int numSeats, Level minLevel, Level maxLevel, String customerEmail);
	
    String reserveSeats(int seatHoldId, String customerEmail);

    
}

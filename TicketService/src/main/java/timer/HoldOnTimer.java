package timer;

import java.util.TimerTask;

import auditorium.Level;
import ticketbookingservice.AuditoriumTicketService;
import ticketbookingservice.SeatHold;

public class HoldOnTimer extends TimerTask {
	
	private SeatHold seatHold;
	private AuditoriumTicketService auditoriumTicketService;
	
	public HoldOnTimer(SeatHold seatHold,AuditoriumTicketService auditoriumTicketService){
		
		this.seatHold = seatHold;
		this.auditoriumTicketService = auditoriumTicketService;
	}

	@Override
	public void run() {
		
		//This will execute after the the end of the time
		
		/*
		 * The line below will expire the timer and set the boolean field to false conveying that
		 * the timer has passed and the 
		 *  
		 * */ 
		
		//checking if the tickets have already been booked.
		if(this.auditoriumTicketService.getOnHoldTicket().get(this.seatHold.getSeatHoldId())){
			
			//releasing the tickets by adding back the number of tickets in the pool of the respective
			
			System.out.println("releasing held tickets!");
			String levelName = seatHold.getLevelName();
			System.out.println("The level released is : "+levelName);
			Level level = this.auditoriumTicketService.getAuditoriumLevels().get(levelName);
			level.releaseTickets(seatHold);
			this.auditoriumTicketService.getOnHoldTicket().put(seatHold.getSeatHoldId(), false);
			
		}
		
		
		
		
		
	}

}

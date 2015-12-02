package ticketbookingservice;

//This class holds the confirmation information once the ticket has been booked
public class Confirmation {
	
	private SeatHold seatHold;
	private String confirmationId;
	
	public SeatHold getSeatHold() {
		return seatHold;
	}
	public void setSeatHold(SeatHold seatHold) {
		this.seatHold = seatHold;
	}
	public String getConfirmationId() {
		return confirmationId;
	}
	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}
	
	

}

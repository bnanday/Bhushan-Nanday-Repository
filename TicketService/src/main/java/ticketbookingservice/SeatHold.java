package ticketbookingservice;

import java.util.Map;

/**
 *
 * @author bnanday
 */
public class SeatHold {
	
	
	//This is a dynamic seatHoldId which is unique for every transaction.
	private int seatHoldId;
	
	/*
	 * This map stores the exact seats on hold. The key is the row and value is the number of seats on
	held in that row.
	 * */
	private Map<Integer,Integer> seatsOnHOld;
	private String levelName;
	private String customerEmail;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public Map<Integer, Integer> getSeatsOnHOld() {
		return seatsOnHOld;
	}
	public void setSeatsOnHOld(Map<Integer, Integer> seatsOnHOld) {
		this.seatsOnHOld = seatsOnHOld;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	
	
	
	
	
    
    
    
}

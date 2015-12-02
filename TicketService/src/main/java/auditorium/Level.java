/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auditorium;

import java.util.HashMap;
import java.util.Map;

import ticketbookingservice.AuditoriumTicketService;
import ticketbookingservice.Confirmation;
import ticketbookingservice.SeatHold;

/**
 *
 * @author bnanday
 */
public class Level {
    
    private String name;
    private int levelId;
    private int numRows;
    private int numSeatsPerRow;
    private double pricePerTicket;
    private Map<Integer,Integer> numAvailableSeats;

    public Level() {
        //default constructor
    }
    
    public void init(){
    	//This init method constructs the individual Levels as specified inside of the config file    	
    	this.numAvailableSeats = new HashMap<Integer,Integer>();
        for(int i=1; i<this.numRows; i++){            
            numAvailableSeats.put(i,this.numSeatsPerRow);
        }
    }
    
    

    public Level(String name, int levelId, int numRows, int numSeatsPerRow, double pricePerTicket) {
        this.name = name;
        this.levelId = levelId;
        this.numRows = numRows;
        this.numSeatsPerRow = numSeatsPerRow;
        this.pricePerTicket = pricePerTicket;
    }
    
    public int getAvailableSeats(){
    	int totalAvailableSeats=0;
    	for(Map.Entry<Integer, Integer> m:this.numAvailableSeats.entrySet()){
    		totalAvailableSeats+=m.getValue();
    		
    	}
    	
    	return totalAvailableSeats;
    }
    
    public SeatHold holdSeats(int numSeatsToHold){
    	
    	SeatHold seatHold = new SeatHold();
    	
    	/*
    	 * 
    	 * Assuming that the lower row is the better row.
    	 * eg: row 0 is better than row 1
    	 * This algorithm will hold the best seats according to this convention.
    	 * */
    	
    	for(Map.Entry<Integer, Integer> e:this.numAvailableSeats.entrySet()){
    		
    		
    		int currentRow = e.getKey();
    		int numSeatsInCurrentRow = e.getValue();
    		Map<Integer,Integer> ticketsOnHold = new HashMap<Integer,Integer>();
    		
    		if(numSeatsToHold <= numSeatsInCurrentRow){
    			//holding seats in the current row
    			this.numAvailableSeats.put(currentRow, numSeatsInCurrentRow-numSeatsToHold);
    			ticketsOnHold.put(currentRow, numSeatsToHold);
    			continue;
    		}else{
    			//assinging the available seats in the current row.
    			
    			if(this.numAvailableSeats.get(currentRow) > 0){
    				
    				
    				numSeatsToHold = numSeatsToHold - this.numAvailableSeats.get(currentRow);    				
    				this.numAvailableSeats.put(currentRow, 0);
    				
    				if(numSeatsToHold == 0){
    					continue;
    				}
    				
    			}
    			
    		}
    		
    		seatHold.setSeatsOnHOld(ticketsOnHold);
    	}
    	
    	return seatHold;
    }
    
    /*
     * This method will release all the tickets that were booked on the particular seatHoldId
     * */
    public synchronized void releaseTickets(SeatHold seatHold){ 
    	
    	int numSeats;
    	int bookedSeats;
    	int row;
    	for(Map.Entry<Integer, Integer> e:seatHold.getSeatsOnHOld().entrySet()){
    		
    		row = e.getKey();
    		bookedSeats = e.getValue();
    		numSeats = this.numAvailableSeats.get(row);
    		this.numAvailableSeats.put(row, numSeats+bookedSeats);
    	}
    }
    
    //This method has to be synchronized due to multiple threads invoking it.
    public synchronized Confirmation bookSeats(SeatHold seathold){
    	
    	Map<Integer,Integer> ticketsToBook = seathold.getSeatsOnHOld();
    	
    	for(Map.Entry<Integer, Integer> e:ticketsToBook.entrySet()){
    		
    		int row = e.getKey();
    		int seats = e.getValue();
    		int numSeatsCurrentlyAvailable = this.numAvailableSeats.get(row);
    		
    		this.numAvailableSeats.put(row, numSeatsCurrentlyAvailable-seats);
    	}
    	
    	Confirmation confirmation = new Confirmation();
    	confirmation.setConfirmationId(seathold.getSeatHoldId()+"-"+seathold.getCustomerEmail());
    	confirmation.setSeatHold(seathold);
    	
    	return confirmation;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumSeatsPerRow() {
        return numSeatsPerRow;
    }

    public void setNumSeatsPerRow(int numSeatsPerRow) {
        this.numSeatsPerRow = numSeatsPerRow;
    }

    public double getPricePerTicket() {
        return pricePerTicket;
    }

    public void setPricePerTicket(double pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }

    public Map<Integer, Integer> getNumAvailableSeats() {
        return numAvailableSeats;
    }

    public void setNumAvailableSeats(Map<Integer, Integer> numAvailableSeats) {
        this.numAvailableSeats = numAvailableSeats;
    }
    
    
    
    
    
}

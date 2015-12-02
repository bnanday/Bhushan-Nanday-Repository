
package ticketbookingservice;

import auditorium.Level;
import auditorium.LevelTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bnanday
 */

/*
 * This is the class which is the heart of the entire system and which renders all the services
 * related to ticket booking.
 * This class implements the TicketService interface which was mentioned in the document.
 * I have used a set of HashMaps as the data structure to store all the related data.
 * But in the real production environment, the data will be stored and retrieved from inside a relational database table.
 * */

public class AuditoriumTicketService implements TicketService{
	
	private AuditoriumTicketService(){
		
		// private constructor for implementing the singleton design pattern
	}
	//This is the only instance of the Auditorium ticket service as it is singleton.
    private static AuditoriumTicketService auditoriumTicketService = new AuditoriumTicketService();    
    private static Map<String,auditorium.Level>auditoriumLevels;
    private static Map<Integer,Boolean> onHoldTicket;
    private static Map<Integer,String> levelsOfAuditorium;
    private static Map<Integer,SeatHold> seatHoldObjects;
    private static Map<String,SeatHold> confirmedTransaction;
    
    
    public Map<Integer, SeatHold> getSeatHoldObjects() {
		return seatHoldObjects;
	}

	public void setSeatHoldObjects(Map<Integer, SeatHold> seatHoldObjects) {
		this.seatHoldObjects = seatHoldObjects;
	}

	public static Map<String, SeatHold> getConfirmedTransaction() {
		return confirmedTransaction;
	}

	public static void setConfirmedTransaction(Map<String, SeatHold> confirmedTransaction) {
		AuditoriumTicketService.confirmedTransaction = confirmedTransaction;
	}

	
	
   
    public static void init(){
    	
    	/*
    	 * This init method reads the data from the Spring config.xml file beans
    	 * and constructs the Auditorium structure.
    	 * We can change this structure by modifying the config.xml file.
    	 * */ 
    	confirmedTransaction = new HashMap<String,SeatHold>();
    	auditoriumTicketService = new AuditoriumTicketService();
        auditoriumLevels = new HashMap<String,auditorium.Level>();
        levelsOfAuditorium = new HashMap<Integer, String>();
        onHoldTicket = new HashMap<Integer,Boolean>();
        seatHoldObjects = new HashMap<Integer,SeatHold>();
        
    
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/config.xml");
        // Now we get all the available levels through the spring dependency injection.
        LevelTypes levels = (LevelTypes)ac.getBean("levelTypes");        
        ArrayList listOfLevels = levels.getLevelTypes();        
        ListIterator li = listOfLevels.listIterator();
        
        while(li.hasNext()){
            String s = (String)li.next();
            Level l = (Level) ac.getBean(s);
            l.init();
            auditoriumLevels.put(s,l);
            levelsOfAuditorium.put(l.getLevelId(),s);
        }
    }
    
    public AuditoriumTicketService getAuditoriumTicketServiceInstance(){
    	return AuditoriumTicketService.auditoriumTicketService;
    }
    
    public int numSeatsAvailable(Level venueLevel) {
    	//to get the total seats per level
    	return venueLevel.getAvailableSeats(); 
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, Level minLevel, Level maxLevel, String customerEmail) {
        
    	SeatHold seatHold = new SeatHold();
    	
    	/*
    	 *  checking availability for the best seats in the auditorium. Assuming that the user wants all the seats
    	 *   in the same level;
    	 *   We have to check once again for the availibility of tickets.
    	 *   I am assuming that the minLevel is better than the maxLevel.
    	 *   This algorithm will try to assign best tickets to the user.
    	 * 
    	 * */
    	
    	int minId = minLevel.getLevelId();
    	int maxId = maxLevel.getLevelId();
    	
    	int seats=0;
    	String currentLevel="";
    	Level level = new Level();
    	boolean isAvailable = false;
    	
    	for(int i=minId; i<=maxId; i++){
    		 currentLevel = this.levelsOfAuditorium.get(i);
    		 level = this.auditoriumLevels.get(currentLevel);
    		 seats = level.getAvailableSeats();
    		 if(numSeats <= seats){    			 
    			 isAvailable = true;
    			 continue;    			 
    		 }		 
    	
    	}
    	
    	if(isAvailable){
    		
    		seatHold = level.holdSeats(numSeats);  
    		seatHold.setLevelName(level.getName());
    		seatHold.setCustomerEmail(customerEmail);
        	//Here I am generating a unique seatHold id for every new Hold transaction.
        	seatHold.setSeatHoldId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));        	
    		//Adding the hold entry into the map containing all the held 
        	this.onHoldTicket.put(seatHold.getSeatHoldId(), true);
    		seatHold.setStatus("Seats are confirmed");    		
    	}else{    		
    		seatHold.setStatus("Not enough seats available in any of the levels");
    	}
    	   	
    	this.seatHoldObjects.put(seatHold.getSeatHoldId(), seatHold);
    	return seatHold;
    }

    @Override
    public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
    	
    	SeatHold seatHold = this.seatHoldObjects.get(seatHoldId);    	
    	String levelName = seatHold.getLevelName();
    	System.out.println("The level to be booked is:"+levelName);
		Level level = this.auditoriumLevels.get(levelName);
		Confirmation confirmation = level.bookSeats(seatHold);
		
		//releasing the hold after the tickets are confirmed and booked.
		this.onHoldTicket.put(seatHoldId, false);
		
		return confirmation.getConfirmationId();
		
    }

    public static AuditoriumTicketService getAuditoriumTicketService() {
		return auditoriumTicketService;
	}



	
    public static void setAuditoriumTicketService(AuditoriumTicketService auditoriumTicketService) {
		AuditoriumTicketService.auditoriumTicketService = auditoriumTicketService;
	}



	
    public static Map<String, auditorium.Level> getAuditoriumLevels() {
		return auditoriumLevels;
	}



	
    public static void setAuditoriumLevels(Map<String, auditorium.Level> auditoriumLevels) {
		AuditoriumTicketService.auditoriumLevels = auditoriumLevels;
	}



	public static Map<Integer, Boolean> getOnHoldTicket() {
		return onHoldTicket;
	}



	public static void setOnHoldTicket(Map<Integer, Boolean> onHoldTicket) {
		AuditoriumTicketService.onHoldTicket = onHoldTicket;
	}



	public static Map<Integer, String> getLevelsOfAuditorium() {
		return levelsOfAuditorium;
	}



	public static void setLevelsOfAuditorium(Map<Integer, String> levelsOfAuditorium) {
		AuditoriumTicketService.levelsOfAuditorium = levelsOfAuditorium;
	}
    
    
    
   
    
    
    
}

package ticketbookingservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Logger;

import auditorium.Level;
import timer.HoldOnTimer;

public class TicketServiceInstance extends Thread {
	
	AuditoriumTicketService auditoriumTicketServiceInstance;
	
	public TicketServiceInstance(){
		/*
		 * Getting the singleton instance of Auditorium Ticket Service
		 * This instance will give us access to all the necessary methods of the service.
		 * */
		auditoriumTicketServiceInstance = AuditoriumTicketService.getAuditoriumTicketService();
	}
	
	public void run(){
		/*
		 * This is the main menu of the instance thread.
		 * It will be displayed every time a new call is made to to service
		 * */
		int x = 0;		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(x != 4){
			System.out.println("Please enter your choice number: ");
			System.out.println("1: Find the number of available seats");
			System.out.println("2: Hold seats ");
			System.out.println("3: Book seats (Please have your hold confirmation number handy.) ");
			System.out.println("4: Exit the application");
			System.out.println("//////////////////////////////////////////////////////////////////");

			try {
				x = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Thats not a valid entry. Please enter a valid number.Lets try again");
				this.run();
			} catch (IOException e) {
				System.out.println("I/O exception caught. Lets try it once again");
				//re launching the main menu				
				this.run();
			}
			
			switch(x){			
			case 1:this.findSeats();
			break;
			case 2:try {
					this.holdSeats();
					break;
				} catch (IOException e) {
					System.out.println("I/O exception caught. Lets try it once again");
					//re launching the main menu				
					this.run();
				}
				break;
			case 3:try {
					this.bookSeats();
					break;
				} catch (IOException e) {					
					System.out.println("I/O exception caught. Lets try it once again");
					//re launching the main menu				
					this.run();
				}
			case 4: System.out.println("Thank you for using the ticket booking service application");
					break;
			}			
		}
	}
	
	public void findSeats(){
		//This method find number of seats available in the level specified.
		System.out.println("Welcome to the seat find application");
		System.out.println("Please enter the level which you are trying to book tickets for: ");
		int x = 0;
		for(Map.Entry<String, Level> m: this.auditoriumTicketServiceInstance.getAuditoriumLevels().entrySet()){
			System.out.println(m.getValue().getLevelId()+" : "+m.getKey());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			x = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			System.out.println("please enter a valid number!");
			System.out.println("Lets try again.");
			findSeats();
		} 
		
		String s = this.auditoriumTicketServiceInstance.getLevelsOfAuditorium().get(x);
		Level l = this.auditoriumTicketServiceInstance.getAuditoriumLevels().get(s);
		System.out.println("You have selected: "+s);		
		System.out.println("The available seats for your selection are: "+
		this.auditoriumTicketServiceInstance.numSeatsAvailable(l));
		
		System.out.println("How would you like to proceed now?");
		System.out.println("1: Hold Tickets");		
		System.out.println("2: Exit Service");
		System.out.println("Please make the appropriate selection number");
		System.out.println("//////////////////////////////////////////////////////////////////");
		
		try {
			x = Integer.parseInt(br.readLine());
			
			switch(x){
			
			case 1:this.holdSeats();
			break;
			case 2:return;
			
			}
		} catch (NumberFormatException e1) {			
			System.out.println("You have made an incorrect selection.");
			System.out.println("PLease select the number corrosponding to the options.");
			this.findSeats();	

		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		System.out.println("At the end of find seats");
		
		
	}
	
	public int findSeats(String levelString){
		
		Level level = this.auditoriumTicketServiceInstance.getAuditoriumLevels().get(levelString);
		
		return level.getAvailableSeats();
	}
	
	public void holdSeats() throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please specify the minimum level of your choice");
		System.out.println("You have the follwoing options");
		//Mentioning all the available options.
		for(String s:this.auditoriumTicketServiceInstance.getAuditoriumLevels().keySet()){
			System.out.println(s);
		}
		Level minLevel,maxLevel;
		String maxLevelSelection,minLevelSelection;
		try{
			minLevelSelection = br.readLine();
			int minLevelNumber = this.auditoriumTicketServiceInstance.getAuditoriumLevels().get(minLevelSelection).getLevelId();
			int numLevels = this.auditoriumTicketServiceInstance.getAuditoriumLevels().size();
			
			if(minLevelNumber == numLevels){
				System.out.println("You have selected the greatest level");
				//There is only one level in contention.
				maxLevelSelection = minLevelSelection;
			}else{			
				System.out.println("Please specify the maximum level of your choice");
				System.out.println("You have the follwoing options which are greater than the minimum level you selected");
				//Mentioning only the options greater .
				for(Map.Entry<String, Level> e:this.auditoriumTicketServiceInstance.getAuditoriumLevels().entrySet()){
					if(e.getValue().getLevelId() > minLevelNumber )
					System.out.println(e.getKey());
				}
				maxLevelSelection = br.readLine();
			}
			
			
			System.out.println("Please enter your email address to book your tickets.");
			String customerEmail = br.readLine();
			
			minLevel = this.auditoriumTicketServiceInstance.getAuditoriumLevels().get(minLevelSelection);
			maxLevel = this.auditoriumTicketServiceInstance.getAuditoriumLevels().get(maxLevelSelection);
			int minLevelId = minLevel.getLevelId();
			int maxLevelId = maxLevel.getLevelId();
			
			System.out.println("Given below are the number of seats available in each level");
			for(int i=minLevelId; i<=maxLevelId; i++){
				
				String thisLevel = this.auditoriumTicketServiceInstance.getLevelsOfAuditorium().get(i);
				System.out.println(thisLevel+" : "+this.findSeats(thisLevel));
			}
			
			System.out.println("Please enter the number of seats to be held.");
			int numSeatsToHold = Integer.parseInt(br.readLine());
			
			System.out.println("Thank you for all the selections. Please wait while we hold your tickets");
			SeatHold seatHold = this.auditoriumTicketServiceInstance.findAndHoldSeats(numSeatsToHold, minLevel, maxLevel, customerEmail);
			
			System.out.println("Your tickets have been blocked. Please note down your confirmation number.");
			System.out.println("Your confirmation number is: "+seatHold.getSeatHoldId());
			
			System.out.println("You have 5 minutes to confirm your booking. Do you want to proceed to book the tickets?");
			System.out.println("press 'y' or 'n' to proceed");
			System.out.println("//////////////////////////////////////////////////////////////////");

			
			// Implementing the timer mechanism
			Timer timer = new Timer();
			HoldOnTimer timerTask = new HoldOnTimer(seatHold,this.auditoriumTicketServiceInstance);
			//starting the timer for 5 mins
			timer.schedule(timerTask, (5*1000*60) );
			//the timer will expire after 5 mins and will call the the function which will release the already held ticket.
			
			
			String bookSelection = br.readLine();
			
			if(bookSelection.equalsIgnoreCase("y")){
				if(this.auditoriumTicketServiceInstance.getOnHoldTicket().get(seatHold.getSeatHoldId()))
				this.bookSeats(seatHold);
				
			}else if(bookSelection.equalsIgnoreCase("n")){
				return;
			}
			
			System.out.println("At the end of hold seats");
		}catch(Exception E){
			
			System.out.println("You did not enter the option properly. Please try again");
			this.holdSeats();
		}
		
		
	}
	
	public void bookSeats(SeatHold seatHold){
		
		if(this.auditoriumTicketServiceInstance.getOnHoldTicket().get(seatHold.getSeatHoldId())){
			
		String confirmation = 	this.auditoriumTicketServiceInstance.reserveSeats(seatHold.getSeatHoldId(), seatHold.getCustomerEmail());
		
		System.out.println("Thank you for booking seats. Your confirmation code is: "+confirmation);
		}
		System.out.println("//////////////////////////////////////////////////////////////////");

		
		System.out.println("At the end of book seats");
		
		
		
	}
	
	public void bookSeats() throws IOException{
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("please enter your seat Hold confirmation id");
			int seatHoldId = Integer.parseInt(br.readLine());
			System.out.println("Please enter your email");
			String email = br.readLine();
			SeatHold seatHold = new SeatHold();
			seatHold.setSeatHoldId(seatHoldId);
			seatHold.setCustomerEmail(email);
			this.bookSeats(seatHold);			
		}catch(Exception e){
			System.out.println("Incorrect email or seat confirmation number. Please try again.");
			this.bookSeats();
			
		}
		
	}
	
	
	

}

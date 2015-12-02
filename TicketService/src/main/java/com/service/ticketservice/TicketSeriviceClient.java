package com.service.ticketservice;

import auditorium.Level;
import java.util.Map;
import ticketbookingservice.AuditoriumTicketService;
import ticketbookingservice.TicketServiceInstance;



public class TicketSeriviceClient{
	
	static{
		
		/*
		 * This static block initializes the auditorium by taking in the Spring Beans from the config.xml file.
		 * The auditorium configuration can be changed by modifying the config.xml file at the start-up.
		 *
		 * */
		AuditoriumTicketService.init();
	}
    
    public static void main(String[] args) throws InterruptedException{       
        
        //Every time a new request comes,a new Thread of TicketServiceInstance will be launched.
    	//In this demo, I am just launching one Thread
    	
    	TicketServiceInstance instance = new TicketServiceInstance();
        instance.start();
        instance.join();
        System.out.println("instance completed");
        System.exit(0);
        
        
    
    }



}
package p1;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receive extends Thread
{
	byte[] seq;
	byte[] nonce;
	
	public Receive(byte[] seq, byte[] nonce)
	{
		this.seq=seq;
		this.nonce=nonce;
		
	}
	public void run()
	{
		try{
		System.out.println("data transfer started");
		DatagramSocket RxSocket = new DatagramSocket(9023);
		HandshakeObject ob=new HandshakeObject();
		
		byte[] data = new byte[48];
		
		byte[] encrypteddata = new byte[351];
		DatagramPacket datapacket= new DatagramPacket(data, data.length);
		int counter=1;
		boolean check=false;
		int datacounter=0;
		byte[] dack = new byte[6];
		
		byte[] ip = new byte[4];
		ip[0]=(byte)127;
		ip[1]=(byte)0;
		ip[2]=(byte)0;
		ip[3]=(byte)1;
				
		InetAddress a2 = InetAddress.getByAddress("Bhushan", ip);
		DatagramPacket dackpacket= new DatagramPacket(dack,dack.length,a2,9022);
		
		int lastisearliercounter = 1;// This counter is used to receive the last data packet again if the last DACK packet
		// was not received by the transmitter or the last DACK packet did not pass the integrity test.
		
		boolean againdata=false;
		while(counter<11 && lastisearliercounter<4)//change
		{
			if(counter==10)
				{// we set the timeout only after the last dack packet is sent and the receiver waits 
				// for the last data packet again if the transmission of the last dack packet was not successful
				RxSocket.setSoTimeout(10000);
				
				}
			try{
				System.out.println("waiting for data packet");
				RxSocket.receive(datapacket);
				System.out.println("data packet received");
				againdata=true;
			}
			catch(Exception e)
			{
				lastisearliercounter++;
				againdata=false;// This variable is false if datapacket is not received before timeout
			}
			
			
			
			
		
		 
		PacketTest pt = new PacketTest();
	    if(counter==1)
	    {
	    	// if its the first data packet we test it with the sequence number obtained from the handshaking phase.
	    	ob=pt.datapackettest(data, seq);
	    }
	    if(counter>1)
	    {// For all data packets other than the first packet, we test it by incrementing the previous sequence number by 1. 
	    	if(seq[1]==255 && seq[0]<255)
			{
				seq[1]=0;
				seq[0]++;
			}
			if(seq[1]<255)
			{
				seq[1]++;
			}
			if(seq[0]==255 && seq[1]==255)
			{
				seq[1]=0;
				seq[0]=0;
			}
			
			ob=pt.datapackettest(data, seq);	
			
			
	    }
	    
	// ob.getTest() returns boolean value indicating whether the data packet test was successful;    
	    if(ob.getTest()==true && counter==10)
	    {
	    	
	    	if(ob.isearlier==true && againdata==true)
    		{
	    		System.out.println("inside!!!!");
	    		System.out.println("Sending last dack again");
    			RxSocket.send(dackpacket);
    			
    			seq=ob.seq;
    			lastisearliercounter++;
    			if(lastisearliercounter==4)//change here
    			{
    				counter++;
    			}
    		}
	    	
	    }
	    
	    if(ob.getTest()==false && counter==10 && againdata==true)
	    {
	    	//System.out.println("test failed!");
	    	lastisearliercounter++;
	    }
	    if(ob.getTest() && counter<10 )
	    {
	    	
	    	System.out.println("data packet is valid");
	    	if(counter==9)
	    	{
	    		if(ob.isearlier)
	    		{
	    			// ob.isearlier() returns true if the transmitter has resent the previous datapacket.
	    			// In that case we just send the previously calculated DACK packet without incrementing the main packet counter.
	    			RxSocket.send(dackpacket);
	    			seq=ob.seq;
	    			
	    		}
	    		else
	    		{
	    			// Extracting the encrypted payload from datapacket.
	    			int pcounter1=6;
		    		for(int i=datacounter; i<encrypteddata.length; i++)
		    		{
		    			encrypteddata[i]=data[pcounter1];
		    			pcounter1++;
		    		}
		    		datacounter=datacounter+40;
	    			//create a new dack packet
	    			dack[0]=0x00;
	    			dack[1]=0x04;/* change here  0x04*/
	    			dack[2]=seq[0];
	    			dack[3]=seq[1];
	    			
	    			byte[] intcheck = new byte[2];
	    			intcheck[0]=0;
	    			intcheck[1]=0;
	    			
	    			for(int i=0; i<4; i=i+2)
	    			{
	    				intcheck[0]=(byte)(intcheck[0]^dack[i]);
	    				intcheck[1]=(byte)(intcheck[1]^dack[i+1]);
	    			}
	    			
	    			dack[4]=intcheck[0];
	    			dack[5]=intcheck[1];
	    			
	    			RxSocket.send(dackpacket);
	    			System.out.println("last dack sent");
	    			counter++;
	    			
	    		}
	    	
	    		
	    		
	    		
	    	}
	    	if(counter<9)
	    	{
	    		
	    		
	    		
	    		if(ob.isearlier)
	    		{
	    			System.out.println("earlier dack sent");
	    			RxSocket.send(dackpacket);
	    			seq=ob.seq;
	    		}
	    		else
	    		{
	    			int pcounter=6;
		    		for(int i=datacounter; i<datacounter+40; i++)
		    		{
		    			encrypteddata[i]=data[pcounter];
		    			pcounter++;
		    		}
		    		datacounter=datacounter+40;
	    			//create a new dack
	    			dack[0]=0x00;
	    			dack[1]=0x04;/*change here*/
	    			dack[2]=seq[0];
	    			dack[3]=seq[1];
	    			
	    			byte[] intcheck = new byte[2];
	    			intcheck[0]=0;
	    			intcheck[1]=0;
	    			
	    			for(int i=0; i<4; i=i+2)
	    			{
	    				intcheck[0]=(byte)(intcheck[0]^dack[i]);
	    				intcheck[1]=(byte)(intcheck[1]^dack[i+1]);
	    			}
	    			
	    			dack[4]=intcheck[0];
	    			dack[5]=intcheck[1];
	    			
	    			RxSocket.send(dackpacket);
	    			System.out.println("dack sent");
	    			counter++;
	    			
	    		}
	    		
	    		
	    	}
	    	
	    
	    	
	    }
		}
		
		// decrypting the data
		byte[] decrypteddata = Decryption.decrypt(nonce, encrypteddata);
		
		System.out.println("The decrypted bytes are as follows:");
		
		for(int i=0; i<decrypteddata.length; i++)
		{
			System.out.println(decrypteddata[i]);
		}
		
		}
		catch(Exception e){}
		
	}
	

}

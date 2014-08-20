package p1;
import java.net.*;
import java.util.Random;

public class Handshaking 
{
	DatagramSocket RxSocket;
	int port;
	
	public void handshake(DatagramSocket RxSocket)throws Exception
	{
		/*
		 * This handshake method implements handshaking on the receiver side.
		 */
		this.RxSocket=RxSocket;
		
		PacketTest pt = new PacketTest();
		boolean open=true;
		
		boolean datatransferflag=false;
		
		//nonce bytes are randomly generated here and sent to the transmitter through the IACK packet
		byte[] nonce=new byte[8];
		
		Random r = new Random();			
		
		for(int i=0; i<8; i++)
		{
			nonce[i]=(byte)r.nextInt(255);
		}
		
		
		
		byte[] init = new byte[6];
		
		DatagramPacket initpacket= new DatagramPacket(init,init.length);
		
		int counter=4;
		/* we select counter to be 4 because the transmitter can send INIT packet for at the most 4 times.
		 * This function sends IACK packet for every valid INIT packet received. The receiver drops the 
		 * init packet if it is invalid and doesn't pass the packetTest.
		 */
		
		
		/* There is no timeout for the first receive blocking call as the receiver waits for the
		 * transmitter to send the first init packet.
		 */
		
		RxSocket.receive(initpacket);
		System.out.println("Init packet received");
		HandshakeObject ob=pt.initPacketTest(init);
		
		while(counter>0)
		{
			
			
			try
			{
				if(counter!=4)
					{
					/* The transmitter waits for 8 seconds before send the fourth INIT packet. 
					 * Hence we set the timer to 9 seconds for the subsequent receive blocking calls
					 */
					
					RxSocket.setSoTimeout(9000);
				RxSocket.receive(initpacket);
			System.out.println("init packet received");
			ob=pt.initPacketTest(init);
			
					}
			port=initpacket.getPort();
			// ob.test() returns true if the init packet passes all the tests else it returns false.
			if(ob.test==true)
		{
				
				System.out.println("init packet is valid");
			byte[] iack = new byte[14];
			
			iack[0]=0x00;
			iack[1]=0x01;// change here to 01
			iack[2]=ob.getSeq()[0];
			iack[3]=ob.getSeq()[1];
			iack[4]=nonce[0];
			iack[5]=nonce[1];
			iack[6]=nonce[2];
			iack[7]=nonce[3];
			iack[8]=nonce[4];
			iack[9]=nonce[5];
			iack[10]=nonce[6];
			iack[11]=nonce[7];
			
			
			byte[] integrity = new byte[2];
			integrity[0]=0;
			integrity[1]=0;
			
			for(int i=0; i<=10; i=i+2)
			{
				integrity[0]=(byte)(integrity[0]^iack[i]);
				integrity[1]=(byte)(integrity[1]^iack[i+1]);
			}
			
			iack[12]=integrity[0];
			iack[13]=integrity[1];
			
			byte[] ip = new byte[4];
			ip[0]=(byte)127;
			ip[1]=(byte)0;
			ip[2]=(byte)0;
			ip[3]=(byte)1;
					
			InetAddress a2 = InetAddress.getByAddress("Bhushan", ip);
			
			DatagramPacket iackPacket = new DatagramPacket(iack,iack.length, a2, 9055);
			
			RxSocket.send(iackPacket);
			System.out.println("Iack packet sent");
			
			byte[] seq= new byte[2];
			seq[0]=ob.getSeq()[0];
			seq[1]=ob.getSeq()[1];
			
			if(open==true)
				{
				
				/*we have defined the boolean variable 'open' to ensure that the data transfer thread 
				 * on the receiver side opens only once when the first valid init packet is received.
				 * For all the subsequent Init packets, only IACK packets will be sent.
				 */
				
				System.out.println("entering the data transfer phase on receiver");
					Receive r1 = new Receive(seq,nonce);
					r1.start();// starting the data transfer thread.
				
			    
			    	open=false;
				}
			
			
		
			counter--;
		}
		else
		{
				System.out.println("Init packet is invalid.");
				counter--;
		}
		}
		catch(Exception e)
		{
				
				counter--;
		}
			
			
		
		
		}
		
		
		
		
		
		
	}
	

}

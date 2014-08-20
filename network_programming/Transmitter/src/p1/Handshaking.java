package p1;
import java.net.*;
import java.util.*;

public class Handshaking

{
	/*
	 * This class initiates handshaking on the transmitter side. The method handshake() has a return type 
	 * HandshakeObject. This object contains vital information of nonce, initial sequence number and 
	 * a boolean field which tells us if the handshaking was successful.
	 */
	DatagramSocket TxSocket;
	
	
	public  HandshakeObject handshake(DatagramSocket TxSocket) throws Exception
	{
		this.TxSocket=TxSocket;
		
		byte[] init = new byte[6];
		init[0]=0x00;
		init[1]=0x00;//change here to 00;
	
		// Here we randomly create the initial sequence number for init packet.
		Random r =new Random();		
		byte[] inseq = new byte[2];
		r.nextBytes(inseq);
		
		
		init[2]=inseq[0];
		init[3]=inseq[1];
		
		byte[] intcheck = new byte[2];
		intcheck[0]= 0;
		intcheck[1]= 0;
		
		
	
		
		
		intcheck[0]=(byte)((int)intcheck[0]^(int)init[0]);
		intcheck[1]=(byte)((int)intcheck[1]^(int)init[1]);
		intcheck[0]=(byte)((int)intcheck[0]^(int)init[2]);
		intcheck[1]=(byte)((int)intcheck[1]^(int)init[3]);
		
		init[4]=intcheck[0];
		init[5]=intcheck[1];
		
		byte[] ip = new byte[4];
		ip[0]=(byte)127;
		ip[1]=(byte)0;
		ip[2]=(byte)0;
		ip[3]=(byte)1;
				
		InetAddress a2 = InetAddress.getByAddress("Bhushan", ip);
		DatagramPacket initpacket = new DatagramPacket(init,init.length,a2,9056);
		int value = 1000;
		TxSocket.setSoTimeout(value);
		TxSocket.send(initpacket);
		System.out.println("Init packet sent");
		
		byte[] iack = new byte[16];
		boolean isValid = false;
		
		HandshakeObject ob1 = new HandshakeObject(false);
		
		DatagramPacket iackpacket = new DatagramPacket(iack, iack.length);
		while( ob1.getAuthenticate() ==false && (value<=8000))
		{	
			
			try
		
		{
			TxSocket.receive(iackpacket);
			System.out.println("Iack packet received");
			boolean y = PacketCheck.handshakePacketCheck(iack, inseq);
			
			if(y==false)
			{
				System.out.println("False iack packet received");
				ob1=new HandshakeObject(false);
				TxSocket.setSoTimeout(2*value);
				if(value!=8000)
					{
					
						TxSocket.send(initpacket);
						System.out.println("Init packet sent again");
					}
				value=value*2;
			}
			else
			{
				System.out.println("valid iack packet received");
				ob1=new HandshakeObject(true);
			}
			
		}
		
		catch(Exception e)
		{
			if(value==8000)
			{// After the fourth timeout
				throw new Exception("Communication failure! Link could not be set up");
			}
			else
			{
				System.out.println("iack packet not received");
				TxSocket.send(initpacket);
				TxSocket.setSoTimeout(2*value);
				value=value*2;
			}
			
		}
		}
			
			
		
		
		byte[] nonce = new byte[8];
		int counter =0;
		
		for(int i=4; i<12; i++)
		{
			nonce[counter]=iack[i];
			counter++;
		}
		
		// we extract the nonce value from Iack packet and give it to the data transfer phase through HandshakeObject ob1
		if(ob1.getAuthenticate())
		{
			ob1=new HandshakeObject(nonce,inseq, true);
		}
		
		
		
		return ob1;		
		
	}

}

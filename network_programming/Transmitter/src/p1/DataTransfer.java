package p1;
import java.net.*;
import java.util.*;
public class DataTransfer
{
	byte[] inseq;	
	byte[] nonce;
	
	public DataTransfer(byte[] inseq, byte[] nonce)
	{
		this.inseq=inseq;
		this.nonce=nonce;
	}
	
	public void sendData()throws Exception
	{
		// This method initiates data transfer in the transmitter side
		
		System.out.println("Data transfer starts");
		byte[] totalpayload = new byte[351];
		Random r = new Random();
		r.nextBytes(totalpayload);
		System.out.println("The bytes to be sent are as follows:");
		for(int i=0; i<351; i++)
		{
			System.out.println(totalpayload[i]);
		}
		
				
		byte[] secretkey = {32,32,32,32,32,32,32,32};
		
		Encryption e = new Encryption(nonce, secretkey, totalpayload);
		
		// Encrypting the bytes to be sent
		
		byte[] encryptedPayLoad=e.encrypt();
		
		// setting the sequence number
		
		byte[] seq=new byte[2];
		seq = inseq;
	    int counter=1;
	    int counter1=0;
	    
		DatagramSocket TxSocket = new DatagramSocket(9022);
		
		byte[] ip = new byte[4];
		ip[0]=(byte)127;
		ip[1]=(byte)0;
		ip[2]=(byte)0;
		ip[3]=(byte)1;
				
		InetAddress a2 = InetAddress.getByAddress("Bhushan", ip);
		
		while(counter<10)
		{		
			byte[] data = new byte[48];
			if(counter==9)
		{
			data[0]=0x00;
			data[1]=0x03;
		}
		else
		{
			data[0]=0x00;
			data[1]=0x02;//change here  02;
		}
		
		if(counter>1)
		{
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
		}
		data[2]=seq[0];
		data[3]=seq[1];
		data[4]=00;
		if(counter==9)
		{
			data[5]=31;
		}
		else
		{
			data[5]=40;
		}
		
		byte[] payload = new byte[40];
		
		if(counter<9)			
		{
			int pcounter=0;
			for(int i=counter1; i<counter1+40; i++)
			{
				payload[pcounter]=encryptedPayLoad[i];
				pcounter++;
			}
			counter1=counter1+40;
		}
		else
		{
			int pcounter=0;
			for(int i=320;i<351;i++)
			{
				payload[pcounter]=encryptedPayLoad[i];
				pcounter++;
			}
			
			for(int i =31; i<40; i++)
			{
				payload[i]=0;
			}
			
		}
		
		for(int k=6; k<46; k++)
		{
			data[k]=payload[k-6];
		}
		
		byte[] intcheck = new byte[2];
		intcheck[0]= 0;
		intcheck[1]= 0;
		
	
		
		for(int k=0; k<46; k=k+2)
		{
			intcheck[0]=(byte)((int)intcheck[0]^(int)data[k]);
			intcheck[1]=(byte)((int)intcheck[1]^(int)data[k+1]);
		}
		
		data[46]=intcheck[0];
		data[47]=intcheck[1];
		
		
		DatagramPacket datapacket = new DatagramPacket(data,data.length,a2,9023);
		
		if(counter==1)
		{
			// This sleep timer is given to ensure that the transmitter data transfer phase does not start before the receiver data transfer phase.
			
			
			Thread.sleep(3000);
		}
		TxSocket.send(datapacket);
		System.out.println("data packet sent");
				
		int value=1000;
		
		byte[] dack = new byte[6];
		
		DatagramPacket dackpacket = new DatagramPacket(dack,dack.length);
		TxSocket.setSoTimeout(value);
		
		HandshakeObject ob1 = new HandshakeObject(false);
		
		
		while( ob1.getAuthenticate() == false && (value<=8000))
		{	
			
			try
		
		{
			TxSocket.receive(dackpacket);
			System.out.println("dack received");
			
			
			boolean y = PacketCheck.dackPacketCheck(dack, seq);
			if(y==false)
			{
				System.out.println("False dack packet");
				ob1=new HandshakeObject(false);
				TxSocket.setSoTimeout(2*value);
				if(value!=8000)
				{
					TxSocket.send(datapacket);
				}
				value=value*2;
			}
			else
			{
				System.out.println("valid dack received");
				ob1=new HandshakeObject(true);
				counter++;
			}
			
		}
		
		catch(Exception a)
		{
			if(value==8000)
			{
				System.out.println("dack not received");
				throw new Exception("Communication failure! Time out!");
			}
			else
			{
				System.out.println("dack 1 not received");
				TxSocket.send(datapacket);
				TxSocket.setSoTimeout(2*value);
				value=value*2;
			}
			
		}
		}
		
		if(value>8000)
		{ 
			// Timer expires for the forth time.
			throw new Exception("Communication Failure!");
		}
		
		}
		
		
	}

}

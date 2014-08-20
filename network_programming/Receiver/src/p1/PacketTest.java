package p1;

import java.util.Random;

public class PacketTest
{
	public HandshakeObject initPacketTest(byte[] init)
	{
		HandshakeObject ob1;
		boolean isearlier=false;
		
		
		byte[] seq = new byte[2];
		seq[0]=init[2];
		seq[1]=init[3];
		
		/* we check the init packet for integrity test and type test. The packet is deemed valid
		 * only if it passes both the tests.		 * 
		 */
		
		boolean typeflag=false;// for type test
		boolean iflag=false;//for integrity test
		
		if(init[0]==0x00 && init[1]==0x00)
		{
			typeflag=true;
		}
		
		//Integrity test
		byte[] integrity = new byte[2];
		integrity[0]=(byte)(init[0]^integrity[0]);
		integrity[1]=(byte)(init[1]^integrity[1]);
		integrity[0]=(byte)(init[2]^integrity[0]);
		integrity[1]=(byte)(init[3]^integrity[1]);
		integrity[0]=(byte)(init[4]^integrity[0]);
		integrity[1]=(byte)(init[5]^integrity[1]);
		
		if(integrity[0]==0 && integrity[1]==0)
		{
			iflag=true;
		}
		
		
		if(typeflag==true && iflag==true)
		{
			
			/*Along with the validity result we embed the sequence number in HandshakeObject and then send it 
			 * back to the handshaking phase so that we can create IACK packet with the same sequence number.
			 */
			
			ob1=new HandshakeObject(seq,true);			
			
		}
		else
		{
			ob1=new HandshakeObject(seq,false);
		}
		
		return ob1;
	}
	
	public HandshakeObject datapackettest(byte[] data, byte[] seq)
	{
		boolean isearlier=false;// to check if it is an earlier packet re-transmitted by the transmitter corresponding to a lost dack packet.
		boolean islastflag=false;// to check if its the last data packet
		byte[] inseq = seq;
		boolean iflag=false,seqflag=false,typeflag=false;
		HandshakeObject ob;
		
		if(data[0]==0x00 && data[1]==0x02)
		{
			typeflag=true;
		}
		
		if(data[0]==0x00 && data[1]==0x03)
		{
			typeflag=true;
			islastflag=true;
		}
		
		byte[] intcheck = new byte[2];
		intcheck[0]=0;
		intcheck[1]=0;
		
		for(int i=0; i<48; i=i+2)
		{
			intcheck[0]=(byte)(intcheck[0]^data[i]);
			intcheck[1]=(byte)(intcheck[1]^data[i+1]);
		}
		
		if(intcheck[0]==0 && intcheck[1]==0)
		{
			iflag=true;
			
			if(data[2]==seq[0] && data[3]==seq[1])
			{
				seqflag=true;
				isearlier=false;
			}
			else
			{
				if(seq[1]==0 && seq[0]>0)
				{
					seq[1]= (byte)255;
					seq[0]--;
				}
				if(seq[1]>0)
				{
					seq[1]--;
				}
				if(seq[0]==0 && seq[1]==0)
				{
					seq[1]=(byte)255;
					seq[0]=(byte)255;
				}
				
				if(data[2]==seq[0] && data[3]==seq[1])
				{
					isearlier=true;
					seqflag=true;
					
				}
			}
		}
		
		
		/* Along with the validity result we are embedding if the data packet is re-transmitted,
		 * the sequence number and a boolean field indicating if its the last data packet.
		 */
		if(iflag==true && typeflag==true && seqflag==true )
		{
			return new HandshakeObject(isearlier,true,inseq,islastflag);
		}
		else
			return new HandshakeObject(isearlier,false,inseq, islastflag);
	}

}

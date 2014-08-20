package p1;

public class PacketCheck 
{
	public static boolean handshakePacketCheck(byte[] iack, byte[] inseq)
	{
		/*
		 * This method tests the iack packet for type number, sequence number and integrity test.
		 * It returns true if the iack packet passes all three tests.
		 */
		boolean typeflag,echoflag, iflag;
		
		if(iack[0]==0x00 && iack[1]==0x01)
		{
			typeflag=true;
		}
		else
			typeflag = false;
		
		if(iack[2]==inseq[0] && iack[3]==inseq[1])
		{
			echoflag=true;
		}
		else
			echoflag=false;
		
		byte[] intcheck = new byte[2];
		intcheck[0]= 0x00;
		intcheck[1]= 0x00;
		
		for(int i=0; i<13; i=i+2)
		{
			intcheck[0]=(byte)((int)intcheck[0]^(int)iack[i]);
			intcheck[1]=(byte)((int)intcheck[1]^(int)iack[i+1]);
		}
		
		if(intcheck[0]==0 && intcheck[1]==0)
		{
			iflag=true;
		}
		else
			iflag=false;
		
		if(typeflag==true && echoflag==true && iflag==true)
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean dackPacketCheck(byte[] dack, byte[] seq)
	{
		
		boolean typeflag,iflag,sflag;
		
		if(dack[0]==0x00 && dack[1]==0x04)
		{
			typeflag=true;
		}
		else
		{
			typeflag=false;
		}
		
		if(dack[2]==seq[0] && dack[3]==seq[1])
		{
			sflag=true;
		}
		else
			sflag=false;
		
		byte[] intcheck = new byte[2];
		intcheck[0]= 0;
		intcheck[1]= 0;
		
		for(int i=0; i<5; i=i+2)
		{
			intcheck[0]=(byte)(intcheck[0]^dack[i]);
			intcheck[1]=(byte)(intcheck[1]^dack[i+1]);
		}
		
		if(intcheck[0]==0 && intcheck[1]==0)
		{
			iflag=true;
		}
		else
			iflag=false;
		
		if(typeflag==true && sflag==true && iflag==true)
		{
			return true;
		}
		else
			return false;
	}

}

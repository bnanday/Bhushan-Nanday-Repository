package p1;
import java.net.*;

public class HandshakeObject
{
	byte[] nonce;
	boolean test;
	byte[] seq;
	boolean isearlier;
	boolean islast;
	
	public HandshakeObject(){}
	public HandshakeObject(byte[] seq, boolean test)
	{
		
		this.test=test;
		this.seq=seq;
	}
	
	public HandshakeObject(boolean isearlier, boolean test, byte[] seq, boolean islast)
	{
		this.isearlier=isearlier;
		this.test=test;
		this.seq=seq;
		this.islast=islast;
	}
	
	
	public boolean getTest()
	{
		return this.test;
	}
	
	public byte[] getSeq()
	{
		return this.seq;
	}

}

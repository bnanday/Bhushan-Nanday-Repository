package p1;

public class HandshakeObject
{
	private boolean x;
	private byte[] nonce;
	private byte[] inseq;
	
	public HandshakeObject(){}
	public HandshakeObject(byte[] nonce, boolean x)
	{
		this.nonce=nonce;
		this.x=x;
	}
	public HandshakeObject(boolean x)
	{
		
		this.x=x;
	}
	
	public HandshakeObject(byte[] nonce,byte[] inseq, boolean x)
	{
		this.nonce=nonce;
		this.x=x;
		this.inseq=inseq;
	
		
	}
	
	public boolean getAuthenticate()
	{
		return x;
	}
	
	public byte[] getNonce()
	{
		return nonce;
	}
	
	public byte[] getinseq()
	{
		return this.inseq;
	}
	
	
	

}

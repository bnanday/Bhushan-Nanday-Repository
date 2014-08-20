package p1;
import java.net.*;

public class Transmitter {

	public static void main(String[] args)throws Exception
	{
		// This is the main method of the transmitter side. We call the handshaking method from here.
		//We create a DatagramSocket object here and pass it to the handshaking method.
		
		DatagramSocket TxSocket = new DatagramSocket(9055);		
		Handshaking s1 =new Handshaking();
		
		/* The return type of Handshake method is  HandshakeObject. This object contains vital information
		 * of nonce, initial sequence number and a boolean value which tells us if the authentication
		 * has been successful*/
		
		
		HandshakeObject ob=s1.handshake(TxSocket);
		
		/* The method ob.getAuthenticate() returns true if authentication is successful else it returns false
		 * We start the data transfer phase if the handshaking process is successful, else we close the 
		 * communication attempt*/
		
		if(ob.getAuthenticate())
		{			
			DataTransfer df = new DataTransfer(ob.getinseq(), ob.getNonce() );
			df.sendData();
		}
		else
		{
			
			System.out.println("system.exit");
			System.exit(0);
		}
		
		// TODO Auto-generated method stub

	}

}

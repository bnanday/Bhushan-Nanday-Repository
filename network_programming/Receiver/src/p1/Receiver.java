package p1;
import java.net.*;
public class Receiver {

	public static void main(String[] args)throws Exception
	{
		/*
		 * This is the main method of Receiver side. It first calls the handshaking method by passing RxSocket
		 * as a parameter
		 */
		DatagramSocket RxSocket = new DatagramSocket(9056);
		
		Handshaking h = new Handshaking();
		h.handshake(RxSocket);



	}

}

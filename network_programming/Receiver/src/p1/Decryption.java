package p1;

public class Decryption
{
	public static byte[] decrypt(byte[] nonce, byte[]data)
	{
		byte[] decrypteddata = new byte[351];
		byte[] secretkey={32,32,32,32,32,32,32,32};
		
byte[] key=new byte[16];
		
		int i=0;
		int j=0;
		int counter=0;
				
		while(counter<15)
		{
			key[counter]=secretkey[j];
			key[counter+1]=nonce[i];
			j++;
			i++;
			counter=counter+2;
			
		}
		
		// initializing s
		
		byte[] S = new byte[256];
		byte[] T = new byte[256];
		
		for(int k=0; k<256; k++)
		{
			S[k]=(byte)k;
			T[k]=(byte)Math.abs((key[k%16]));
		}
		
		// Initial Permutation
		
		int m=0;
		byte temp;
		
		for(int n=0; n<256; n++)
		{
			m=Math.abs((m+S[n]+T[n])%256);
			temp=S[m];
			S[m]=S[n];
			S[n]=temp;
		}
		
		// decryption
		
		int a=0,b=0,keycounter=0,t, datacounter=0;
		byte k;
		
		while(keycounter<351)
		{
			a=Math.abs((a+1)%256);
			b=Math.abs((b+S[a])%256);
			
			temp=S[a];
			S[a]=S[b];
			S[b]=temp;
			
			t=Math.abs((S[a]+S[b])%256);
			k=S[t];
			
			decrypteddata[datacounter]=(byte)(data[datacounter]^k);
			datacounter++;
			keycounter++;
			
			
			
		}
		
		return decrypteddata;
		
		
	}

}

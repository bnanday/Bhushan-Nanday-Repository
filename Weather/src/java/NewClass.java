
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.parser.JSONParser;
        

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bhushan
 */
public class NewClass
{
    // First set the base url and then append the location string to it.
    
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    
    
    public static String getWeatherInfo(String location) {
	        HttpURLConnection con = null ;
	        InputStream is = null;
	 
	        try {
                    // to get the temperature in Fahrenheits, we nee to specify the units as imperial at the end of the url string.
	            con = (HttpURLConnection) ( new URL(BASE_URL + location + "&units=imperial")).openConnection();
	            con.setRequestMethod("GET");
	            con.setDoInput(true);
	            con.setDoOutput(true);
	            con.connect();
	 
	            // Let's read the response. The response that we get is in the form of a JSON string.
	            StringBuffer buffer = new StringBuffer();
	            is = con.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            String line = null;
	            while (  (line = br.readLine()) != null )
	                buffer.append(line + "\r\n");
	 
	            is.close();
	            con.disconnect();
	            return buffer.toString();// This string is then returned to the servlet which after parsing extracts the temperature.
	        }
	        catch(Throwable t) {
	            t.printStackTrace();
	        }
	        finally {
	            try { is.close(); } catch(Throwable t) {}
	            try { con.disconnect(); } catch(Throwable t) {}
	        }
	 
	        return null;
	 
	    }
    
}

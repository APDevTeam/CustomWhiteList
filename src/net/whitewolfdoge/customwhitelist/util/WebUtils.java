package net.whitewolfdoge.customwhitelist.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * This class is for housing web utility code.
 *
 */
public class WebUtils{
	/**
	 * This method is for downloading a page and returning it as a String
	 * @param	URL source			The URL of the page to be downloaded
	 * @return	String response		The entire page in a String
	 * @throws	IOException			When something goes terribly wrong
	 */
	public static String downloadPage(URL source) throws IOException{
		try{
			HttpURLConnection conn = (HttpURLConnection)source.openConnection();
			// Set timeouts
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(2500);
			
			// I'll pretend that I'm wget from cygwin!
			conn.setRequestProperty("User-Agent", "Wget/1.16.2 (cygwin)");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Accept-Encoding", "identity");
			conn.setRequestProperty("Host", "mcuuid.net");
			conn.setRequestProperty("Connection", "Keep-Alive");
		
			conn.setRequestMethod("GET");
			
			if(!conn.getResponseMessage().contains("OK")){ // If it wasn't okay
				throw new IOException(); // Then we give up
			}
			
			StringBuilder sb = new StringBuilder();
			
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String resp;
			while((resp = br.readLine()) != null){ // While what we are reading is data
				sb.append(resp); // Add the data
			}
			
			return sb.toString();
		}
		catch(SocketTimeoutException stex){
			throw new IOException();
		}
	}
}

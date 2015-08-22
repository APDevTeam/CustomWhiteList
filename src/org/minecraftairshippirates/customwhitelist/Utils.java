package org.minecraftairshippirates.customwhitelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is for housing utility methods that are publicly accessible
 *
 */
public class Utils{
	/**
	 * This method is for downloading a page and returning it as a String
	 * @param	URL source			The URL of the page to be downloaded
	 * @return	String response		The entire page in a String
	 * @throws	IOException			When something goes terribly wrong
	 */
	public static String downloadPage(URL source) throws IOException{
		HttpURLConnection conn = (HttpURLConnection)source.openConnection();
		
		// I'll pretend that I'm wget from cygwin!
		conn.setRequestProperty("User-Agent", "Wget/1.16.2 (cygwin)");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Accept-Encoding", "identity");
		conn.setRequestProperty("Host", "mcuuid.net");
		conn.setRequestProperty("Connection", "Keep-Alive");
	
		conn.setRequestMethod("GET");
		
		StringBuilder sb = new StringBuilder();
		
		InputStream is = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String resp;
		while((resp = br.readLine()) != null){ // While what we are reading is data
			sb.append(resp); // Add the data
		}
		
		return sb.toString();
	}
}

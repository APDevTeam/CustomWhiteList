package org.minecraftairshippirates.customwhitelist;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is to be the home for all methods relating to fetching UUIDs
 * from mcuuid.net.
 *
 */
public class UUIDFetcher{
	/**
	 * The following method is to get a UUID from any accessible source.
	 * Note that due to the nature of downloading data, this method may take
	 * unpredictable times to return.
	 * @param	String username		The username of the player
	 * @return	String UUID			The UUID of the player
	 */
	public static String getUUID(String username) throws UUIDNotFoundException{
		String UUID = null;
		
		// The idea here is to try the next method if one runs into an error.
		
		// Try searching mcuuid.net
		UUID = fetchFromMCUUIDnet(username);
		
		if(UUID == null){ // If nothing was found
			 throw new UUIDNotFoundException();
		}
		
		return UUID;
	}
	
	/**
	 * The following method is to get a UUID from mcuuid.net. Note that due
	 * to the nature of downloading data, this method may take unpredictable
	 * times to return.
	 * @param	String username		The Username of the player
	 * @return	String uuid			The UUID of the player
	 */
	private static String fetchFromMCUUIDnet(String username) throws UUIDNotFoundException{
		
		// Download the page
		URL pageurl;
		try{
			pageurl = new URL("http://mcuuid.net/?q=" + username);
		}
		catch(MalformedURLException mue){
			// This exception really should not happen!
			throw new UUIDNotFoundException();
		}
		
		String page = null;
		try{
			page = Utils.downloadPage(pageurl);
		}
		catch(IOException e){
			// If this happens, there probably was a connection error.
			throw new UUIDNotFoundException();
		}
		
		// Find the UUID if it's there
		int indexOfUUID = page.indexOf("Full UUID:");
		
		if(indexOfUUID == -1){ // If the UUID cannot be found
			throw new UUIDNotFoundException();
		}
		
		// Trim the page
		page = page.substring(indexOfUUID);
		
		// Moving up to the UUID
		indexOfUUID = page.indexOf("value=\"");
		
		// Trim the page down to the beginning of the value area
		page = page.substring(indexOfUUID);
		
		// Move up to the starting quote
		indexOfUUID = page.indexOf('\"');
		
		// Trim the page down to the beginning of the UUID
		page = page.substring(indexOfUUID + 1);
		
		// Find the end of the UUID
		int endOfUUID = page.indexOf('\"');
		
		// Trim the page down to the UUID
		page = page.substring(0, endOfUUID);
		
		return page;
	}
}

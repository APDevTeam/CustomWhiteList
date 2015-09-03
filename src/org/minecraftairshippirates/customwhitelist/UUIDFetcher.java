package org.minecraftairshippirates.customwhitelist;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * This class is to be the home for all methods relating to fetching UUIDs.
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
		try{
			UUID = fetchFromMCUUIDnet(username);
		}
		catch(IOException e){
			// There was probably a connection issue, just skip this source
		}
		catch(UUIDNotFoundException unfex){
			throw unfex;
		}
		catch(Exception ex){
			// There was some other issue
			System.err.println("There was an error trying to get a UUID from mcuuid.net:");
			ex.printStackTrace();
		}
		
		// Try searching mcuuid.com
		try{
			UUID = fetchFromMCUUIDcom(username).toString();
		}
		catch(IOException e){
			// There was probably a connection issue, just skip this source
		}
		catch(UUIDNotFoundException unfex){
			throw unfex;
		}
		catch(Exception ex){
			// There was some other issue
			System.err.println("There was an error trying to get a UUID from mcuuid.com:");
			ex.printStackTrace();
		}
		
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
	private static String fetchFromMCUUIDnet(String username) throws Exception, IOException, UUIDNotFoundException{
		
		// Download the page
		URL pageurl;
		try{
			pageurl = new URL("http://mcuuid.net/?q=" + username);
		}
		catch(MalformedURLException mue){
			// This exception really should not happen!
			throw new Exception();
		}
		
		String page = null;
		try{
			page = Utils.downloadPage(pageurl);
		}
		catch(IOException e){
			// If this happens, there probably was a connection error or timeout.
			throw new IOException();
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
	
	/**
	 * The following method is to get a UUID from mcuuid.com. Note that due
	 * to the nature of downloading data, this method may take unpredictable
	 * times to return.
	 * @param	String username		The Username of the player
	 * @return	String uuid			The UUID of the player
	 */
	private static UUID fetchFromMCUUIDcom(String username) throws Exception, IOException, UUIDNotFoundException{
		
		// Download the page
		URL pageurl;
		try{
			pageurl = new URL("http://mcuuid.com/api/" + username);
		}
		catch(MalformedURLException mue){
			// This exception really should not happen!
			throw new Exception();
		}
		
		String page = null;
		try{
			page = Utils.downloadPage(pageurl);
		}
		catch(IOException e){
			// If this happens, there probably was a connection error or timeout.
			throw new IOException();
		}
		
		// Find the UUID if it's there
		int indexOfUUID = page.indexOf("\"uuid_formatted\":\"");
		
		if(indexOfUUID == -1){ // If the UUID cannot be found
			throw new UUIDNotFoundException();
		}
		
		// Move the marker to the beginning of the formatted UUID
		indexOfUUID += "\"uuid_formatted\":\"".length();
		
		// Trim the page's end off
		try{
			page = page.substring(indexOfUUID, indexOfUUID + 36);
		}
		catch(StringIndexOutOfBoundsException sioobex){
			throw new UUIDNotFoundException();
		}
		
		try{
			UUID uuid = UUID.fromString(page);
			return uuid;
		}
		catch(IllegalArgumentException iaex){
			throw new UUIDNotFoundException();
		}
	}
}

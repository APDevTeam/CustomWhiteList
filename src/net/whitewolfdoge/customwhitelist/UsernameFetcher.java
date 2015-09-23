package net.whitewolfdoge.customwhitelist;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * This class is to be the home for all methods relating to fetching usernames.
 */
public class UsernameFetcher{
	/**
	 * The following method is to get a username from any accessible source.
	 * Note that due to the nature of downloading data, this method may take
	 * unpredictable times to return.
	 * @param	UUID uuid			The UUID of the player
	 * @return	String username		The username of the player
	 */
	public static String getUsername(UUID uuid) throws UsernameNotFoundException{
		String username = null;
		
		// The idea here is to try the next method if one runs into an error.
		
		// Try searching mcuuid.net
		try{
			username = fetchFromMCUUIDnet(uuid);
		}
		catch(IOException ioex){
			// There was probably a connection issue, just skip this source
		}
		catch(UsernameNotFoundException unnfex){
			throw unnfex;
		}
		catch(Exception ex){
			// There was some other issue
			System.err.println("There was an error trying to get a username from mcuuid.net:");
			ex.printStackTrace();
		}
		
		// Try searching mcuuid.com
		try{
			username = fetchFromMCUUIDcom(uuid);
		}
		catch(IOException ioex){
			// There was probably a connection issue, just skip this source
		}
		catch(UsernameNotFoundException unnfex){
			throw unnfex;
		}
		catch(Exception ex){
			// There was some other issue
			System.err.println("There was an error trying to get a username from mcuuid.com:");
			ex.printStackTrace();
		}
		
		if(username == null){ // If nothing was found
			throw new UsernameNotFoundException();
		}
		
		return username;
	}
	
	/**
	 * The following method is to get a username from mcuuid.net. Note that due
	 * to the nature of downloading data, this method may take unpredictable
	 * times to return.
	 * @param	UUID uuid			The UUID of the player
	 * @return	String username		The username of the player
	 */
	private static String fetchFromMCUUIDnet(UUID uuid) throws Exception, IOException, UsernameNotFoundException, IOException{
		// Download the page
		URL pageurl;
		try{
			pageurl = new URL("http://mcuuid.net/?q=" + uuid.toString());
		}
		catch(MalformedURLException mue){
			// This exception really should not happen!
			throw new Exception();
		}
		
		String page = null;
		try{
			page = Utils.downloadPage(pageurl);
		}
		catch(IOException ioex){
			// If this happens, there probably was a connection error or timeout.
			throw new IOException();
		}

		// See if we found anything
		if(page.indexOf("Full UUID:") == -1){
			throw new UsernameNotFoundException();
		}
		
		// Cut off the footer, it will interfere with the method
		page = page.substring(0, page.indexOf("<footer>"));
		
		int indexOfUsername = 4 + page.indexOf("<h3>");
		String username = page.substring(indexOfUsername, page.indexOf("</h3>"));
		
		return username;
	}
	
	/**
	 * The following method is to get a username from mcuuid.com. Note that due
	 * to the nature of downloading data, this method may take unpredictable
	 * times to return.
	 * @param	UUID uuid			The UUID of the player
	 * @return	String username		The username of the player
	 */
	private static String fetchFromMCUUIDcom(UUID uuid) throws Exception, IOException, UsernameNotFoundException, IOException{
		// Download the page
		URL pageurl;
		try{
			pageurl = new URL("http://mcuuid.com/api/" + uuid.toString());
		}
		catch(MalformedURLException mue){
			// This exception really should not happen!
			throw new Exception();
		}
		
		String page = null;
		try{
			page = Utils.downloadPage(pageurl);
		}
		catch(IOException ioex){
			// If this happens, there probably was a connection error or timeout.
			throw new IOException();
		}

		// See if we found anything
		int indexOfUsername = page.indexOf("\"name\":\"");
		if(indexOfUsername == -1){
			throw new UsernameNotFoundException();
		}
		
		indexOfUsername += "\"name\":\"".length();
		
		try{
			page = page.substring(indexOfUsername);
			page = page.substring(0, page.indexOf("\""));
			return page;
		}
		catch(IndexOutOfBoundsException ioobex){
			throw new UsernameNotFoundException();
		}
	}
}

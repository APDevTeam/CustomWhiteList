package net.whitewolfdoge.customwhitelist.util;

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
	 * @return	UUID uuid			The UUID of the player
	 */
	public static UUID getUUID(String username) throws UUIDResolverNotAvailableException, UUIDNotFoundException, UsernameInvalidException{
		UUIDResolver ur;
		
		// TODO Validate username
		
		// The idea here is to try the next method if one runs into an error.
		
		// Try searching NameMC.com
		try {
			ur = new NameMC_com();
			return ur.getUUID(username);
		}
		catch(UUIDResolverNotAvailableException urnaex){
			// Continue
		}
		
		throw new UUIDResolverNotAvailableException("No UUIDResolver was available to service the request to resolve UUID for \"" + username + '\"'); // If we got this far and no UUID was returned, nothing was found
	}
}

package net.whitewolfdoge.customwhitelist.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


class NameMC_com implements UUIDResolver{
	
	/**
	 * The following method gets a UUID from a username by using the NameMC.com web service
	 * @param	username	The username of the player
	 * @return				The UUID of the player
	 * @throws	UUIDNotFoundException				When a UUID could not be found with the username
	 * @throws	UUIDResolverNotAvailableException	When the NameMC.com web service can't be used at this time
	 * @throws	UsernameInvalidException			When a username has an invalid format
	 */
	@Override
	public UUID getUUID(String username) throws UUIDResolverNotAvailableException, UUIDNotFoundException, UsernameInvalidException{
		
		/*
		 * Test to see if the service is online
		 */
		try{
			URL addr = null;
			try {
				addr = new URL("https://namemc.com/profile/" + username);
			}
			catch(MalformedURLException mfuex){
				throw new UsernameInvalidException();
			}
			// @formatter:off
			String page =			WebUtils.downloadPage(addr);
			String	startMatch =	"class=\"uuid\"",
					startTag =		">",
					endTag =		"</";
			int startIndex =		page.indexOf(startMatch);
			if(startIndex == -1) throw new UUIDNotFoundException(); // If no UUID class element is found, then no UUID was found
			String crop1 =			page.substring(startIndex + startMatch.length() + startTag.length());
			String crop2 =			crop1.substring(0, crop1.indexOf(endTag));
			UUID id =				UUID.fromString(crop2);
			// @formatter:on
			return id;
		}
		catch(IOException | IndexOutOfBoundsException ex){
			throw new UUIDResolverNotAvailableException("The NameMC.com web service can't be used at this time.", ex);
		}
	}
}

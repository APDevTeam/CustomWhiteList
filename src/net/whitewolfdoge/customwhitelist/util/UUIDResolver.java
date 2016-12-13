package net.whitewolfdoge.customwhitelist.util;

import java.util.UUID;

interface UUIDResolver{
	/**
	 * The following method gets a UUID from a username
	 * @param	username	The username of the player
	 * @return				The UUID of the player
	 * @throws	UUIDNotFoundException				When a UUID could not be found with the username
	 * @throws	UUIDResolverNotAvailableException	When the UUID resolver can't be used at this time
	 * @throws	UsernameInvalidException			When a username has an invalid format
	 */
	public UUID getUUID(String username) throws UUIDResolverNotAvailableException, UUIDNotFoundException, UsernameInvalidException;
}

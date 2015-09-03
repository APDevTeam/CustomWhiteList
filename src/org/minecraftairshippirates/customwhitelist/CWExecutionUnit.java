package org.minecraftairshippirates.customwhitelist;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * This class is to house all necessary components for executing a CW function
 */
/**
 * @author white
 *
 */
public class CWExecutionUnit{
	public static final int TYPE_ADD_USER_BY_NAME = 0,
		TYPE_ADD_USER_BY_UUID = 1,
		
		TYPE_REMOVE_USER_BY_NAME = 2,
		TYPE_REMOVE_USER_BY_UUID = 3,
		
		TYPE_CHECK_USER_BY_NAME = 4,
		TYPE_CHECK_USER_BY_UUID = 5,
		TYPE_CHECK_USER_BY_UUID_WITH_RESOLVE = 6,
		
		TYPE_LIST_WITHOUT_RESOLVE = 7,
		TYPE_LIST_WITH_RESOLVE = 8;
	
	private static final int[] VALID_TYPES = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	
	private final CustomWhitelistPlugin cwp;
	private final int type;
	private final CommandSender sender;
	private final String[] subCmdArgs, subCmdOpts;
	
	CWExecutionUnit(CustomWhitelistPlugin newCWP, int newType, CommandSender newSender, String[] newSubCmdArgs, String[] newSubCmdOpts) throws InvalidCWEUTypeException{
		boolean typeIsValid = false;
		for(int i : VALID_TYPES){ // For every element in VALID_TYPES
			if(i == newType){ // If the current element matches newType
				typeIsValid = true; // Mark it as valid
				break; // Then exit the loop
			}
		}
		if(!typeIsValid){ // If the type is not valid
			throw new InvalidCWEUTypeException();
		}
		cwp = newCWP;
		type = newType;
		sender = newSender;
		subCmdArgs = newSubCmdArgs;
		subCmdOpts = newSubCmdOpts;
	}
	
	/**
	 * This method is to be called to process the CWEU
	 */
	public void process(){
		if((type == TYPE_ADD_USER_BY_NAME) || (type == TYPE_ADD_USER_BY_UUID)){ // If add type
			processAdd();
		}
		else if((type == TYPE_REMOVE_USER_BY_NAME) || (type == TYPE_REMOVE_USER_BY_UUID)){ // If remove type
			processRemove();
		}
		else if((type == TYPE_CHECK_USER_BY_NAME) || (type == TYPE_CHECK_USER_BY_UUID) || (type == TYPE_CHECK_USER_BY_UUID_WITH_RESOLVE)){ // If check type
			try{
				processCheck();
			}
			catch(Exception ex){
				System.err.println("There was an exception when trying to check with resolve:");
				ex.printStackTrace();
			}
		}
		else if((type == TYPE_LIST_WITHOUT_RESOLVE) || (type == TYPE_LIST_WITH_RESOLVE)){ // If list type
			try{
				processList();
			}
			catch(Exception ex){
				System.err.println("There was an exception when trying to list with resolve:");
				ex.printStackTrace();
			}
		}
		else{ // Else we don't know what is going on
			// Flail and be confused
		}
	}
	
	/**
	 * This method is to get a simple description of the CWEU's task
	 */
	public String getDescription(){
		if(type == TYPE_ADD_USER_BY_NAME){
			return "ADD_USER_BY_NAME:" + subCmdArgs[0];
		}
		else if(type == TYPE_ADD_USER_BY_UUID){
			return "ADD_USER_BY_UUID:" + subCmdArgs[0];
		}
		else if(type == TYPE_REMOVE_USER_BY_NAME){
			return "REMOVE_USER_BY_NAME:" + subCmdArgs[0];
		}
		else if(type == TYPE_REMOVE_USER_BY_UUID){
			return "REMOVE_USER_BY_UUID:" + subCmdArgs[0];
		}
		else if(type == TYPE_CHECK_USER_BY_NAME){
			return "CHECK_USER_BY_NAME:" + subCmdArgs[0];
		}
		else if(type == TYPE_CHECK_USER_BY_UUID){
			return "CHECK_USER_BY_UUID:" + subCmdArgs[0];
		}
		else if(type == TYPE_CHECK_USER_BY_UUID_WITH_RESOLVE){
			return "CHECK_USER_BY_UUID_WITH_RESOLVE:" + subCmdArgs[0];
		}
		else if(type == TYPE_LIST_WITHOUT_RESOLVE){
			return "LIST_WITHOUT_RESOLVE:" + subCmdArgs[0];
		}
		else if(type == TYPE_LIST_WITH_RESOLVE){
			return "LIST_WITH_RESOLVE:" + subCmdArgs[0];
		}
		else{
			return "UNKNOWN_TYPE:" + subCmdArgs[0];
		}
	}
	
	/**
	 * This method is to handle processing add type CWEUs
	 */
	private void processAdd(){
		if(type == TYPE_ADD_USER_BY_NAME){
			try{ // Try to get the UUID
				String stuuid = UUIDFetcher.getUUID(subCmdArgs[0]);
				try{
					UUID uuid = UUID.fromString(stuuid);
					OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
					if(ofp.isWhitelisted()){ // If the player is already on the whitelist
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is already on the whitelist.");
					}
					else{ // They're to be added
						ofp.setWhitelisted(true);
						sender.sendMessage('\"' + subCmdArgs[0] + "\" was added to the whitelist.");
					}
				}
				catch(IllegalArgumentException iaex){
					sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to add a user by name, see the log for details.");
					cwp.getLogger().warning("There was an exception preprocessing trying to add a user by name: " + subCmdArgs[0]);
					iaex.printStackTrace();
				}
			}
			catch(UUIDNotFoundException unfex){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be added to the whitelist.");
			}
		}
		else if(type == TYPE_ADD_USER_BY_UUID){
			try{
				UUID uuid = UUID.fromString(subCmdArgs[0]);
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted()){ // If the player is already on the whitelist
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is already on the whitelist.");
				}
				else{ // They're to be added
					ofp.setWhitelisted(true);
					sender.sendMessage('\"' + subCmdArgs[0] + "\" was added to the whitelist.");
				}
			}
			catch(IllegalArgumentException iaex){
				sender.sendMessage(ChatColor.RED + "There was an exception addding a user by UUID, see the log for details.");
				cwp.getLogger().warning("There was an exception addding a user by UUID: " + subCmdArgs[0]);
				iaex.printStackTrace();
			}
		}
		else; // Else it wasn't supposed to be here
	}
	
	/**
	 * This method is to handle processing remove type CWEUs
	 */
	private void processRemove(){
		if(type == TYPE_REMOVE_USER_BY_NAME){
			try{ // Try to get the UUID
				String stuuid = UUIDFetcher.getUUID(subCmdArgs[0]);
				try{
					UUID uuid = UUID.fromString(stuuid);
					OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
					if(!ofp.isWhitelisted()){ // If the player is not on the whitelist
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
					}
					else{ // They're to be removed
						ofp.setWhitelisted(false);
						sender.sendMessage('\"' + subCmdArgs[0] + "\" was removed from the whitelist.");
					}
				}
				catch(IllegalArgumentException iaex){
					sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to remove a user by name, see the log for details.");
					cwp.getLogger().warning("There was an exception preprocessing trying to remove a user by name: " + subCmdArgs[0]);
					iaex.printStackTrace();
				}
			}
			catch(UUIDNotFoundException unfe){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be removed from the whitelist.");
			}
		}
		else if(type == TYPE_REMOVE_USER_BY_UUID){
			try{
				UUID uuid = UUID.fromString(subCmdArgs[0]);
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(!ofp.isWhitelisted()){ // If the player is not on the whitelist
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
				}
				else{ // They're to be removed
					ofp.setWhitelisted(false);
					sender.sendMessage('\"' + subCmdArgs[0] + "\" was removed from the whitelist.");
				}
			}
			catch(IllegalArgumentException iaex){
					sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to remove a user by UUID, see the log for details.");
					cwp.getLogger().warning("There was an exception preprocessing trying to remove a user by UUID: " + subCmdArgs[0]);
					iaex.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is to handle processing check type CWEUs
	 */
	private void processCheck() throws Exception{
		if(type == TYPE_CHECK_USER_BY_NAME){
			try{ // Try to get the UUID
				String stuuid = UUIDFetcher.getUUID(subCmdArgs[0]);
				try{
					UUID uuid = UUID.fromString(stuuid);
					OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
					if(ofp.isWhitelisted()){ // If the player is whitelisted
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is on the whitelist.");
					}
					else{ // Else the player is not whitelisted
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
					}
				}
				catch(IllegalArgumentException iaex){
					sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to check a user by name, see the log for details.");
					cwp.getLogger().warning("There was an exception preprocessing trying to check a user by name: " + subCmdArgs[0]);
					iaex.printStackTrace();
				}
			}
			catch(UUIDNotFoundException unfex){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be added to the whitelist.");
			}
		}
		else if(type == TYPE_CHECK_USER_BY_UUID){
			try{
				UUID uuid = UUID.fromString(subCmdArgs[0]);
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted()){ // If the player is whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is on the whitelist.");
				}
				else{ // Else the player is not whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
				}
			}
			catch(IllegalArgumentException iaex){
				sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to check a user by name, see the log for details.");
				cwp.getLogger().warning("There was an exception preprocessing trying to check a user by name: " + subCmdArgs[0]);
				iaex.printStackTrace();
			}
		}
		else if(type == TYPE_CHECK_USER_BY_UUID_WITH_RESOLVE){
			boolean resolve = false;
			for(String opt : subCmdOpts){ // For every option
				if(opt.equalsIgnoreCase("-r")){
					resolve = true;
				}
			}
			if(!resolve){ // If resolve is off
				throw new Exception();
			}
			try{
				UUID uuid = UUID.fromString(subCmdArgs[0]);
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				try{ // Try to resolve the username
					String username = UsernameFetcher.getUsername(uuid);
					if(ofp.isWhitelisted()){ // If the player is whitelisted
						sender.sendMessage('\"' + username + "\" is on the whitelist.");
					}
					else{ // Else the player is not whitelisted
						sender.sendMessage('\"' + username + "\" is not on the whitelist.");
					}
				}
				catch(UsernameNotFoundException unnfex){
					if(ofp.isWhitelisted()){ // If the player is whitelisted
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is on the whitelist.");
					}
					else{ // Else the player is not whitelisted
						sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
					}
				}
			}
			catch(IllegalArgumentException iaex){
				sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to check a user by name, see the log for details.");
				cwp.getLogger().warning("There was an exception preprocessing trying to check a user by name: " + subCmdArgs[0]);
				iaex.printStackTrace();
			}
		}
		else; // Else it wasn't supposed to be here
	}
	
	/**
	 * This method is to handle processing list type CWEUs
	 * @throws Exception When resolve was requested by the plugin but not the sender
	 */
	private void processList() throws Exception{
		OfflinePlayer[] wlofps = cwp.getServer().getWhitelistedPlayers().toArray(new OfflinePlayer[cwp.getServer().getWhitelistedPlayers().size()]);
		StringBuilder sb = new StringBuilder();
		sb.append("There are " + wlofps.length + " whitelisted players:");
		if(type == TYPE_LIST_WITHOUT_RESOLVE){
			for(OfflinePlayer ofp : wlofps){ // For every element in the array
				UUID uuid = ofp.getUniqueId();
				sb.append('\n' + uuid.toString());
			}
			sender.sendMessage(sb.toString());
		}
		else if(type == TYPE_LIST_WITH_RESOLVE){
			boolean resolve = false;
			for(String opt : subCmdOpts){ // For every option
				if(opt.equalsIgnoreCase("-r")){
					resolve = true;
				}
			}
			if(!resolve){ // If resolve is off
				throw new Exception();
			}
			for(OfflinePlayer ofp : wlofps){ // For every element in the array
				UUID uuid = ofp.getUniqueId();
				try{ // Try to resolve the username
					String username = UsernameFetcher.getUsername(uuid);
					sb.append('\n' + username);
				}
				catch(UsernameNotFoundException unnfex){ // The username of the UUID could not be resolved
					sb.append('\n' + uuid.toString());
				}
			}
			sender.sendMessage(sb.toString());
		}
		else; // Else it wasn't supposed to be here
	}
}

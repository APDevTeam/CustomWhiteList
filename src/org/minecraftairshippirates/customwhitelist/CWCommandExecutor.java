package org.minecraftairshippirates.customwhitelist;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * This class is to handle the commands sent to the plugin.
 *
 */
public class CWCommandExecutor implements CommandExecutor{
	private final String MSG_TOO_MANY_ARGS = "Too many arguments!",
					MSG_INVALID_OPTION = "That option is not valid for this command!",
					MSG_INSUFFICIENT_PERMS = "You don't have permission to do that!",
					MSG_ADD_USAGE = "Usage: /customwhitelist add <player>",
					MSG_REMOVE_USAGE = "Usage: /customwhitelist remove <player>",
					MSG_CHECK_USAGE = "Usage: /customwhitelist check <player>";
	
	private final CustomWhitelistPlugin cwp;
	
	/**
	 *This constructor is to create a new CWCE.
	 * @param CustomWhitelistPlugin newCWP	The CW plugin instance
	 */
	public CWCommandExecutor(CustomWhitelistPlugin newCWP){
		cwp = newCWP;
	}
	
	/**
	 * This method is to process the commands sent to this CWCE.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("customwhitelist") || cmd.getName().equalsIgnoreCase("cw")){ // If the command was "customwhitelist" or "cw"
			
			// Process information
			try{ // Try preprocessing the command
				String subCmd = getSubCmd(args);
				String[] subCmdArgs = getSubCmdArgs(args);
				String[] subCmdOptions = getSubCmdOptions(args);
				
				try{ // Try processing the command
					if(subCmd == null){ // A subcommand was not found
						return false;
					}
					
					// Run a subcommand if there is one
					if(subCmd.equalsIgnoreCase("add")){ // If the subcommand was "add"
						return add(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("remove")){ // If the subcommand was "remove"
						return remove(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("check")){ // If the subcommand was "check"
						return check(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("list")){ // If the subcommand was "list"
						return list(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("on")){ // If the subcommand was "on"
						return on(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("off")){ // If the subcommand was "off"
						return off(sender, subCmdArgs, subCmdOptions);
					}
					else if(subCmd.equalsIgnoreCase("reload")){ // If the subcommand was "reload"
						return reload(sender, subCmdArgs, subCmdOptions);
					}
					else{ // Subcommand not recognized
						return false;
					}
				}
				catch(Exception ex){
					cwp.getLogger().warning("There was an exception executing a CW subcommand: " + subCmd);
					ex.printStackTrace();
					return true;
				}
			}
			catch(Exception ex){
				cwp.getLogger().warning("There was an exception preprocessing CW subcommands: ");
				ex.printStackTrace();
				return true;
			}
		}
		else{ // Command not recognized by this plugin
			return false;
		}
	}
	
	/**
	 * This method is to get the subcommand sent to the CWCE
	 * @param args				The args sent to the CWCE
	 * @return String subCmd	The subcommand if there is one, else null
	 */
	private String getSubCmd(String[] args){
		for(String cur : args){
			if(!cur.startsWith(String.valueOf('-'))){ // If the element does not start with '-'
				return cur;
			}
		}
		
		return null;
	}
	
	/**
	 * This method is to get the subcommand arguments sent to the CWCE
	 * @param args					The args sent to the CWCE
	 * @return String[] subCmdArgs	The subcommand arguments 
	 */
	private String[] getSubCmdArgs(String[] args){
		String[] subCmdArgs;
		
		// The number of subcommand arguments must be known to create an array of the proper length, so they will be counted.
		int subCmdArgsCount = -1; // This is set to -1 because the technique used also counts the subcommand, which we must discount.
		for(String arg : args){
			if(!arg.startsWith(String.valueOf('-'))){ // If the element does not start with '-'
				subCmdArgsCount++;
			}
		}
		
		if(subCmdArgsCount < 1){ // If there is less than one subcommand argument, there's nothing to copy over.
			subCmdArgs = new String[0];
		}
		else{ // Else there are things that will be copied over
			subCmdArgs = new String[subCmdArgsCount];
			int subCmdArgsNext = -1; // This is set to -1 because the technique used also counts the subcommand, which we must discount.
			for(String arg : args){
				if(!arg.startsWith(String.valueOf('-'))){ // If the element does not start with '-'
					if(subCmdArgsNext == -1){ // If this is the first one to be copied
						subCmdArgsNext++;
						// Skip it, because it's the subcommand which is not a subcommand argument
					}
					else{ // It's not the first one, and actually is a subcommand argument
						subCmdArgs[subCmdArgsNext++] = arg; // Copy it to the next space in subCmdArgs, and increment subCmdArgsNext.
					}
				}
			}
		}
		
		return subCmdArgs;
	}
	
	/**
	 * This method is to get the subcommand options sent to the CWCE
	 * @param args						The args sent to the CWCE
	 * @return String[] subCmdOptions	The subcommand options
	 */
	private String[] getSubCmdOptions(String[] args){
		String[] subCmdOptions;
		
		// The number of subcommand options must be known to create an array of the proper length, so they will be counted.
		int subCmdOptionsCount = 0;
		for(String arg : args){
			if(arg.startsWith(String.valueOf('-'))){ // If the element starts with '-'
				subCmdOptionsCount++;
			}
		}
		
		if(subCmdOptionsCount == 0){ // If there are no subcommand options, there's nothing to copy over.
			subCmdOptions = new String[0];
		}
		else{ // Else there are things that will be copied over
			subCmdOptions = new String[subCmdOptionsCount];
			int subCmdOptionsNext = 0;
			for(String arg : args){
				if(arg.startsWith(String.valueOf('-'))){ // If the element starts with '-'
					subCmdOptions[subCmdOptionsNext++] = arg; // Copy it to the next space in subCmdOptions, and increment subCmdOptionsNext
				}
			}
		}
		
		return subCmdOptions;
	}
	
	/**
	 * This method is to handle the add subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean add(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.add")){ // If the sender doesn't have the permission for the "add" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			sender.sendMessage(ChatColor.RED + MSG_ADD_USAGE);
			return true;
		}
		else if(subCmdArgs.length == 0){ // Else if there is no users listed
			sender.sendMessage(ChatColor.RED + MSG_ADD_USAGE);
			return true;
		}
		else{ // Else there was one or more players to be added
			for(String user : subCmdArgs){
				if(!(user.length() > 20)){ // If user is longer than 16 characters, it's too long to be a username, using 20 just to be safe
					if(user.length() > 16){ // If the username is invalid
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid username.");
					}
					else{ // Else the username is valid
						try{
							CWExecutionUnit cweu = new CWExecutionUnit(cwp, CWExecutionUnit.TYPE_ADD_USER_BY_NAME, sender, new String[]{user}, new String[0]);
							cweu.process(); // TODO Make queue this
						}
						catch(InvalidCWEUTypeException icweutex){
							sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to add a user by name, see the log for details.");
							cwp.getLogger().warning("There was an exception preprocessing trying to add a user by name: " + user);
							icweutex.printStackTrace();
						}
					}
				}
				else{ // Else we assume it's a UUID
					// Remove any dashes
					String stuuid = user.replace(String.valueOf('-'), "");
					
					// Verify length
					if(stuuid.length() != 32){ // If stuuid is not the proper length
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID ");
					}
					else{ // We assume it's a valid UUID
						// Add dashes in their proper locations
						stuuid = stuuid.substring(0, 8) + '-' + // Eight
							stuuid.substring(8, 12) + '-' + // Four
							stuuid.substring(12, 16) + '-' + // Four
							stuuid.substring(16, 20) + '-' + // Four
							stuuid.substring(20, 32); // Twelve
						try{
							UUID uuid = UUID.fromString(stuuid);
							try{
								CWExecutionUnit cweu = new CWExecutionUnit(cwp, CWExecutionUnit.TYPE_ADD_USER_BY_UUID, sender, new String[]{uuid.toString()}, new String[0]);
								cweu.process();
							}
							catch(InvalidCWEUTypeException icweutex){
								sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to add a user by uuid, see the log for details.");
								cwp.getLogger().warning("There was an exception preprocessing trying to add a user by uuid: " + uuid);
								icweutex.printStackTrace();
							}
						}
						catch(IllegalArgumentException iaex){
							sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID ");
						}
					}
				}
			}
			
			return true;
		}
	}
	
	/**
	 * This method is to handle the remove subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean remove(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.remove")){ // If the sender doesn't have the permission for the "remove" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			sender.sendMessage(ChatColor.RED + MSG_REMOVE_USAGE);
			return true;
		}
		else if(subCmdArgs.length == 1){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(subCmdArgs[0]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted() == false){ // If the player is not whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
				}
				else{ // They're on the whitelist
					ofp.setWhitelisted(false); // Remove them
					sender.sendMessage("Removed \"" + subCmdArgs[0] + "\" from the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be removed from the whitelist.");
			}
			
			return true;
		}
		else if(subCmdArgs.length > 1){ // Too many arguments
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			sender.sendMessage(ChatColor.RED + MSG_REMOVE_USAGE);
			return true;
		}
		else{ // No player was typed
			sender.sendMessage(ChatColor.RED + MSG_REMOVE_USAGE);
			return true;
		}
	}
	
	/**
	 * This method is to handle the check subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean check(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.check")){ // If the sender doesn't have the permission for the "check" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			sender.sendMessage(ChatColor.RED + MSG_CHECK_USAGE);
			return true;
		}
		else if(subCmdArgs.length == 1){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(subCmdArgs[0]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted()){ // If the player is whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is on the whitelist.");
				}
				else{ // Else the player is not whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is not on the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be checked.");
			}
			
			return true;
		}
		else if(subCmdArgs.length > 1){ // Too many arguments
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			sender.sendMessage(ChatColor.RED + MSG_CHECK_USAGE);
			return true;
		}
		else{ // No player was typed
			sender.sendMessage(ChatColor.RED + MSG_CHECK_USAGE);
			return true;
		}
	}
	
	/**
	 * This method is to handle the list command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean list(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.list")){ // If the sender doesn't have the permission for the "list" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			return false;
		}
		else if(subCmdArgs.length == 0){ // There wasn't an argument, list the players
			OfflinePlayer[] wlofps = cwp.getServer().getWhitelistedPlayers().toArray(new OfflinePlayer[cwp.getServer().getWhitelistedPlayers().size()]);
			StringBuilder sb = new StringBuilder();
			sb.append("There are " + wlofps.length + " whitelisted players:");
			for(OfflinePlayer ofp : wlofps){ // For every element in the array
				UUID uuid = ofp.getUniqueId();
				sb.append('\n' + uuid.toString());
			}
			sender.sendMessage(sb.toString());
			
			return true;
		}
		else{ // Else there was an argument
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			return false;
		}
	}
	
	/**
	 * This method is to handle the on subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean on(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.on")){ // If the sender doesn't have the permission for the "on" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			return false;
		}
		else if(subCmdArgs.length == 0){ // There wasn't an argumet, turn on the whitelist
			if(!cwp.getServer().hasWhitelist()){ // If the server does not have a whitelist
				cwp.getServer().setWhitelist(true);
				sender.sendMessage("Whitelisting was turned on.");
			}
			else{ // It's already on
				sender.sendMessage("Whitelisting is already on.");
			}
			
			return true;
		}
		else{ // Else there was an argument
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			return false;
		}
	}
	
	/**
	 * This method is to handle the off subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean off(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.off")){ // If the sender doesn't have the permission for the "off" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			return false;
		}
		else if(subCmdArgs.length == 0){ // There wasn't an argument, turn off the whitelist
			if(cwp.getServer().hasWhitelist()){ // If the server has a whitelist
				cwp.getServer().setWhitelist(false);
				sender.sendMessage("Whitelisting was turned off.");
			}
			else{ // It's already off
				sender.sendMessage("Whitelisting is already off.");
			}
			
			return true;
		}
		else{ // Else there was an argument
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			return false;
		}
	}
	
	/**
	 * This method is to handle the reload subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean reload(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		if(!sender.hasPermission("customwhitelist.reload")){ // If the sender doesn't have the permission for the "reload" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(subCmdOptions.length != 0){ // If there is an option
			sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
			return false;
		}
		else if(subCmdArgs.length == 0){ // There wasn't an argument, reload the whitelist
			cwp.getServer().reloadWhitelist();
			sender.sendMessage("The whitelist has been reloaded");
			return true;
		}
		else{ // Else there was an argument
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			return false;
		}
	}
}

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
			String[] argsCpy = new String[args.length]; // Make new array with the same size as args
			for(int fa = 0; fa < args.length; fa++){ // Copy args and store it in argsCpy
				argsCpy[fa] = args[fa];
			}
			String subCmd = null;
			String[] subCmdArgs;
			String[] subCmdOptions;
			// Find the subcommand and store it
			for(int fb = 0; fb < argsCpy.length; fb++){ // For every element in argsCpy while subcommand is null
				if(!argsCpy[fb].startsWith(String.valueOf('-'))){ // If the current string wasn't prefixed with '-'
					subCmd = argsCpy[fb]; // Store subcommand from argsCpy
					argsCpy[fb] = null; // Nullify subcommand from argsCpy (This pretty much removes it)
					fb = argsCpy.length; // Stop the array from running
				}
			}
			// Find the subcommand args and count them
			int subCmdArgsCount = 0;
			for(int fc = 0; fc < argsCpy.length; fc++){ // For every element in argsCpy
				if(argsCpy[fc] == null){ // If the current element is nullified (probably cause it's been removed)
					// Do nothing
				}
				else if(argsCpy[fc].startsWith(String.valueOf('-'))){ // If the current element is not prefixed with '-'
					subCmdArgsCount++; // Count it
				}
				if(!(fc < argsCpy.length)){ // If this is the last iteration
				}
			}
			subCmdArgs = new String[subCmdArgsCount]; // Create an array large enough for all subcommand args
			// Find the subcommand args and store them
			for(int fc = 0; fc < argsCpy.length; fc++){ // For every element in argsCpy
				int subCmdArgsNext = 0;
				if(argsCpy[fc] == null){ // If the current element is nullified (probably cause it's been deleted)
					// Do nothing
				}
				else if(!argsCpy[fc].startsWith(String.valueOf('-'))){ // If the current element is not prefixed with '-'
					subCmdArgs[subCmdArgsNext++] = argsCpy[fc]; // Copy it to the subcommand args array and increment subCmdArgsNext
					argsCpy[fc] = null; // Nullify subcommand arg from argsCpy (This pretty much removes it)
				}
			}
			// The rest of the non-nullified elements must be prefixed with '-' and thus they are subcommand options
			// Count the rest of the elements
			int subCmdOptionsCount = 0;
			for(String arg : argsCpy){
				if(arg == null){  // If the current element is nullified (probably cause it's been deleted)
					// Do nothing
				}
				else{ // Else it must be a subcommand option
					subCmdOptionsCount++; // Count it
				}
			}
			
			// Copy the rest of the elements and store them
			subCmdOptions = new String[subCmdOptionsCount]; // Instantiate subcommand options array large enough for the options
			int subCmdOptionsNext = 0;
			for(String arg : argsCpy){
				if(arg == null){  // If the current element is nullified (probably cause it's been deleted)
					// Do nothing
				}
				else{ // Else it must be a subcommand option
					subCmdOptions[subCmdOptionsNext++] = arg;  // Copy it to the subcommand options array and increment subCmdArgsNext
				}
			}
			argsCpy = null; // Every element should have been processed, so we don't need the copy anymore.
			
			if(subCmd == null){ // A subcommand was not found
				return false;
			}
			
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
		else{ // Command not recognized by this plugin
			return false;
		}
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
		else if(subCmdArgs.length == 1){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(subCmdArgs[0]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted() == true){ // If the player is already whitelisted
					sender.sendMessage('\"' + subCmdArgs[0] + "\" is already on the whitelist.");
				}
				else{ // They're not on the whitelist
					ofp.setWhitelisted(true); // Add them
					sender.sendMessage("Added \"" + subCmdArgs[0] + "\" to the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + subCmdArgs[0] + "\" was not found and could not be added to the whitelist.");
			}
			
			return true;
		}
		else if(subCmdArgs.length > 1){ // Too many arguments
			sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
			sender.sendMessage(ChatColor.RED + MSG_ADD_USAGE);
			return true;
		}
		else{ // No player was typed
			sender.sendMessage(ChatColor.RED + MSG_ADD_USAGE);
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

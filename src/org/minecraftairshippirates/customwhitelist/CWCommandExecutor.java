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
			if(args.length >= 1){ // If there was at least one argument
				if(args[0].equalsIgnoreCase("add")){ // If the subcommand was "add"
					return add(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("remove")){ // If the subcommand was "remove"
					return remove(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("check")){ // If the subcommand was "check"
					return check(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("list")){ // If the subcommand was "list"
					return list(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("on")){ // If the subcommand was "on"
					if(!sender.hasPermission("customwhitelist.on")){ // If the sender doesn't have the permission for the "on" subcommand
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 1){ // Turn on the whitelist
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
				else if(args[0].equalsIgnoreCase("off")){ // If the subcommand was "off"
					if(!sender.hasPermission("customwhitelist.off")){ // If the sender doesn't have the permission for the "off" subcommand
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 1){ // Turn off the whitelist
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
				else if(args[0].equalsIgnoreCase("reload")){ // If the subcommand was "reload"
					if(!sender.hasPermission("customwhitelist.reload")){ // If the sender doesn't have the permission for the "reload" subcommand
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 1){ // Reload the whitelist
						cwp.getServer().reloadWhitelist();
						sender.sendMessage("The whitelist has been reloaded");
						return true;
					}
					else{ // Else there was an argument
						sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
						return false;
					}
				}
				else{ // Subcommand not recognized
					return false;
				}
			}
			else{ // No subcommands
				return false;
			}
		}
		else{ // Command not recognized
			return false;
		}
	}
	
	/**
	 * This method is to handle the add command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean add(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.hasPermission("customwhitelist.add")){ // If the sender doesn't have the permission for the "add" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(args.length == 2){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted() == true){ // If the player is already whitelisted
					sender.sendMessage('\"' + args[1] + "\" is already on the whitelist.");
				}
				else{ // They're not on the whitelist
					ofp.setWhitelisted(true); // Add them
					sender.sendMessage("Added \"" + args[1] + "\" to the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + args[1] + "\" was not found and could not be added to the whitelist.");
			}
			
			return true;
		}
		else if(args.length >= 3){ // Too many arguments
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
	 * This method is to handle the remove command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean remove(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.hasPermission("customwhitelist.remove")){ // If the sender doesn't have the permission for the "remove" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(args.length == 2){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted() == false){ // If the player is not whitelisted
					sender.sendMessage('\"' + args[1] + "\" is not on the whitelist.");
				}
				else{ // They're on the whitelist
					ofp.setWhitelisted(false); // Remove them
					sender.sendMessage("Removed \"" + args[1] + "\" from the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + args[1] + "\" was not found and could not be removed from the whitelist.");
			}
			
			return true;
		}
		else if(args.length >= 3){ // Too many arguments
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
	 * This method is to handle the check command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean check(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.hasPermission("customwhitelist.check")){ // If the sender doesn't have the permission for the "check" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(args.length == 2){ // If there is a player
			try{
				UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
				OfflinePlayer ofp = cwp.getServer().getOfflinePlayer(uuid);
				if(ofp.isWhitelisted()){ // If the player is whitelisted
					sender.sendMessage('\"' + args[1] + "\" is on the whitelist.");
				}
				else{ // Else the player is not whitelisted
					sender.sendMessage('\"' + args[1] + "\" is not on the whitelist.");
				}
			}
			catch(UUIDNotFoundException e){
				sender.sendMessage(ChatColor.RED.toString() + '\"' + args[1] + "\" was not found and could not be checked.");
			}
			
			return true;
		}
		else if(args.length >= 3){ // Too many arguments
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
	 * This method is to handle the check command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean list(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.hasPermission("customwhitelist.list")){ // If the sender doesn't have the permission for the "list" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else if(args.length == 1){ // List the players
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
}

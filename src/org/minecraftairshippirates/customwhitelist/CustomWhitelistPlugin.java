package org.minecraftairshippirates.customwhitelist;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhitelistPlugin extends JavaPlugin{
	private final String MSG_TOO_MANY_ARGS = "Too many arguments!",
		MSG_INSUFFICIENT_PERMS = "You don't have permission to do that!",
		MSG_ADD_USAGE = "Usage: /customwhitelist add <player>",
		MSG_REMOVE_USAGE = "Usage: /customwhitelist remove <player>",
		MSG_CHECK_USAGE = "Usage: /customwhitelist check <player>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("customwhitelist") || cmd.getName().equalsIgnoreCase("cw")){ // If "customwhitelist" or "cw"
			if(args.length >= 1){ // If there was at least one argument
				if(args[0].equalsIgnoreCase("add")){ // If the subcommand was "add"
					if(!sender.hasPermission("customwhitelist.add")){ // If the sender doesn't have the permission for this command
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 2){ // If there is a player
						try{
							UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
							OfflinePlayer ofp = getServer().getOfflinePlayer(uuid);
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
				else if(args[0].equalsIgnoreCase("remove")){ // If the subcommand was "remove"
					if(!sender.hasPermission("customwhitelist.remove")){ // If the sender doesn't have the permission for this command
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 2){ // If there is a player
						try{
							UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
							OfflinePlayer ofp = getServer().getOfflinePlayer(uuid);
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
				else if(args[0].equalsIgnoreCase("check")){ // If the subcommand was "check"
					if(!sender.hasPermission("customWhitelist.check")){ // If the sender doesn't have the permission for this command
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 2){ // If there is a player
						try{
							UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
							OfflinePlayer ofp = getServer().getOfflinePlayer(uuid);
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
}

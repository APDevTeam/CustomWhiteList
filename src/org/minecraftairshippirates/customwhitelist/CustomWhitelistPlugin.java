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
		MSG_ADD_USAGE = "Usage: /customwhitelist add <player>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("customwhitelist") || cmd.getName().equalsIgnoreCase("cw")){ // If "customwhitelist" or "cw"
			if(args.length >= 1){ // If there was at least one argument
				if(args[0].equalsIgnoreCase("add")){ // If the first argument was "add"
					if(!sender.hasPermission("customwhitelist.add")){ // If the sender doesn't have the permission for this command
						sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
						return true;
					}
					else if(args.length == 2){ // If there was a player
						try{
							UUID uuid = UUID.fromString(UUIDFetcher.getUUID(args[1]));
							OfflinePlayer ofp = getServer().getOfflinePlayer(uuid);
							if(ofp.isWhitelisted() == true){ // If the player is already whitelisted
								sender.sendMessage('\"' + args[1] + "\" is already on the whitelist.");
							}
							else{ // They're not on the whitelist
								ofp.setWhitelisted(true); // Add them
							}
							sender.sendMessage("Added \"" + args[1] + "\" to the whitelist.");
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

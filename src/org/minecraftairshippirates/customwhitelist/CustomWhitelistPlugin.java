package org.minecraftairshippirates.customwhitelist;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhitelistPlugin extends JavaPlugin{
	private final String tooManyArgs = "Too many arguments!", insufficientPermissions = "You don't have permission to do that!";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		/**if(cmd.getName().equalsIgnoreCase("customwhitelist") && args.length == 0){ // If nothing was provided with "customwhitelist"
			sender.sendMessage("Usage: customwhitelist add <player>");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("cw") && args.length == 0){ // If nothing was provided with "cw"
			sender.sendMessage("Usage: cw add <player>");
		}
		else return false;**/
		
		
		if(cmd.getName().equalsIgnoreCase("customwhitelist") || cmd.getName().equalsIgnoreCase("cw")){ // If "customwhitelist" or "cw"
			if(args.length >= 1){ // If there was at least one argument
				if(args[0].equalsIgnoreCase("add")){ // If the first argument was "add"
					if(!sender.hasPermission("customwhitelist.add")){
						sender.sendMessage(Color.RED + insufficientPermissions);
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
							sender.sendMessage(Color.RED.toString() + '\"' + args[1] + "\" was not found and could not be added to the whitelist.");
						}
						
						return true;
					}
					else if(args.length >= 3){
						sender.sendMessage(Color.RED + tooManyArgs);
						return false;
					}
					else return false;
				}
				else return false;
			}
			else{ // No arguments to process
				return false;
			}
		}
		else{ // Command not recognized
			return false;
		}
	}
}

package org.minecraftairshippirates.customwhitelist;

import org.bukkit.Color;
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
					if(args.length == 2){ // If there was a player
						
						// TODO whitelist the player
						sender.sendMessage("Working on it!");
						
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
			else return false;
		}
		else return false;
	}
}

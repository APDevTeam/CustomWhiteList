package net.whitewolfdoge.customwhitelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

/**
 * This class is to handle the commands sent to the plugin.
 *
 */
public class CWLCommandExecutor implements TabExecutor{
	private final String MSG_TOO_MANY_ARGS = "Too many arguments!",
					MSG_INVALID_OPTION = "That option is not valid for this command!",
					MSG_INSUFFICIENT_PERMS = "You don't have permission to do that!",
					MSG_ADD_USAGE = "Usage: /customwhitelist add <player>",
					MSG_REMOVE_USAGE = "Usage: /customwhitelist remove <player>",
					MSG_CHECK_USAGE = "Usage: /customwhitelist check <player> [-r]";
	
	private final CustomWhiteListPlugin cwp;
	private final CWLEUExecutor cweuExecutor;
	
	/**
	 *This constructor is to create a new CWCE.
	 * @param CustomWhiteListPlugin newCWP	The CW plugin instance
	 */
	public CWLCommandExecutor(CustomWhiteListPlugin newCWP, CWLEUExecutor newCWEUExecutor){
		cwp = newCWP;
		cweuExecutor = newCWEUExecutor;
	}
	
	/**
	 * This method is to process the commands sent to this CWCE.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("customwhitelist")){ // If the command was "customwhitelist" or one of its aliases
			
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
	 * This method is to get the list of possible commands
	 * @return List<String>	A list of possible commands
	 */
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
		List<String> pos = new ArrayList<String>();
		pos.add("add");
		pos.add("remove");
		pos.add("check");
		pos.add("list");
		pos.add("on");;
		pos.add("off");;
		pos.add("reload");
		
		return pos;
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
		else if(subCmdArgs.length == 0){ // Else if there are no users listed
			sender.sendMessage(ChatColor.RED + MSG_ADD_USAGE);
			return true;
		}
		else{ // Else there is one or more players to be added
			for(String user : subCmdArgs){
				if(!(user.length() > 24)){ // If user is not longer than 24 characters, we assume the sender means a username
					if(user.length() > 16){ // If the username is longer than 16 characters, it is invalid
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid username.");
					}
					else{ // Else the username is valid
						try{
							CWLExecutionUnit cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_ADD_USER_BY_NAME, sender, new String[]{user}, new String[0]);
							try{
								cweuExecutor.add(cweu);
							}
							catch(IllegalStateException isex){
								cwp.getLogger().warning("The task \"" + cweu.getDescription() + "\" could not be queued because the queue was full.");
							}
						}
						catch(InvalidCWLEUTypeException icweutex){
							sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to add a user by name, see the log for details.");
							cwp.getLogger().warning("There was an exception preprocessing trying to add a user by name: " + user);
							icweutex.printStackTrace();
						}
					}
				}
				else{ // Else we assume the sender means a UUID
					// Remove any hyphens
					String stuuid = user.replace(String.valueOf('-'), "");
					
					// Verify length
					if(stuuid.length() != 32){ // If stuuid is not the proper length
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID.");
					}
					else{ // Else we assume it's a valid UUID
						// Add hyphens in their proper locations
						stuuid = stuuid.substring(0, 8) + '-' + // Eight
							stuuid.substring(8, 12) + '-' + // Four
							stuuid.substring(12, 16) + '-' + // Four
							stuuid.substring(16, 20) + '-' + // Four
							stuuid.substring(20, 32); // Twelve
						try{
							UUID uuid = UUID.fromString(stuuid);
							try{
								CWLExecutionUnit cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_ADD_USER_BY_UUID, sender, new String[]{uuid.toString()}, new String[0]);
								cweu.process();
							}
							catch(InvalidCWLEUTypeException icweutex){
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
		else if(subCmdArgs.length == 0){ // Else if there are no users listed
			sender.sendMessage(ChatColor.RED + MSG_REMOVE_USAGE);
			return true;
		}
		else{ // Else there was one or more players to be added
			for(String user : subCmdArgs){
				if(!(user.length() > 24)){ // If user is not longer than 24 characters, we assume the sender means a username
					if(user.length() > 16){ // If the username is longer than 16 characters, it is invalid
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid username.");
					}
					else{ // Else the username is valid
						try{
							CWLExecutionUnit cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_REMOVE_USER_BY_NAME, sender, new String[]{user}, new String[0]);
							try{
								cweuExecutor.add(cweu);
							}
							catch(IllegalStateException isex){
								cwp.getLogger().warning("The task \"" + cweu.getDescription() + "\" could not be queued because the queue was full.");
							}
						}
						catch(InvalidCWLEUTypeException icweutex){
							sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to remove a user by name, see the log for details.");
							cwp.getLogger().warning("There was an exception preprocessing trying to remove a user by name: " + user);
							icweutex.printStackTrace();
						}
					}
				}
				else{ // Else we assume the sender means a UUID
					// Remove any hyphens
					String stuuid = user.replace(String.valueOf('-'), "");
					
					// Verify length
					if(stuuid.length() != 32){ // If stuuid is not the proper length
						sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID.");
					}
					else{ // Else we assume it's a valid UUID
						// Add hyphens in their proper locations
						stuuid = stuuid.substring(0, 8) + '-' + // Eight
							stuuid.substring(8, 12) + '-' + // Four
							stuuid.substring(12, 16) + '-' + // Four
							stuuid.substring(16, 20) + '-' + // Four
							stuuid.substring(20, 32); // Twelve
						try{
							UUID uuid = UUID.fromString(stuuid);
							try{
								CWLExecutionUnit cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_REMOVE_USER_BY_UUID, sender, new String[]{uuid.toString()}, new String[0]);
								cweu.process();
							}
							catch(InvalidCWLEUTypeException icweutex){
								sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to remove a user by uuid, see the log for details.");
								cwp.getLogger().warning("There was an exception preprocessing trying to remove a user by uuid: " + uuid);
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
	 * This method is to handle the check subcommand
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean check(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		boolean resolve = false;
		if(!sender.hasPermission("customwhitelist.check")){ // If the sender doesn't have the permission for the "check" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else{
			if(subCmdOptions.length > 0){ // If there is at least one option
				for(String opt : subCmdOptions){
					if(opt.equalsIgnoreCase("-r")){ // If the option was -r (resolve)
						resolve = true;
					}
					else{ // Else it was not a valid option
						sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
						return false;
					}
				}
			}
			if(subCmdArgs.length == 0){ // Else if there are no users listed
				sender.sendMessage(ChatColor.RED + MSG_CHECK_USAGE);
				return true;
			}
			else{ // Else there is one or more players to be checked
				for(String user : subCmdArgs){
					if(!(user.length() > 24)){ // If user is not longer than 24 characters, we assume the sender means a username
						if(user.length() > 16){ // If the username is longer than 16 characters, it is invalid
							sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid username.");
						}
						else{ // Else the username is valid
							try{
								CWLExecutionUnit cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_CHECK_USER_BY_NAME, sender, new String[]{user}, new String[0]);
								try{
									cweuExecutor.add(cweu);
								}
								catch(IllegalStateException isex){
									cwp.getLogger().warning("The task \"" + cweu.getDescription() + "\" could not be queued because the queue was full.");
								}
							}
							catch(InvalidCWLEUTypeException icweutex){
								sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to check a user by name, see the log for details.");
								cwp.getLogger().warning("There was an exception preprocessing trying to check a user by name: " + user);
								icweutex.printStackTrace();
							}
						}
					}
					else{ // Else we assume the sender means a UUID
						// Remove any hyphens
						String stuuid = user.replace(String.valueOf('-'), "");
						
						// Verify length
						if(stuuid.length() != 32){ // If stuuid is not the proper length
							sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID.");
						}
						else{ // Else we assume it's a valid UUID
							// Add hyphens in their proper locations
							stuuid = stuuid.substring(0, 8) + '-' + // Eight
								stuuid.substring(8, 12) + '-' + // Four
								stuuid.substring(12, 16) + '-' + // Four
								stuuid.substring(16, 20) + '-' + // Four
								stuuid.substring(20, 32); // Twelve
							try{
								UUID uuid = UUID.fromString(stuuid);
								try{
									CWLExecutionUnit cweu;
									if(!resolve){ // If resolve is off
										cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_CHECK_USER_BY_UUID, sender, new String[]{uuid.toString()}, new String[0]);
										cweu.process();
									}
									else{
										cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_CHECK_USER_BY_UUID_WITH_RESOLVE, sender, new String[]{uuid.toString()}, new String[]{"-r"});
										try{
											cweuExecutor.add(cweu);
										}
										catch(IllegalStateException isex){
											cwp.getLogger().warning("The task \"" + cweu.getDescription() + "\" could not be queued because the queue was full.");
										}
									}
								}
								catch(InvalidCWLEUTypeException icweutex){
									sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to check a user by uuid, see the log for details.");
									cwp.getLogger().warning("There was an exception preprocessing trying to check a user by uuid: " + uuid);
									icweutex.printStackTrace();
								}
							}
							catch(IllegalArgumentException iaex){
								sender.sendMessage(ChatColor.RED.toString() + '\"' + user + "\" is not a valid UUID ");
							}
						}
					}
				}
			}
			
			return true;
		}
	}
	
	/**
	 * This method is to handle the list command
	 * @return boolean usedProperly		Returns true if the command was used properly
	 */
	private boolean list(CommandSender sender, String[] subCmdArgs, String[] subCmdOptions){
		boolean resolve = false;
		if(!sender.hasPermission("customwhitelist.list")){ // If the sender doesn't have the permission for the "list" subcommand
			sender.sendMessage(ChatColor.RED + MSG_INSUFFICIENT_PERMS);
			return true;
		}
		else{
			if(subCmdOptions.length > 0){ // If there is at least one option
				for(String opt : subCmdOptions){
					if(opt.equalsIgnoreCase("-r")){ // If the option was -r (resolve)
						resolve = true;
					}
					else{ // Else it was not a valid option
						sender.sendMessage(ChatColor.RED + MSG_INVALID_OPTION);
						return false;
					}
				}
			}
			if(subCmdArgs.length == 0){ // There wasn't an argument, list the players
				try{
					CWLExecutionUnit cweu;
					if(!resolve){ // If resolve is off
						cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_LIST_WITHOUT_RESOLVE, sender, new String[0], new String[0]);
						cweu.process();
					}
					else{ // Else resolve is on
						cweu = new CWLExecutionUnit(cwp, CWLExecutionUnit.TYPE_LIST_WITH_RESOLVE, sender, new String[0], new String[]{"-r"});
						try{
							cweuExecutor.add(cweu);
						}
						catch(IllegalStateException isex){
							cwp.getLogger().warning("The task \"" + cweu.getDescription() + "\" could not be queued because the queue was full.");
						}
					}
				}
				catch(InvalidCWLEUTypeException icweutex){
					sender.sendMessage(ChatColor.RED + "There was an exception preprocessing trying to list users, see the log for details.");
					cwp.getLogger().warning("There was an exception preprocessing trying to list users:");
					icweutex.printStackTrace();
				}
				
				return true;
			}
			else{ // Else there was an argument
				sender.sendMessage(ChatColor.RED + MSG_TOO_MANY_ARGS);
				return false;
			}
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

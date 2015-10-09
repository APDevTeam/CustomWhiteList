package net.whitewolfdoge.customwhitelist;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import net.whitewolfdoge.customwhitelist.util.WhiteListFetcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhiteListPlugin extends JavaPlugin{
	private final String MSG_PREFIX =		ChatColor.DARK_PURPLE + "CWL: ",
		MSG_ERR_PREFIX =							MSG_PREFIX + ChatColor.RED + "Error: ";
	protected final String MSG_ERR_TOO_MANY_ARGS =	MSG_ERR_PREFIX + "Too many arguments!",
		MSG_ERR_INVALID_OPTION =					MSG_ERR_PREFIX + "That option is not valid for this command!",
		MSG_ERR_INSUFFICIENT_PERMS =				MSG_ERR_PREFIX + "You don't have permission to do that!",
		MSG_ERR_ADD_USAGE =							MSG_ERR_PREFIX + "Usage: /customwhitelist add <player>",
		MSG_ERR_REMOVE_USAGE =						MSG_ERR_PREFIX + "Usage: /customwhitelist remove <player>",
		MSG_ERR_CHECK_USAGE = 						MSG_ERR_PREFIX + "Usage: /customwhitelist check <player> [-r]",
		MSG_ERR_INVALID_USERNAME =					MSG_ERR_PREFIX + '\"' + "$0" + "\" is not a valid username.",
		MSG_ERR_INVALID_UUID =						MSG_ERR_PREFIX + '\"' + "$0" + "\" is not a valid UUID.",
		MSG_ERR_USER_NOT_FOUND =					MSG_ERR_PREFIX + '\"' + "$0" + "\" was not found.",
		MSG_ERR_USER_NOT_FOUND_ADD =				MSG_ERR_PREFIX + '\"' + "$0" + "\" was not found and could not be added to the white list.",
		MSG_ERR_USER_NOT_FOUND_REMOVE =				MSG_ERR_PREFIX + '\"' + "$0" + "\" was not found and could not be removed from the white list.",
		
		MSG_WL_LISTED =								MSG_PREFIX + '\"' + "$0" + "\" is on the white list.",
		MSG_WL_UNLISTED =							MSG_PREFIX + '\"' + "$0" + "\" is not on the white list.",
		MSG_WL_ADDED =								MSG_PREFIX + '\"' + "$0" + "\" was added to the white list.",
		MSG_WL_ADDED_ALREADY =						MSG_PREFIX + '\"' + "$0" + "\" is already on the white list.",
		MSG_WL_REMOVED = 							MSG_PREFIX + '\"' + "$0" + "\" was removed from the white list.",
		MSG_WL_REMOVED_ALREADY =					MSG_PREFIX + '\"' + "$0" + "\" is not on the white list.",
		MSG_WL_ENABLED =							MSG_PREFIX + "White listing was turned on.",
		MSG_WL_ENABLED_ALREADY =					MSG_PREFIX + "White listing is already on.",
		MSG_WL_DISABLED =							MSG_PREFIX + "White listing was turned off.",
		MSG_WL_DISABLED_ALREADY =					MSG_PREFIX + "White listing is already off.",
		MSG_WL_RELOADED = 							MSG_PREFIX + "The white list has been reloaded",
		
		LOG_WL_ADDED =								'\"' + "$0" + "\" was added to the white list.",
		LOG_WL_REMOVED =							'\"' + "$0" + "\" was removed from the white list.",
		LOG_WL_ENABLED =							"White listing was turned on.",
		LOG_WL_DISABLED =							"White listing was turned off.",
		LOG_WL_RELOADED =							"The white list has been reloaded";
	
	private CWLCommandExecutor cwlce;
	private CWLEUExecutor cwleuExecutor;
	
	@Override
	public void onEnable(){
		cwleuExecutor = new CWLEUExecutor();
		cwlce = new CWLCommandExecutor(this, cwleuExecutor);
		try{
			getCommand("customwhitelist").setExecutor(cwlce);
		}
		catch(NullPointerException npex){
			getServer().getLogger().warning("Setting executor for CWL command failed. CWL command will not work!");
		}
		patchWhiteList();
	}
	
	@Override
	public void onDisable(){
		while(true){
			try{
				getLogger().warning("Cancelling " + cwleuExecutor.remove().getDescription());
			}
			catch(NoSuchElementException nseex){
				break;
			}
		}
		if(cwleuExecutor.isRunning()){
			getLogger().info("Waiting for cwleuExecutor to finish...");
			cwleuExecutor.waitForFinish();
		}
	}
	
	protected void patchWhiteList(){
		try{
			// Try to patch the white list
			LinkedList<OfflinePlayer> ofpll = WhiteListFetcher.getWhitelistedPlayers();
			OfflinePlayer ofp;
			while((ofp = ofpll.poll()) != null){
				ofp.setWhitelisted(true);
			}
		}
		catch(Exception ex){
			Bukkit.getLogger().warning("An exception occured while patching the whitelist, one or more players may not be whitelisted!");
			ex.printStackTrace();
		}
	}
}

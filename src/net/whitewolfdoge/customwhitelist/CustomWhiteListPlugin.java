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
		MSG_ERR_CHECK_USAGE = 						MSG_ERR_PREFIX + "Usage: /customwhitelist check <player> [-r]";
	
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

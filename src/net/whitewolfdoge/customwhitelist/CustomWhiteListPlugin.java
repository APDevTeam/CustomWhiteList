package net.whitewolfdoge.customwhitelist;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import net.whitewolfdoge.customwhitelist.util.WhiteListFetcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhiteListPlugin extends JavaPlugin{
	private final String CWLCHATPREFIX =		ChatColor.DARK_PURPLE + "CWL: ",
		MSGERRPREFIX =							CWLCHATPREFIX + ChatColor.RED + "Error: ";
	protected final String MSG_TOO_MANY_ARGS =	MSGERRPREFIX + "Too many arguments!",
		MSG_INVALID_OPTION =					MSGERRPREFIX + "That option is not valid for this command!",
		MSG_INSUFFICIENT_PERMS =				MSGERRPREFIX + "You don't have permission to do that!",
		MSG_ADD_USAGE =							MSGERRPREFIX + "Usage: /customwhitelist add <player>",
		MSG_REMOVE_USAGE =						MSGERRPREFIX + "Usage: /customwhitelist remove <player>",
		MSG_CHECK_USAGE = 						MSGERRPREFIX + "Usage: /customwhitelist check <player> [-r]";
	
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

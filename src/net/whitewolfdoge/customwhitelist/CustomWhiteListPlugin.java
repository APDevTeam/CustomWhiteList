package net.whitewolfdoge.customwhitelist;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import net.whitewolfdoge.customwhitelist.util.WhiteListFetcher;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhiteListPlugin extends JavaPlugin{
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

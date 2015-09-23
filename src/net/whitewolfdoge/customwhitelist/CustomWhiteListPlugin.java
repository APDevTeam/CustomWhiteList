package net.whitewolfdoge.customwhitelist;

import java.util.NoSuchElementException;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhiteListPlugin extends JavaPlugin{
	private CWLCommandExecutor cwce;
	private CWLEUExecutor cweuExecutor;
	
	@Override
	public void onEnable(){
		cweuExecutor = new CWLEUExecutor();
		cwce = new CWLCommandExecutor(this, cweuExecutor);
		try{
			getCommand("customwhitelist").setExecutor(cwce);
			getCommand("cw").setExecutor(cwce);
		}
		catch(NullPointerException npex){
			getServer().getLogger().warning("Setting executor for CW commands failed. CW commands will not work!");
		}
	}
	
	@Override
	public void onDisable(){
		while(true){
			try{
				getLogger().warning("Cancelling " + cweuExecutor.remove().getDescription());
			}
			catch(NoSuchElementException nseex){
				break;
			}
		}
		if(cweuExecutor.isRunning()){
			getLogger().info("Waiting for Executor to finish...");
			cweuExecutor.waitForFinish();
		}
	}
}

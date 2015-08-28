package org.minecraftairshippirates.customwhitelist;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomWhitelistPlugin extends JavaPlugin{
	private CWCommandExecutor cwce;
	
	@Override
	public void onEnable(){
		cwce = new CWCommandExecutor(this);
		try{
			getCommand("customwhitelist").setExecutor(cwce);
			getCommand("cw").setExecutor(cwce);
		}
		catch(NullPointerException npex){
			getServer().getLogger().warning("Setting executor for CW commands failed. CW commands will not work!");
		}
	}
}

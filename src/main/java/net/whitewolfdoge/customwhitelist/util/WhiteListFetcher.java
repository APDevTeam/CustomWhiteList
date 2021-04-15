package net.whitewolfdoge.customwhitelist.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * This class is for housing white list information fetching.
 * 
 */
public class WhiteListFetcher{
	/**
	 * This method is to get all players on the white list from file.
	 * 
	 */
	public static LinkedList<OfflinePlayer> getWhitelistedPlayers(){
		File whitelist;
		LinkedList<OfflinePlayer> ofpll;
		try{
			whitelist = new File(Bukkit.getWorldContainer().getCanonicalPath() + File.separatorChar + "whitelist.json");
			if(!whitelist.exists()){
				throw new IOException();
			}
			else{
				// Get whitelisted players and add them to the linked list
				ofpll = new LinkedList<OfflinePlayer>();
				
				try{
					JsonElement jse = new JsonParser().parse(new FileReader(whitelist));
					
					JsonArray jsaA = jse.getAsJsonArray();
					Iterator<JsonElement> jsaiA = jsaA.iterator();
					while(jsaiA.hasNext()){
						JsonElement currJSEa = jsaiA.next();
						
						String uuidstr = currJSEa.getAsJsonObject().get("uuid").getAsString();
						UUID uuid = UUID.fromString(uuidstr);
						
						ofpll.offer(Bukkit.getOfflinePlayer(uuid));
					}
				}
				catch(FileNotFoundException fnfex){
					throw new IOException();
				}
				catch(JsonIOException | JsonSyntaxException | IllegalArgumentException jsex){
					Bukkit.getLogger().warning("There was an exception while parsing the white list.");
					throw new IOException();
				}
			}
		}
		catch(IOException ioex){
			ofpll = new LinkedList<OfflinePlayer>();
		}
		
		return ofpll;
	}
}

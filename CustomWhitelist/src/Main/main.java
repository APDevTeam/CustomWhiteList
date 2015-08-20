package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class main extends JavaPlugin implements CommandExecutor, Listener {
	private static main instance;

	@Override
	public void onEnable() {
		instance = this;
		loadConfiguration();
	}

	@Override
	public void onDisable() {
		instance.saveConfig();
	}

	public static main getInstance() {
		return instance;
	}

	public void loadConfiguration() {
		instance.getConfig().options().copyDefaults(true);
		instance.saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName() == "CustomWhitelist" && args.length == 2) {
			if (args[0].equalsIgnoreCase("remove")) {
				if (Bukkit.getPlayerExact(args[1]) instanceof Player) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
					while (Bukkit.getWhitelistedPlayers().contains(player.getName()) == false) {
						//idk what to do here if we add to the whitelist its not gonna do the lookup till the server restarts then it will fail :/
					}
				} else {
					TextComponent message = new TextComponent( "This is not a valid player!" );
					message.setColor( ChatColor.RED );
					message.setBold( true );
					Bukkit.getPlayer(sender.getName()).spigot().sendMessage(message);
				}
			} else {
				if (args[0].equalsIgnoreCase("add")) {
					if (Bukkit.getPlayerExact(args[1]) instanceof Player) {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						while (Bukkit.getWhitelistedPlayers().contains(player.getName()) == false) {
							//idk what to do here if we add to the whitelist its not gonna do the lookup till the server restarts then it will fail :/
						}
					} else {
						TextComponent message = new TextComponent( "This is not a valid player!" );
						message.setColor( ChatColor.RED );
						message.setBold( true );
						Bukkit.getPlayer(sender.getName()).spigot().sendMessage(message);
					}
				}
			}
		} else {
			
		}
		return true;
	}
}
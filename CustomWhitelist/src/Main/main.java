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
		sender.sendMessage(ChatColor.BLUE + "Success");
		if (cmd.getName().equalsIgnoreCase("WA")) {
			sender.sendMessage(ChatColor.BLUE + "Success");
			if (!(args[0] == null)) {
				sender.sendMessage(ChatColor.BLUE + "Success");
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
				sender.sendMessage(ChatColor.BLUE + "Success");
				while (1 == 1) {
					sender.sendMessage(ChatColor.BLUE + "Success");
					if (!Bukkit.getWhitelistedPlayers().contains(player)) {
						Bukkit.getWhitelistedPlayers().add(player);
						sender.sendMessage(ChatColor.BLUE + "Success");
					} else {
						sender.sendMessage(ChatColor.BLUE + "Success");
						break;
					}
				}
			} else {
				if (cmd.getName() == "WR") {
					if (!(args[0] == null)) {
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
						while (1 == 1) {
							if (Bukkit.getWhitelistedPlayers().contains(player)) {
								Bukkit.getWhitelistedPlayers().remove(player);
						} else {
							sender.sendMessage(ChatColor.BLUE + "Success");
							break;
						}
						return true;
					}
				} else {
					return false;
				}
				return false;
			}
		}
		return false;
	}
		return false;
}
}
package ru.diancore.enterpassport.Commands;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.core.pattern.EqualsIgnoreCaseReplacementConverter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ru.diancore.enterpassport.EnterPassport;
import ru.diancore.enterpassport.Events.GUIEvent;
import ru.diancore.enterpassport.GUI.Passport;
import ru.diancore.enterpassport.Utils.DBManager;

public class MainCommands implements CommandExecutor {

	private EnterPassport plugin;
	private Inventory gui;
	private Inventory guiOther;
	public String PREFIX = "§6§lEP §3>> §f";
	private DBManager dbmanager;
	private ConsoleCommandSender console;

	public MainCommands(EnterPassport enterPassport) {
		this.plugin = enterPassport;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			plugin.getLogger().warning("This command must be enterend from game!");
			return true;
		};
		
		Player player = (Player) sender;
		
		try {
			dbmanager = new DBManager(plugin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (args.length == 0) {
			if (player.hasPermission("enterpassport.me")) {
				
				gui = new Passport(plugin).getInventory(player.getName());
				Bukkit.getPluginManager().registerEvents(new GUIEvent(plugin, player), plugin);
				player.openInventory(gui);
				return true;
				
			} else {
				player.sendMessage(plugin.getConfig().getString("permissionErrorMessage").replace("{player}", player.getName()).replace("&", "§"));
				return true;
			}
		}
		
		if (args[0].equals("reload")) {
			if (player.hasPermission("enterpassport.admin")) {
				
				plugin.reloadConfig();
				sender.sendMessage(PREFIX + "§ehas been reloaded!");
				return true;
				
			} else {
				player.sendMessage(plugin.getConfig().getString("permissionErrorMessage").replace("{player}", player.getName()).replace("&", "§"));
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("help")) {
			if (player.hasPermission("enterpassport.help")) {
				player.sendMessage("\n");
				player.sendMessage("                  §e▶▸ §6EnterPassport §e◂◀");
				player.sendMessage("\n");
				player.sendMessage("§d/ep | /pass | /passport §9- §7Открыть мой пасспорт");
				player.sendMessage("§d/ep <player> | /pass <player> | /passport <player> §9- §7Посмотреть паспорт игрока");
				player.sendMessage("§d/ep toggletrigger | /pass toggletrigger | /passport toggletrigger §9 - §7Включить/Выключить триггер нажатия на игрока (Просмотр паспорта)");
				player.sendMessage("§d/ep | /pass | /passport setsex §a<m/f> §9- §7Указать пол");
				player.sendMessage("§d/ep | /pass | /passport setrealname §a<name> §9- §7Указать реальное имя");
				player.sendMessage("§d/ep | /pass | /passport setvk §a<link> §9 - §7Указать ВКонтакте");
				player.sendMessage("§d/ep | /pass | /passport setds §a<TAG> §9 - §7Указать DISCORD");
				player.sendMessage("§d/ep | /pass | /passport settg §a<link> §9 - §7Указать TELEGRAM");
				player.sendMessage("§d/ep | /pass | /passport setage §a<age> §9- §7Указать свой возраст");
				player.sendMessage("\n");
				player.sendMessage("                  §e▶▸ §6EnterPassport §e◂◀");
				player.sendMessage("\n");
				return true;
			} else {
				player.sendMessage(plugin.getConfig().getString("permissionErrorMessage").replace("{player}", player.getName()).replace("&", "§"));
				return true;
			}
		}
		
		if (player.hasPermission("enterpassport.me.edit")) {
			if (args[0].equalsIgnoreCase("toggletrigger")) {
				Boolean current_trigger = dbmanager.getPlayerTrigger(player.getName());
				
				if (current_trigger) {
					dbmanager.updatePlayerTrigger(player.getName(), false);
					player.sendMessage(PREFIX + "Триггер §cотключен");
					return true;
				} else {
					dbmanager.updatePlayerTrigger(player.getName(), true);
					player.sendMessage(PREFIX + "Триггер §aвключен");
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("setsex")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setsex §a<m/f> §9- §7Указать пол");
					return true;
				}
				
				String[] allowed_args = new String[] {"m", "f"};
				
				if ( !Arrays.asList(allowed_args).contains(args[1]) ) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setsex §a<m/f> §9- §7Указать пол");
					return true;
				}
				
				String sex = args[1].equalsIgnoreCase("m") ? "Мужской" : "Женский";
				dbmanager.updatePlayerInformation(player.getName(), "sex", sex);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
			
			if (args[0].equalsIgnoreCase("setrealname")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setreal §a<name> §9- §7Указать имя");
					return true;
				}
				
				dbmanager.updatePlayerInformation(player.getName(), "realname", args[1]);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
			
			if (args[0].equalsIgnoreCase("setage")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setage §a<age> §9- §7Указать возраст");
					return true;
				}
				
				dbmanager.updatePlayerInformation(player.getName(), "age", args[1]);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
			
			if (args[0].equalsIgnoreCase("setvk")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setvk §a<vk> §9- §7Указать VK");
					return true;
				}
				
				dbmanager.updatePlayerInformation(player.getName(), "vk", args[1]);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
			
			if (args[0].equalsIgnoreCase("setds")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport setds §a<Discord> §9- §7Указать Discord");
					return true;
				}
				
				dbmanager.updatePlayerInformation(player.getName(), "discord", args[1]);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
			
			if (args[0].equalsIgnoreCase("settg")) {
				if (args.length < 2) {
					player.sendMessage(PREFIX + "§d/ep | /pass | /passport settg §a<Telegram> §9- §7Указать Telegram");
					return true;
				}
				
				dbmanager.updatePlayerInformation(player.getName(), "telegram", args[1]);
				
				player.sendMessage(PREFIX + "Информация изменена!");
								
				return true;
			}
		} else {
			player.sendMessage(plugin.getConfig().getString("permissionErrorMessage").replace("{player}", player.getName()).replace("&", "§"));
			return true;
		}
		
		if (args.length == 1) {
			if (player.hasPermission("enterpassport.other")) {
				
				if (Bukkit.getPlayerExact(args[0]) == null) {
					player.sendMessage(PREFIX + "Игрока нет на сервере!");
					return true;
				}
				
				guiOther = new Passport(plugin).getInventory(args[0]);
				Player triggerplayer = Bukkit.getPlayerExact(args[0]);
				Bukkit.getPluginManager().registerEvents(new GUIEvent(plugin, triggerplayer), plugin);
				player.openInventory(guiOther);
				return true;
			}
		}
		
		return true;
	}

}

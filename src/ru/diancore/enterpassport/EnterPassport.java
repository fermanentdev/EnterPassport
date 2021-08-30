package ru.diancore.enterpassport;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ru.diancore.enterpassport.Commands.MainCommands;
import ru.diancore.enterpassport.Events.GUIEvent;
import ru.diancore.enterpassport.Events.JoinEvent;
import ru.diancore.enterpassport.Events.PlayClickToPlayerEvent;

public class EnterPassport extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		
		File config = new File(getDataFolder() + File.separator + "config.yml");
		if (!config.exists())
		{
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayClickToPlayerEvent(this), this);
		getCommand("enterpassport").setExecutor(new MainCommands(this));
	}
	
}

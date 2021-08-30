package ru.diancore.enterpassport.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.diancore.enterpassport.EnterPassport;
import ru.diancore.enterpassport.Utils.DBManager;

public class JoinEvent implements Listener {

	private EnterPassport plugin;
	private DBManager dbmanager;

	public JoinEvent(EnterPassport enterPassport) {
		this.plugin = enterPassport;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		try {
			dbmanager = new DBManager(plugin);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Player player = e.getPlayer();
		dbmanager.createPlayerInformation(player.getName());
	}

}

package ru.diancore.enterpassport.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

import ru.diancore.enterpassport.EnterPassport;
import ru.diancore.enterpassport.GUI.Passport;
import ru.diancore.enterpassport.Utils.DBManager;

public class PlayClickToPlayerEvent implements Listener {
	private EnterPassport plugin;
	private DBManager db;
	
	public PlayClickToPlayerEvent(EnterPassport enterPassport) {
		this.plugin = enterPassport;
	}
	
	@EventHandler
	public void interractHandler(PlayerInteractEntityEvent event) {
		
		if (event.getRightClicked().getType() == EntityType.PLAYER) {
			
			try {
				db = new DBManager(plugin);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Boolean trigger = db.getPlayerTrigger(event.getPlayer().getName());
			
			if (trigger) {
				Player clicked_player = (Player) event.getRightClicked();
				Player this_player = (Player) event.getPlayer();
				
				Inventory gui = new Passport(plugin).getInventory(clicked_player.getName());
				Bukkit.getPluginManager().registerEvents(new GUIEvent(plugin, clicked_player), plugin);
				this_player.openInventory(gui);
			} else {
				return;
			}
			
			return;
		} else {
			return;
		}
	}

}

package ru.diancore.enterpassport.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.diancore.enterpassport.GUI.Passport;

import ru.diancore.enterpassport.EnterPassport;
import ru.diancore.enterpassport.GUI.Passport;

public class GUIEvent implements Listener {
	
	private Player player;
	private Passport passport;
	private EnterPassport plugin;
	public String PREFIX = "§6§lEP §3>> §f";
	
	public GUIEvent(EnterPassport enterPassport, Player player) {
		this.player = player;
		this.passport = new Passport(plugin);
		this.plugin = enterPassport;
	}

	@EventHandler
	public void guiHandler(InventoryClickEvent e) {
		String guiTitle = plugin.getConfig().getString("guiName").replace("{player}", player.getName()).replace("&", "§");
		
		if (e.getView().getTitle().equalsIgnoreCase(guiTitle)) {
			e.setCancelled(true);
		}
	}
}

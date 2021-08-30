package ru.diancore.enterpassport.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import ru.diancore.enterpassport.EnterPassport;
import ru.diancore.enterpassport.Utils.DBManager;
import me.clip.placeholderapi.PlaceholderAPI;

public class Passport {
	
	private Inventory inventory;
	private Player player;
	private EnterPassport plugin;
	private DBManager db;
	
	private String a;
	private String b;
	private String c;
	private String d;
	private String e;
	private String f;
	
	public Passport(EnterPassport enterPassport) {
		this.plugin = enterPassport;
	}

	@SuppressWarnings({ "null", "unchecked" })
	public Inventory getInventory(String playername) {
		player = Bukkit.getPlayerExact(playername);
		
		try {
			db = new DBManager(plugin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String guiName = plugin.getConfig().getString("guiName").replace("&", "§").replace("{player}", player.getName());
	    String playerHeadName = plugin.getConfig().getString("playerHeadName").replace("&", "§").replace("{player}", player.getName());
		String descName = plugin.getConfig().getString("descriptionName").replace("&", "§").replace("{player}", player.getName());
		String glassName = plugin.getConfig().getString("glassName").replace("&", "§").replace("{player}", player.getName());
		List<String> passportLore = (List<String>) plugin.getConfig().getList("PassportLore");
		List<String> formattedPassportLore = new ArrayList<String>();
		
		List<String> descLore = (List<String>) plugin.getConfig().getList("DescriptionLore");
		List<String> formattedDescLore = new ArrayList<String>();
		String[] playerInformation = db.getPlayerInformation(player.getName());
		
		if (playerInformation[0] == null) {
			a = "§eНе указано";
		} else {
			a = playerInformation[0];
		}
		if (playerInformation[1] == null) {
			b = "§eНе указано";
		} else {
			b = playerInformation[1];
		}
		if (playerInformation[2] == null) {
			c = "§eНе указано";
		} else {
			c = playerInformation[2];
		}
		if (playerInformation[3] == null) {
			d = "§eНе указано";
		} else {
			d = playerInformation[3];
		}
		if (playerInformation[4] == null) {
			e = "§eНе указано";
		} else {
			e = playerInformation[4];
		}
		if (playerInformation[5] == null) {
			f = "§eНе указано";
		} else {
			f = playerInformation[5];
		}
		
		for (String fs : passportLore) {
			formattedPassportLore.add(
					fs.replace("\n", "").replace("&", "§").replace("{player}", player.getDisplayName())
					.replace("{player_realname}", a)
					.replace("{player_age}", b)
					.replace("{player_sex}", c)
					.replace("{player_vk}", d)
					.replace("{player_ds}", e)
					.replace("{player_tg}", f)
					);
		}
		
		for (String fs1 : descLore) {
			formattedDescLore.add(
					fs1.replace("\n", "").replace("&", "§").replace("{player}", player.getDisplayName())
					.replace("{player_realname}", playerInformation[0] == null ? "§eНе указано" : playerInformation[0])
					.replace("{player_age}", playerInformation[1] == null ? "§eНе указано": playerInformation[1])
					.replace("{player_sex}", playerInformation[2] == null ? "§eНе указано" : playerInformation[2])
					.replace("{player_vk}", playerInformation[3] == null ? "§eНе указано" : playerInformation[3])
					.replace("{player_ds}", playerInformation[4] == null ? "§eНе указано": playerInformation[4])
					.replace("{player_tg}", playerInformation[5] == null ? "§eНе указано" : playerInformation[5])
					);
		}
		
		
		inventory = Bukkit.createInventory(null, 9, guiName);
		ItemStack itemGlassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemStack itemPlayerHead = new ItemStack(Material.PLAYER_HEAD, 1);
		ItemStack itemPaper = new ItemStack(Material.PAPER);
		ItemStack itemTrigger = new ItemStack(Material.REPEATER);

		SkullMeta headMeta = (SkullMeta) itemPlayerHead.getItemMeta();
		headMeta.setOwningPlayer(player);
		headMeta.setDisplayName(playerHeadName);
		headMeta.setLore(PlaceholderAPI.setPlaceholders(player, formattedPassportLore));
		itemPlayerHead.setItemMeta(headMeta);
		
		ItemMeta glassMeta = itemGlassPane.getItemMeta();
		glassMeta.setDisplayName(glassName);
		itemGlassPane.setItemMeta(glassMeta);
		
		ItemMeta paperMeta = itemPaper.getItemMeta();
		paperMeta.setDisplayName(descName);
		paperMeta.setLore(PlaceholderAPI.setPlaceholders(player, formattedDescLore));
		itemPaper.setItemMeta(paperMeta);
		
		
		for (int i = 0; i < 9; i++) {
			inventory.setItem(i, itemGlassPane);
		}
		
		inventory.setItem(3, itemPlayerHead);
		inventory.setItem(5, itemPaper);
		
		return inventory;
	}
}
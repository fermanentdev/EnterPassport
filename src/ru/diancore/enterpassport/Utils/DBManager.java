package ru.diancore.enterpassport.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.file.FileConfiguration;

import ru.diancore.enterpassport.EnterPassport;

public class DBManager {
	private EnterPassport plugin;
	private String url;

	@SuppressWarnings("deprecation")
	public DBManager(EnterPassport enterPassport) throws Exception {
		this.plugin = enterPassport;
		
		FileConfiguration config = plugin.getConfig();
		
		url = "jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "database.db";
		Class.forName("org.sqlite.JDBC").newInstance();
		
		Connection conn = getConnection();
		Statement s = conn.createStatement();
		
		s.executeUpdate("CREATE TABLE IF NOT EXISTS players (`id` INTEGER AUTO_INCREMENT UNIQUE, `player` VARCHAR(255) NOT NULL UNIQUE, `realname` VARCHAR(255), `age` VARCHAR(255),"
				+ "`sex` VARCHAR(255), `vk` VARCHAR(255), `discord` VARCHAR(255), `telegram` VARCHAR(255),"
				+ "`trigger` TINYINT DEFAULT false);");
		
		s.close();
		conn.close();
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}
	
	public void createPlayerInformation(String player) {
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			
			try {
				s.executeUpdate(String.format("INSERT INTO players (`player`) VALUES ('%s');", 
						player));
			} catch (SQLException e) {
				// Player has already exists;
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getPlayerInformation(String player) {
		String realname = null;
		String age = null;
		String sex = null;
		String vk = null;
		String tg = null;
		String ds = null;
		
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			
			ResultSet result = s.executeQuery("SELECT * FROM players WHERE player = '" + player + "';");
			
			if (result.next()) {
				realname = result.getString("realname");
				age = result.getString("age");
				sex = result.getString("sex");
				vk = result.getString("vk");
				ds = result.getString("discord");
				tg = result.getString("telegram");
			}
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] name = new String[] {realname, age, sex, vk, ds, tg};
		return name;
	}
	
	public Boolean getPlayerTrigger(String player) {
		boolean trigger = false;
		
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			
			ResultSet result = s.executeQuery("SELECT trigger FROM players WHERE player = '" + player + "';");
			
			if (result.next()) {
				trigger = result.getBoolean("trigger");
			}
			
			s.close();
			c.close();
			
			if (trigger) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return trigger;
	}
	
	public void updatePlayerInformation(String player, String field, String new_value) {
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			
			s.executeUpdate(String.format("UPDATE players SET %s = '%s' WHERE player = '%s'", field, new_value, player));
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlayerTrigger(String player, Boolean new_value) {
		try {
			Connection c = getConnection();
			Statement s = c.createStatement();
			
			s.executeUpdate("UPDATE players SET trigger = " + new_value +" WHERE player = '" + player + "';");
			
			s.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// Author: Le00nn
// Date: 19/08/2023
// Version: 1.2.0

package se.le00nn.randomtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomTP extends JavaPlugin implements Listener {

	@Override
	public void onDisable() {
		Bukkit.getLogger().warning("["+getName(this)+"] (Version: "+getVersion(this)+") disabled!"); // Plugin disabled!
	}

	@Override
	public void onEnable() {
		Bukkit.getLogger().info("["+getName(this)+"] (Version: "+getVersion(this)+") enabled!"); // Plugin enabled!
		
		getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getLogger().info("["+getName(this)+"] Registered events!");
		
		getConfiguration().load();
		
		getConfiguration().setProperty("config.version", getConfigVersion());
		getConfiguration().save();
		Bukkit.getLogger().info("["+getName(this)+"] Using config version " + getConfigVersion() + "!");
		
		if(getConfiguration().getProperty("location") == null) {
			getConfiguration().setProperty("location.a.x", -500);
			getConfiguration().setProperty("location.a.z", -500);
			getConfiguration().setProperty("location.b.x", 500);
			getConfiguration().setProperty("location.b.z", 500);
			getConfiguration().save();
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args) {
		if(!(sender instanceof Player)) {
			// Player command.
			
			// /rs
			if(c.equals("rs")) {
				if(args[0] == null || args.length <= 0) return false;
							
				// rs reload
				if(args[0].toString().matches("reload")) {
					Bukkit.getLogger().info("["+getName(this)+"] CONSOLE: Reloaded configuration file.");
					getConfiguration().load();
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(player.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "(CONSOLE) Reloaded RandomSpawn configuration file.");
					}
					return false;
				}
				
				// rs set 
				if(args[0].toString().matches("set")) {
					if(args[3] == null || args.length <= 3) return false;
					// rs set a 
					// rs set a x z
					if(args[1] == "a") {
						if(args[2].matches("[0-9]+")) getConfiguration().setProperty("location.a.x", Integer.parseInt(args[2])); // rs set a x ?
						if(args[3].matches("[0-9]+")) getConfiguration().setProperty("location.a.z", Integer.parseInt(args[3])); // rs set a ? z
					}
					// rs set b 
					// rs set b x z
					if(args[1] == "b") {
						if(args[2].matches("[0-9]+")) getConfiguration().setProperty("location.b.x", Integer.parseInt(args[2])); // rs set b x ?
						if(args[3].matches("[0-9]+")) getConfiguration().setProperty("location.b.z", Integer.parseInt(args[3])); // rs set b ? z
					}
					Bukkit.getLogger().info("["+getName(this)+"] CONSOLE: Updated configuration file.");
					getConfiguration().save();
					Bukkit.getLogger().info("["+getName(this)+"] CONSOLE: Reloaded configuration file.");
					getConfiguration().load();
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(player.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "(CONSOLE) Updated and reloaded RandomSpawn configuration file.");
					}
					return false;
				}
			}
		} else {
			// Player command
			
			Player player = (Player)sender;
			if(player.isOp() != true) {
				player.sendMessage(ChatColor.RED + "You are not operator.");
				return false;
			}
			
			// /rs
			if(c.equals("rs")) {
				if(args[0] == null || args.length <= 0) return false;
				
				// /rs reload
				if(args[0].toString().matches("reload")) {
					Bukkit.getLogger().info("["+getName(this)+"] Reloaded configuration file.");
					player.sendMessage(ChatColor.GREEN + "Reloaded RandomSpawn configuration file.");
					for(Player player1 : Bukkit.getOnlinePlayers()) {
						if(player1.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "(CONSOLE) Reloaded RandomSpawn configuration file.");
					}
					getConfiguration().load();
					return false;
				}
				
				// /rs set 
				if(args[0].toString().matches("set")) {
					if(args[3] == null || args.length <= 3) return false;
					// /rs set a 
					// /rs set a x z
					if(args[1] == "a") {
						if(args[2].matches("[0-9]+")) getConfiguration().setProperty("location.a.x", Integer.parseInt(args[2])); // rs set a x ?
						if(args[3].matches("[0-9]+")) getConfiguration().setProperty("location.a.z", Integer.parseInt(args[3])); // rs set a ? z
					}
					// /rs set b 
					// /rs set b x z
					if(args[1] == "b") {
						if(args[2].matches("[0-9]+")) getConfiguration().setProperty("location.b.x", Integer.parseInt(args[2])); // rs set b x ?
						if(args[3].matches("[0-9]+")) getConfiguration().setProperty("location.b.z", Integer.parseInt(args[3])); // rs set b ? z
					}
					Bukkit.getLogger().info("["+getName(this)+"] Updated configuration file.");
					getConfiguration().save();
					Bukkit.getLogger().info("["+getName(this)+"] Reloaded configuration file.");
					getConfiguration().load();
					player.sendMessage(ChatColor.GREEN + "Updated RandomSpawn configuration file.");
					for(Player player1 : Bukkit.getOnlinePlayers()) {
						if(player1.isOp()) player1.sendMessage(ChatColor.LIGHT_PURPLE + "(CONSOLE) Updated and reloaded RandomSpawn configuration file.");
					}
					return false;
				}
				
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String plname = player.getName();
		
		Location loc = calculatePosition(player, plname);
		if(loc != null) player.teleport(loc);
		
		return;
	}
	
	// Calculate position to teleport to on first join.
	private Location calculatePosition(Player player, String plname) {
		if(getConfiguration().getProperty("location.hastp."+plname) != null) return null; // Prevent teleport of previously spawned players.
		
		int ax = (int)getConfiguration().getProperty("location.a.x");
		int az = (int)getConfiguration().getProperty("location.a.z");
		int bx = (int)getConfiguration().getProperty("location.b.x");
		int bz = (int)getConfiguration().getProperty("location.b.z");
		
		int xmin = Math.min(ax, bx);
		int zmin = Math.min(az, bz);
		int xmax = Math.max(ax, bx);
		int zmax = Math.max(az, bz);
		
		int spawnx = (int)((Math.random() * (xmax - xmin)) + xmin);
		int spawnz = (int)((Math.random() * (zmax - zmin)) + zmin);
		
		World world = player.getWorld();
		
		int spawny = 127;
		for(int y = 127; y>4; y--) {
			 int blockId = world.getBlockAt(spawnx, y, spawnz).getTypeId();
			 if(blockId != 0) {
				 spawny = y+2;
				 break;
			 }
		}
		
		Location loc = new Location(world, spawnx, spawny, spawnz);
		getConfiguration().setProperty("location.hastp."+plname, true);	// Memory of previous players who first joined to not randomly tp them everytime they join.
		getConfiguration().save();
		Bukkit.getLogger().info("["+getName(this)+"] Player '" + plname + "' joined and got teleported to " + spawnx + ", " + spawny + ", " + spawnz + "... Magic!");
		
		return loc;
	}
	
	// Get plugin name
	private String getName(RandomTP rtp) {
		return rtp.getDescription().getName();
	}
			
	// Get plugion version
	private String getVersion(RandomTP rtp) {
		return rtp.getDescription().getVersion();
	}
			
	// Config version
	private int getConfigVersion() {
		return 1002; // First version of Config version.
	}

}

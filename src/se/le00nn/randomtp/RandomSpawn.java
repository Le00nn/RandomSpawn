// Author: Le00nn
// Date: 23/08/2023
// Version: 1.3.0

package se.le00nn.randomtp;

import java.util.List;

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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomSpawn extends JavaPlugin implements Listener {

	@Override
	public void onDisable() {
		
		Bukkit.getLogger().warning("["+getName()+"] (Version: "+getVersion()+") disabled!"); // Plugin disabled!
		
	}

	@Override
	public void onEnable() {
		
		Bukkit.getLogger().info("["+getName()+"] (Version: "+getVersion()+") enabled!"); // Plugin enabled!
		
		getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getLogger().info("["+getName()+"] Registered events!");
		
		getConfiguration().load();
		
		getConfiguration().setProperty("config.version", getConfigVersion());

		Bukkit.getLogger().info("["+getName()+"] Using config version " + getConfigVersion() + "!");
		
		if(getConfiguration().getProperty("location") == null) {
			getConfiguration().setProperty("location.a.x", -500);
			getConfiguration().setProperty("location.a.z", -500);
			getConfiguration().setProperty("location.b.x", 500);
			getConfiguration().setProperty("location.b.z", 500);
		}
		
		if(getConfiguration().getProperty("settings") == null) {
			getConfiguration().setProperty("settings.spawns.allowWater", false);
		}
		
		if(getConfiguration().getBoolean("settings.spawns.allowWater", false) == false) {
			Bukkit.getLogger().warning("["+getName()+"] Water spawns have been disabled!");
		}
		
		getConfiguration().save();
		
	}
	
	private boolean isPlayer(CommandSender player) {
		return (player instanceof Player);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args) {
		
		if(isPlayer(sender)) {
			if(!((Player)sender).isOp()) {
				((Player)sender).sendMessage(ChatColor.RED + "You are not a server operator!");
				return false;
			}
		}
		
		// /rs
		if(c.matches("rs")) {
			if(args[0] == null || args.length <= 0) return false;
							
			// rs reload
			if(args[0].toString().matches("reload")) {
				Bukkit.getLogger().info("["+getName()+"] [CONSOLE] Reloaded configuration file.");
				
				getConfiguration().load();
				
				if(getConfiguration().getProperty("config.version") == null) {
					if((int)getConfiguration().getProperty("config.version") != getConfigVersion()) {
						getConfiguration().setProperty("config.version", getConfigVersion());
					}
				}
				
				if(getConfiguration().getProperty("location") == null) {
					getConfiguration().setProperty("location.a.x", -500);
					getConfiguration().setProperty("location.a.z", -500);
					getConfiguration().setProperty("location.b.x", 500);
					getConfiguration().setProperty("location.b.z", 500);
				}
				
				if(getConfiguration().getProperty("settings") == null) {
					getConfiguration().setProperty("settings.spawns.allowWater", false);
				}
				
				getConfiguration().save();
				
				if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.GREEN + "Reloaded RandomSpawn configuration file.");
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "[CONSOLE] Reloaded RandomSpawn configuration file.");
				}
				
				return false;
			}
			
			// rs recreate
			if(args[0].toString().matches("recreate")) {
				Bukkit.getLogger().info("["+getName()+"] [CONSOLE] Recreated the configuration file.");
				
				List<String> keys = getConfiguration().getKeys();
				
				for(String key : keys) {
					getConfiguration().removeProperty(key);
				}
				
				getConfiguration().setProperty("config.version", getConfigVersion());
				getConfiguration().setProperty("location.a.x", -500);
				getConfiguration().setProperty("location.a.z", -500);
				getConfiguration().setProperty("location.b.x", 500);
				getConfiguration().setProperty("location.b.z", 500);
				getConfiguration().setProperty("settings.spawns.allowWater", false);

				getConfiguration().save();
				
				if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.GREEN + "Recreated RandomSpawn configuration file.");
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "[CONSOLE] Recreated RandomSpawn configuration file.");
				}
				
				return false;
				
			}
				
			// rs set 
			if(args[0].toString().matches("set")) {
				if(args[3] == null || args.length <= 3) return false;
				
				// rs set x 
				// rs set x x1 x2
				if(args[1].toLowerCase().equals("x")) {
					if(args[2].matches("[0-9]+")) {
						getConfiguration().setProperty("location.a.x", Integer.parseInt(args[2])); // rs set x x1 ?
					} else {
						if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.RED + "Positions must be a valid number.");
						Bukkit.getLogger().severe("["+getName()+"] Positions must be a valid number.");
						return false;
					}
					if(args[3].matches("[0-9]+")) {
						getConfiguration().setProperty("location.b.x", Integer.parseInt(args[3])); // rs set x ? x2
					} else {
						if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.RED + "Positions must be a valid number.");
						Bukkit.getLogger().severe("["+getName()+"] Positions must be a valid number.");
						return false;
					}
				}
				
				// rs set z 
				// rs set z z1 z2
				if(args[1].toLowerCase().equals("z")) {
					if(args[2].matches("[0-9]+")) {
						getConfiguration().setProperty("location.a.z", Integer.parseInt(args[2])); // rs set z z1 ?
					} else {
						if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.RED + "Positions must be a valid number.");
						Bukkit.getLogger().severe("["+getName()+"] Positions must be a valid number.");
						return false;
					}
					if(args[3].matches("[0-9]+")) {
						getConfiguration().setProperty("location.b.z", Integer.parseInt(args[3])); // rs set z ? z2
					} else {
						if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.RED + "Positions must be a valid number.");
						Bukkit.getLogger().severe("["+getName()+"] Positions must be a valid number.");
						return false;
					}
				}
				
				if(isPlayer(sender)) ((Player)sender).sendMessage(ChatColor.GREEN + "Updated and reloaded configuration file.");
				Bukkit.getLogger().info("["+getName()+"] [CONSOLE] Updated and reloaded configuration file.");
				getConfiguration().save();
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.isOp()) player.sendMessage(ChatColor.LIGHT_PURPLE + "[CONSOLE] Updated and reloaded RandomSpawn configuration file.");
				}
			}
		}
		
		return false;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		getConfiguration().load();
		Player player = e.getPlayer();
		String plname = player.getName();
		Location loc = calculatePosition(player, plname, false);
		if(loc != null) {
			String pos = loc.getX()+","+loc.getY()+","+loc.getZ();
			getConfiguration().setProperty("spawns."+plname, pos);
			getConfiguration().save();
			player.teleport(loc);
		}
		
		return;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		getConfiguration().load();
		Player player = e.getPlayer();
		String plname = player.getName();
		String pos = getConfiguration().getString("spawns."+plname);
		if(pos != null) {
			Location loc = reloadSpawn(pos, player, plname);
			if(e.getRespawnLocation() != loc) e.setRespawnLocation(loc);
		}
		return;
	}
	
	// Reload position to respawn.
	private Location reloadSpawn(String pos, Player player, String plname) {
		String[] posi = pos.split(",");
		if(posi.length != 3) {
			return calculatePosition(player, plname, true);
		}
		World world = player.getWorld();
		Location loc = new Location(world, Double.parseDouble(posi[0]), Double.parseDouble(posi[1]), Double.parseDouble(posi[2]));
		Bukkit.getLogger().info("["+getName()+"] Player '" + plname + "' just died and respawned at " + posi[0] + ", " + posi[1] + ", " + posi[2] + ".");
		return loc;
		}
	
	// Calculate position to teleport to on first join.
	private Location calculatePosition(Player player, String plname, boolean bypassCheck) {
		if(getConfiguration().getProperty("spawns."+plname) != null && !bypassCheck) return null; // Prevent teleport of previously spawned players.
		
		int ax = -500;
		int az = -500;
		int bx =  500;
		int bz =  500;
		if(getConfiguration().getProperty("location.a.x") == null) {
			getConfiguration().setProperty("location.a.x", -500);
		} else {
			ax = (int)getConfiguration().getProperty("location.a.x");
		}
		if(getConfiguration().getProperty("location.a.z") == null) {
			getConfiguration().setProperty("location.a.z", -500);
		} else {
			az = (int)getConfiguration().getProperty("location.a.z");
		}
		if(getConfiguration().getProperty("location.b.x") == null) {
			getConfiguration().setProperty("location.b.x", 500);
		} else {
			bx = (int)getConfiguration().getProperty("location.b.x");
		}
		if(getConfiguration().getProperty("location.b.z") == null) {
			getConfiguration().setProperty("location.b.z", 500);
		} else {
			bz = (int)getConfiguration().getProperty("location.b.z");
		}
		
		boolean allowWaterSpawn = false;
		if(getConfiguration().getProperty("settings.spawns.allowWater") == null) {
			allowWaterSpawn = getConfiguration().getBoolean("settings.spawns.allowWater", false);
		} else {
			getConfiguration().setProperty("settings.spawns.allowWater", false);
		}
		
		int xmin = Math.min(ax, bx);
		int zmin = Math.min(az, bz);
		int xmax = Math.max(ax, bx);
		int zmax = Math.max(az, bz);
		
		World world = player.getWorld();
		
		// Prevent spawning in water
		Object[] j = calculus(world, xmin, zmin, xmax, zmax);
		double spawnx = (double)j[0];
		int spawny = (int)j[1];
		double spawnz = (double)j[2];
		int blokid = (int)j[3];
		
		if(!allowWaterSpawn) {
			while(blokid != 0 && blokid == 9) {
				j = calculus(world, xmin, zmin, xmax, zmax);
				spawnx = (double)j[0];
				spawny = (int)j[1];
				spawnz = (double)j[2];
				blokid = (int)j[3];
			}
		}
		
		Location loc = new Location(world, spawnx, spawny, spawnz);
		getConfiguration().save();
		Bukkit.getLogger().info("["+getName()+"] Player '" + plname + "' just joined spawned at " + spawnx + ", " + spawny + ", " + spawnz + "... Magic!");
		return loc;
	}
	
	private Object[] calculus(World world, int xmin, int zmin, int xmax, int zmax) {
		
		int spawnx = (int)((Math.random() * (xmax - xmin)) + xmin);
		int spawnz = (int)((Math.random() * (zmax - zmin)) + zmin);
		
		double sx = spawnx - 0.3;
		double sz = spawnz - 0.3;
		
		int spawny = 127;
		int blokid = -1;
		for(int y = 127; y>4; y--) {
			int blockId = world.getBlockAt(spawnx, y, spawnz).getTypeId();
			if(blockId != 0) {
				blokid = blockId;
				spawny = y+2;
				break;
			}
		}
		
		Object[] l = {sx, spawny, sz, blokid};
		return l;
		
	}
	
	// Get plugin name
	private String getName() {
		return this.getDescription().getName();
	}
			
	// Get plugion version
	private String getVersion() {
		return this.getDescription().getVersion();
	}
			
	// Config version
	private int getConfigVersion() {
		return 1003;
	}

}

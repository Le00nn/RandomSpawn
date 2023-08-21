// Author: Le00nn
// Date: 21/08/2023
// Version: 1.2.2

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
import org.bukkit.event.player.PlayerRespawnEvent;
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
			// Console command.
			
			// /rs
			if(c.matches("rs")) {
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
					// rs set x 
					// rs set x x1 x2
					if(args[1].toLowerCase().equals("x")) {
						if(args[2].matches("[0-9]+")) {
							getConfiguration().setProperty("location.a.x", Integer.parseInt(args[2])); // rs set x x1 ?
						} else {
							Bukkit.getLogger().severe("["+getName(this)+"] Positions must be a valid number.");
						}
						if(args[3].matches("[0-9]+")) {
							getConfiguration().setProperty("location.b.x", Integer.parseInt(args[3])); // rs set x ? x2
						} else {
							Bukkit.getLogger().severe("["+getName(this)+"] Positions must be a valid number.");
						}
					}
					// rs set z 
					// rs set z z1 z2
					if(args[1].toLowerCase().equals("z")) {
						if(args[2].matches("[0-9]+")) {
							getConfiguration().setProperty("location.a.z", Integer.parseInt(args[2])); // rs set z z1 ?
						} else {
							Bukkit.getLogger().severe("["+getName(this)+"] Positions must be a valid number.");
						}
						if(args[3].matches("[0-9]+")) {
							getConfiguration().setProperty("location.b.z", Integer.parseInt(args[3])); // rs set z ? z2
						} else {
							Bukkit.getLogger().severe("["+getName(this)+"] Positions must be a valid number.");
						}
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
			if(c.matches("rs")) {
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
					// /rs set x 
					// /rs set x x1 x2
					if(args[1].toLowerCase().equals("x")) {
						if(args[2].toString().matches("[0-9]+")) {
							getConfiguration().setProperty("location.a.x", Integer.parseInt(args[2])); // /rs set x x1 ?
						} else {
							player.sendMessage(ChatColor.RED + "Positions must be a valid number.");
						}
						if(args[3].toString().matches("[0-9]+")) {
							getConfiguration().setProperty("location.b.x", Integer.parseInt(args[3])); // /rs set x ? x2
						} else {
							player.sendMessage(ChatColor.RED + "Positions must be a valid number.");
						}
					}
					// /rs set z 
					// /rs set z z1 z2
					if(args[1].toLowerCase().equals("z")) {
						if(args[2].toString().matches("[0-9]+")) {
							getConfiguration().setProperty("location.a.z", Integer.parseInt(args[2])); // /rs set z z1 ?
						} else {
							player.sendMessage(ChatColor.RED + "Positions must be a valid number.");
						}
						if(args[3].toString().matches("[0-9]+")) {
							getConfiguration().setProperty("location.b.z", Integer.parseInt(args[3])); // /rs set z ? z2
						} else {
							player.sendMessage(ChatColor.RED + "Positions must be a valid number.");
						}
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
		getConfiguration().load();
		Player player = e.getPlayer();
		String plname = player.getName();
		Location loc = calculatePosition(player, plname);
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
		getConfiguration().load();
		String[] posi = pos.split(",");
		World world = player.getWorld();
		Location loc = new Location(world, Double.parseDouble(posi[0]), Double.parseDouble(posi[1]), Double.parseDouble(posi[2]));
		Bukkit.getLogger().info("["+getName(this)+"] Player '" + plname + "' just died and respawned at " + posi[0] + ", " + posi[1] + ", " + posi[2] + "... Magic!");
		return loc;
		}
	
	// Calculate position to teleport to on first join.
	private Location calculatePosition(Player player, String plname) {
		getConfiguration().load();
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

package org.greenem.plugins.bingo.main;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;
import static org.greenem.plugins.bingo.util.Stash.isSenderAPlayer;
import static org.greenem.plugins.bingo.util.Stash.isSenderAPlayerOrConsole;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.FileUtil;
import org.greenem.plugins.bingo.config.ConfigOperations;
import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.events.BingoEvents;
import org.greenem.plugins.bingo.files.LoadingData;
import org.greenem.plugins.bingo.functionality.BingoOperations;
import org.greenem.plugins.bingo.functionality.GameOperations;
import org.greenem.plugins.bingo.map.GridOperations;
import org.greenem.plugins.bingo.map.NameDifferences;
import org.greenem.plugins.bingo.map.TaskRenderer;
import org.greenem.plugins.bingo.util.DebugModeSwitch;
import org.greenem.plugins.bingo.util.OsDependent;
import org.greenem.plugins.bingo.util.SimpleLogger;

public class Main extends JavaPlugin implements Listener {
	public Plugin plugin = this;

	private static Main instance;
	public static Main getInstance() { // not synchronized, but that's ok
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		log(ProjectInfo.projectName + " plugin has been enabled!"); // ChatColor.GREEN?
		initAll();
		ConfigOperations.updateAndLoadConfig();
		finishInit();
		
		NameDifferences.init();
		/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new BukkitRunnable() {
			@Override
			public void run() {
				GridOperations.listMissingResources();
			}
		});*/
		//BufferedImage im = getFileInsideOfJar("3dTextures/acacia_boat.png");
		/*try {
			ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void onDisable() {
		log(ProjectInfo.projectName + " plugin has been disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = null;
//		String name = sender.getName();
		try {
			p = (Player) sender;
		} catch (ClassCastException e) {}
		
		if(cmd.getName().equalsIgnoreCase("bgstart")){
			if(isSenderAPlayerOrConsole(sender)) {
				try {
					GameOperations.start(sender, args);
					for(Player ap : Bukkit.getOnlinePlayers()) {
						giveAMap(ap);
					}
				} catch (Throwable e) {
					sender.sendMessage(ChatColor.RED + "Произошла ошибка");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bglist")){
			if(isSenderAPlayerOrConsole(sender)) {
				GameOperations.printList(sender, 2);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bglock")){
			if(isSenderAPlayerOrConsole(sender)) {
				GameOperations.lockGame(sender);
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bgtestscan")){
			if(isSenderAPlayerOrConsole(sender)) {
				//GridOperations.scanTexturesNames();
				sender.sendMessage("The command was disabled due to maintanence");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("bgtestmap")){
//			if(isSenderAPlayerOrConsole(sender)) {
//				Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
//					@Override
//					public void run() {
//						test1(sender);
//					}
//				});
//			}
			if(isSenderAPlayer(sender)) giveAMap((Player) sender);
		}
		else if(cmd.getName().equalsIgnoreCase("bgtestresolution")){
			if(isSenderAPlayerOrConsole(sender)) {
				test2(sender, args);
			}
		}
		return true;
	}
	
	public static BufferedImage getFileInsideOfJar(String path) {
		Class<?> clazz = Main.class; // Replace with your class name
		InputStream stream = clazz.getResourceAsStream("/PackedData/" + path);
		//log(stream);

		if (stream == null) {
		    return null;
		}

		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		/*File file = new File(url.getFile());
		log(file.exists());
		return file;*/
	}
    
	private void initAll() {
		initFiles();
		DebugModeSwitch.init();
		References.os = OsDependent.getSystemType();
		registerListeners();
		RegisterAllCommands();
	}
	
	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new BingoEvents(), this);
	}
	
	private void RegisterAllCommands() { // can forget to change command name everywhere and get a bug
//		getCommand("test").setTabCompleter(new TestCompleter());
	}
	
	private void initFiles() {
		ProjectInfo.serverDirectory = Bukkit.getWorldContainer();
		
		ProjectInfo.configsDirectory = new File(ProjectInfo.serverDirectory, "plugins/configs");
		ProjectInfo.pluginConfigDirectory = new File(ProjectInfo.configsDirectory, "Bingo");
		ProjectInfo.pluginConfigDirectory.mkdirs();
		
		ProjectInfo.filesDirectory = new File(ProjectInfo.serverDirectory, "plugins/files");
		ProjectInfo.pluginFilesDirectory = new File(ProjectInfo.filesDirectory, "Bingo");
	}
	
	private void finishInit() {
		BingoOperations.initForbidden();
		BingoOperations.initAllowed();
		LoadingData.loadTask();
		LoadingData.loadProgress();
		if(SimpleLogger.debugOutputEnabled) {
			log(ChatColor.AQUA + "Debug mode is enabled");
		}
	}
	
	private void giveAMap(Player p) {
		GridOperations.generateImage(References.amountOfitems);
//		Bukkit.getScheduler().runTask(this, new Runnable() {
//			@Override
//			public void run() {
				MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0)); // safe???
				view.getRenderers().clear();
				TaskRenderer renderer = new TaskRenderer();
				File f = new File(ProjectInfo.pluginFilesDirectory, "generated.png"); //"D:\\Stuff\\Misc\\For Everything\\MinecraftItemsImages\\generated.png");
				if(renderer.init(f)) {
					view.addRenderer(renderer);
					ItemStack is = new ItemStack(Material.FILLED_MAP);
					MapMeta meta = (MapMeta) is.getItemMeta();
					meta.setMapView(view);
					is.setItemMeta(meta);
					if(p instanceof Player) {
						//Player p = (Player) sender;
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
						p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
						p.getInventory().addItem(is);
					}
				} else {
					debug(ChatColor.RED + "Rendering map view error, can't find the file");
					return;
				}
//			}
//		});
	}
	
	private void test2(CommandSender sender, String[] args) {
		GridOperations.generateTESTImage(args);
		MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0)); // safe???
		view.getRenderers().clear();
		TaskRenderer renderer = new TaskRenderer();
		File f = new File(ProjectInfo.pluginFilesDirectory, "generatedTest.png");
		if(renderer.init(f)) {
			view.addRenderer(renderer);
			ItemStack is = new ItemStack(Material.FILLED_MAP);
			MapMeta meta = (MapMeta) is.getItemMeta();
			meta.setMapView(view);
			is.setItemMeta(meta);
			if(sender instanceof Player) {
				Player p = (Player) sender;
				p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
				p.getInventory().addItem(is);
			}
		} else {
			debug(ChatColor.RED + "Rendering map view error, can't find the file");
			return;
		}
	}
}
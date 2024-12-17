package org.greenem.plugins.bingo.map;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.main.Main;

public class MapViewManager {
	private static Map<String, MapView> playerViews = new HashMap<>();
	private static Map<String, TaskRenderer> playerRenderers = new HashMap<>();
	private static Map<String, Integer> playerViewIds = new HashMap<>();
	
	public static MapView getPlayerMapView(String pName) {
		MapView view = null;
		TaskRenderer renderer = null;
		
		if(playerRenderers.containsKey(pName)) renderer = playerRenderers.get(pName);
		else {
			renderer = new TaskRenderer(pName);
			
			File f = new File(ProjectInfo.pluginFilesDirectory, "generatedMaps/" + pName + ".png");
			if(!renderer.init(f)) {
				debug(ChatColor.RED + "Rendering map view error, can't find the file");
				return null;
			}
		}
		
		if(playerViews.containsKey(pName)) view = playerViews.get(pName);
		else {
			view = Bukkit.createMap(Bukkit.getWorlds().get(0)); // sus out of bounds
			view.getRenderers().clear();
			view.addRenderer(renderer);
		}
		
//		playerRenderers.put(pName, renderer);
//		playerViews.put(pName, view);
		
//		playerViewIds.put(pName, view.getId());
		debug("Created " + view.getId());
		return view;
	}
	
	public static int getPlayerMapId(String pName) {
		if(!playerViewIds.containsKey(pName)) return -1;
		else return playerViewIds.get(pName);
	}
	
	public static void updateMapId(String pName, MapMeta meta) {
		playerViewIds.put(pName, meta.getMapView().getId());
		debug("Setting ID " + playerViewIds.get(pName));
	}
	
	public static void updateMapForPlayer(String pName) {
		File f = new File(ProjectInfo.pluginFilesDirectory, "generatedMaps/" + pName + ".png");
		
		GridOperations.generateImage(References.amountOfitems, pName);
		for(MapRenderer renderer : getPlayerMapView(pName).getRenderers()) {
			if(renderer instanceof TaskRenderer) {
				if(!((TaskRenderer) renderer).init(f)) {
					debug(ChatColor.RED + "Rendering map view error, can't find the file");
				}
				((TaskRenderer) renderer).scheduleRendering();
			}
		}
		
		Player p = Bukkit.getPlayerExact(pName);
		ItemStack itemToModify = Main.findItemToModify(p);
		Main.giveAMap(p, itemToModify);
	}
}

package org.greenem.plugins.bingo.functionality;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.files.SavingData;

import com.google.common.collect.Lists;

public class BingoOperations {
	private static Random rand = new Random(); // System.currentTimeMillis()?
	
	public static ArrayList<Material> allMaterials = new ArrayList<>(Arrays.asList(Material.values()));
	//  new ArrayList<>(Arrays.asList(Material.WATER, Material.ACACIA_BOAT, Material.CAVE_SPIDER_SPAWN_EGG, Material.ANDESITE_STAIRS));
	public static ArrayList<Material> forbidden = new ArrayList<>();
	public static ArrayList<Material> allowed = new ArrayList<>();
	
	public static ArrayList<Material> generateArray(int length) {
		ArrayList<Material> ms = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			int a = 0;
			int r = rand.nextInt(allowed.size());
//			debug(r + " " + allowed.size());
			Material m = allowed.get(r); // includes?
			ms.add(m);
		}
		return ms;
	}
	
	@SuppressWarnings("unchecked")
	public static void initAllowed() {
		allowed = (ArrayList<Material>) allMaterials.clone();
		for (Iterator<Material> it = allowed.iterator(); it.hasNext(); ) {
			Material m = it.next();
			// m.name().toLowerCase().contains("purpur")
			// remove ore blocks?
			// can players use that item?
			// do they need to keep all of them to the end? (config, default: false)
			String lowercase = m.name().toLowerCase();
		    if (forbidden.contains(m) || lowercase.contains("legacy") || lowercase.contains("command") || lowercase.contains("spawn_egg") || lowercase.contains("infested")) { 
		        it.remove();
		    }
		}
	}
	
	public static void initForbidden() {
		forbidden.add(Material.END_PORTAL);
		forbidden.add(Material.END_PORTAL_FRAME);
		forbidden.add(Material.NETHER_PORTAL); // WHAT ABOUT TOUCHING IT
		forbidden.add(Material.WATER);
		forbidden.add(Material.LAVA);
		forbidden.add(Material.FROSTED_ICE);
		forbidden.add(Material.SPAWNER);
		forbidden.add(Material.FIRE);
		forbidden.add(Material.AIR);
		forbidden.add(Material.PISTON_HEAD);
		forbidden.add(Material.MOVING_PISTON);
		forbidden.add(Material.FARMLAND);
		forbidden.add(Material.PUMPKIN_STEM);
		forbidden.add(Material.MELON_STEM);
		forbidden.add(Material.BEDROCK);
		forbidden.add(Material.SNOW);
		forbidden.add(Material.AMETHYST_CLUSTER);
		forbidden.add(Material.CHORUS_PLANT); // think again
		forbidden.add(Material.LIGHT);
		// that portal block from deep dark 1.19 (reinforced deepslate)
	}
	
	public static void checkNewItem(Player p, Material m) {
		if(References.materialList.contains(m)) {
			if(References.playersProgress.get(p.getName())==null) {
				References.playersProgress.put(p.getName(), new ArrayList<>()); 
			}
			boolean hasAlready = References.playersProgress.get(p.getName()).contains(m);
			if(!hasAlready) {
				References.playersProgress.get(p.getName()).add(m);
				if(References.materialList.size()==References.playersProgress.get(p.getName()).size()) {
					for (Player pl : Bukkit.getOnlinePlayers()) {
						pl.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + p.getName() + ChatColor.RESET + "" + ChatColor.GOLD + " has collected all the items!");
					}
					p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Congratulations!", ChatColor.YELLOW + "You have collected all the items!", 4, 50, 30);
				} else {
					p.sendMessage(ChatColor.WHITE + "You have obtained " + ChatColor.GREEN + "" + ChatColor.BOLD + GameOperations.formatStringCase(m.name()) + ChatColor.RESET + "" + ChatColor.WHITE + "!");
					GameOperations.printList(p, 3);
					SavingData.saveProgress(p.getName()); // what if simultaneously??? // TODO
				}
			}
		}
	}
}

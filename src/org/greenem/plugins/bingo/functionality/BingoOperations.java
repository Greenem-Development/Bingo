package org.greenem.plugins.bingo.functionality;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;
import org.greenem.plugins.bingo.config.ConfigData;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.files.SavingData;
import org.greenem.plugins.bingo.map.GridOperations;
import org.greenem.plugins.bingo.map.MapViewManager;

import com.google.common.collect.Lists;

public class BingoOperations {
	private static Random rand = new Random(); // System.currentTimeMillis()?
	
	public static ArrayList<Material> allMaterials = new ArrayList<>(Arrays.asList(Material.values()));
	//  new ArrayList<>(Arrays.asList(Material.WATER, Material.ACACIA_BOAT, Material.CAVE_SPIDER_SPAWN_EGG, Material.ANDESITE_STAIRS));
	public static ArrayList<Material> forbidden = new ArrayList<>();
	public static ArrayList<Material> forbidden1_19 = new ArrayList<>();
	public static ArrayList<Material> forbidden1_17_1_18 = new ArrayList<>();
	public static ArrayList<Material> allowed = new ArrayList<>();
	
	public static ArrayList<Material> generateArray(int length) {
		ArrayList<Material> ms = new ArrayList<>();
		ArrayList<Material> allowedCopy = (ArrayList<Material>) allowed.clone();
		for (int i = 0; i < length; i++) {
			int a = 0;
			int r = rand.nextInt(allowedCopy.size());
//			debug(r + " " + allowed.size());
			Material m = allowedCopy.get(r); // includes?
			ms.add(m);
			allowedCopy.remove(r);
		}
		return ms;
	}
	
	public static void checkMaterialTextures() {
		for (Material m : Material.values()) {
			if(allowed.contains(m)) {
				if(!GridOperations.checkItemImageAvailable(m.name().toLowerCase())) {
					debug(ChatColor.RED + "" + m.name().toLowerCase());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void initAllowed() { // TODO DO WHEN RELOADING CONFIG SOMEHOW!!!
		allowed = (ArrayList<Material>) allMaterials.clone();
		for (Iterator<Material> it = allowed.iterator(); it.hasNext(); ) {
			Material m = it.next();
			// m.name().toLowerCase().contains("purpur")
			// remove ore blocks?
			// can players use that item?
			// do they need to keep all of them to the end? (config, default: false)
			String lowercase = m.name().toLowerCase();
			try { if(forbidden1_19.contains(m)) { it.remove(); } } catch (NoSuchFieldError e) {}
			try { if(forbidden1_17_1_18.contains(m)) { it.remove(); } } catch (NoSuchFieldError e) {}
		    if (forbidden.contains(m) || lowercase.contains("legacy") || lowercase.contains("command") || lowercase.contains("spawn_egg") || lowercase.contains("infested")
		    		 || lowercase.contains("wall_sign")|| lowercase.contains("wall_hanging_sign") || lowercase.contains("wall_banner") || lowercase.contains("wall_fan") || lowercase.contains("candle_cake")
		    		 || lowercase.contains("wall_head") || lowercase.contains("wall_skull") || lowercase.contains("potted") || lowercase.contains("banner_pattern")) { // TODO check for misspelling
		        it.remove();
		    }
		    else if(!ConfigData.allowEndDimensionItems || !ConfigData.allowEndCityItems || !ConfigData.allowDragonEgg) {
		    	if(removeEndItem(m, lowercase)) {
		    		it.remove();
		    	}
		    }
		}
	}
	
	private static void tryToAdd(ArrayList<Material> list, Material m) {
		try { list.add(m); } catch (NoSuchFieldError e) {}
	}
	
	public static void initForbidden1_19() { // TODO what about existing, but only for /give (1.18 sculk)
		//forbidden1_19.add(Material.REINFORCED_DEEPSLATE); // SPECIFIC API VERSION
		
//		tryToAdd(forbidden1_19, Material.ACACIA_HANGING_SIGN);
	}
	
	public static void initForbidden1_17_1_18() {
		forbidden1_17_1_18.add(Material.POWDER_SNOW);
		forbidden1_17_1_18.add(Material.AMETHYST_CLUSTER);
		forbidden1_17_1_18.add(Material.BUDDING_AMETHYST);
		forbidden1_17_1_18.add(Material.LARGE_AMETHYST_BUD);
		forbidden1_17_1_18.add(Material.SMALL_AMETHYST_BUD);
		forbidden1_17_1_18.add(Material.MEDIUM_AMETHYST_BUD);
		forbidden1_17_1_18.add(Material.LIGHT);
		forbidden1_17_1_18.add(Material.BIG_DRIPLEAF_STEM); // others?
		forbidden1_17_1_18.add(Material.BIG_DRIPLEAF);
		forbidden1_17_1_18.add(Material.SMALL_DRIPLEAF);
	}
	
	public static void initForbidden() { // what about renamed
		try { initForbidden1_19(); } catch (NoSuchFieldError e) {}
		try { initForbidden1_17_1_18(); } catch (NoSuchFieldError e) {}
		forbidden.add(Material.END_PORTAL);
		forbidden.add(Material.END_PORTAL_FRAME);
		forbidden.add(Material.END_GATEWAY);
		forbidden.add(Material.NETHER_PORTAL); // WHAT ABOUT TOUCHING IT
		forbidden.add(Material.WATER);
		forbidden.add(Material.LAVA);
		forbidden.add(Material.FROSTED_ICE);
		forbidden.add(Material.SPAWNER);
		forbidden.add(Material.FIRE);
		forbidden.add(Material.SOUL_FIRE);
		forbidden.add(Material.AIR);
		forbidden.add(Material.PISTON_HEAD);
		forbidden.add(Material.MOVING_PISTON);
		forbidden.add(Material.FARMLAND);
		forbidden.add(Material.PUMPKIN_STEM);
		forbidden.add(Material.MELON_STEM);
		forbidden.add(Material.BEDROCK);
		forbidden.add(Material.BARRIER);
		forbidden.add(Material.SNOW);
		forbidden.add(Material.CHORUS_PLANT); // think again
		forbidden.add(Material.STRUCTURE_BLOCK);
		forbidden.add(Material.STRUCTURE_VOID);
//		forbidden.add(Material.LEGACY_BED_BLOCK); //?????
		forbidden.add(Material.PLAYER_WALL_HEAD); // TODO And a bunch of other wall stuff, including corals
		// that portal block from deep dark 1.19 (reinforced deepslate)
		forbidden.add(Material.TRIPWIRE); // what is similar?
		forbidden.add(Material.WEEPING_VINES); // others?
		// nether vines?
		forbidden.add(Material.PETRIFIED_OAK_SLAB); // ?
		forbidden.add(Material.PLAYER_HEAD);
		forbidden.add(Material.BUBBLE_COLUMN);
		forbidden.add(Material.ATTACHED_MELON_STEM);
		forbidden.add(Material.ATTACHED_PUMPKIN_STEM);
		forbidden.add(Material.CAVE_AIR);
		forbidden.add(Material.CAVE_VINES_PLANT);
		forbidden.add(Material.KELP_PLANT);
		forbidden.add(Material.LAVA_CAULDRON);
		forbidden.add(Material.WATER_CAULDRON);
		forbidden.add(Material.POWDER_SNOW_CAULDRON);
		forbidden.add(Material.REDSTONE_WALL_TORCH);
		forbidden.add(Material.SOUL_WALL_TORCH);
		forbidden.add(Material.WALL_TORCH); //wall skull todo
		forbidden.add(Material.TWISTING_VINES_PLANT);
		forbidden.add(Material.WEEPING_VINES_PLANT); // chorus plant?
		forbidden.add(Material.BUNDLE);
//		forbidden.add(Material.WRITTEN_BOOK); // ???
		forbidden.add(Material.KNOWLEDGE_BOOK);
		forbidden.add(Material.JIGSAW); // wtf even is this
//		forbidden.add(Material.BLACK_CANDLE_CAKE); // maybe allow some technical blocks and download textures? (3d, also only when "stand on blocks" is enabled)
		forbidden.add(Material.DEBUG_STICK);
		forbidden.add(Material.CARROTS);
		forbidden.add(Material.POTATOES);
		forbidden.add(Material.BAMBOO_SAPLING); // ?
		forbidden.add(Material.VOID_AIR);
		forbidden.add(Material.DIRT_PATH);
		forbidden.add(Material.FARMLAND);
//		forbidden.add(Material.POTION); // TODO functionality, also other metas
//		forbidden.add(Material.SPLASH_POTION);
//		forbidden.add(Material.LINGERING_POTION);
		
		
		//		forbidden.add(Material.CREEPER_BANNER_PATTERN); // later
		forbidden.add(Material.TIPPED_ARROW); // Functionality later
		forbidden.add(Material.ENCHANTED_BOOK); // Functionality later
	}
	
	public static void checkNewItem(Player p, Material m) {
//		debug(m.name());
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
					MapViewManager.updateMapForPlayer(p.getName());
				} else {
					p.sendMessage(ChatColor.WHITE + "You have obtained " + ChatColor.GREEN + "" + ChatColor.BOLD + GameOperations.formatStringCase(m.name()) + ChatColor.RESET + "" + ChatColor.WHITE + "!");
					SavingData.saveProgress(p.getName()); // TODO what if simultaneously???
					GameOperations.printList(p, 3);
					MapViewManager.updateMapForPlayer(p.getName());
				}
			}
		}
	}
	
	private static boolean removeEndItem(Material m, String name) {
		if(!ConfigData.allowDragonEgg && m==Material.DRAGON_EGG) return true;
		if(!ConfigData.allowEndCityItems && removeEndCityItem(m, name)) return true; // does second part run if the first one is false
		if(!ConfigData.allowEndDimensionItems) {
			if(m==Material.END_CRYSTAL || m==Material.END_ROD || m==Material.END_STONE || m==Material.DRAGON_BREATH || name.contains("chorus") || m==Material.LINGERING_POTION) return true;
		}
		return false;
	}
	
	private static boolean removeEndCityItem(Material m, String name) {
		if(name.contains("shulker") || name.contains("end_stone") || name.contains("purpur")) return true;
		if(m==Material.DRAGON_HEAD || m==Material.ELYTRA) return true;
		return false;
	}
}

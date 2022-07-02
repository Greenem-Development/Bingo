package org.greenem.plugins.bingo.map;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class NameDifferences {
	public static Map<String, String> differences = new HashMap<>();
	
	public static void init() {
		init3DTextures();
	}
	
	private static void init3DTextures() { // recheck
		// boat, armor, filled map, shield, disc (from assets)
		differences.put("lapis_ore", "lapis_lazuli_ore");
		differences.put("deepslate_lapis_ore", "deepslate_lapis_lazuli_ore");
		differences.put("coal_block", "block_of_coal");
		differences.put("raw_iron_block", "block_of_raw_iron");
		differences.put("raw_copper_block", "block_of_raw_copper");
		differences.put("raw_gold_block", "block_of_raw_gold");
		differences.put("amethyst_block", "block_of_amethyst");
		differences.put("iron_block", "block_of_iron");
		differences.put("copper_block", "block_of_copper");
		differences.put("gold_block", "block_of_gold");
		differences.put("diamond_block", "block_of_diamond");
		differences.put("netherite_block", "block_of_netherite"); // difficult
		differences.put("waxed_copper_block", "waxed_block_of_copper");
		differences.put("lapis_block", "block_of_lapis_lazuli");
		differences.put("smooth_quartz", "smooth_quartz_block");
		differences.put("jack_o_lantern", "jack_o'lantern");
		differences.put("vine", "vines");
		differences.put("emerald_block", "block_of_emerald");
		differences.put("quartz_block", "block_of_quartz");
		differences.put("hay_block", "hay_bale");
		differences.put("redstone", "redstone_dust");
		differences.put("redstone_block", "block_of_redstone");
		differences.put("repeater", "redstone_repeater");
		differences.put("comparator", "redstone_comparator");
		differences.put("chest_minecart", "minecart_with_chest");
		differences.put("furnace_minecart", "minecart_with_furnace");
		differences.put("tnt_minecart", "minecart_with_tnt");
		differences.put("hopper_minecart", "minecart_with_hopper");
		differences.put("spruce_boat", "spruce_boat");
		differences.put("birch_boat", "birch_boat"); //search for broken texture names
		differences.put("jungle_boat", "jungle_boat");
		differences.put("acacia_boat", "acacia_boat");
		differences.put("dark_oak_boat", "dark_oak_boat");
		differences.put("jigsaw", "jigsaw_block");
		differences.put("turtle_helmet", "turtle_shell");
		differences.put("quartz", "nether_quartz");
		differences.put("leather_helmet", "leather_cap");
		differences.put("leather_chestplate", "leather_tunic");
		differences.put("leather_leggings", "leather_pants");
//		differences.put("leather_boots", "");
//		differences.put("chainmail_chestplate", "");
//		differences.put("chainmail_leggings", "");
//		differences.put("chainmail_boots", "");
//		differences.put("iron_chestplate", "");
//		differences.put("iron_leggings", "");
//		differences.put("iron_boots", "");
//		differences.put("diamond_chestplate", "");
//		differences.put("diamond_leggings", "");
//		differences.put("diamond_boots", "");
//		differences.put("golden_chestplate", "");
//		differences.put("golden_leggings", "");
//		differences.put("golden_boots", "");
//		differences.put("netherite_chestplate", "");
//		differences.put("netherite_leggings", "");
//		differences.put("netherite_boots", "");
		differences.put("porkchop", "raw_porkchop");
		differences.put("enchanted_golden_apple", "enchanted_golden_apple"); // can be animated
		differences.put("pufferfish_bucket", "bucket_of_pufferfish");
		differences.put("salmon_bucket", "bucket_of_salmon");
		differences.put("cod_bucket", "bucket_of_cod");
		differences.put("tropical_fish_bucket", "bucket_of_tropical_fish");
		differences.put("axolotl_bucket", "bucket_of_axolotl");
		differences.put("slime_ball", "slimeball");
		differences.put("cod", "raw_cod");
		differences.put("salmon", "raw_salmon");
		differences.put("pufferfish", "pufferfish"); //
		differences.put("filled_map", "filled_map"); //
		differences.put("beef", "raw_beef");
		differences.put("cooked_beef", "steak"); // lol
		differences.put("chicken", "raw_chicken");
		differences.put("ender_eye", "eye_of_ender");
		differences.put("experience_bottle", "bottle_o'_enchanting");
		differences.put("written_book", "written_book");
		differences.put("writable_book", "book_and_quill");
//		differences.put("glow_item_frame", "");
		differences.put("map", "empty_map");
		differences.put("rabbit", "raw_rabbit");
		differences.put("rabbit_foot", "rabbit's_foot");
		differences.put("mutton", "raw_mutton");
		differences.put("dragon_breath", "raw_mutton");
//		differences.put("flower_banner_pattern", "");
//		differences.put("creeper_banner_pattern", "");
//		differences.put("skull_banner_pattern", "");
//		differences.put("mojang_banner_pattern", "");
//		differences.put("globe_banner_pattern", "");
//		differences.put("piglin_banner_pattern", "");
	}
}

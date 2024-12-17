package org.greenem.plugins.bingo.map;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class NameDifferences {
	public static Map<String, String> differences = new HashMap<>();
	
	public static void init() {
		initRemappings();
	}
	
	private static void initOfficialAssetRemappings() {
		
	}
	
	private static void initRemappings() { // recheck
		// material.name() to texture name
		
		// Blocks
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
		differences.put("bamboo_block", "block_of_bamboo");
		differences.put("stripped_bamboo_block", "block_of_stripped_bamboo");
		differences.put("redstone_block", "block_of_redstone");
		differences.put("resin_block", "block_of_resin"); // manual
		
		
		
		// Items
//		differences.put("open_eyeblossom", "open_eyeblossom");
//		differences.put("closed_eyeblossom", "eyeblossom");
		differences.put("compass", "compass_28");
		differences.put("recovery_compass", "recovery_compass_28");
		differences.put("clock", "clock_00");
		differences.put("crossbow", "crossbow_standby");
		
		
		// Items
		/*
		differences.put("redstone", "redstone_dust");
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
//		differences.put("piglin_banner_pattern", "");*/
		
		// new versions
//		differences.put("bamboo_block", "block_of_bamboo");
//		differences.put("stripped_bamboo_block", "block_of_stripped_bamboo");
//		differences.put("oak_chest_boat", "oak_boat_with_chest");
//		differences.put("spruce_chest_boat", "spruce_boat_with_chest");
//		differences.put("birch_chest_boat", "birch_boat_with_chest");
//		differences.put("jungle_chest_boat", "jungle_boat_with_chest");
//		differences.put("acacia_chest_boat", "acacia_boat_with_chest");
//		differences.put("cherry_chest_boat", "cherry_boat_with_chest");
//		differences.put("dark_oak_chest_boat", "dark_oak_boat_with_chest");
//		differences.put("pale_oak_chest_boat", "pale_oak_boat_with_chest");
//		differences.put("mangrove_chest_boat", "mangrove_boat_with_chest");
//		differences.put("bamboo_chest_raft", "bamboo_raft_with_chest");
//		differences.put("tadpole_bucket", "bucket_of_tadpole");
//		differences.put("filled_map", "map");
//		differences.put("map", "empty_map");
//		differences.put("potion", "potions");
//		differences.put("music_disc_13", "music_disc_(13)");
//		differences.put("music_disc_cat", "music_disc_(cat)");
//		differences.put("music_disc_blocks", "music_disc_(blocks)");
//		differences.put("music_disc_chirp", "music_disc_(chirp)");
//		differences.put("music_disc_creator", "music_disc_(creator)");
//		differences.put("music_disc_creator_music_box", "music_disc_(creator_(music_box))");
//		differences.put("music_disc_far", "music_disc_(far)");
//		differences.put("music_disc_mall", "music_disc_(mall)");
//		differences.put("music_disc_mellohi", "music_disc_(mellohi)");
//		differences.put("music_disc_stal", "music_disc_(stal)");
//		differences.put("music_disc_strad", "music_disc_(strad)");
//		differences.put("music_disc_ward", "music_disc_(ward)");
//		differences.put("music_disc_11", "music_disc_(11)");
//		differences.put("music_disc_wait", "music_disc_(wait)");
//		differences.put("music_disc_otherside", "music_disc_(otherside)");
//		differences.put("music_disc_relic", "music_disc_(relic)");
//		differences.put("music_disc_5", "music_disc_(5)");
//		differences.put("music_disc_pigstep", "music_disc_(pigstep)");
//		differences.put("music_disc_precipice", "music_disc_(precipice)");
//		differences.put("disc_fragment_5", "disc_fragment");
//		differences.put("netherite_upgrade_smithing_template", "netherite_upgrade");
//		differences.put("sentry_armor_trim_smithing_template", "sentry_armor_trim");
//		differences.put("dune_armor_trim_smithing_template", "dune_armor_trim");
//		differences.put("coast_armor_trim_smithing_template", "coast_armor_trim");
//		differences.put("wild_armor_trim_smithing_template", "wild_armor_trim");
//		differences.put("ward_armor_trim_smithing_template", "ward_armor_trim");
//		differences.put("eye_armor_trim_smithing_template", "eye_armor_trim");
//		differences.put("vex_armor_trim_smithing_template", "vex_armor_trim");
//		differences.put("tide_armor_trim_smithing_template", "tide_armor_trim");
//		differences.put("snout_armor_trim_smithing_template", "snout_armor_trim");
//		differences.put("rib_armor_trim_smithing_template", "rib_armor_trim");
//		differences.put("spire_armor_trim_smithing_template", "spire_armor_trim");
//		differences.put("wayfinder_armor_trim_smithing_template", "wayfinder_armor_trim");
//		differences.put("shaper_armor_trim_smithing_template", "shaper_armor_trim");
//		differences.put("silence_armor_trim_smithing_template", "silence_armor_trim");
//		differences.put("raiser_armor_trim_smithing_template", "raiser_armor_trim");
//		differences.put("host_armor_trim_smithing_template", "host_armor_trim");
//		differences.put("flow_armor_trim_smithing_template", "flow_armor_trim");
//		differences.put("bolt_armor_trim_smithing_template", "bolt_armor_trim");
//		differences.put("oak_wall_hanging_sign", "oak_hanging_sign");
//		differences.put("spruce_wall_hanging_sign", "spruce_hanging_sign");
//		differences.put("birch_wall_hanging_sign", "birch_hanging_sign");
//		differences.put("acacia_wall_hanging_sign", "acacia_hanging_sign");
//		differences.put("cherry_wall_hanging_sign", "cherry_hanging_sign");
//		differences.put("jungle_wall_hanging_sign", "jungle_hanging_sign");
//		differences.put("dark_oak_wall_hanging_sign", "dark_oak_hanging_sign");
//		differences.put("pale_oak_wall_hanging_sign", "pale_oak_hanging_sign");
//		differences.put("mangrove_wall_hanging_sign", "mangrove_hanging_sign");
//		differences.put("crimson_wall_hanging_sign", "crimson_hanging_sign");
//		differences.put("warped_wall_hanging_sign", "warped_hanging_sign");
//		differences.put("bamboo_wall_hanging_sign", "bamboo_hanging_sign");
	}
}

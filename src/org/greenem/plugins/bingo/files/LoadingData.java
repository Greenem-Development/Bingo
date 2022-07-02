package org.greenem.plugins.bingo.files;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.greenem.plugins.bingo.config.ConfigData;
import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.util.GameModeType;

public class LoadingData {
	@SuppressWarnings("unchecked")
	public static void loadTask() {
		if(!ConfigData.saveGameProgress) return;
		File f = new File(ProjectInfo.pluginFilesDirectory, "task.yml");
		YamlConfiguration y = new YamlConfiguration();
		try {
			y.load(f);
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} finally {
			if(y.isSet("materialsList")) {
				ArrayList<String> materialNames = null;
				try {
					materialNames = (ArrayList<String>) y.get("materialsList");
				} catch (ClassCastException e) {
					log(ChatColor.RED + "The save file got corrupted");
					return;
				} finally {
					References.materialList = new ArrayList<>();
					for (int i = 0; i < materialNames.size(); i++) {
						References.materialList.add(Material.valueOf(materialNames.get(i)));
					}
				}
			}
			if(y.isSet("mode")) {
				References.mode = GameModeType.valueOf(y.getString("mode"));
			}
		}
		References.amountOfitems = References.materialList.size();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadProgress() {
		if(!ConfigData.saveGameProgress) return;
		File f = new File(ProjectInfo.pluginFilesDirectory, "progress.yml");
		YamlConfiguration y = new YamlConfiguration();
		try {
			y.load(f);
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} finally {
			if(y.isSet("progress")) {
				MemorySection info = (MemorySection) y.get("progress");
				for (String k : info.getKeys(true)) {
					ArrayList<Material> mList = new ArrayList<>();
					for (String m : (ArrayList<String>) info.get(k)) {
						mList.add(Material.valueOf(m));
					}
					References.playersProgress.put(k, mList);
				}
//				for (String k : info.keySet()) {
//					ArrayList<Material> mList = new ArrayList<>();
//					for (String m : info.get(k)) {
//						mList.add(Material.valueOf(m));
//					}
//					References.playersProgress.put(k, mList);
//				}
			}
		}
	}
}

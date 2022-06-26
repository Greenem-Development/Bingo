package org.greenem.plugins.bingo.files;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.greenem.plugins.bingo.config.ConfigData;
import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.data.References;

public class SavingData {
	public static void saveTask(boolean reset) {
		if(!ConfigData.saveGameProgress) return;
		ProjectInfo.pluginFilesDirectory.mkdirs();
		File f = new File(ProjectInfo.pluginFilesDirectory, "task.yml");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		YamlConfiguration y = new YamlConfiguration();
		try {
			y.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} finally {
			String[] names = new String[References.materialList.size()];
			for (int i = 0; i < names.length; i++) {
				names[i] = References.materialList.get(i).name();
			}
			y.set("materialsList", names);
			if(!y.isSet("mode") || reset) {
				y.set("mode", References.mode.toString());
			}
			try {
				y.save(f);
			} catch (IOException e) {
				e.printStackTrace(); // TODO print errors or not
			}
		}
	}
	
	public static void saveProgress(String pName) {
		if(!ConfigData.saveGameProgress) return;
		ProjectInfo.pluginFilesDirectory.mkdirs();
		File f = new File(ProjectInfo.pluginFilesDirectory, "progress.yml");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		YamlConfiguration y = new YamlConfiguration();
		try {
			y.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} finally {
			ArrayList<String> mList = new ArrayList<>();
			for (Material m : References.playersProgress.get(pName)) {
				mList.add(m.name());
			}
			y.set("progress." + pName, mList);
			try {
				y.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void resetProgress() {
		if(!ConfigData.saveGameProgress) return;
		ProjectInfo.pluginFilesDirectory.mkdirs();
		File f = new File(ProjectInfo.pluginFilesDirectory, "progress.yml");
		if(f.exists()) {
			YamlConfiguration y = new YamlConfiguration();
			try {
				y.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

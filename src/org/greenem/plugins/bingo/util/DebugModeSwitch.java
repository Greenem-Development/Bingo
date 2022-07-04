package org.greenem.plugins.bingo.util;

import java.io.File;

import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.map.GridOperations;

public class DebugModeSwitch {
	public static void init() {
		File f = new File(ProjectInfo.pluginConfigDirectory, "enableDebug.dev");
		if(!f.exists()) {
			SimpleLogger.debugOutputEnabled = false;
			GridOperations.drawDebugGrid = false;
		}
	}
}

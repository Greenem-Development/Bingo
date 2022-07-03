package org.greenem.plugins.bingo.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.greenem.plugins.bingo.util.GameModeType;
import org.greenem.plugins.bingo.util.OsType;

public class References {
	public static OsType os;
	public static GameModeType mode = GameModeType.LINEAR;
	public static ArrayList<Material> materialList;
	public static Map<String, ArrayList<Material>> playersProgress = new HashMap<>();
	public static boolean inGame = false;
	public static int amountOfitems;
	public static BufferedImage gridMainImage;
}

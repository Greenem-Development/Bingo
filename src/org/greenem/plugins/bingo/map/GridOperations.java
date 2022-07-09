package org.greenem.plugins.bingo.map;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.util.FileUtil;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.functionality.BingoOperations;
import org.greenem.plugins.bingo.util.StandartUtils;

public class GridOperations {
	public static boolean drawDebugGrid = false;
	
	private static int betweenItems = 5;
	private static int imageResolution = 16; // 16 - default, 25 - max for 5*5, also can try 32
	private static DecimalFormat kDf = new DecimalFormat("0.0");
	
	public static Map<Material, String> nameComparison;
	
	public static void generateImage(int items) { // TODO maybe custom modes of the map? Like open in front of everything + esc
		int[] grid = generateLayout(items);
		int width = grid[0];
		int height = getGridHeight(grid);
		int atTheSide = 10;
		int x = 2 * atTheSide + getSideSize(width, imageResolution, betweenItems) - betweenItems;
		int y = 2 * atTheSide + getSideSize(height, imageResolution, betweenItems) - betweenItems;
		int max = x;
		if(y>x) max = y;
		float k = 1;
		int betweenItems0 = betweenItems;
		int block0 = imageResolution;
		boolean hasAlreadyRenderedFirstRow = false;
		boolean hasAlreadyRenderedLastRow = false;
		if(max>65 && max<128) {
//			k = (int) Math.floor((float) 128 / max);
			k = java.lang.Float.valueOf(kDf.format((double) 128/max).replace(",", "."));
			max = 128;
			int amountOfItems = getMaxGridAmount(grid);
			boolean found = false;
			a1 : while (!found) {
				debug(max);
				for(int i = 0; i < 20; i++) {
					for(int j = -1; j < 2; j+=2) {
						y = i * j;
						int totalBetween = (int) (max - (2*atTheSide + amountOfItems*(block0*k + y)));
						if(totalBetween % (amountOfItems-1) == 0) {
							int newBetween = totalBetween / (amountOfItems-1);
							debug(newBetween);
							if(newBetween >= betweenItems0) {
								betweenItems0 = newBetween;
								block0 = (int) (block0*k + y);
								found = true;
								break a1;
							}
						}
					}
				}
				max++;
			}
			debug(atTheSide + " " + block0 + " " + betweenItems0 + " " + k);
//			int result = 2*atTheSide + amountOfItems*block0 + (amountOfItems-1)*betweenItems0;
//			debug(result == max);
		}
		
		BufferedImage target = new BufferedImage(max, max, BufferedImage.TYPE_INT_ARGB); // what about width <= 0 ?
		Graphics2D g = (Graphics2D) target.getGraphics();
		//g.setColor(Color.GRAY);
		//g.fillRect(0, 0, target.getWidth(), target.getHeight()); // (0,0) is at the top left // TODO what about getting "g" width and height?
		BufferedImage background = null;
		try {
			background = ImageIO.read(new File("G:\\for everything\\5\\хрень\\bgTest1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		g.drawImage(background, 0, 0, max, max, null);
		
		BufferedImage itemBackground = null;
		try {
			itemBackground = ImageIO.read(new File("G:\\for everything\\5\\хрень\\itemBgTest1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if(drawDebugGrid) {
		g.setColor(Color.GREEN);
			g.fillRect(0, 0, atTheSide, max);
			g.fillRect(0, 0, max, atTheSide);
			g.fillRect(max-atTheSide, 0, atTheSide, max);
			g.fillRect(0, max-atTheSide, max, atTheSide);
		}
		
		int counter = 0;
		int lastNext = 0;
		boolean drawYellow = false;
		for(int i = 0; i < grid.length; i++) {
			int offset = (target.getWidth() - (2 * atTheSide + getSideSize(grid[i], block0, betweenItems0)  - betweenItems0)) / 2;
			
			if(drawDebugGrid) {
				int next = getHowManySameDown(grid, i);
				g.setColor(Color.YELLOW);
				drawYellow = lastNext <= next;
				if(drawYellow) {
					g.fillRect(0, atTheSide + i*betweenItems0 + block0*(i), atTheSide + offset, block0*(next+1)+betweenItems0*next);
					g.fillRect(max-(atTheSide + offset)-1, atTheSide + i*betweenItems0 + block0*(i), offset+atTheSide+1, block0*(next+1)+betweenItems0*next);
				}
				lastNext = next;
			}
			
			for(int j = 0; j < grid[i]; j++) {
				BufferedImage texture = null;
				try {
					texture = ImageIO.read(new File("G:\\for everything\\5\\хрень\\unknownTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String theName = References.materialList.get(counter).name().toLowerCase();
				if(NameDifferences.differences.containsKey(theName)) {
					theName = NameDifferences.differences.get(theName);
				}
				
				try {
					texture = ImageIO.read(new File("G:\\for everything\\5\\хрень\\3dTextures\\" + theName + ".png")); // G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way2Output\\
				} catch (IOException e) {
					debug(ChatColor.RED + "Unable to load " + ChatColor.WHITE + References.materialList.get(counter).name().toLowerCase() + ChatColor.RED + " texture");
				}
				
				int imgMaxDim = texture.getHeight();
				if(texture.getWidth() > imgMaxDim) imgMaxDim = texture.getWidth();
				BufferedImage temp = new BufferedImage(imgMaxDim, imgMaxDim, BufferedImage.TYPE_INT_ARGB);
				Graphics2D tempG = (Graphics2D) temp.getGraphics();
				if(texture.getHeight()>texture.getWidth()) {
					tempG.drawImage(texture, imgMaxDim/2-texture.getWidth()/2, 0, null);
				} else {
					tempG.drawImage(texture, 0, imgMaxDim/2-texture.getHeight()/2, null);
				}
				
				int curX = atTheSide + getSideSize(j, block0, betweenItems0) + offset;
				int curY = atTheSide + getSideSize(i, block0, betweenItems0);
				g.drawImage(itemBackground, curX-2, curY-2, block0+4, block0+4, null);
				g.drawImage(temp, curX, curY, block0, block0, null);
				
				if(drawDebugGrid) {
					if(!hasAlreadyRenderedFirstRow && i==0) {
						g.fillRect(curX, 0, block0*grid[i] + betweenItems0*(grid[i]-1), atTheSide);
						hasAlreadyRenderedFirstRow = true;
					}
					
					if(!hasAlreadyRenderedLastRow && i==grid.length-1) {
						g.fillRect(curX, max-atTheSide, block0*grid[i] + betweenItems0*(grid[i]-1), atTheSide);
						hasAlreadyRenderedLastRow = true;
					}
					
					g.setColor(Color.RED);
					g.drawRect(atTheSide + getSideSize(j, block0, betweenItems0) + offset + block0, atTheSide + getSideSize(i, block0, betweenItems0) + block0 - ((int)Math.ceil(block0/2))-1, betweenItems0, 1); // ceil? (and why it works like that)
					g.drawRect(atTheSide + getSideSize(j, block0, betweenItems0) + offset + block0 - ((int)Math.ceil(block0/2))-1, atTheSide + getSideSize(i, block0, betweenItems0) + block0, 1, betweenItems0);
				}
				counter++;
			}
		}
		
//		BufferedImage finalImage = target;
//		if(max>65 && max<118) {
//			BufferedImage scaled = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
//			Graphics2D gS = (Graphics2D) scaled.getGraphics();
//			gS.drawImage(target, 0, 0, 128, 128, null);
//			finalImage = scaled; // what if a bit earlier? (link variable)
//			debug("scaled");
//		}
		References.gridMainImage = target;
		File outputfile = new File("G:\\for everything\\5\\хрень\\generated.png");
		try {
			ImageIO.write(target, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateTESTImage(String[] args) { // TODO maybe custom modes of the map? Like open in front of everything + esc
		if(args.length==0) return;
		if(!StandartUtils.isInteger(args[0])) return;
		int fillResolution = Integer.parseInt(args[0]); // 99;
		int block = 16;
		Material m = Material.DEAD_FIRE_CORAL_BLOCK;
		BufferedImage texture = null;
		String theName = m.name().toLowerCase();
		try {
			texture = ImageIO.read(new File("G:\\for everything\\5\\хрень\\3dTextures\\" + theName + ".png")); // G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way2Output\\
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage target = new BufferedImage(fillResolution, fillResolution, BufferedImage.TYPE_INT_ARGB); // what about width <= 0 ?
		Graphics2D g = (Graphics2D) target.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, target.getWidth(), target.getHeight()); // (0,0) is at the top left // TODO what about getting "g" width and height?
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 16, 16);
//		g.fillRect(target.getWidth()-atTheSide, target.getHeight()-atTheSide, atTheSide, atTheSide);
		g.drawImage(texture, 0, 0, block, block, null);
		File outputfile = new File("G:\\for everything\\5\\хрень\\generatedTest.png");
		try {
			ImageIO.write(target, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getHowManySameDown(int[] grid, int current) {
		int counter = 0;
		for (int i = current+1; i < grid.length; i++) {
			if(grid[i]==grid[current]) counter++;
			else break;
		}
		return counter;
	}
	
	private static int getSideSize(int amount, int block, int between) {
		int result = block * amount;
		if(amount>0) result += between * (amount);
		return result;
	}
	
	private static int getGridHeight(int[] grid) {
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			if(grid[i]!=0) counter++;
		}
		return counter;
	}
	
	private static int getMaxGridAmount(int[] grid) {
		int max = grid.length;
		for (int i = 0; i < grid.length; i++) {
			if(grid[i]>max) max = grid[i];
		}
		return max;
	}
	
	public static int[] generateLayout(int cells) {
//		ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
		int base = (int) Math.ceil(Math.sqrt(cells));
		
		int[] grid = new int[base];
		for (int i = 0; i < grid.length; i++) {
			grid[i] = base;
		}
		
		int currently = base * base;
		
		while(currently > cells) { // && currently - cells > getRightCells(grid))
			if(getRightCells(grid)>=grid[grid.length - 1]) {
				removeMostBottomRight(grid);
			} else {
				grid[grid.length - 1]--;
			}
			currently--;
		}
		return grid;
		
//		System.out.println("Layout for " + cells + " squares:");
//		for (int i = 0; i < grid.length; i++) {
//			System.out.println("* ".repeat(grid[i]));
//		}
	}
	
	private static int getRightCells(int[] grid) {
		int max = 0;
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			if(grid[i] == grid[0]) {
				counter++;
			}
		}
		return counter;
	}
	
	private static void removeMostBottomRight(int[] grid) {
		for(int i = grid.length-1; i >= 0; i--) {
			if(grid[i] == grid[0]) {
				grid[i]--;
				return;
			}
		}
	}
	
	public static String sortWords(String s) {
		String[] arr = s.split("_");
		Arrays.sort(arr);
		String result = "";
		for(int i = 0; i < arr.length; i++) {
			result += arr[i];
		}
		return result;
	}
	
	public static void scanTexturesNames() { //TODO WTF
		nameComparison = new HashMap<>();
		File dir = new File("G:\\for everything\\5\\хрень\\3dTextures");
		File output = new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way2Output");
		File testOutput = new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way2OutputTest");
		output.mkdirs();
		testOutput.mkdirs();
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
//		String lastSubstr = "testtesttest";
		int cNotFound = 0;
		for(Material m : BingoOperations.allowed) {
			String mNameOriginal = m.name().toLowerCase();
			String mNameSotred = sortWords(mNameOriginal);
			boolean found = false;
			for(int i = 0; i < files.length; i++) {
				File texture = files[i];
				String tNameOriginal = texture.getName().substring(0,texture.getName().length()-4);
				String tNameSorted = sortWords(tNameOriginal);
				if(mNameSotred.equals(tNameSorted)) {
					nameComparison.put(m, tNameSorted);
					found = true;
					break;
				}
			}
			if(!found) {
				debug(ChatColor.RED + "Didn't find: " + mNameOriginal);
				cNotFound++;
			}
		}
		for(Entry<Material, String> entry : nameComparison.entrySet()) {
			debug(ChatColor.GREEN + "" + entry.getKey().name().toLowerCase() + "  -  " + entry.getValue());
		}
		debug(cNotFound + " " + nameComparison.size());
//		for(int i = 0; i < files.length; i++) {
//			File texture = files[i];
//			String tName = sortWords(texture.getName().substring(0,texture.getName().length()-4));
//			if(tName.startsWith(lastSubstr)) {
//				continue;
//			}
//			for(Material m : BingoOperations.allowed) {
//				String name = sortWords(m.name().toLowerCase());
//				if(tName.equals(name)) {
//					lastSubstr = name;
//					saveAndCrop(texture, output, m.name().toLowerCase());
////					FileUtil.copy(texture, new File(output, name + ".png"));
////					debug("1: " + name);
//					break;
//				} else if(tName.contains(name)) {
//					lastSubstr = name;
////					FileUtil.copy(texture, new File(testOutput, name + ".png"));
//					saveAndCrop(texture, testOutput, m.name().toLowerCase());
////					debug("2:   " + tName + " |->| " + name);
//					break;
//				} else {
//					//System.out.println("3!!!!: " + tName + " " + name);
//				}
//			}
//		}
	}
	
	private static void saveAndCrop(File image, File destination, String name) {
		BufferedImage original = null;
		boolean gray = image.getName().contains("leaves") && !image.getName().contains("azalea");
		try { original = ImageIO.read(image); } catch (IOException e) { e.printStackTrace(); }
		
		if((original.getWidth()==16 && original.getHeight()!=16)) { // || gray
			BufferedImage target = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) target.getGraphics();
			g.drawImage(original, 0, 0, null);
			debug(image.getName());
			if(gray) {
				g.setXORMode(new Color(0,200,0));
				g.setColor(new Color(0,200,0));
	            Double rect = new Rectangle2D.Double(0, 0, 16, 16);
	            g.fill(rect);
				
//				debug("123");
//				Color darkOliveGreen = new Color(85, 107, 47);
//				g.setColor(darkOliveGreen);
//				g.fillRect(0, 0, original.getWidth(), original.getHeight());
			}
			try {
				ImageIO.write(target, "png", new File(destination, name + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if(!(original.getWidth()==16 && original.getHeight()==16)) {
//				try {
////					debug(image.getCanonicalPath());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			FileUtil.copy(image, new File(destination, name + ".png"));
		}
	}
	
	public static void listMissingResources() {
//		String s = "";
		BufferedImage texture = null;
		for (Material m : BingoOperations.allowed) {
			String theName = m.name().toLowerCase();
			if(NameDifferences.differences.containsKey(theName)) {
				theName = NameDifferences.differences.get(theName); 
			}
			
			try {
				texture = ImageIO.read(new File("G:\\for everything\\5\\хрень\\3dTextures\\" + theName + ".png")); // G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way2Output\\
			} catch (IOException e) {
//				s += "differences.put(\"" + m.name().toLowerCase() + "\", \"\");\n";
				debug(ChatColor.RED + "Missing texture for " + ChatColor.WHITE + m.name().toLowerCase());
			}
		}
//		debug(s);
	}
}

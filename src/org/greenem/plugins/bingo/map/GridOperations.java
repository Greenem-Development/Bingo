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
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.util.FileUtil;
import org.greenem.plugins.bingo.data.References;
import org.greenem.plugins.bingo.functionality.BingoOperations;

public class GridOperations {
	private static int betweenItems = 5;
	
	public static String sortWords(String s) {
		String[] arr = s.split("_");
		Arrays.sort(arr);
		String result = "";
		for(int i = 0; i < arr.length; i++) {
			result += arr[i];
		}
		return result;
	}
	
	public static void scanTexturesNames() {
		File dir = new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\allNeeded");
		File output = new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\output");
		File testOutput = new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\outputTest");
		output.mkdirs();
		testOutput.mkdirs();
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
		String lastSubstr = "testtesttest";
		for(int i = 0; i < files.length; i++) {
			File texture = files[i];
			String tName = sortWords(texture.getName().substring(0,texture.getName().length()-4));
			if(tName.startsWith(lastSubstr)) {
				continue;
			}
			for(Material m : BingoOperations.allowed) {
				String name = sortWords(m.name().toLowerCase());
				if(tName.equals(name)) {
					lastSubstr = name;
					saveAndCrop(texture, output, m.name().toLowerCase());
//					FileUtil.copy(texture, new File(output, name + ".png"));
//					debug("1: " + name);
					break;
				} else if(tName.contains(name)) {
					lastSubstr = name;
//					FileUtil.copy(texture, new File(testOutput, name + ".png"));
					saveAndCrop(texture, testOutput, m.name().toLowerCase());
//					debug("2:   " + tName + " |->| " + name);
					break;
				} else {
					//System.out.println("3!!!!: " + tName + " " + name);
				}
			}
		}
	}
	
	private static void saveAndCrop(File image, File destination, String name) {
		BufferedImage original = null;
		boolean gray = image.getName().contains("leaves") && !image.getName().contains("azalea");
		try { original = ImageIO.read(image); } catch (IOException e) { e.printStackTrace(); }
		
		if((original.getWidth()==16 && original.getHeight()!=16) || gray) {
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
	
	public static void generateImage(int items) { // TODO maybe custom modes of the map? Like open in front of everything + esc
		// and load back to References TODO
		int[] grid = generateLayout(items);
		int width = grid[0];
		int height = getGridHeight(grid);
		int atTheSide = 10;
		BufferedImage target = new BufferedImage(2 * atTheSide + getSideSize(width) - betweenItems, 2 * atTheSide + getSideSize(height) - betweenItems, BufferedImage.TYPE_INT_ARGB); // what about width <= 0 ?
		Graphics2D g = (Graphics2D) target.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, target.getWidth(), target.getHeight()); // (0,0) is at the top left // what about getting "g" width and height? //TODO
		g.setColor(Color.RED);
		g.fillRect(0, 0, atTheSide, atTheSide);
		g.fillRect(target.getWidth()-atTheSide, target.getHeight()-atTheSide, atTheSide, atTheSide);
		int counter = 0;
		for(int i = 0; i < grid.length; i++) {
			int offset = (target.getWidth() - (2 * atTheSide + getSideSize(grid[i])  - betweenItems)) / 2;
			for(int j = 0; j < grid[i]; j++) {
				BufferedImage texture = null;
				try {
					texture = ImageIO.read(new File("G:\\for everything\\5\\хрень\\unknownTexture.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					texture = ImageIO.read(new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way1Output\\" + References.materialList.get(counter).name() + ".png"));
				} catch (IOException e) {
//					e.printStackTrace();
					try {
						texture = ImageIO.read(new File("G:\\Minecraft Stuff\\Resources\\VanillaDefault+1.19\\assets\\minecraft\\textures\\way1OutputTest\\" + References.materialList.get(counter).name() + ".png"));
					} catch (IOException e1) {
//						e1.printStackTrace();
//						debug(ChatColor.RED + "Unable to load " + ChatColor.WHITE + References.materialList.get(counter).name().toLowerCase() + ChatColor.RED + " texture");
					}
				}
//				debug(offset + " " + getSideSize(j) + " " + getSideSize(i));
				g.drawImage(texture, atTheSide + getSideSize(j) + offset, atTheSide + getSideSize(i), null);
				counter++;
			}
		}
		File outputfile = new File("G:\\for everything\\5\\хрень\\genarated.png");
		try {
			ImageIO.write(target, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getSideSize(int amount) {
		int result = 16 * amount;
		if(amount>0) result += betweenItems * (amount);
		return result;
	}
	
	private static int getGridHeight(int[] grid) {
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			if(grid[i]!=0) counter++;
		}
		return counter;
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
}

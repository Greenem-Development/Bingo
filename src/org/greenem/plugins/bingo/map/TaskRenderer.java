package org.greenem.plugins.bingo.map;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.greenem.plugins.bingo.data.ProjectInfo;
import org.greenem.plugins.bingo.main.Main;

public class TaskRenderer extends MapRenderer {
	private BufferedImage img = null;
	private boolean done;
	private File file = null;
	
	public TaskRenderer(String pName) {
//		super();
		scheduleRendering();
	}
	
	public void scheduleRendering() {
		done = false;
	}
	
	public TaskRenderer setFile(File f) {
		this.file = f;
		return this;
	}
	
	private boolean loadImage(File f) {
		if(f == null) return false;
		try {
			img = ImageIO.read(f);
			img = MapPalette.resizeImage(img);
			debug("Reloaded the image");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean init() {
		return loadImage(this.file);
	}
	
//	private boolean testSwitch = true;
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		if(done) return;
		
		debug("Rendering for " + player.getName());
		
		if(this.img == null) init();
		
//		if(testSwitch) loadImage(this.file);
//		else this.img = Main.getFileInsideOfJar("unknownTexture.png"); // loadImage(new File(ProjectInfo.pluginFilesDirectory, "generated.png"));
//		if(testSwitch) testSwitch = false;
		
		view.setTrackingPosition(false);
//		view.setScale(Scale.FARTHEST);
		canvas.drawImage(0, 0, img);
		done = true;
	}
}

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

public class TaskRenderer extends MapRenderer {
	private BufferedImage img;
	private boolean done;
	
	public TaskRenderer(String pName) {
//		super();
		scheduleRendering();
	}
	
	public void scheduleRendering() {
		done = false;
	}
	
	public boolean init(File f) {
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
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		if(done) return;
		
		debug("Rendering for " + player.getName());
		
		view.setTrackingPosition(false);
//		view.setScale(Scale.FARTHEST);
		canvas.drawImage(0, 0, img);
		done = true;
	}
}

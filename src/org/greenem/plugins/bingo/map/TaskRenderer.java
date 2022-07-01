package org.greenem.plugins.bingo.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class TaskRenderer extends MapRenderer {
	private BufferedImage img;
	private boolean done;
	
	public TaskRenderer() {
		done = false;
	}
	
	public boolean init(File f) {
		try {
			img = ImageIO.read(f);
			img = MapPalette.resizeImage(img);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		if(done) return;
		
		view.setTrackingPosition(false);
//		view.setScale(Scale.FARTHEST);
		canvas.drawImage(0, 0, img);
		done = true;
	}
}

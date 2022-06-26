package org.greenem.plugins.bingo.events;

import static org.greenem.plugins.bingo.util.SimpleLogger.debug;
import static org.greenem.plugins.bingo.util.SimpleLogger.log;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.greenem.plugins.bingo.functionality.BingoOperations;

public class BingoEvents implements Listener {
	@EventHandler
	public void onInventoryUpdate(InventoryClickEvent e) {
		if(e.getCurrentItem()==null) return;
		Material m = e.getCurrentItem().getType();
		BingoOperations.checkNewItem((Player) e.getWhoClicked(), m);
//		debug("1: " + m);
	}
	
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Material m = e.getItem().getItemStack().getType();
			BingoOperations.checkNewItem((Player) e.getEntity(), m);
//			debug("2: " + m);
		}
	}
	
	@EventHandler
	public void onArrowPickup(PlayerPickupArrowEvent e) {
		Material m = e.getItem().getItemStack().getType();
		BingoOperations.checkNewItem(e.getPlayer(), m);
//		debug("3: " + m);
	}
	
	@EventHandler
	public void onDispencerArmorPickup(BlockDispenseArmorEvent e) {
		if(e.getTargetEntity() instanceof Player) {
			Material m = e.getItem().getType();
			BingoOperations.checkNewItem((Player) e.getTargetEntity(), m);
//			debug("4: " + m);
		}
	}
}
